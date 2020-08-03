package com.cmy.kotlinsimple

import android.app.Application
import com.bytedance.sdk.openadsdk.TTAdConfig
import com.bytedance.sdk.openadsdk.TTAdSdk
import com.cmy.common.repository.remote.HttpManager
import timber.log.Timber


open class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initLog()
        initHttp()
        initAd()
    }

    private fun initLog() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    /**
     * 初始化OkHttp+Retrofit的网络请求框架
     * setBaseUrl() 以及 build()方法 必须调用
     */
    private fun initHttp() {
        HttpManager.getInstance()
            .setBaseUrl("https://gank.io/api/")
//            .setDefaultErrMsg()
//            .setOkHttpBuilder()
//            .setRetrofitBuilder()
            .build()
    }

    /**
     * 初始化广告
     */
    private fun initAd() {
        TTAdSdk.init(
            this,
            TTAdConfig.Builder()
                .appId("5090690")
                .appName("全能压缩")
                .build()
        )
    }

}