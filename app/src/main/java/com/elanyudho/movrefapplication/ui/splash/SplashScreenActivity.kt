package com.elanyudho.movrefapplication.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import com.elanyudho.core.abstraction.BaseActivityBinding
import com.elanyudho.movrefapplication.databinding.ActivitySplashScreenBinding
import com.elanyudho.movrefapplication.ui.main.MainActivity
import javax.inject.Inject

class SplashScreenActivity : BaseActivityBinding<ActivitySplashScreenBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivitySplashScreenBinding
        get() =  { ActivitySplashScreenBinding.inflate(it) }

    override fun setupView() {
        Handler(Looper.getMainLooper()).postDelayed({
            moveNext()
        }, 2000L)
    }

    private fun moveNext() {
        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}