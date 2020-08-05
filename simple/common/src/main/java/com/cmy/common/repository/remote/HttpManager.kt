package com.cmy.common.repository.remote

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * @author pokerfaceCmy
 * @date 2020/7/29
 * @description 网络数据请求
 * @email cheng.meng.yuan@qq.com
 */
open class HttpManager private constructor() {
    private var mBaseUrl: String = "https://www.baidu.com/"
    private var mDefaultErrMsg: String = "服务器开小差了"
    private var mOkHttpBuilder: OkHttpClient.Builder = generateOkHttpBuilder()
    private var mRetrofitBuilder: Retrofit.Builder = generateRetrofitBuilder(mBaseUrl)

    companion object {
        @Volatile
        private var INSTANCE: HttpManager? = null

        //双重校验锁的单例模式
        fun getInstance(): HttpManager {
            if (INSTANCE == null) {
                synchronized(HttpManager::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = HttpManager()
                    }
                }
            }
            return INSTANCE!!
        }

        @JvmStatic
        fun <T> create(cls: Class<T>): T {
            return getInstance().mRetrofitBuilder.build()
                .create(cls)
        }

        fun <T> composeRequest(observable: Observable<T>): Observable<T> {
            return observable.compose(ioToMain())
        }

        private fun <T> ioToMain(): ObservableTransformer<T, T>? {
            return ObservableTransformer { upstream: Observable<T> ->
                upstream
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }

    }


    fun setBaseUrl(baseUrl: String): HttpManager {
        getInstance().mBaseUrl = baseUrl
        return getInstance()
    }

    fun setDefaultErrMsg(defaultErrMsg: String): HttpManager {
        getInstance().mDefaultErrMsg = defaultErrMsg
        return this
    }

    fun setOkHttpBuilder(builder: OkHttpClient.Builder): HttpManager {
        getInstance().mOkHttpBuilder = builder
        return this
    }

    fun setRetrofitBuilder(builder: Retrofit.Builder): HttpManager {
        getInstance().mRetrofitBuilder = builder
        return this
    }

    fun build() {

    }


    /**
     * 创建OkHttpBuilder
     */
    private fun generateOkHttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

    }

    /**
     * 创建RetrofitBuilder
     */
    private fun generateRetrofitBuilder(baseUrl: String): Retrofit.Builder {
        return Retrofit.Builder()
            .client(mOkHttpBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(baseUrl)
    }

}