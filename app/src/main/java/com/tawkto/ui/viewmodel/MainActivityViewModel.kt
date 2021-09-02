package com.tawkto.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tawkto.data.local.entities.Users
import com.tawkto.data.remote.models.GithubProjectListDataResponse
import com.tawkto.data.repo.Repository

class MainActivityViewModel(private val repo: Repository) : ViewModel() {

//    var remoteRecords: LiveData<GithubProjectListDataResponse>? = null
    var users: LiveData<List<Users>>? = null

/*
    fun getRemoteRecord(scope: Int): LiveData<GithubProjectListDataResponse> {
        if (remoteRecords == null) {
            remoteRecords = repo.getRemoteRecord(scope)
        }
        return remoteRecords!!
    }
*/
    fun getRemoteRecord(scope: Int) {
            repo.getRemoteRecord(scope)
    }

    suspend fun getUsers(): LiveData<List<Users>> {
        if (users == null) {
            try {
                users = repo.getUsers()
            } catch (e: Exception) {
                println(e)
            }
        }
        return users!!
    }


}