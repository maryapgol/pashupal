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

package com.aztechz.probeez.data

import androidx.recyclerview.widget.DiffUtil

/**
 * Alias to represent a folder (a String title) into which emails can be placed.
 */
typealias TaskFolder = String

object TaskFolderDiff : DiffUtil.ItemCallback<TaskFolder>() {
    override fun areItemsTheSame(oldItem: TaskFolder, newItem: TaskFolder) = oldItem == newItem
    override fun areContentsTheSame(oldItem: TaskFolder, newItem: TaskFolder) = oldItem == newItem
}
