package com.tawkto.data.local.doa

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tawkto.data.local.entities.UserDetails
import com.tawkto.data.local.entities.Users


@Dao
interface UsersDao {

//    @Query("SELECT * FROM Users where territory like :region and area <> 'NA' ORDER BY id desc")
    @Query("SELECT * FROM Users order by id asc")
    fun getUsers(): LiveData<List<Users>>

    @Query("SELECT * FROM Users where id=:id")
    fun getUser(id: Int): Users

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUsers(users: List<Users>)

    @Query("SELECT * FROM UserDetails where login=:login")
    fun getUserDetails(login: String): LiveData<UserDetails>

    @Update
    fun getUserDetailsUpdate(userDetails: UserDetails): Int

    @Update
    fun getUserUpdate(user: Users): Int

    @Query("update Users set notes=:notes where id=:id and login=:login")
    fun getUserUpdate(notes: String, login: String, id: Int): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUserDetails(userDetails: UserDetails)

}