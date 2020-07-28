package com.cmy.kotlinsimple.dialog.rating

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.cmy.kotlinsimple.R

class RatingDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate: View = View.inflate(context, R.layout.dialog_rating, null)
        setContentView(inflate)
    }


    override fun show() {
        super.show()
        //获取window
        val window: Window = this.window!!
        val layoutParams = window.attributes

        //从底部弹出
        layoutParams.gravity = Gravity.BOTTOM
        //宽度占满全屏
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        window.setBackgroundDrawableResource(R.drawable.background_shape_radius_top_white)
        window.decorView.setPadding(0, 0, 0, 0)
        window.attributes = layoutParams
    }
}