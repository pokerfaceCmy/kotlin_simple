package com.cmy.kotlinsimple

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils
import com.cmy.common.repository.LoadingDialog
import com.cmy.common.repository.remote.HttpManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var s: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val http: HttpManager = HttpManager.Builder().setBaseUrl("https://gank.io").build()
        LogUtils.d(http.mBaseUrl)
        LogUtils.d(http.mDefaultErrorMsg)
        LogUtils.d(::s.isLateinit)
        s = "1"
        LogUtils.d(::s.isLateinit)
        button.setOnClickListener {
//            val ratingDialog = RatingDialog(this)
//            ratingDialog.show()
            val loadingDialog = LoadingDialog(this)
            loadingDialog.show()
        }
    }
}