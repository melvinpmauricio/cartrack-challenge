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
    val isDeleted = MutableLiveData<Boolean>()
    val isLoadMore = MutableLiveData<Boolean>()

    fun getUsers(count: Int) {
        disposables.add(repository.getUsers(count, PAGINATION_LIMIT)
            .doOnSubscribe { loading.value = true }
            .doOnTerminate { loading.value = false }
            .subscribe({
                it?.let { listUsers ->
                    insertUsers(listUsers)
                    users.value = listUsers
                    isLoadMore.value = listUsers.size == PAGINATION_LIMIT
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

    fun deleteUsers() {
        disposables.add(
            repository.deleteUsers()
                .subscribe({
                    isDeleted.value = true
                    Log.d("deleteUsers", "Users deleted")
                }, {
                    Log.e("Error", "Unable to delete Users from db : ${it.message}")
                })
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

    companion object {
        const val PAGINATION_LIMIT = 7
    }
}