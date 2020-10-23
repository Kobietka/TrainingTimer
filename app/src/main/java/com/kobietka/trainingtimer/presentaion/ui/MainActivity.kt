package com.kobietka.trainingtimer.presentaion.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kobietka.trainingtimer.R
import com.kobietka.trainingtimer.presentaion.common.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presentationComponent.inject(this)
    }
}
