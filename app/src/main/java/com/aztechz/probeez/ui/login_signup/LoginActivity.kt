package com.aztechz.probeez.ui.login_signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aztechz.probeez.MainActivity
import com.aztechz.probeez.R
import com.aztechz.probeez.databinding.ActivityLoginBinding
import com.aztechz.probeez.model.SignUpResponseModel
import com.aztechz.probeez.model.login.LoginRequestModel
import com.aztechz.probeez.model.login.LoginResponseDataModel
import com.aztechz.probeez.ui.ProfileActivity
import com.aztechz.probeez.util.DataProcessor
import com.aztechz.probeez.util.contentView
import com.aztechz.probeez.utils.CustomProgress
import com.aztechz.probeez.utils.DataState
import com.aztechz.probeez.viewmodel.LoginViewModel
import com.aztechz.probeez.viewmodel.SignUpViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by contentView(R.layout.activity_login)
    private var loginViewModel: LoginViewModel? = null
    private val TAG = "LoginActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        loginViewModel?.login?.observe(this, Observer {

            when (it) {
                is DataState.Success<LoginResponseDataModel> -> {
                    CustomProgress.hideProgress()

                    Log.i(TAG, " Succcess: " + it.data)
                    if (it.data.statusCode == "001") {
                        val dt = DataProcessor(applicationContext)
                        dt.setStr("email", it.data.data?.email)
                        dt.setStr("phone", it.data.data?.phone)
                        dt.setStr("user_id", it.data.data?.userId)
                        dt.setStr("cred", it.data.data?.userId)
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Snackbar.make(
                            binding.root,
                            it.data?.message.toString(),
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                is DataState.Error -> {
                    CustomProgress.hideProgress()

                    Log.i(TAG, " Error: " + it.exception.printStackTrace())

                }

                is DataState.Loading -> {
                    CustomProgress.showProgress(this@LoginActivity, false)

                    Log.i(TAG, " Loading: ")

                }
            }

        })

        binding.apply {
            btnLogin.setOnClickListener {
                if (validateLogin() == null) {
                    val loginRequestModel = LoginRequestModel(
                        binding.signinTaskEmailTextInp.editText.toString(),
                        binding.signinPassword.editText.toString()
                    )
                    loginViewModel?.login(loginRequestModel)
                } else {
                    Snackbar.make(binding.root, "Error: ${validateLogin()}", Snackbar.LENGTH_SHORT)
                        .show()

                }
            }

            signUptextView.setOnClickListener {
                startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
            }
        }
    }

    private fun validateLogin(): String? {
        return when {
            binding.signinTaskEmailTextInp.editText?.text.toString()
                .isBlank() -> "Please enter either email id or phone number"
            //isValidEmail(binding.signupTaskEmailInp.editText?.text.toString()) -> "Please enter valid email"
            //binding.signupTaskPhoneInp.editText?.text.toString().length != 10 -> "Please enter valid number "
            binding.signinPassword.editText?.text.toString().isEmpty() -> "Please enter password"
            else -> null
        }
    }
}