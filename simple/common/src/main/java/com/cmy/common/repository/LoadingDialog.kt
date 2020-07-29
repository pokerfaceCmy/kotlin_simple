package com.cmy.common.repository

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.cmy.common.R

/**
 * @author pokerfaceCmy
 * @date 2020/7/29
 * @description 展示等待的动画
 * @email cheng.meng.yuan@qq.com
 */
class LoadingDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: 2020/7/29 完成loading的布局文件
        val inflate: View = View.inflate(context, R.layout.dialog_loading, null)
        setContentView(inflate)

    }

    override fun show() {
        super.show()
        //点击外部区域的时候不会取消掉dialog
        this.setCanceledOnTouchOutside(false)
    }
}