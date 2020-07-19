package com.cartrack.challenge.network.services

import com.cartrack.challenge.models.User
import io.reactivex.Flowable
import retrofit2.http.GET

interface UserService {

    @GET("/users")
    fun getUsers(): Flowable<List<User>>
}