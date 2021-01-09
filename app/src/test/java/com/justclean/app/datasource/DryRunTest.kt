/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.datasource

import com.justclean.app.datasource.di.testApp
import org.junit.Test
import org.koin.core.context.startKoin

class DryRunTest : BaseUnitTest() {

    @Test
    fun testConfiguration() {
        startKoin { modules(testApp) }
    }

}