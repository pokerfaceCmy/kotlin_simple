package com.cmy.common.repository.remote

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


/**
 * @author pokerfaceCmy
 * @date 2020/7/29
 * @description 网络数据请求
 * @email cheng.meng.yuan@qq.com
 */
open class HttpManager private constructor(
    var mBaseUrl: String,
    var mDefaultErrorMsg: String,
    var mOkHttpBuilder: OkHttpClient.Builder,
    var mRetrofitBuilder: Retrofit.Builder
) {

    private constructor(builder: Builder) : this(
        builder.baseUrl,
        builder.defaultErrorMsg,
        builder.okHttpBuilder,
        builder.retrofitBuilder
    )


    class Builder {
        lateinit var baseUrl: String
            private set
        lateinit var defaultErrorMsg: String
            private set
        lateinit var okHttpBuilder: OkHttpClient.Builder
            private set
        lateinit var retrofitBuilder: Retrofit.Builder
            private set

        fun setBaseUrl(baseUrl: String) = apply { this.baseUrl = baseUrl }
        fun setDefaultErrorMsg(defaultErrorMsg: String) =
            apply { this.defaultErrorMsg = defaultErrorMsg }

        fun setOkHttpBuilder(okHttpBuilder: OkHttpClient.Builder) =
            apply { this.okHttpBuilder = okHttpBuilder }

        fun setRetrofitBuilder(retrofitBuilder: Retrofit.Builder) =
            apply { this.retrofitBuilder = retrofitBuilder }

        fun build(): HttpManager {
            return HttpManager(
                baseUrl,
                if (!::defaultErrorMsg.isLateinit) defaultErrorMsg else "服务器开小差啦",
                if (!::okHttpBuilder.isLateinit) okHttpBuilder else generateOkHttpBuilder(),
                if (!::retrofitBuilder.isLateinit) retrofitBuilder else generateRetrofitBuilder(
                    baseUrl
                )
            )
        }

        /**
         * 创建OkHttpBuilder
         */
        private fun generateOkHttpBuilder(): OkHttpClient.Builder {
            return OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
        }

        /**
         * 创建RetrofitBuilder
         */
        private fun generateRetrofitBuilder(baseUrl: String): Retrofit.Builder {
            return Retrofit.Builder().baseUrl(baseUrl)
        }
    }

    fun getUrl(): String {
        return mBaseUrl
    }

    fun <T> create(cls: Class<T>): T {
        return mRetrofitBuilder
            .client(mOkHttpBuilder.build())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(cls)
    }

    fun <T> composeRequest(observable: Observable<T>): Observable<T> {
        return observable.compose(composeApi())
    }

    private fun <T> composeApi(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable: Observable<T> ->
            observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}