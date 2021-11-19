package com.aztechz.probeez.ui.login_signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.aztechz.probeez.MainActivity
import com.aztechz.probeez.R
import com.aztechz.probeez.databinding.ActivitySignupBinding
import com.aztechz.probeez.ui.ProfileActivity
import com.aztechz.probeez.util.DataProcessor
import com.aztechz.probeez.util.contentView
import com.google.android.material.snackbar.Snackbar

class SignupActivity : AppCompatActivity() {

    private val binding: ActivitySignupBinding by contentView(R.layout.activity_signup)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnSignup.setOnClickListener {
            if (validateSignUp() == null) {
                //Create user profile
                    val dt = DataProcessor(applicationContext)
                dt.setStr("email",binding.signupTaskEmailInp.editText.toString())
                dt.setStr("phone",binding.signupTaskPhoneInp.editText.toString())
                dt.setStr("cred",binding.signupTaskPhoneInp.editText.toString())
                startActivity(Intent(this@SignupActivity, ProfileActivity::class.java))
            }else {
                Snackbar.make(binding.root, "Error: ${validateSignUp()}", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

    }


    fun validateSignUp(): String? {
        return when {
            binding.signupTaskEmailInp.editText?.text.toString().isBlank() &&
                    binding.signupTaskPhoneInp.editText?.text.toString().isBlank() -> "Please enter either email id or phone number"
            //isValidEmail(binding.signupTaskEmailInp.editText?.text.toString()) -> "Please enter valid email"
            //binding.signupTaskPhoneInp.editText?.text.toString().length != 10 -> "Please enter valid number "
            binding.signupPassword.editText?.text.toString().isEmpty() -> "Please enter password"
            binding.signupRePassword.editText?.text.toString().isEmpty() -> "Please re-enter password"
            binding.signupPassword.editText?.text.toString().length < 6 -> "Password should be greater than 5 characters"
            binding.signupPassword.editText?.text.toString() != binding.signupRePassword.editText?.text.toString() -> "Passwords do not match"
            else -> null
        }
    }

    fun isValidEmail(strEmail: String): Boolean {
        if (strEmail.isEmpty()) {
            return false
        }
        return Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()
    }
}