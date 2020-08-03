package com.cmy.domain.gank

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cmy.common.repository.remote.HttpManager
import com.cmy.domain.R


class GankActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gank)

        HttpManager.composeRequest(
            HttpManager.create(GankContract.Model::class.java).getGankGirls()
        ).subscribe(
            { Log.d("TAG", it.toString()) },
            { Log.d("TAG", it.toString()) }
        )

    }
}