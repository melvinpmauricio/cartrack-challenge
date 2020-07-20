package com.cartrack.challenge.repository

import androidx.sqlite.db.SimpleSQLiteQuery
import com.cartrack.challenge.models.Customer
import com.cartrack.challenge.models.User
import com.cartrack.challenge.models.dao.CustomerDao
import com.cartrack.challenge.models.dao.UserDao
import com.cartrack.challenge.network.services.UserService
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val customerDao: CustomerDao,
    private val userService: UserService
) {

    fun getUsers(start: Int, limit: Int): Flowable<List<User>> {
        return userService.getUsers(start, limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getUsersDb(category: String, order: String): Single<List<User>?> {
        val query = "SELECT * FROM ${User.TABLE_NAME} ORDER BY $category $order"
        val statement = SimpleSQLiteQuery(query)

        return userDao.getAllUsersDB(statement)
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

    fun insertUsers(users: List<User>): Completable {
        return userDao.insertUsers(users)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }

    fun deleteUsers(): Completable {
        return userDao.deleteUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}