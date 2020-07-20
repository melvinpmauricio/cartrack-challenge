package com.cartrack.challenge.repository

import com.cartrack.challenge.models.Customer
import com.cartrack.challenge.models.User
import com.cartrack.challenge.models.dao.CustomerDao
import com.cartrack.challenge.network.services.UserService
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val customerDao: CustomerDao,
    private val userService: UserService
) {

    fun getUsers(): Flowable<List<User>> {
        return userService.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun insertCustomer(customer: Customer): Completable {
        return customerDao.insertCustomer(customer)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun login(username: String, password: String): Single<Customer?> {
        return customerDao.checkCustomerCredential(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}