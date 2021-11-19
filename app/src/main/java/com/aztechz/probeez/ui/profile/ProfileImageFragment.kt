package com.aztechz.probeez.ui.profile

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Px
import androidx.core.view.forEach
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.aztechz.probeez.R
import com.aztechz.probeez.data.topics
import com.aztechz.probeez.databinding.FragmentProfileImageBinding
import com.aztechz.probeez.util.TopicsAdapter
import com.aztechz.probeez.util.spring

class ProfileImageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProfileImageBinding.inflate(inflater, container, false).apply {
            /*fab.setOnClickListener {
                findNavController().navigate(R.id.action_onboarding_to_featured)
            }*/
            saveNextImage.setOnClickListener {
                findNavController().navigate(R.id.action_profileImageFragment_to_profilePersonalFragment)
            }
            topicGrid.apply {
                adapter = TopicsAdapter(context).apply {
                    // We're setting reverseLayout on the RV to layout from RTL, but we still want
                    // data ordered LTR, so reverse it before setting
                    submitList(topics.reversed())
                }
                smoothScrollToPositionWithSpeed(topics.size)
                addOnScrollListener(
                    OscillatingScrollListener(resources.getDimensionPixelSize(R.dimen.grid_2))
                )
            }
        }
        return binding.root
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