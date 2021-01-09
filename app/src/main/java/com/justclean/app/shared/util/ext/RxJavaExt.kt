/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.util.ext

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import com.justclean.app.shared.rx.SchedulerProvider


/**
 * Use SchedulerProvider configuration
 */
fun <T> Single<T>.with(schedulerProvider: SchedulerProvider): Single<T> =
    observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())

fun Completable.with(schedulerProvider: SchedulerProvider): Completable =
    observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())

fun Completable.withDB(schedulerProvider: SchedulerProvider): Completable =
    observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.computation())

fun <T> Observable<T>.with(schedulerProvider: SchedulerProvider): Observable<T> =
    observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())

fun <T> Observable<T>.withDB(schedulerProvider: SchedulerProvider): Observable<T> =
    observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.computation())


