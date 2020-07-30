package com.cmy.common.repository

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import com.blankj.utilcode.util.ToastUtils
import com.cmy.common.R
import kotlinx.android.synthetic.main.dialog_loading.*

/**
 * @author pokerfaceCmy
 * @date 2020/7/29
 * @description 展示等待的动画
 * @email cheng.meng.yuan@qq.com
 */
class LoadingDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate: View = View.inflate(context, R.layout.dialog_loading, null)
        setContentView(inflate)
        init()
    }

    private fun init() {

    }

    override fun show() {
        super.show()
        //点击外部区域的时候不会取消掉dialog
        this.setCanceledOnTouchOutside(false)
        //点击返回按钮不消失
        //this.setCancelable(false)
        //获取window
        val window: Window = this.window!!
        //dialog背景透明
        window.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun cancel() {
        super.cancel()
        animationLoading.cancelAnimation()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        ToastUtils.showLong("已取消")
    }
}