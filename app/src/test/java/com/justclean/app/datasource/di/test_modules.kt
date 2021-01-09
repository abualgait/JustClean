/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.datasource.di


import com.justclean.app.datasource.util.TestSchedulerProvider
import com.justclean.app.shared.koin.appModule

import org.koin.dsl.module


val testRxModule = module { single { TestSchedulerProvider() } }

val testApp = appModule