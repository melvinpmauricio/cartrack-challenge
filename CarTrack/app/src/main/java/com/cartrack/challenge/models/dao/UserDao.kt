package com.cartrack.challenge.models.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.cartrack.challenge.models.User

@Dao
interface UserDao {

    @Query("SELECT * FROM ${User.TABLE_NAME} ORDER BY ${User.ID} ASC")
    fun getAllUsersDB(): LiveData<List<User>>

}