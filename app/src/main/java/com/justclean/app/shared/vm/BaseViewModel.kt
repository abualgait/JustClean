/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.vm


import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.justclean.app.shared.data.DataManager
import com.justclean.app.shared.databases.DBRepository
import com.justclean.app.shared.factory.FavRepositoryFactory
import com.justclean.app.shared.network.ApiRepository
import com.justclean.app.shared.network.NetworkHelper
import com.justclean.app.shared.rx.SchedulerProvider
import com.justclean.app.shared.ui.view.BaseView
import com.justclean.app.shared.util.SharedPref
import com.justclean.app.shared.util.io.app.MyApp
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel(dm: DataManager,val networkHelper: NetworkHelper) : ViewModel() {

    lateinit var view: BaseView<*, *>
    var pref: SharedPref = dm.pref
    var api: ApiRepository = dm.api
    var database: DBRepository = dm.database
    var scheduler: SchedulerProvider = dm.schedulerProvider
    var favRepositoryFactory: FavRepositoryFactory = dm.favRepositoryFactory


    val disposables = CompositeDisposable()

    fun isConnected() = networkHelper.isNetworkConnected()



    fun launch(job: () -> Disposable) {
        disposables.add(job())
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    protected fun activity(): FragmentActivity? {
        return view.activity()
    }


    protected fun getString(@StringRes res: Int): String {
        return MyApp.context.getString(res)
    }


}
