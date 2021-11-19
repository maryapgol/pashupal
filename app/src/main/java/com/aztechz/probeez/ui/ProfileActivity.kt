package com.aztechz.probeez.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aztechz.probeez.R
import com.aztechz.probeez.databinding.ActivityLoginBinding
import com.aztechz.probeez.databinding.ActivityProfileBinding
import com.aztechz.probeez.util.contentView

class ProfileActivity : AppCompatActivity() {
    private val binding: ActivityProfileBinding by contentView(R.layout.activity_profile)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {}
    }
}