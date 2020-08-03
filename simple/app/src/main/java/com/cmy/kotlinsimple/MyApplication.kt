package com.cmy.kotlinsimple

import android.app.Application
import com.cmy.common.repository.remote.HttpManager


open class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initHttp()
    }

    private fun initHttp() {
        HttpManager.getInstance()
            .setBaseUrl("https://gank.io/api/")
            .build()

    }


}