package com.cartrack.challenge.views.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.room.EmptyResultSetException
import com.cartrack.challenge.base.BaseViewModel
import com.cartrack.challenge.models.CarTrackDatabase
import com.cartrack.challenge.models.Customer
import com.cartrack.challenge.repository.UserRepository
import com.cartrack.challenge.utils.StringUtils
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repository: UserRepository) : BaseViewModel() {
    val isLoggedIn = MutableLiveData<Boolean>()

    fun loginUser(username: String, password: String) {
        disposables.add(repository.login(username, password)
            .doOnSubscribe { loading.value = true }
            .doAfterTerminate { loading.value = false }
            .subscribe({
                isLoggedIn.value = it != null
            }, {
                if (it is EmptyResultSetException) {
                    errorCode.value = CarTrackDatabase.EMPTY_RESULT_SET
                }
            })
        )
    }

    fun initializeDb() {
        val customer = Customer(
            USERNAME, StringUtils.md5(PASSWORD), "Philippines"
        )

        disposables.add(repository.insertCustomer(customer)
            .doOnSubscribe { loading.value = true }
            .doOnTerminate { loading.value = false }
            .subscribe({
                Log.d("Insert", "inserted")
            }, {
                Log.e("Error", "error : ${it.message}")
            })
        )
    }

    companion object {
        const val USERNAME = "admin"
        const val PASSWORD = "password"
    }
}