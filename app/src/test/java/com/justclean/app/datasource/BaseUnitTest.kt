/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.datasource

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.justclean.app.datasource.di.postDetailsFragVm
import com.justclean.app.datasource.di.testApp
import com.justclean.app.shared.util.SharedPref
import org.junit.Before
import org.junit.Rule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.test.AutoCloseKoinTest
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

open class BaseUnitTest : AutoCloseKoinTest() {

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        startKoin {
            androidContext(Mockito.mock(Context::class.java))
            printLogger(Level.DEBUG)
            modules(listOf(testApp,postDetailsFragVm))
            declareMock<SharedPref> {

            }
        }
    }


    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val rule2 = RxSchedulersOverrideRule()

}