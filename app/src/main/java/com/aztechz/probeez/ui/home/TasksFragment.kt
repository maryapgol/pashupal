package com.aztechz.probeez.ui.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsClient.getPackageName
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.aztechz.probeez.MainActivity
import com.aztechz.probeez.databinding.FragmentTasksBinding
import com.aztechz.probeez.model.reports.GenerateReport
import com.aztechz.probeez.model.reports.ReportRequestModel
import com.aztechz.probeez.model.task.OnTaskSelectListener
import com.aztechz.probeez.model.task.TaskListResponseData
import com.aztechz.probeez.model.task.TaskListResponseModel
import com.aztechz.probeez.retrofit.RetrofitClient
import com.aztechz.probeez.ui.task.adapter.TaskListAdapter
import com.aztechz.probeez.util.DataProcessor
import com.aztechz.probeez.utils.CustomProgress
import com.aztechz.probeez.utils.DataState
import com.aztechz.probeez.viewmodel.ReportViewModel
import com.aztechz.probeez.viewmodel.TaskViewModel
import com.facebook.FacebookSdk.getApplicationContext
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TasksFragment : Fragment() {

    private val TAG: String = "TasksFragment"
    private var binding: FragmentTasksBinding? = null
    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var taskListAdapter: TaskListAdapter
    private lateinit var arrListTaskListResponseModel: ArrayList<TaskListResponseData>
    private val reportViewModel: ReportViewModel by viewModels()
    private var venderId = "emptyvendor"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 101);

        arrListTaskListResponseModel = ArrayList<TaskListResponseData>()
        taskListAdapter = TaskListAdapter(
            arrListTaskListResponseModel as List<TaskListResponseData>,
            onTaskSelectListener
        )
        binding?.rvTaskList?.adapter = taskListAdapter
        binding?.rvTaskList?.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        taskViewModel.taskList.observe(viewLifecycleOwner, Observer {
            Log.i(TAG, " task list" + it)

            when (it) {
                is DataState.Success<TaskListResponseModel> -> {
                    CustomProgress.hideProgress()

                    if (it.data.statusCode == "01") {
                        if (!it.data.data.isNullOrEmpty()) {
                            arrListTaskListResponseModel.clear()
                            arrListTaskListResponseModel?.addAll(it.data.data as Collection<TaskListResponseData>)
                            taskListAdapter.notifyDataSetChanged()
                        }
                    }
                }

                is DataState.Loading -> {
                    CustomProgress.showProgress(activity as Context, false)

                }

                is DataState.Error -> {
                    CustomProgress.hideProgress()

                    Snackbar.make(
                        binding?.root as View,
                        it.exception.message.toString(),
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                    Log.i(TAG, " task error" + it.exception.printStackTrace())

                }
            }
        })

        reportViewModel.report.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataState.Success<ResponseBody> -> {
                    CustomProgress.hideProgress()
                    println("Reponse Body: " + it.data.contentType())

                }

                is DataState.Loading -> {
                    CustomProgress.showProgress(activity as Context, false)

                }

                is DataState.Error -> {
                    CustomProgress.hideProgress()

                    Snackbar.make(
                        binding?.root as View,
                        it.exception.message.toString(),
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                    Log.i(TAG, " task error" + it.exception.printStackTrace())

                }
            }
        })

        val dt = DataProcessor(view.context)

        taskViewModel.getTaskListResponse(dt.getStr("user_id") as String)
    }

    private val onTaskSelectListener = object : OnTaskSelectListener {

        override fun onTaskSelected(position: Int) {
            if(venderId=="emptyvendor")
            {
                venderId = arrListTaskListResponseModel.get(position).vendorId.toString()
                arrListTaskListResponseModel.get(position).isSelected =  !(arrListTaskListResponseModel.get(
                    position
                ).isSelected?:false)
            }else{
                if(venderId == arrListTaskListResponseModel.get(position).vendorId)
                {
                    arrListTaskListResponseModel.get(position).isSelected =  !(arrListTaskListResponseModel.get(
                        position
                    ).isSelected?:false)
                }else{
                    Snackbar.make(
                        binding?.framemain?.rootView as View,
                        "Multiple select needs same vendor",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
            taskListAdapter.notifyDataSetChanged()
            var found = false
            for (taskList in arrListTaskListResponseModel) {
                if (taskList.isSelected == true) {
                    found = true
                    break
                }
            }
            if(!found)
            {
                venderId = "emptyvendor"
            }
            (activity as MainActivity).setReportVisibility(found)
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
     fun generateReport(generateReport: GenerateReport)
    {
       var arrListTask = ArrayList<String>()
       for(task in arrListTaskListResponseModel)
       {
           arrListTask.add(task._id.toString())
       }
        val reportRequestModel = ReportRequestModel(
            arrListTaskListResponseModel.get(0).vendorId, arrListTaskListResponseModel.get(
                0
            ).userId, arrListTask
        )
        val gson = GsonBuilder().setLenient().create()
        //reportViewModel.getReport(reportRequestModel)
        CustomProgress.showProgress(activity as Activity, true)
        val logging = HttpLoggingInterceptor()
// set your desired log level
// set your desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()

// add your other interceptors …
// add logging as last interceptor
        httpClient.addInterceptor(logging) // <-- this is the important line!
        httpClient.connectTimeout(100, TimeUnit.SECONDS)
        httpClient.readTimeout(100, TimeUnit.SECONDS)
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://13.126.31.168/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
        val api = retrofit.create(RetrofitClient::class.java)
        val call = api.generateReport(reportRequestModel)
        call.enqueue(object : Callback<ResponseBody?> {

            override fun onResponse(call: Call<ResponseBody?>?, response: Response<ResponseBody?>) {
                //val post = JsonObject()[response.body().toString()].asJsonObject

                if (response.isSuccessful) {

                    /*binding?.clProgress?.visibility = View.VISIBLE
                    downloadPdfTask = DownloadPdfTask()
                    downloadPdfTask?.execute(response.body())*/
                    object : AsyncTask<Void?, Long?, Void?>() {

                        override fun doInBackground(vararg p0: Void?): Void? {
                            response.body()?.let { saveToDisk(it) }
                            return null
                        }
                    }.execute()
                } else {
                    CustomProgress.hideProgress()
                }
                Log.i("TasksFragment", "Report: " + response.body())
                /* if (post["Level"].asString.contains("Administrator")) {
                }*/
            }

            override fun onFailure(call: Call<ResponseBody?>?, t: Throwable?) {
                print("Report: " + t?.printStackTrace())
                CustomProgress.hideProgress()

            }
        })
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)

    }



    private fun askForPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                activity as Activity,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity as Activity,
                    permission
                )
            ) {
                ActivityCompat.requestPermissions(
                    activity as Activity,
                    arrayOf(permission),
                    requestCode
                )
            } else {
                ActivityCompat.requestPermissions(
                    activity as Activity,
                    arrayOf(permission),
                    requestCode
                )
            }
        } else if (ContextCompat.checkSelfPermission(
                activity as Activity,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            Toast.makeText(getApplicationContext(), "Permission was denied", Toast.LENGTH_SHORT)
                .show()
        }
    }


    private fun saveToDisk(body: ResponseBody) {
        try {
            val destinationFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),Date().time.toString()+".pdf")
            var `is`: InputStream? = null
            var os: OutputStream? = null
            try {
                Log.d(TAG, "File Size=" + body.contentLength())
                `is` = body.byteStream()
                os = FileOutputStream(destinationFile)
                val data = ByteArray(4096)
                var count: Int
                var progress = 0
                while (`is`.read(data).also { count = it } != -1) {
                    os.write(data, 0, count)
                    progress += count
                    Log.d(
                        TAG,
                        "Progress: " + progress + "/" + body.contentLength() + " >>>> " + progress.toFloat() / body.contentLength()
                    )
                }
                os.flush()
                CustomProgress.hideProgress()

                Log.d(TAG, "File saved successfully!"+destinationFile.absolutePath)
                return
            } catch (e: IOException) {
                e.printStackTrace()
                Log.d(TAG, "Failed to save the file!")
                return
            } finally {
                `is`?.close()
                os?.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d(TAG, "Failed to save the file!")
            return
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ActivityCompat.checkSelfPermission(activity as Activity, permissions[0]) == PackageManager.PERMISSION_GRANTED) {

            if (requestCode == 101)
                Toast.makeText(activity as Activity, "Permission granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity as Activity, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }
}