package com.tawkto.data.local.db

import android.content.Context
import android.util.Log
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.tawkto.data.local.doa.UsersDao
import com.tawkto.data.local.entities.UserDetails
import com.tawkto.data.local.entities.Users

@Database(
    entities = arrayOf(
        Users::class, UserDetails::class
    ), version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private val LOG_TAG: String = AppDatabase::class.simpleName.toString()
        private val LOCK: Any = Object()
        private val DATABSE_NAME: String = "myDB"

        @Volatile
        private var sInstance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (sInstance == null) {
                synchronized(LOCK) {
                    Log.d(LOG_TAG, "Creating new database instance")
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, AppDatabase.DATABSE_NAME
                    )
                        .build()
                }
            }
            Log.d(LOG_TAG, "Getting the database instance")
            return sInstance!!
        }

    }


    abstract fun usersDoa(): UsersDao

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("not implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("not implemented")
    }

    override fun clearAllTables() {
        TODO("not implemented")
    }
}