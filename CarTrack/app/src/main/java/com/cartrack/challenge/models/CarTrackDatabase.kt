package com.cartrack.challenge.models

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cartrack.challenge.models.CarTrackDatabase.Companion.VERSION
import com.cartrack.challenge.models.dao.UserDao

@Database(entities = [User::class], version = VERSION)
abstract class CarTrackDatabase : RoomDatabase() {

    abstract fun UserDao(): UserDao

    companion object {
        const val VERSION = 1
        const val DB_NAME = "CARTRACK_DATABASE"
    }
}