package com.cartrack.challenge.network.services

import com.cartrack.challenge.models.User
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("/users")
    fun getUsers(
        @Query("_start") pageStart: Int,
        @Query("_limit") pageLimit: Int
    ): Flowable<List<User>>
}