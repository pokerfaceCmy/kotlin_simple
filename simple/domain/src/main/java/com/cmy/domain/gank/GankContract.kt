package com.cmy.domain.gank

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface GankContract {
    interface Model {
        @GET("v2/data/category/Girl/type/Girl/page/1/count/10")
        fun getGankGirls(): Observable<Girl>
    }
}