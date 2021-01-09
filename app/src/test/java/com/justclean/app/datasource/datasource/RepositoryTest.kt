/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.datasource.datasource

import com.justclean.app.datasource.di.testApp
import com.justclean.app.shared.network.ApiRepository
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject

class RepositoryTest : AutoCloseKoinTest() {

    val repository by inject<ApiRepository>()

    @Before
    fun before() {
        startKoin { modules(testApp) }
    }


    @Test
    fun testCachedSearch() {

        val events1 = repository.getPosts().blockingFirst()
        val events2 = repository.getPosts().blockingFirst()
        assertEquals(events1, events2)
    }

    @Test
    fun testGetPostsSuccess() {
        repository.getPosts().test()
        val test = repository.getPosts().test()
        test.awaitTerminalEvent()
        test.assertComplete()
    }

    @Test
    fun testGetPostsFailed() {
        val test = repository.getPosts().test()
        test.awaitTerminalEvent()
        test.assertValue { list -> list.isEmpty() }
    }
}