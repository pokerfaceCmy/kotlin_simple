package com.cmy.kotlinsimple

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cmy.kotlinsimple.dialog.rating.RatingDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            val ratingDialog = RatingDialog(this)
            ratingDialog.show()
        }
    }
}