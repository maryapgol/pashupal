package com.aztechz.probeez.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aztechz.probeez.MainActivity
import com.aztechz.probeez.R
import com.aztechz.probeez.databinding.FragmentProfileAssetsBinding
import com.aztechz.probeez.databinding.FragmentProfileProfessionalBinding
import java.lang.NullPointerException

class ProfileAssetsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentProfileAssetsBinding.inflate(inflater, container, false).apply {

            saveNextAssets.setOnClickListener {
                startActivity(Intent(activity,MainActivity::class.java))
            }


        }
        return binding.root
    }

}