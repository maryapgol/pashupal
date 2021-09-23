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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.aztechz.probeez.data.Task
import com.aztechz.probeez.data.TaskDiffCallback
import com.aztechz.probeez.databinding.TaskItemLayoutBinding

/**
 * Simple adapter to display Task's in MainActivity.
 */
class TaskAdapter(
        private val listener: TaskAdapterListener
) : ListAdapter<Task, TaskViewHolder>(TaskDiffCallback) {

    interface TaskAdapterListener {
        fun onTaskClicked(cardView: View, task: Task)
        fun onTaskLongPressed(task: Task): Boolean
        fun onTaskStarChanged(task: Task, newValue: Boolean)
        fun onTaskArchived(task: Task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            TaskItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}