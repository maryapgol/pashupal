package com.aztechz.probeez.ui.login_signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.aztechz.probeez.MainActivity
import com.aztechz.probeez.R
import com.aztechz.probeez.databinding.ActivitySignupBinding
import com.aztechz.probeez.model.SignUpResponseModel
import com.aztechz.probeez.model.signup.SignUpRequest
import com.aztechz.probeez.ui.ProfileActivity
import com.aztechz.probeez.util.DataProcessor
import com.aztechz.probeez.util.contentView
import com.aztechz.probeez.utils.CustomProgress
import com.aztechz.probeez.utils.DataState
import com.aztechz.probeez.viewmodel.SignUpViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {

    private val TAG: String = "SignupActivity"
    private val binding: ActivitySignupBinding by contentView(R.layout.activity_signup)
    private var signUpResponseModel: SignUpViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        signUpResponseModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        signUpResponseModel?.signUp?.observe(this, Observer {


            when(it)
            {
                is DataState.Success<SignUpResponseModel> -> {
                    CustomProgress.hideProgress()

                    Log.i(TAG," Success: "+ it.data)
                    if(it.data.statusCode == "001")
                    {
                        println("logging: "+it)
                        //Create user profile
                        val dt = DataProcessor(applicationContext)
                        dt.setStr("email",binding.signupTaskEmailInp.editText.toString())
                        dt.setStr("phone",binding.signupTaskPhoneInp.editText.toString())
                        dt.setStr("user_id",it.data.data?.userId)
                        dt.setStr("cred",it.data.data?.userId)
                        startActivity(Intent(this@SignupActivity, ProfileActivity::class.java))
                        finish()
                    }else{
                        Snackbar.make(binding.root, it.data?.message.toString(), Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }

                is DataState.Error -> {
                    CustomProgress.hideProgress()

                    Log.i(TAG," Error: "+it.exception.printStackTrace())

                }

                is DataState.Loading -> {
                    CustomProgress.showProgress(this@SignupActivity,false)
                    Log.i(TAG," Loading: ")

                }
            }


        })

        binding.btnSignup.setOnClickListener {
            if (validateSignUp() == null) {

                val signUpRequest = SignUpRequest("",binding.signupTaskEmailInp.editText.toString(),binding.signupTaskPhoneInp.editText.toString(),binding.signupRePassword.editText.toString())
                signUpResponseModel?.signUp(signUpRequest)

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