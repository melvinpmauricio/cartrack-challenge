package com.cartrack.challenge.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = User.TABLE_NAME)
data class User(
    @PrimaryKey
    @ColumnInfo(name = ID)
    val id: Long,

    @ColumnInfo(name = NAME)
    val name: String,

    @ColumnInfo(name = USERNAME)
    val username: String,

    @ColumnInfo(name = EMAIL)
    val email: String,

    @Embedded
    val address: Address,

    @ColumnInfo(name = PHONE)
    val phone: String,

    @ColumnInfo(name = WEBSITE)
    val website: String,

    @Embedded
    val company: Company
) {
    companion object {
        const val TABLE_NAME = "USERS"
        const val ID = "ID"
        const val NAME = "NAME"
        const val USERNAME = "USERNAME"
        const val EMAIL = "EMAIL"
        const val ADDRESS = "ADDRESS"
        const val PHONE = "PHONE"
        const val WEBSITE = "WEBSITE"
        const val COMPANY = "COMPANY"
    }
}

data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    @Embedded
    val geo: GeoLocation
)
data class GeoLocation(
    val lat: Float,
    val lng: Float
)

data class Company(
    @ColumnInfo(name = "COMPANY_NAME")
    val name: String,
    val catchPhrase: String,
    val bs: String
)
