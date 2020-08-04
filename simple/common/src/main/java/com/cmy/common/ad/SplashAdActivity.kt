package com.cmy.common.ad

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.sdk.openadsdk.AdSlot
import com.bytedance.sdk.openadsdk.TTAdNative
import com.bytedance.sdk.openadsdk.TTAdSdk
import com.bytedance.sdk.openadsdk.TTSplashAd
import com.cmy.common.R
import kotlinx.android.synthetic.main.activity_splash_ad.*
import timber.log.Timber


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
            .setImageAcceptedSize(740, 1334)
            .build()
        mTTAdNative?.loadSplashAd(adSlot, object : TTAdNative.SplashAdListener {
            override fun onSplashAdLoad(ad: TTSplashAd?) {
                Timber.i("onSplashAdLoad")
                val view: View? = ad?.splashView
                splashConstraintLayout.removeAllViews()
                splashConstraintLayout.addView(view)
                ad?.setSplashInteractionListener(object : TTSplashAd.AdInteractionListener {
                    override fun onAdClicked(view: View?, type: Int) {
                        Timber.i("onAdClicked")
                    }

                    override fun onAdSkip() {
                        Timber.i("onAdSkip")
                    }

                    override fun onAdShow(view: View?, type: Int) {
                        Timber.i("onAdShow")
                    }

                    override fun onAdTimeOver() {
                        Timber.i("onAdTimeOver")
                    }
                })
            }

            override fun onError(code: Int, errMsg: String?) {
                Timber.e("onError")
            }

            override fun onTimeout() {
                Timber.w("onTimeout")
            }
        })
    }


}