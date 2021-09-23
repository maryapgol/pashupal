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
import com.aztechz.probeez.ui.home.Mailbox

/**
 * A simple data class to represent an Task.
 */
data class Task(
    val id: Long,
    val sender: Account,
    val vendors: List<Account> = emptyList(),
    val subject: String = "",
    val body: String = "",
    val attachments: List<TaskAttachment> = emptyList(),
    var isImportant: Boolean = false,
    var isStarred: Boolean = false,
    var mailbox: Mailbox = Mailbox.HOME
) {
    val senderPreview: String = "${sender.fullName} - 4 hrs ago"
    val hasBody: Boolean = body.isNotBlank()
    val hasAttachments: Boolean = attachments.isNotEmpty()
    val vendorsPreview: String = vendors
        .map { it.firstName }
        .fold("") { name, acc -> "$acc, $name" }
    val nonUserAccountVendors = vendors
        .filterNot { AccountStore.isUserAccount(it.uid) }
}

object TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
}

