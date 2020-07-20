package com.cartrack.challenge

import android.widget.ProgressBar
import com.cartrack.challenge.di.DaggerAppComponent
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.impl.RefreshFooterWrapper
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

    companion object {
        init {
            SmartRefreshLayout.setDefaultRefreshHeaderCreater { context, layout ->
                layout.setPrimaryColorsId(R.color.colorAccent, android.R.color.white)
                MaterialHeader(context)

            }
            SmartRefreshLayout.setDefaultRefreshFooterCreater { context, layout ->
                RefreshFooterWrapper(ProgressBar(context,null, android.R.attr.progressBarStyleSmall))
            }
        }
    }
}