package com.tawkto.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "UserDetails", indices = [Index(value = ["id","login","name"], unique = true)])
class UserDetails {
    @PrimaryKey(autoGenerate = true)
    var mIdUserDetails: Int? = null
    var id: Int? = null
    var login: String? = null
    var avatar_url: String? = null
    var html_url: String? = null
    var name: String? = null
    var company: String? = null
    var blog: String? = null
    var location: String? = null
    var email: String? = null
    var created_at: String? = null
    var updated_at: String? = null
    var notes: String? = null
    var followers: String? = null
    var following: String? = null

    override fun equals(other: Any?): Boolean {
        return mIdUserDetails == other
    }

    override fun hashCode(): Int {
        return mIdUserDetails!!
    }

    override fun toString(): String {
        return "UserDetails(mIdUserDetails=$mIdUserDetails, Id=$id, login=$login, avatar_url=$avatar_url, html_url=$html_url, " +
                "name=$name, company=$company, blog=$blog, location=$location, email=$email, " +
                "created_at=$created_at, updated_at=$updated_at, notes=$notes, followers=$followers, following=$following)"
    }
}