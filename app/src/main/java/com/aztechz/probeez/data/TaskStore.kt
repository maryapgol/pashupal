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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.aztechz.probeez.R
import com.aztechz.probeez.ui.home.Mailbox

/**
 * A static data store of [Task]s.
 */
object TaskStore {

    private val allTasks = mutableListOf(
        Task(
            0L,
            AccountStore.getContactAccountById(9L),
            listOf(AccountStore.getDefaultUserAccount()),
            "Package shipped!",
            """
                Cucumber Mask Facial has shipped.

                Keep an eye out for a package to arrive between this Thursday and next Tuesday. If for any reason you don't receive your package before the end of next week, please reach out to us for details on your shipment.

                As always, thank you for shopping with us and we hope you love our specially formulated Cucumber Mask!
            """.trimIndent(),
            isStarred = true
        ),
        Task(
            1L,
            AccountStore.getContactAccountById(6L),
            listOf(AccountStore.getDefaultUserAccount()),
            "Brunch this weekend?",
            """
                I'll be in your neighborhood doing errands and was hoping to catch you for a coffee this Saturday. If you don't have anything scheduled, it would be great to see you! It feels like its been forever.

                If we do get a chance to get together, remind me to tell you about Kim. She stopped over at the house to say hey to the kids and told me all about her trip to Mexico.

                Talk to you soon,

                Ali
            """.trimIndent()
        ),
        Task(
            2L,
            AccountStore.getContactAccountById(5L),
            listOf(AccountStore.getDefaultUserAccount()),
            "Bonjour from Paris",
            "Here are some great shots from my trip...",
            listOf(
                TaskAttachment(R.drawable.paris_1, "Bridge in Paris"),
                TaskAttachment(R.drawable.paris_2, "Bridge in Paris at night"),
                TaskAttachment(R.drawable.paris_3, "City street in Paris"),
                TaskAttachment(R.drawable.paris_4, "Street with bike in Paris")
            ),
            true
        ),
        Task(
            3L,
            AccountStore.getContactAccountById(8L),
            listOf(AccountStore.getDefaultUserAccount()),
            "High school reunion?",
            """
                Hi friends,

                I was at the grocery store on Sunday night.. when I ran into Genie Williams! I almost didn't recognize her afer 20 years!

                Anyway, it turns out she is on the organizing committee for the high school reunion this fall. I don't know if you were planning on going or not, but she could definitely use our help in trying to track down lots of missing alums. If you can make it, we're doing a little phone-tree party at her place next Saturday, hoping that if we can find one person, thee more will...
            """.trimIndent(),
            mailbox = Mailbox.TASKS
        ),
        Task(
            4L,
            AccountStore.getContactAccountById(11L),
            listOf(
                AccountStore.getDefaultUserAccount(),
                AccountStore.getContactAccountById(8L),
                AccountStore.getContactAccountById(5L)
            ),
            "Brazil trip",
            """
                Thought we might be able to go over some details about our upcoming vacation.

                I've been doing a bit of research and have come across a few paces in Northern Brazil that I think we should check out. One, the north has some of the most predictable wind on the planet. I'd love to get out on the ocean and kitesurf for a couple of days if we're going to be anywhere near or around Taiba. I hear it's beautiful there and if you're up for it, I'd love to go. Other than that, I haven't spent too much time looking into places along our road trip route. I'm assuming we can find places to stay and things to do as we drive and find places we think look interesting. But... I know you're more of a planner, so if you have ideas or places in mind, lets jot some ideas down!

                Maybe we can jump on the phone later today if you have a second.
            """.trimIndent(),
            isStarred = true
        ),
        Task(
            5L,
            AccountStore.getContactAccountById(13L),
            listOf(AccountStore.getDefaultUserAccount()),
            "Update to Your Itinerary",
            ""
        ),
        Task(
            6L,
            AccountStore.getContactAccountById(10L),
            listOf(AccountStore.getDefaultUserAccount()),
            "Recipe to try",
            "Raspberry Pie: We should make this pie recipe tonight! The filling is " +
                "very quick to put together.",
            mailbox = Mailbox.TASKS
        ),
        Task(
            7L,
            AccountStore.getContactAccountById(9L),
            listOf(AccountStore.getDefaultUserAccount()),
            "Delivered",
            "Your shoes should be waiting for you at home!"
        ),
        Task(
          8L,
          AccountStore.getContactAccountById(13L),
          listOf(AccountStore.getDefaultUserAccount()),
          "Your update on Google Play Store is live!",
          """
              Your update, 0.1.1, is now live on the Play Store and available for your alpha users to start testing.
              
              Your alpha testers will be automatically notified. If you'd rather send them a link directly, go to your Google Play Console and follow the instructions for obtaining an open alpha testing link.
          """.trimIndent(),
          mailbox = Mailbox.REPORTS
        ),
        Task(
          10L,
          AccountStore.getContactAccountById(5L),
          listOf(AccountStore.getDefaultUserAccount()),
          "Try a free TrailGo account",
          """
            Looking for the best hiking trails in your area? TrailGo gets you on the path to the outdoors faster than you can pack a sandwich. 
            
            Whether you're an experienced hiker or just looking to get outside for the afternoon, there's a segment that suits you.
          """.trimIndent(),
          mailbox = Mailbox.REPORTS
        ),
        Task(
          10L,
          AccountStore.getContactAccountById(5L),
          listOf(AccountStore.getDefaultUserAccount()),
          "Free money",
          """
            You've been selected as a winner in our latest raffle! To claim your prize, click on the link.
          """.trimIndent(),
          mailbox = Mailbox.REPORTS
        )
    )

    private val __TASKS: MutableLiveData<List<Task>> = MutableLiveData()

    private val home: LiveData<List<Task>>
        get() = Transformations.map(__TASKS) { emails ->
            emails.filter { it.mailbox == Mailbox.HOME }
        }

    private val profile: LiveData<List<Task>>
        get() = Transformations.map(__TASKS) { emails ->
            emails.filter { it.isStarred }
        }

    private val tasks: LiveData<List<Task>>
        get() = Transformations.map(__TASKS) { emails ->
            emails.filter { it.mailbox == Mailbox.TASKS }
        }

    private val reports: LiveData<List<Task>>
        get() = Transformations.map(__TASKS) { emails ->
            emails.filter { it.mailbox == Mailbox.REPORTS }
        }

    init {
        __TASKS.value = allTasks
    }

    fun getTasks(mailbox: Mailbox): LiveData<List<Task>> {
        return when (mailbox) {
            Mailbox.HOME -> home
            Mailbox.PROFILE -> profile
            Mailbox.TASKS -> tasks
            Mailbox.REPORTS -> reports
        }
    }

    /**
     * Get an [Task] with the given [id].
     */
    fun get(id: Long): Task? {
        return allTasks.firstOrNull { it.id == id }
    }

    /**
     * Create a new, blank [Task].
     */
    fun create(): Task {
        return Task(
            System.nanoTime(), // Unique ID generation.
            AccountStore.getDefaultUserAccount()
        )
    }

    /**
     * Create a new [Task] that is a reply to the email with the given [replyToId].
     */
    fun createReplyTo(replyToId: Long): Task {
        val replyTo = get(replyToId) ?: return create()
        return Task(
            id = System.nanoTime(),
            sender = replyTo.vendors.firstOrNull() ?: AccountStore.getDefaultUserAccount(),
            vendors = listOf(replyTo.sender) + replyTo.vendors,
            subject = replyTo.subject,
            isStarred = replyTo.isStarred,
            isImportant = replyTo.isImportant
        )
    }

    /**
     * Delete the [Task] with the given [id].
     */
    fun delete(id: Long) {
        update(id) { mailbox = Mailbox.REPORTS }
    }

    /**
     * Update the [Task] with the given [id] by applying all mutations from [with].
     */
    fun update(id: Long, with: Task.() -> Unit) {
        allTasks.find { it.id == id }?.let {
            it.with()
            __TASKS.value = allTasks
        }
    }

    /**
     * Get a list of [TaskFolder]s by which [Task]s can be categorized.
     */
    fun getAllFolders() = listOf(
        "Images",
        "Videos",
        "Documents",
        "Links"
    )
}

