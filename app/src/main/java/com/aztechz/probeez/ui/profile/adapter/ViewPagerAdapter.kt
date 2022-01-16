package com.aztechz.probeez.ui.profile.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.aztechz.probeez.ui.profile.PersonalProfileFragment
import com.aztechz.probeez.ui.profile.ProfessionalProfileFragment


class ViewPagerAdapter(private val fragmentManager: FragmentManager): FragmentPagerAdapter(
    fragmentManager
) {


    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        if (position === 0) {
            fragment = PersonalProfileFragment()
        } else if (position === 1) {
            fragment = ProfessionalProfileFragment()
        }
        return fragment!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title: String? = null
        if (position == 0) {
            title = "Personal"
        } else if (position == 1) {
            title = "Professional"
        }
        return title
    }
}