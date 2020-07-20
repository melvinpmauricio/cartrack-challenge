package com.cartrack.challenge.views.userlist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cartrack.challenge.base.BaseViewModel
import com.cartrack.challenge.models.User
import com.cartrack.challenge.repository.UserRepository
import javax.inject.Inject

class UserListViewModel @Inject constructor(private val repository: UserRepository) :
    BaseViewModel() {
    val users = MutableLiveData<List<User>>()

    fun getUsers() {
        disposables.add(repository.getUsers()
            .doOnSubscribe { loading.value = true }
            .doOnTerminate { loading.value = false }
            .subscribe({
                it?.let { listUsers ->
                    insertUsers(listUsers)
                    users.value = listUsers
                }
            }, {
                Log.e("Error", "error : ${it.message}")
            })
        )
    }

    private fun insertUsers(users: List<User>) {
        disposables.add(
            repository.insertUsers(users)
                .subscribe()
        )
    }

    fun sortUsers(category: String, order: String) {
        disposables.add(
            repository.getUsersDb(category, order)
                .subscribe({
                    it?.let { sortedUsers ->
                        users.value = sortedUsers
                    }
                }, {
                    Log.e("Error", "Unable to get Users from db : ${it.message}")
                })
        )
    }
}