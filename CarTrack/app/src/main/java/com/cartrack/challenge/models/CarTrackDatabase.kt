package com.cartrack.challenge.models

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cartrack.challenge.models.CarTrackDatabase.Companion.VERSION
import com.cartrack.challenge.models.dao.CustomerDao
import com.cartrack.challenge.models.dao.UserDao

@Database(entities = [User::class, Customer::class], version = VERSION)
abstract class CarTrackDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun customerDao(): CustomerDao

    companion object {
        const val VERSION = 3
        const val DB_NAME = "CARTRACK_DATABASE"
    }
}