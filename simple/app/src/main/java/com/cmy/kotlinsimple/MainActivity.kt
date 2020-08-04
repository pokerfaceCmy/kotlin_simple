package com.cmy.kotlinsimple


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cmy.common.ad.SplashAdActivity
import com.cmy.common.repository.LoadingDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button.setOnClickListener {
//            val ratingDialog = RatingDialog(this)
//            ratingDialog.show()
            val loadingDialog = LoadingDialog(this)
            loadingDialog.show()
        }


        startActivity(Intent(this, SplashAdActivity::class.java))
    }
}