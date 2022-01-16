package com.aztechz.probeez.ui.task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.aztechz.probeez.R
import com.aztechz.probeez.databinding.ActivityTaskBinding
import com.aztechz.probeez.model.task.TaskRequestModel
import com.aztechz.probeez.model.task.TaskResponseModel
import com.aztechz.probeez.model.vendor.AddVendorResponseModel
import com.aztechz.probeez.model.vendor.VendorListData
import com.aztechz.probeez.model.vendor.VendorListResponseModel
import com.aztechz.probeez.repository.vendor.AddVendorRequestModel
import com.aztechz.probeez.util.DataProcessor
import com.aztechz.probeez.util.SpinnerAdapters
import com.aztechz.probeez.utils.CustomProgress
import com.aztechz.probeez.utils.DataState
import com.aztechz.probeez.viewmodel.TaskViewModel
import com.aztechz.probeez.viewmodel.VendorViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskBinding
    private val taskViewModel: TaskViewModel by viewModels()
    private val vendorViewModel: VendorViewModel by viewModels()
    private val taskTypes = arrayOf("Personal", "Professional")
    private var tasktype = ""
    private lateinit var alertDialog: android.app.AlertDialog
    private var strVendorId: String? = null
    private var isProfessionalSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(findViewById(R.id.toolbar_main))
        taskViewModel.task.observe(this@TaskActivity, androidx.lifecycle.Observer {

            when (it) {
                is DataState.Success<TaskResponseModel> -> {
                    CustomProgress.hideProgress()
                    Log.i("ComposeFragment", " " + it.data)
                    if (it.data.statusCode == "001") {
                        Snackbar.make(
                            binding.root,
                            it.data.message.toString(),
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        Snackbar.make(
                            binding.root,
                            it.data.message.toString(),
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                is DataState.Loading -> {
                    CustomProgress.showProgress(this@TaskActivity, false)

                }

                is DataState.Error -> {
                    CustomProgress.hideProgress()

                    Snackbar.make(
                        binding.root,
                        it.exception.message.toString(),
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }

            }

        })

        vendorViewModel.vendor.observe(this@TaskActivity, androidx.lifecycle.Observer {
            if (alertDialog != null) {
                alertDialog.dismiss()
            }
            when (it) {


                is DataState.Success<AddVendorResponseModel> -> {
                    CustomProgress.hideProgress()
                    vendorViewModel.getVendorList(DataProcessor(this@TaskActivity).getStr("user_id").toString())

                    Log.i("ComposeFragment", " " + it.data)
                    if (it.data.statusCode == "001") {
                        Snackbar.make(
                            binding.root,
                            it.data.message.toString(),
                            Snackbar.LENGTH_SHORT
                        )
                            .show()

                    } else {
                        Snackbar.make(
                            binding.root,
                            it.data.message.toString(),
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                is DataState.Loading -> {
                    CustomProgress.showProgress(this@TaskActivity, false)

                }

                is DataState.Error -> {
                    CustomProgress.hideProgress()

                    Snackbar.make(
                        binding.root,
                        it.exception.message.toString(),
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }

            }

        })

        vendorViewModel.vendorList.observe(this@TaskActivity, androidx.lifecycle.Observer {

            when (it) {


                is DataState.Success<VendorListResponseModel> -> {
                    CustomProgress.hideProgress()
                    if (it.data.statusCode == "01") {
                        val arrListVendor = ArrayList<VendorListData>()
                        val vendor = VendorListData(
                            0,
                            "0",
                            "",
                            "",
                            "Select Vendor"
                        )
                        arrListVendor.add(vendor)
                        arrListVendor.addAll(it.data.data as Collection<VendorListData>)
                        Log.i("TaskActivityResponse", "Task List: " + arrListVendor)

                        val vendorAdapter = ArrayAdapter<VendorListData>(
                            this@TaskActivity,
                            R.layout.spinner_item_layout,
                            arrListVendor as MutableList<VendorListData>
                        )
                        binding.taskVendorSpinner.adapter = vendorAdapter
                        binding.taskVendorSpinner.setSelection(0)

                    } else {
                        Snackbar.make(
                            binding.root,
                            it.data.message.toString(),
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                is DataState.Loading -> {
                    CustomProgress.showProgress(this@TaskActivity, false)

                }

                is DataState.Error -> {
                    CustomProgress.hideProgress()

                    Snackbar.make(
                        binding.root,
                        it.exception.message.toString(),
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                    Log.i("TaskActivity","Error vendor: "+it.exception.printStackTrace())
                }

            }

        })

        binding.run {
            /* closeIcon.setOnClickListener { findNavController().navigateUp() }
             email = composeTask*/

            //composeTask.nonUserAccountVendors.forEach { addVendorChip(it) }

            val adapters = SpinnerAdapters(this@TaskActivity)

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
                        if (i == 1) {
                            isProfessionalSelected = true
                            taskVendorSpinner.visibility = View.VISIBLE
                            vendorAddIcon.visibility = View.VISIBLE
                            vendorDivider.visibility = View.VISIBLE
                            taskAmount.visibility = View.VISIBLE
                            amountDivider.visibility = View.VISIBLE
                            //fetch vendor list
                            vendorViewModel.getVendorList(DataProcessor(this@TaskActivity).getStr("user_id").toString())
                        } else {
                            isProfessionalSelected = false
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

                datePicker.show(supportFragmentManager, "SELECT DATE")
            }

            datePicker.addOnPositiveButtonClickListener {

                Log.i("ComposeFragment", "data set: " + datePicker.headerText)
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

                materialTimePicker.show(supportFragmentManager, "SELECT TIME")

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

            vendorAddIcon.setOnClickListener {
                val builder: android.app.AlertDialog.Builder =
                    android.app.AlertDialog.Builder(this@TaskActivity)
                val viewGroup = findViewById<ViewGroup>(android.R.id.content)
                val dialogView: View = LayoutInflater.from(it.context)
                    .inflate(R.layout.custom_dialog_add_vendor, viewGroup, false)
                val btnAddVendor = dialogView.findViewById<MaterialButton>(R.id.btnAddVendor)
                val edtEnterVendor = dialogView.findViewById<EditText>(R.id.edtVendorName)
                btnAddVendor.setOnClickListener {
                    if (!edtEnterVendor.text.toString().isNullOrEmpty()) {
                        val addVendorRequestModel = AddVendorRequestModel(
                            edtEnterVendor.text.toString(),
                            DataProcessor(this@TaskActivity).getStr("user_id")
                        )
                        vendorViewModel.addVendor(addVendorRequestModel)
                    } else {
                        Snackbar.make(
                            binding.root,
                            resources.getString(R.string.plz_enter_vendor_name),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                }
                builder.setView(dialogView)
                alertDialog = builder.create()
                alertDialog.show()
            }
            /*   vendor_add_icon.setOnClickListener {

               }
               */



            btnAddTask.setOnClickListener {
                saveTask()
                // findNavController().navigateUp()
            }

            // TODO: Set up MaterialContainerTransform enterTransition and Slide returnTransition.
        }

        binding.taskVendorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val vendorList = parent?.getItemAtPosition(position) as VendorListData
                Log.i("TaskActivity","On item Selected:"+vendorList._id)
                Log.i("TaskActivity","Version:"+vendorList.__v)

                if(!vendorList._id.isNullOrEmpty())
                  {
                      strVendorId = vendorList._id
                  }
            }

        }

    }

    private fun getFormattedDate(inputValue: String): String {
        val inputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        var date: Date? = null
        try {
            date = inputFormat.parse(inputValue)
            return outputFormat.format(date)

        } catch (e: ParseException) {
            Snackbar.make(
                binding.root,
                resources.getString(R.string.select_date_time),
                Snackbar.LENGTH_SHORT
            )
                .show()
        }

        return ""

    }

    private fun saveTask() {
//binding.taskTimeSelector.text.toString() ass this wen fixed time format
        if(isProfessionalSelected && strVendorId.isNullOrEmpty()) {
            Snackbar.make(
                binding.root,
                resources.getString(R.string.select_vendor),
                Snackbar.LENGTH_SHORT
            )
                .show()
            return
        }
        val dataProcessor = DataProcessor(this@TaskActivity)
        if (getFormattedDate(binding.taskDateSelector.text.toString()).isNullOrEmpty()) {
            Snackbar.make(
                binding.root,
                resources.getString(R.string.enter_valid_date_time),
                Snackbar.LENGTH_SHORT
            )
                .show()
            return
        }
        val tasks = TaskRequestModel(
            binding.taskBodyTextView.text.toString(),
            getFormattedDate(binding.taskDateSelector.text.toString()),
            "",
            binding.taskTitleText.text.toString(),
            binding.taskTypeSpinner.selectedItem.toString(),
            dataProcessor.getStr("user_id").toString(),
            strVendorId!!
        )
        Log.i("ComposeFragment", "Request: " + tasks)
        taskViewModel.addTask(tasks)
        /*  val tasks = Tasks(
              title = binding.taskTitleText.toString(),
              type = binding.taskTypeSpinner.selectedItem.toString(),
              vendor = binding.taskVendorSpinner.selectedItem.toString(),
              date = binding.taskDateSelector.text.toString(),
              time = binding.taskTimeSelector.text.toString(),
              amount = binding.taskAmount.toString(),
              details = binding.taskBodyTextView.toString()
          )*/


        // DataProcessor(requireContext()).setObject("task", tasks)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}