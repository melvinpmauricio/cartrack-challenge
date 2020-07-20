package com.cartrack.challenge.di

import com.cartrack.challenge.views.login.LoginActivity
import com.cartrack.challenge.views.userlist.UserListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributesLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    internal abstract fun contributesUserListActivity(): UserListActivity

}