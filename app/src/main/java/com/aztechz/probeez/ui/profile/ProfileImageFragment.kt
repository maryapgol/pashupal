package com.aztechz.probeez.ui.profile

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Px
import androidx.core.view.forEach
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aztechz.probeez.data.Topic
import com.aztechz.probeez.data.topics
import com.aztechz.probeez.databinding.FragmentProfileImageBinding
import com.aztechz.probeez.model.profile.OnInterestSelectListener
import com.aztechz.probeez.model.profile.ProfileData
import com.aztechz.probeez.ui.profile.adapter.InterestAdapter
import com.aztechz.probeez.util.spring
import kotlinx.android.synthetic.main.fragment_profile_image.*

class ProfileImageFragment : Fragment() {

    private var arrList: ArrayList<String>? = ArrayList()
    private var arrListTopic: ArrayList<Topic?>? = null
    private val profileImageFragment: ProfileImageFragmentArgs by navArgs()
    private var binding : FragmentProfileImageBinding?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         binding = FragmentProfileImageBinding.inflate(inflater, container, false).apply {
            /*fab.setOnClickListener {
                findNavController().navigate(R.id.action_onboarding_to_featured)
            }*/
            arrListTopic = ArrayList()
            arrListTopic?.addAll(topics.toList())
            topicGrid.apply {
                adapter = InterestAdapter(activity as Activity, arrListTopic,onItemSelectedListener)
               layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL)
                smoothScrollToPositionWithSpeed(topics.size)
                /*addOnScrollListener(
                    OscillatingScrollListener(resources.getDimensionPixelSize(R.dimen.grid_2))
                )*/
            }

            saveNextImage.setOnClickListener {
                arrList?.clear()
                for(topic in arrListTopic as ArrayList)
                {
                    if(topic?.isSelected == true) {
                        arrList?.add(topic.name.toString())
                    }
                }
                val action =
                    ProfileImageFragmentDirections.actionProfileImageFragmentToProfilePersonalFragment(
                        signupTaskEmailInp.editText?.text.toString(),
                        arrList?.toTypedArray()!!
                    )
                findNavController().navigate(action)
            }

            skip.setOnClickListener {
                val action =
                    ProfileImageFragmentDirections.actionProfileImageFragmentToProfilePersonalFragment(
                        signupTaskEmailInp.editText?.text.toString(),
                        arrList?.toTypedArray()!!
                    )
                findNavController().navigate(action)
            }


            pdSubtitleText.typeface = com.aztechz.probeez.utils.Utility.fontRegular
            saveNextImage.typeface = com.aztechz.probeez.utils.Utility.fontMedium
            skip.typeface = com.aztechz.probeez.utils.Utility.fontMedium
            pdTitleText.typeface = com.aztechz.probeez.utils.Utility.fontBold
            choose.typeface = com.aztechz.probeez.utils.Utility.fontBold
            signupTaskEmailInp.typeface = com.aztechz.probeez.utils.Utility.fontRegular
            edtSignUp.typeface = com.aztechz.probeez.utils.Utility.fontRegular
        }
        return binding?.root as View
    }

    private fun setProfileData(profileData: ProfileData?) {
        print("Profile data: "+profileData)
        if(profileData!=null)
        {
            arrList?.clear()
            arrList?.addAll(profileData.personalDetails?.intersts as Collection<String>)
            binding?.signupTaskEmailInp?.editText?.setText(profileData.personalDetails?.about ?:"")
            for(topic in arrListTopic ?: ArrayList())
            {
               for(courseName in arrList?: ArrayList())
               {
                   if(topic?.name == courseName)
                   {
                       topic.isSelected = true
                       break
                   }
               }
            }
            binding?.topicGrid?.adapter?.notifyDataSetChanged()
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arrList = ArrayList()
        setProfileData(profileImageFragment.profileData)

    }

    private val onItemSelectedListener = object : OnInterestSelectListener {
        override fun onInterestSelected(position: Int) {
            arrListTopic?.get(position)?.isSelected = !((arrListTopic?.get(position)?.isSelected)?:false)
            topic_grid.adapter?.notifyDataSetChanged()
        }

    }
}


/**
 * Oscillates a [RecyclerView]'s children based on the horizontal scroll velocity.
 */
private const val MAX_OSCILLATION_ANGLE = 6f // ±6º

class OscillatingScrollListener(
    @Px private val scrollDistance: Int
) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        // Calculate a rotation to set from the horizontal scroll
        val clampedDx = dx.coerceIn(-scrollDistance, scrollDistance)
        val rotation = (clampedDx / scrollDistance) * MAX_OSCILLATION_ANGLE
        recyclerView.forEach {
            // Alter the pivot point based on scroll direction to make motion look more natural
            it.pivotX = it.width / 2f + clampedDx / 3f
            it.pivotY = it.height / 3f
            it.spring(SpringAnimation.ROTATION).animateToFinalPosition(rotation)
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState != RecyclerView.SCROLL_STATE_DRAGGING) {
            recyclerView.forEach {
                it.spring(SpringAnimation.ROTATION).animateToFinalPosition(0f)
            }
        }
    }
}



fun RecyclerView.smoothScrollToPositionWithSpeed(
    position: Int,
    millisPerInch: Float = 100f
) {
    val lm = requireNotNull(layoutManager)
    val scroller = object : LinearSmoothScroller(context) {
        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
            return millisPerInch / displayMetrics.densityDpi
        }
    }
    scroller.targetPosition = position
    lm.startSmoothScroll(scroller)
}