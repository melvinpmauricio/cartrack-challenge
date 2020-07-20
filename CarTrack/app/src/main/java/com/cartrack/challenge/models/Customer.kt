package com.cartrack.challenge.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cartrack.challenge.models.Customer.Companion.TABLE_NAME

/**
 * This Data class will be use as Login Credential
 */
@Entity(tableName = TABLE_NAME)
data class Customer(
    @PrimaryKey
    @ColumnInfo(name = USERNAME)
    val username: String,

    @ColumnInfo(name = PASSWORD)
    val password: String,

    @ColumnInfo(name = COUNTRY)
    val country: String
) {
    companion object {
        const val TABLE_NAME = "CUSTOMER"
        const val USERNAME = "USERNAME"
        const val PASSWORD = "PASSWORD"
        const val COUNTRY = "COUNTRY"
    }
}