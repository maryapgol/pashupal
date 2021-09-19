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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aztechz.probeez.R
import com.aztechz.probeez.data.EmailStore
import com.aztechz.probeez.ui.home.Mailbox

/**
 * A class which maintains and generates a navigation list to be displayed by [NavigationAdapter].
 */
object NavigationModel {

    const val HOME_ID = 0
    const val PROFILE_ID = 1
    const val TASKS_ID = 2
    const val REPORTS_ID = 3
    const val SETTINGS_ID = 4

    private var navigationMenuItems = mutableListOf(
        NavigationModelItem.NavMenuItem(
            id = HOME_ID,
            icon = R.drawable.ic_twotone_inbox,
            titleRes = R.string.navigation_home,
            checked = false,
            mailbox = Mailbox.HOME
        ),
        NavigationModelItem.NavMenuItem(
            id = PROFILE_ID,
            icon = R.drawable.ic_twotone_stars,
            titleRes = R.string.navigation_profile,
            checked = false,
            mailbox = Mailbox.PROFILE
        ),
        NavigationModelItem.NavMenuItem(
            id = TASKS_ID,
            icon = R.drawable.ic_twotone_send,
            titleRes = R.string.navigation_tasks,
            checked = false,
            mailbox = Mailbox.TASKS
        ),
        NavigationModelItem.NavMenuItem(
            id = REPORTS_ID,
            icon = R.drawable.ic_twotone_delete,
            titleRes = R.string.navigation_reports,
            checked = false,
            mailbox = Mailbox.REPORTS
        ),
        NavigationModelItem.NavMenuItem(
            id = SETTINGS_ID,
            icon = R.drawable.ic_twotone_error,
            titleRes = R.string.navigation_settings,
            checked = false,
            mailbox = Mailbox.SETTINGS
        )
    )

    private val _navigationList: MutableLiveData<List<NavigationModelItem>> = MutableLiveData()
    val navigationList: LiveData<List<NavigationModelItem>>
        get() = _navigationList

    init {
        postListUpdate()
    }

    /**
     * Set the currently selected menu item.
     *
     * @return true if the currently selected item has changed.
     */
    fun setNavigationMenuItemChecked(id: Int): Boolean {
        var updated = false
        navigationMenuItems.forEachIndexed { index, item ->
            val shouldCheck = item.id == id
            if (item.checked != shouldCheck) {
                navigationMenuItems[index] = item.copy(checked = shouldCheck)
                updated = true
            }
        }
        if (updated) postListUpdate()
        return updated
    }

    private fun postListUpdate() {
        val newList = navigationMenuItems +
            (NavigationModelItem.NavDivider("Folders")) +
            EmailStore.getAllFolders().map { NavigationModelItem.NavEmailFolder(it) }

        _navigationList.value = newList
    }
}

