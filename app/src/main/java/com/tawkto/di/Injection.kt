package com.greenlight.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.greenlight.data.remote.api.ApiClient
import com.tawkto.data.local.db.AppDatabase
import com.tawkto.data.repo.Repository
import com.tawkto.ui.viewmodel.factory.ViewModelMainActivityFactory
import com.tawkto.ui.viewmodel.factory.ViewModelUserDetailsActivityFactory


object Injection {

    var repo: Repository? = null

    //repo provider
    private fun provideRepository(context: Context): Repository {
        val database = AppDatabase.getInstance(context)
        val api = ApiClient.api

        if (repo == null) {
            synchronized(Repository::class.java) {
                if (repo == null) {
                    repo = Repository(database, api)
                }
            }
        }
        return repo!!
    }

    // main activity viewmodel provider
    fun provideMainActivityViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelMainActivityFactory(
            provideRepository(
                context
            )
        )
    }

    // userdetails activity viewmodel provider
    fun provideUserDetailsActivityViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelUserDetailsActivityFactory(
            provideRepository(
                context
            )
        )
    }


}