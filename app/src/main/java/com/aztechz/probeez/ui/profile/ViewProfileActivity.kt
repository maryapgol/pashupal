package com.aztechz.probeez.ui.profile

import android.graphics.Typeface
import android.graphics.fonts.Font
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.aztechz.probeez.R
import com.aztechz.probeez.ui.profile.adapter.ViewPagerAdapter
import com.aztechz.probeez.utils.CustomProgress
import com.aztechz.probeez.utils.DataState
import com.aztechz.probeez.viewmodel.ProfileViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_view_profile.*
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ViewProfileActivity : AppCompatActivity() {
    private val profileViewModel: ProfileViewModel by viewModels()

    private var conMain: ConstraintLayout? = null
    private val TAG = ViewProfileActivity::class.java.name
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_profile)

        conMain = findViewById(R.id.conMain)
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager?.adapter = viewPagerAdapter
        findViewById<com.google.android.material.tabs.TabLayout>(R.id.tabs).setupWithViewPager(
            viewPager
        )
        setTypeFace()
        initializeData()
        changeTabsFont()
    }

    private fun setTypeFace()
    {

    }

    private fun initializeData() {

        Glide.
        with(this@ViewProfileActivity).
        load("https://images.hindustantimes.com/img/2021/09/13/550x309/ronaldo-united-getty-new_1631517666339_1631517671208.jpg").
        into(imgUser)


        profileViewModel.profileData.observe(this, Observer {

            when (it) {
                is DataState.Loading -> {
                    CustomProgress.showProgress(this@ViewProfileActivity, false)


                }

                is DataState.Success -> {
                    CustomProgress.hideProgress()
                    Log.i(TAG, "Response: " + it.data)
                    if (it.data.statusCode == "01") {
                        if (!it.data.data.isNullOrEmpty()) {
                            val personalDetails = it.data.data[0]?.personalDetails
                            val professionalDetails = it.data.data[0]?.professionalDetails
                            txtName.text = it.data.data[0]?.fullname
                            val personalProfileFragment: PersonalProfileFragment =
                                viewPagerAdapter?.getItem(0) as PersonalProfileFragment
                            val professionalProfileFragment: ProfessionalProfileFragment =
                                viewPagerAdapter?.getItem(1) as ProfessionalProfileFragment
                            personalProfileFragment.setData(personalDetails)
                            professionalProfileFragment.setData(professionalDetails)
                        }

                    } else {
                        Snackbar.make(
                            conMain?.rootView!!,
                            it.data?.message.toString(),
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                is DataState.Error -> {
                    CustomProgress.hideProgress()
                    Log.i(TAG, "Error: " + it.exception.printStackTrace())


                }

            }

        })

        profileViewModel.getProfileData()
    }

    private fun changeTabsFont() {
        val vg = tabs.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount
        for (j in 0 until tabsCount) {
            val vgTab = vg.getChildAt(j) as ViewGroup
            val tabChildsCount = vgTab.childCount
            for (i in 0 until tabChildsCount) {
                val tabViewChild: View = vgTab.getChildAt(i)
                if (tabViewChild is TextView) {
                   val font = Typeface.createFromAsset(
                       assets,
                        "fonts/work_sans_bold.ttf");
                    (tabViewChild as TextView).setTypeface(font)
                }
            }
        }
    }
}