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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import com.aztechz.probeez.MainActivity
import com.aztechz.probeez.R
import com.aztechz.probeez.data.Task
import com.aztechz.probeez.data.TaskStore
import com.aztechz.probeez.databinding.FragmentHomeBinding
import com.aztechz.probeez.ui.MenuBottomSheetDialogFragment
import com.aztechz.probeez.ui.nav.NavigationModel

/**
 * A [Fragment] that displays a list of emails.
 */
class HomeFragment : Fragment(), TaskAdapter.TaskAdapterListener {

    private val args: HomeFragmentArgs by navArgs()

    private lateinit var binding: FragmentHomeBinding

    private val emailAdapter = TaskAdapter(this)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Set up MaterialFadeThrough enterTransition.
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: Set up postponed enter transition.


        binding.recyclerView.apply {
            val itemTouchHelper = ItemTouchHelper(ReboundingSwipeActionCallback())
            itemTouchHelper.attachToRecyclerView(this)
            adapter = emailAdapter
        }
        binding.recyclerView.adapter = emailAdapter

        TaskStore.getTasks(args.mailbox).observe(viewLifecycleOwner) {
            emailAdapter.submitList(it)
        }
    }

    override fun onTaskClicked(cardView: View, task: Task) {
        // TODO: Set up MaterialElevationScale transition as exit and reenter transitions.
       /* val directions = HomeFragmentDirections.actionHomeFragmentToTaskFragment(task.id)
        findNavController().navigate(directions)*/
    }

    override fun onTaskLongPressed(task: Task): Boolean {
        MenuBottomSheetDialogFragment
          .newInstance(R.menu.email_bottom_sheet_menu)
          .show(parentFragmentManager, null)

        return true
    }

    override fun onTaskStarChanged(task: Task, newValue: Boolean) {
        TaskStore.update(task.id) { isStarred = newValue }
    }

    override fun onTaskArchived(task: Task) {
        TaskStore.delete(task.id)
    }
}
