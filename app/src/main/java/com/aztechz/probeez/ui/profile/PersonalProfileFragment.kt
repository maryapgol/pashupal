package com.aztechz.probeez.ui.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.aztechz.probeez.R
import com.aztechz.probeez.model.profile.PersonalDetails
import com.aztechz.probeez.utils.CustomProgress
import com.aztechz.probeez.utils.DataState
import com.aztechz.probeez.utils.Utility
import com.aztechz.probeez.viewmodel.ProfileViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_personal_profile.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PersonalProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PersonalProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val profileViewModel: ProfileViewModel by viewModels()
    private val TAG = PersonalProfileFragment::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_profile, container, false)
    }

    fun setData(personalDetails: PersonalDetails?) {
       /* if(!personalDetails?.about.isNullOrEmpty())
        {
            txtAboutLabel.text = personalDetails?.about
            linAbout.visibility = View.VISIBLE
        }else{
            linAbout.visibility = View.GONE

        }
        if(!personalDetails?.addressLine1.isNullOrEmpty())
        {
            txtAddressLabel.text = personalDetails?.addressLine1
            linAddress.visibility = View.VISIBLE
        }else{
            linAddress.visibility = View.GONE

        }

        if(!personalDetails?.city.isNullOrEmpty())
        {
            txtCityLabel.text = personalDetails?.city
            linCity.visibility = View.VISIBLE
        }else{
            linCity.visibility = View.GONE

        }

        if(!personalDetails?.intersts.isNullOrEmpty())
        {
            txtInterestsLabel.text = personalDetails?.intersts?.joinToString(separator = ",")
            linInterests.visibility = View.VISIBLE
        }else{
            linInterests.visibility = View.GONE

        }
*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTypeFace()

        profileViewModel.profileData.observe(viewLifecycleOwner, Observer {

            when (it) {
                is DataState.Loading -> {


                }

                is DataState.Success -> {
                    if (it.data.statusCode == "01") {
                        if (!it.data.data.isNullOrEmpty()) {
                            val personalDetails = it.data.data[0]?.personalDetails
                            if(!personalDetails?.about.isNullOrEmpty())

                            {
                                txtAboutLabel.text = personalDetails?.about
                                linAbout.visibility = View.VISIBLE
                            }else{
                                linAbout.visibility = View.GONE

                            }
                            if(!personalDetails?.addressLine1.isNullOrEmpty())
                            {
                                txtAddressLabel.text = personalDetails?.addressLine1
                                linAddress.visibility = View.VISIBLE
                            }else{
                                linAddress.visibility = View.GONE

                            }

                            if(!personalDetails?.city.isNullOrEmpty())
                            {
                                txtCityLabel.text = personalDetails?.city
                                linCity.visibility = View.VISIBLE
                            }else{
                                linCity.visibility = View.GONE

                            }

                            if(!personalDetails?.intersts.isNullOrEmpty())
                            {
                                txtInterestsLabel.text = personalDetails?.intersts?.joinToString(separator = ",")
                                linInterests.visibility = View.VISIBLE
                            }else{
                                linInterests.visibility = View.GONE

                            }
                        }

                    } else {
                        Snackbar.make(
                            linMain?.rootView!!,
                            it.data?.message.toString(),
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                is DataState.Error -> {
                    Log.i(TAG, "Error: " + it.exception.printStackTrace())


                }

            }

        })

        profileViewModel.getProfileData()
    }

    private fun setTypeFace()
    {
        txtAbout.typeface = Utility.fontMedium
        txtAddress.typeface = Utility.fontMedium
        txtCity.typeface = Utility.fontMedium
        txtInterests.typeface = Utility.fontMedium
        txtAboutLabel.typeface = Utility.fontRegular
        txtAddressLabel.typeface = Utility.fontRegular
        txtCityLabel.typeface = Utility.fontRegular
        txtInterestsLabel.typeface = Utility.fontRegular
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PersonalProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PersonalProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}