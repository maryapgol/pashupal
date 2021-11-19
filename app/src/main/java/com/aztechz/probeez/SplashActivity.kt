package com.aztechz.probeez

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.aztechz.probeez.databinding.ActivitySplashBinding
import com.aztechz.probeez.util.contentView
import com.aztechz.probeez.ui.login_signup.LoginActivity
import com.aztechz.probeez.util.DataProcessor

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 3000 //Time delay to remove splash screen
    private val binding: ActivitySplashBinding by contentView(R.layout.activity_splash)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        val animZoomIn = AnimationUtils.loadAnimation(
            this,
            R.anim.zoom_in
        )
        // assigning that animation to
        // the image and start animation
        binding.logoImage.startAnimation(animZoomIn)
        /* Add Delay to show Login/Home Screen after Splash Screen*/
        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            // create the transition animation - the images in the layouts
            // of both activities are defined with android:transitionName="robot"
            val options = ActivityOptions
                .makeSceneTransitionAnimation(this, binding.logoImage, "logo")
            // start the new activity
            startActivity(intent, options.toBundle())

            val dt = DataProcessor(applicationContext)
            if(dt.sharedPreferenceExist("cred")){
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }
        }, SPLASH_DELAY)
    }
}