package com.aztechz.probeez.view.login_signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aztechz.probeez.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}