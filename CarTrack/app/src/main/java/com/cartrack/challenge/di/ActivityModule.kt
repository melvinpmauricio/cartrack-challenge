package com.cartrack.challenge.di

import com.cartrack.challenge.views.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributesLoginActivity(): LoginActivity

}