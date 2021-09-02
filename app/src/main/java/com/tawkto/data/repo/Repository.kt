package com.tawkto.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.greenlight.data.remote.api.ApiService
import com.tawkto.data.local.db.AppDatabase
import com.tawkto.data.local.entities.UserDetails
import com.tawkto.data.local.entities.Users
import com.tawkto.data.remote.models.GithubProjectListDataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

class Repository(val db: AppDatabase, val api: ApiService) {

//    var users: LiveData<List<Users>> = emptyList()
    lateinit var  users: LiveData<List<Users>>
    lateinit var userDetails: LiveData<UserDetails>

    fun getRemoteRecord(scope: Int) {


        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getUsersResponse(scope).execute().body()
                insertUsersRecords(response!!)
            }catch (e: Exception) {
                println("Exception: $e")
            }
        }
    }

    fun getRemoteRecordUserDetails(login: String) {


        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getUserDetailsResponse(login).execute().body()
                insertUsersDetailsRecord(response!!)
            }catch (e: Exception) {
                println("Exception: $e")
            }
        }
    }

    fun insertUsersRecords(response: GithubProjectListDataResponse) {

        GlobalScope.launch(Dispatchers.IO) {
            db.usersDoa().insertUsers(response)
        }

    }

    fun insertUsersDetailsRecord(userDetails: UserDetails) {

        GlobalScope.launch(Dispatchers.IO) {
            db.usersDoa().insertUserDetails(userDetails)
        }

    }

    suspend fun getUsers(): LiveData<List<Users>> {

        withContext(Dispatchers.IO) {
            try {
                users = db.usersDoa().getUsers()
            } catch (e: Exception) {
                println(e)
            }
        }
        return users
    }


    suspend fun getUserDetails(login: String): LiveData<UserDetails> {

        withContext(Dispatchers.IO) {
            try {
                userDetails = db.usersDoa().getUserDetails(login)
            } catch (e: Exception) {
                println(e)
            }
        }
        return userDetails
    }

    fun getUserDetailsUpdate(userDetails: UserDetails): Int {

        var updateVal:Int = -1

        GlobalScope.launch(Dispatchers.IO) {
            try {
                updateVal = db.usersDoa().getUserDetailsUpdate(userDetails)
                var user = db.usersDoa().getUser(userDetails.id!!)
                user.notes = userDetails.notes
                val userUpdate = db.usersDoa().getUserUpdate(user)
                println("userUpdate=$user")
            } catch (e: Exception) {
                println(e)
            }
        }
        return updateVal
    }


}