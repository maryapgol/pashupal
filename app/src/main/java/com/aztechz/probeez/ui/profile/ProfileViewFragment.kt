package com.aztechz.probeez.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aztechz.probeez.R
import com.aztechz.probeez.databinding.FragmentProfileProfessionalBinding


class ProfileViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentProfileProfessionalBinding.inflate(inflater, container, false).apply {
        }
        return binding.root
        }

}