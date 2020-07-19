package com.cartrack.challenge

import com.cartrack.challenge.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class Application : DaggerApplication() {
    private val appComponent = DaggerAppComponent.builder()
        .application(this)
        .build()

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }
}