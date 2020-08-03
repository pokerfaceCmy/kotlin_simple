package com.cmy.common.ad

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.sdk.openadsdk.AdSlot
import com.bytedance.sdk.openadsdk.TTAdNative
import com.bytedance.sdk.openadsdk.TTAdSdk
import com.bytedance.sdk.openadsdk.TTSplashAd
import com.cmy.common.R
import kotlinx.android.synthetic.main.activity_splash_ad.*


/**
 * @author pokerfaceCmy
 * @date 2020/7/29
 * @description 开屏广告activity
 * @email cheng.meng.yuan@qq.com
 */
class SplashAdActivity : AppCompatActivity() {
    private var mTTAdNative: TTAdNative? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_ad)
        mTTAdNative = TTAdSdk.getAdManager().createAdNative(this)
        loadSplashAd()
    }

    private fun loadSplashAd() {
        val adSlot = AdSlot.Builder()
            .setCodeId("887354746")
            .setSupportDeepLink(true)
            .setImageAcceptedSize(1080, 1920)
            .build()
        mTTAdNative!!.loadSplashAd(adSlot, object : TTAdNative.SplashAdListener {
            override fun onSplashAdLoad(p0: TTSplashAd?) {
                Log.i("loadSplashAd", "onSplashAdLoad:")
                val view: View? = p0?.splashView
                splashConstraintLayout.removeAllViews()
                splashConstraintLayout.addView(view)
                p0?.setSplashInteractionListener(object : TTSplashAd.AdInteractionListener {
                    override fun onAdClicked(p0: View?, p1: Int) {
                        Log.i("loadSplashAd", "onAdClicked:")
                    }

                    override fun onAdSkip() {
                        Log.i("loadSplashAd", "onAdSkip:")
                    }

                    override fun onAdShow(p0: View?, p1: Int) {
                        Log.i("loadSplashAd", "onAdShow:")
                    }

                    override fun onAdTimeOver() {
                        Log.i("loadSplashAd", "onAdTimeOver:")
                    }
                })
            }

            override fun onError(p0: Int, p1: String?) {
                Log.e("loadSplashAd", "onError: $p1")
            }

            override fun onTimeout() {
                Log.i("loadSplashAd", "onTimeout:")
            }
        })
    }

}