package com.aztechz.probeez.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import com.aztechz.probeez.R
import com.aztechz.probeez.databinding.ActivityLoginBinding
import com.aztechz.probeez.databinding.ActivityProfileBinding
import com.aztechz.probeez.databinding.ActivityTaskBinding
import com.aztechz.probeez.model.profile.ProfileData
import com.aztechz.probeez.ui.profile.ProfileImageFragmentArgs
import com.aztechz.probeez.util.contentView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    private var isFromEditProfile = false
    private var profileData: ProfileData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
           if(intent.hasExtra("isFromEditProfile"))
           {
               isFromEditProfile = true
               val bundle = Bundle()
               bundle.putString("profileData","profileData")
               profileData = intent.getSerializableExtra("profileData") as ProfileData

               val navController = findNavController(this@ProfileActivity,R.id.profile_nav_host_fragment)
                   .setGraph(
                       R.navigation.profile_nav_graph,
                       ProfileImageFragmentArgs(profileData!!).toBundle())

/*
               navController.setGraph(navController.graph,bundle)
*/
           }



    }
}