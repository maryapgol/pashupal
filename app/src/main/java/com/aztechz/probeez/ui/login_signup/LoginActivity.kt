package com.aztechz.probeez.ui.login_signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aztechz.probeez.MainActivity
import com.aztechz.probeez.R
import com.aztechz.probeez.databinding.ActivityLoginBinding
import com.aztechz.probeez.util.contentView

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by contentView(R.layout.activity_login)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }
    }
}