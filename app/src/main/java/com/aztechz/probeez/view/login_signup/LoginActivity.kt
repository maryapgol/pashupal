package com.aztechz.probeez.view.login_signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aztechz.probeez.databinding.ActivityLoginBinding
import com.aztechz.probeez.databinding.ActivitySignupBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}