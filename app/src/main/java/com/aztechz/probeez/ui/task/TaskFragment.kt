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

package com.aztechz.probeez.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.aztechz.probeez.data.TaskStore
import com.aztechz.probeez.databinding.FragmentTaskBinding
import kotlin.LazyThreadSafetyMode.NONE

private const val MAX_GRID_SPANS = 3

/**
 * A [Fragment] which displays a single, full email.
 */
class TaskFragment : Fragment() {

    private val args: TaskFragmentArgs by navArgs()
    private val emailId: Long by lazy(NONE) { args.emailId }

    private lateinit var binding: FragmentTaskBinding
    private val attachmentAdapter = TaskAttachmentGridAdapter(MAX_GRID_SPANS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Set up MaterialContainerTransform transition as sharedElementEnterTransition.
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navigationIcon.setOnClickListener {
            findNavController().navigateUp()
        }

        val email = TaskStore.get(emailId)
        if (email == null) {
            showError()
            return
        }

        binding.run {
            this.email = email

            // Set up the staggered/masonry grid recycler
            attachmentRecyclerView.layoutManager = GridLayoutManager(
                requireContext(),
                MAX_GRID_SPANS
            ).apply {
                spanSizeLookup = attachmentAdapter.variableSpanSizeLookup
            }
            attachmentRecyclerView.adapter = attachmentAdapter
            attachmentAdapter.submitList(email.attachments)
        }
    }

    private fun showError() {
        // Do nothing
    }
}