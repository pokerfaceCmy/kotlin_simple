package com.cmy.common.repository.remote

import com.blankj.utilcode.util.LogUtils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


/**
 * @author pokerfaceCmy
 * @date 2020/7/29
 * @description 网络数据请求
 * @email cheng.meng.yuan@qq.com
 */
class HttpManager private constructor() {
    private lateinit var mRetrofitBuilder: Retrofit.Builder
    private lateinit var mOkHttpBuilder: OkHttpClient.Builder
    private lateinit var mRetrofit: Retrofit

    // TODO: 2020/7/30 获取BaseUrl,DefaultErrorMsg,tag

    //静态内部类实现线程安全的单例模式
    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = HttpManager()
    }


    /**
     * 创建OkHttpBuilder
     */
    private fun generateOkHttpBuilder(): OkHttpClient.Builder {
        mOkHttpBuilder = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
        LogUtils.dTag("mOkHttpBuilder的hash值", mOkHttpBuilder.hashCode())
        return mOkHttpBuilder

    }

    /**
     * 创建RetrofitBuilder
     */
    private fun generateRetrofitBuilder(): Retrofit.Builder {
        mRetrofitBuilder = Retrofit.Builder()

        LogUtils.dTag("mRetrofitBuilder的hash值", mRetrofitBuilder.hashCode())
        return mRetrofitBuilder
    }

    fun <T> create(cls: Class<T>): T {
        return generateRetrofitBuilder().build().create(cls)
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