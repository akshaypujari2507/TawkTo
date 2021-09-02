package com.tawkto.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Users", indices = [Index(value = ["id","login"], unique = true)])
class Users {
    @PrimaryKey(autoGenerate = true)
    var mIdUsers: Int? = null
    var id: Int? = null
    var login: String? = null
    var avatar_url: String? = null
    var html_url: String? = null
    var type: String? = null
    var notes: String? = null

    override fun equals(other: Any?): Boolean {
        return mIdUsers == other
    }

    override fun hashCode(): Int {
        return mIdUsers!!
    }

    override fun toString(): String {
        return "Users(mIdUsers=$mIdUsers, Id=$id, login=$login, avatar_url=$avatar_url, html_url=$html_url, type=$type, notes=$notes)"
    }
}