package com.cmy.kotlinsimple


import android.content.Intent
import android.os.Bundle
import com.cmy.common.BaseActivity
import com.cmy.common.ad.BaseSplashAdActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button.setOnClickListener {
            showLoadingDialog()
        }

        startActivity(Intent(this, BaseSplashAdActivity::class.java))
    }
}