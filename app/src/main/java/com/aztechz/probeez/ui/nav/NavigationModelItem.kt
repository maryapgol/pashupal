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

package com.aztechz.probeez.ui.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import com.aztechz.probeez.data.TaskFolder
import com.aztechz.probeez.data.TaskFolderDiff
import com.aztechz.probeez.ui.home.Mailbox

/**
 * A sealed class which encapsulates all objects [NavigationAdapter] is able to display.
 */
sealed class NavigationModelItem {

    /**
     * A class which represents a checkable, navigation destination such as 'Inbox' or 'Sent'.
     */
    data class NavMenuItem(
        val id: Int,
        @DrawableRes val icon: Int,
        @StringRes val titleRes: Int,
        val mailbox: Mailbox,
        var checked: Boolean
    ) : NavigationModelItem()

    /**
     * A class which is used to show a section divider (a subtitle and underline) between
     * sections of different NavigationModelItem types.
     */
    data class NavDivider(val title: String) : NavigationModelItem()

    /**
     * A class which is used to show an [TaskFolder] in the [NavigationAdapter].
     */
    data class NavTaskFolder(val emailFolder: TaskFolder) : NavigationModelItem()

    object NavModelItemDiff : DiffUtil.ItemCallback<NavigationModelItem>() {
        override fun areItemsTheSame(
            oldItem: NavigationModelItem,
            newItem: NavigationModelItem
        ): Boolean {
            return when {
                oldItem is NavMenuItem && newItem is NavMenuItem ->
                    oldItem.id == newItem.id
                oldItem is NavTaskFolder && newItem is NavTaskFolder ->
                    TaskFolderDiff.areItemsTheSame(oldItem.emailFolder, newItem.emailFolder)
                else -> oldItem == newItem
            }
        }

        override fun areContentsTheSame(
            oldItem: NavigationModelItem,
            newItem: NavigationModelItem
        ): Boolean {
            return when {
                 oldItem is NavMenuItem && newItem is NavMenuItem ->
                     oldItem.icon == newItem.icon &&
                     oldItem.titleRes == newItem.titleRes &&
                     oldItem.checked == newItem.checked
                oldItem is NavTaskFolder && newItem is NavTaskFolder ->
                    TaskFolderDiff.areContentsTheSame(oldItem.emailFolder, newItem.emailFolder)
                else -> false
            }
        }
    }
}