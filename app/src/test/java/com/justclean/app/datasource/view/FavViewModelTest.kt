/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.datasource.view


import android.content.Context
import androidx.lifecycle.Observer
import androidx.room.Room
import com.justclean.app.datasource.BaseUnitTest
import com.justclean.app.shared.data.model.Resource
import com.justclean.app.shared.data.model.favourite.FavouriteItem
import com.justclean.app.shared.databases.AppDatabase
import com.justclean.app.shared.interfaces.FavouriteItemDao
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class FavViewModelTest : BaseUnitTest() {


    private var database: AppDatabase? = null
    private var dao: FavouriteItemDao? = null


    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        database = Room.inMemoryDatabaseBuilder(
            (Mockito.mock(Context::class.java)),
            AppDatabase::class.java
        )
            .allowMainThreadQueries().build()

        dao = database?.favItemDao()


    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        database!!.close()
    }


    @Test
    fun testFavsListIsNotEmpty() {
        dao?.insertItems(
            listOf(
                FavouriteItem("body1", 1, "title"),
                FavouriteItem("body2", 2, "title2")
            )
        )?.blockingAwait()
        dao?.getItems()?.test()?.assertValue { list ->
            list.isNotEmpty()
        }
    }

    @Test
    fun deleteTest() {
        val favouriteItem =    FavouriteItem("body1", 1, "title")

        dao?.insertItem(favouriteItem)?.blockingAwait()

        dao?.getItems()?.subscribe({
            dao?.deleteItem(it[0])?.blockingAwait()
        }, { throwable -> throwable.printStackTrace() })

        dao?.getItems()?.test()?.assertValue {
            it.isEmpty()
        }
    }
}