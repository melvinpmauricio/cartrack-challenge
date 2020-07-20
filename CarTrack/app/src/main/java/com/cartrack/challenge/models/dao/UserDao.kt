package com.cartrack.challenge.models.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.cartrack.challenge.models.User
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao {

    @RawQuery
    fun getAllUsersDB(query: SupportSQLiteQuery): Single<List<User>?>

    @Query(
        "SELECT * FROM ${User.TABLE_NAME} WHERE " +
                "${User.ID} = :id " +
                "LIMIT 1"
    )
    fun getUser(id: Long): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(users: List<User>): Completable

    @Query("DELETE FROM ${User.TABLE_NAME}")
    fun deleteUsers() : Completable

}