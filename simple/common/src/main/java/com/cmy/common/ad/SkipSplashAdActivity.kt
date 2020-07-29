package com.cmy.common.ad

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cmy.common.R

/**
 * @author pokerfaceCmy
 * @date 2020/7/29
 * @description 点击跳过开屏广告后展示的activity
 * @email cheng.meng.yuan@qq.com
 */
class SkipSplashAdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skip_splash_ad)
    }
}