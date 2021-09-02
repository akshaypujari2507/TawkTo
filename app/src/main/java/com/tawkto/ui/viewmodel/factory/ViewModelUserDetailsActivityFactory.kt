package com.tawkto.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tawkto.data.repo.Repository
import com.tawkto.ui.viewmodel.UserDetailsActivityViewModel

class ViewModelUserDetailsActivityFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDetailsActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserDetailsActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}