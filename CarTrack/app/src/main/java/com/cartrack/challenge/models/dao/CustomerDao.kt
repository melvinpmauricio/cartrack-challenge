package com.cartrack.challenge.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cartrack.challenge.models.Customer
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CustomerDao {
    @Query(
        "SELECT * FROM ${Customer.TABLE_NAME} WHERE " +
                "${Customer.USERNAME} = :username AND " +
                "${Customer.PASSWORD} = :password LIMIT 1"
    )
    fun checkCustomerCredential(username: String, password: String): Single<Customer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomer(customer: Customer): Completable
}