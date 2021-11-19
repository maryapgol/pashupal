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

package com.aztechz.probeez.ui.compose

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aztechz.probeez.R
import com.aztechz.probeez.data.Task
import com.aztechz.probeez.data.TaskStore
import com.aztechz.probeez.data.Tasks
import com.aztechz.probeez.databinding.FragmentComposeBinding
import com.aztechz.probeez.util.DataProcessor
import com.aztechz.probeez.util.SpinnerAdapters
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*
import kotlin.LazyThreadSafetyMode.NONE

/**
 * A [Fragment] which allows for the composition of a new email.
 */
class ComposeFragment : Fragment() {

    private lateinit var binding: FragmentComposeBinding

    private val args: ComposeFragmentArgs by navArgs()

    // The new email being composed.
    private val composeTask: Task by lazy(NONE) {
        // Get the id of the email being replied to, if any, and either create an new empty email
        // or a new reply email.
        val id = args.replyToTaskId
        if (id == -1L) TaskStore.create() else TaskStore.createReplyTo(id)
    }

    private val taskTypes = arrayOf("Personal","Professional")
    private var tasktype = ""

   /* // Handle closing an expanded vendor card when on back is pressed.
    private val closeVendorCardOnBackPressed = object : OnBackPressedCallback(false) {
        var expandedChip: View? = null
        override fun handleOnBackPressed() {
            expandedChip?.let { collapseChip(it) }
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //requireActivity().onBackPressedDispatcher.addCallback(this, closeVendorCardOnBackPressed)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComposeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.run {
            closeIcon.setOnClickListener { findNavController().navigateUp() }
            email = composeTask

            //composeTask.nonUserAccountVendors.forEach { addVendorChip(it) }

            val adapters = SpinnerAdapters(requireContext())

            taskTypeSpinner.adapter = ArrayAdapter(
                taskVendorSpinner.context,
                R.layout.spinner_item_layout,
                taskTypes
            )
            taskTypeSpinner.setSelection(0)

            taskTypeSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        adapterView: AdapterView<*>?,
                        view: View,
                        i: Int,
                        l: Long
                    ) {
                        //Logic here
                        tasktype = taskTypeSpinner.getItemAtPosition(i).toString()
                        if(i == 1){
                            taskVendorSpinner.visibility = View.VISIBLE
                            vendorAddIcon.visibility = View.VISIBLE
                            vendorDivider.visibility = View.VISIBLE
                            taskAmount.visibility = View.VISIBLE
                            amountDivider.visibility = View.VISIBLE
                        }else{
                            taskVendorSpinner.visibility = View.GONE
                            vendorAddIcon.visibility = View.GONE
                            vendorDivider.visibility = View.GONE
                            taskAmount.visibility = View.GONE
                            amountDivider.visibility = View.GONE
                        }
                    }

                    override fun onNothingSelected(adapterView: AdapterView<*>?) {
                        return
                    }
                }

            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select task date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

            taskDateSelector.setOnClickListener {
                datePicker.show(parentFragmentManager, "MATERIAL_DATE_PICKER")
            }

            datePicker.addOnPositiveButtonClickListener {
                taskDateSelector.text = datePicker.headerText
            }


            taskTimeSelector.setOnClickListener {
                // instance of MDC time picker
                val materialTimePicker: MaterialTimePicker = MaterialTimePicker.Builder()
                    // set the title for the alert dialog
                    .setTitleText("SELECT YOUR TIMING")
                    // set the default hour for the
                    // dialog when the dialog opens
                    .setHour(12)
                    // set the default minute for the
                    // dialog when the dialog opens
                    .setMinute(10)
                    // set the time format
                    // according to the region
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .build()

                materialTimePicker.show(parentFragmentManager, "MATERIAL_TIME_PICKER")

                // on clicking the positive button of the time picker
                // dialog update the TextView accordingly
                materialTimePicker.addOnPositiveButtonClickListener {

                    val pickedHour: Int = materialTimePicker.hour
                    val pickedMinute: Int = materialTimePicker.minute

                    // check for single digit hour hour and minute
                    // and update TextView accordingly
                    val formattedTime: String = when {
                        pickedHour > 12 -> {
                            if (pickedMinute < 10) {
                                "${materialTimePicker.hour - 12}:0${materialTimePicker.minute} pm"
                            } else {
                                "${materialTimePicker.hour - 12}:${materialTimePicker.minute} pm"
                            }
                        }
                        pickedHour == 12 -> {
                            if (pickedMinute < 10) {
                                "${materialTimePicker.hour}:0${materialTimePicker.minute} pm"
                            } else {
                                "${materialTimePicker.hour}:${materialTimePicker.minute} pm"
                            }
                        }
                        pickedHour == 0 -> {
                            if (pickedMinute < 10) {
                                "${materialTimePicker.hour + 12}:0${materialTimePicker.minute} am"
                            } else {
                                "${materialTimePicker.hour + 12}:${materialTimePicker.minute} am"
                            }
                        }
                        else -> {
                            if (pickedMinute < 10) {
                                "${materialTimePicker.hour}:0${materialTimePicker.minute} am"
                            } else {
                                "${materialTimePicker.hour}:${materialTimePicker.minute} am"
                            }
                        }
                    }

                    // then update the preview TextView
                    taskTimeSelector.text = formattedTime
                }
            }



            taskVendorSpinner.adapter = adapters.vendorAdapter
            taskVendorSpinner.setSelection(0)

            sendIcon.setOnClickListener{
                saveTask()
                findNavController().navigateUp()
            }

            // TODO: Set up MaterialContainerTransform enterTransition and Slide returnTransition.
        }
    }

    private fun saveTask() {
        val tasks = Tasks(
            title = binding.taskTitleText.toString(),
            type = binding.taskTypeSpinner.selectedItem.toString(),
            vendor = binding.taskVendorSpinner.selectedItem.toString(),
            date = binding.taskDateSelector.text.toString(),
            time = binding.taskTimeSelector.text.toString(),
            amount = binding.taskAmount.toString(),
            details = binding.taskBodyTextView.toString()
        )
        DataProcessor(requireContext()).setObject("task", tasks)
    }


}