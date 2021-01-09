/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.ui.navhostactivity

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.justclean.app.R
import com.justclean.app.databinding.ActivityNavHostBinding
import com.justclean.app.shared.data.model.Status
import com.justclean.app.shared.ui.activity.BaseActivity
import com.justclean.app.shared.util.setupWithNavController
import org.koin.android.viewmodel.ext.android.viewModel


class NavHostActivity : BaseActivity<NavHostActivityVm, ActivityNavHostBinding>() {

    private var currentNavController: LiveData<NavController>? = null
    override val vm: NavHostActivityVm by viewModel()
    override var layoutId: Int = R.layout.activity_nav_host

    companion object {

        fun startActivity(
            mActivity: Activity?,
            toCart: Boolean = false,
            withflag: Boolean = false
        ) {

            val mIntent = Intent(mActivity, NavHostActivity::class.java)
            if (withflag)
                mIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_NEW_TASK
            mActivity!!.startActivity(mIntent)
        }

    }

    override fun doOnCreate() {
        setTheme(R.style.AppTheme_NoActionBar) //Set AppTheme before setting content view.
        super.doOnCreate()
        setupBottomNavigationBar()

        handleObservers()
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networdStateReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(networdStateReceiver)
    }

    private val networdStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION == intent.action) {
                if (vm.isConnected()) {
                    Log.e("connected","true")
                    vm.syncFavourites()
                }
            }
        }
    }


    private fun handleObservers() {

        vm.response.observe(this, Observer { resource ->
            when (resource.status) {
                Status.COMPLETE -> {
                }
                Status.SUCCESS -> {
                }
                Status.ERROR -> showErrorMessage(resource.message)
                Status.LOADING -> {
                }
                Status.OFFLINE -> {
                }
            }
        })
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {

        val navGraphIds = listOf(
            R.navigation.post,
            R.navigation.favourite
        )

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = binding.bottomNav.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            NavigationUI.setupWithNavController(binding.toolbar, navController)
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }


}



