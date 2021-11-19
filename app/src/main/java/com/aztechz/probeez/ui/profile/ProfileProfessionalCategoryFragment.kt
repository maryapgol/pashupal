package com.aztechz.probeez.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aztechz.probeez.R
import com.aztechz.probeez.databinding.FragmentProfilePersonalBinding
import com.aztechz.probeez.databinding.FragmentProfileProfessionalCategoryBinding


class ProfileProfessionalCategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding =
            FragmentProfileProfessionalCategoryBinding.inflate(inflater, container, false).apply {

                addMoreProfessional.setOnClickListener {
                    findNavController().navigate(R.id.action_profileProfessionalCategoryFragment_self)
                }

                saveNextProfessionalCategory.setOnClickListener {
                    findNavController().navigate(R.id.action_profileProfessionalCategoryFragment_to_profileAssetsFragment)
                }
            }
        return binding.root
    }
}