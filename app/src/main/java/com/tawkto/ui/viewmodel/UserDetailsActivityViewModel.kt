package com.tawkto.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tawkto.data.local.entities.UserDetails
import com.tawkto.data.local.entities.Users
import com.tawkto.data.remote.models.GithubProjectListDataResponse
import com.tawkto.data.repo.Repository

class UserDetailsActivityViewModel(private val repo: Repository) : ViewModel() {

    var user: LiveData<UserDetails>? = null

    fun getRemoteRecord(login: String) {
        repo.getRemoteRecordUserDetails(login)
    }

    suspend fun getUserDetails(login: String): LiveData<UserDetails> {
        if (user == null) {
            try {
                user = repo.getUserDetails(login)
            } catch (e: Exception) {
                println(e)
            }
        }
        return user!!
    }

    fun getUserDetailsUpdate(userDetails: UserDetails): Int {

        var updateVal = -1
            try {
                updateVal = repo.getUserDetailsUpdate(userDetails)
            } catch (e: Exception) {
                println(e)
            }
        return updateVal
    }


}