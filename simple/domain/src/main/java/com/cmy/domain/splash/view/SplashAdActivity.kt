package com.cmy.domain.splash.view

import com.cmy.common.ad.BaseSplashAdActivity
import com.cmy.domain.R
import com.cmy.domain.gank.GankActivity

class SplashAdActivity : BaseSplashAdActivity() {

    override fun getSplashImage(): Int {
        return R.drawable.splash
    }

    override fun getTargetActivity(): Class<*>? {
        return GankActivity::class.java
    }
}