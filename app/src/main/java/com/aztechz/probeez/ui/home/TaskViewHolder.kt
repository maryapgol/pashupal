/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aztechz.probeez.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.aztechz.probeez.R
import com.aztechz.probeez.data.Task
import com.aztechz.probeez.databinding.TaskItemLayoutBinding
import com.aztechz.probeez.ui.common.TaskAttachmentAdapter
import com.aztechz.probeez.util.setTextAppearanceCompat
import com.aztechz.probeez.util.themeStyle
import kotlin.math.abs

class TaskViewHolder(
    private val binding: TaskItemLayoutBinding,
    listener: TaskAdapter.TaskAdapterListener
): RecyclerView.ViewHolder(binding.root), ReboundingSwipeActionCallback.ReboundableViewHolder {

    private val attachmentAdapter = object : TaskAttachmentAdapter() {
        override fun getLayoutIdForPosition(position: Int): Int
            = R.layout.task_attachment_preview_item_layout
    }

    private val starredCornerSize =
        itemView.resources.getDimension(R.dimen.probeez_small_component_corner_radius)

    override val reboundableView: View = binding.cardView

    init {
        binding.run {
            this.listener = listener
            attachmentRecyclerView.adapter = attachmentAdapter
            root.background = TaskSwipeActionDrawable(root.context)
        }
    }

    fun bind(task: Task) {
        binding.email = task
        binding.root.isActivated = task.isStarred

        // Set the subject's TextAppearance
        val textAppearance = binding.subjectTextView.context.themeStyle(
            if (task.isImportant) {
                R.attr.textAppearanceHeadline4
            } else {
                R.attr.textAppearanceHeadline5
            }
        )
        binding.subjectTextView.setTextAppearanceCompat(
            binding.subjectTextView.context,
            textAppearance
        )

        attachmentAdapter.submitList(task.attachments)

        // Setting interpolation here controls whether or not we draw the top left corner as
        // rounded or squared. Since all other corners are set to 0dp rounded, they are
        // not affected.
        val interpolation = if (task.isStarred) 1F else 0F
        updateCardViewTopLeftCornerSize(interpolation)

        binding.executePendingBindings()
    }

    override fun onReboundOffsetChanged(
        currentSwipePercentage: Float,
        swipeThreshold: Float,
        currentTargetHasMetThresholdOnce: Boolean
    ) {
        // Only alter shape and activation in the forward direction once the swipe
        // threshold has been met. Undoing the swipe would require releasing the item and
        // re-initiating the swipe.
        if (currentTargetHasMetThresholdOnce) return

        val isStarred = binding.email?.isStarred ?: false

        // Animate the top left corner radius of the email card as swipe happens.
        val interpolation = (currentSwipePercentage / swipeThreshold).coerceIn(0F, 1F)
        val adjustedInterpolation = abs((if (isStarred) 1F else 0F) - interpolation)
        updateCardViewTopLeftCornerSize(adjustedInterpolation)

        // Start the background animation once the threshold is met.
        val thresholdMet = currentSwipePercentage >= swipeThreshold
        val shouldStar = when {
            thresholdMet && isStarred -> false
            thresholdMet && !isStarred -> true
            else -> return
        }
        binding.root.isActivated = shouldStar
    }

    override fun onRebounded() {
        val email = binding.email ?: return
        binding.listener?.onTaskStarChanged(email, !email.isStarred)
    }

    // We have to update the shape appearance itself to have the MaterialContainerTransform pick up
    // the correct shape appearance, since it doesn't have access to the MaterialShapeDrawable
    // interpolation. If you don't need this work around, prefer using MaterialShapeDrawable's
    // interpolation property, or in the case of MaterialCardView, the progress property.
    private fun updateCardViewTopLeftCornerSize(interpolation: Float) {
        binding.cardView.apply {
            shapeAppearanceModel = shapeAppearanceModel.toBuilder()
                .setTopLeftCornerSize(interpolation * starredCornerSize)
                .build()
        }
    }
}