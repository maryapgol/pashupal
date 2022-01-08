package com.aztechz.probeez.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBinderMapper
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.aztechz.probeez.R
import com.aztechz.probeez.databinding.FragmentComposeBinding
import com.aztechz.probeez.databinding.FragmentTaskBinding
import com.aztechz.probeez.databinding.FragmentTasksBinding
import com.aztechz.probeez.model.task.TaskListResponseData
import com.aztechz.probeez.model.task.TaskListResponseModel
import com.aztechz.probeez.model.task.TaskResponseModel
import com.aztechz.probeez.ui.task.adapter.TaskListAdapter
import com.aztechz.probeez.util.DataProcessor
import com.aztechz.probeez.utils.CustomProgress
import com.aztechz.probeez.utils.DataState
import com.aztechz.probeez.viewmodel.TaskViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TasksFragment : Fragment() {

    private val  TAG: String = "TasksFragment"
    private var binding: FragmentTasksBinding? = null
    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var taskListAdapter: TaskListAdapter
    private lateinit var arrListTaskListResponseModel: ArrayList<TaskListResponseModel>

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arrListTaskListResponseModel = ArrayList<TaskListResponseModel>()
        taskListAdapter = TaskListAdapter(arrListTaskListResponseModel as List<TaskListResponseData>)
        binding?.rvTaskList?.adapter = taskListAdapter
        binding?.rvTaskList?.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)

        taskViewModel.taskList.observe(viewLifecycleOwner, Observer {
            Log.i(TAG," task list"+it )

            when(it)
            {
                is DataState.Success<TaskListResponseModel> ->
                {
                    CustomProgress.hideProgress()

                    if(it.data.statusCode == "01")
                    {
                        if(!it.data.data.isNullOrEmpty())
                        {
                            arrListTaskListResponseModel.clear()
                            arrListTaskListResponseModel.addAll(it.data.data as Collection<TaskListResponseModel>)
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
                    Log.i(TAG," task error"+it.exception.printStackTrace() )

                }
            }
        })
        val dt = DataProcessor(view.context)

        taskViewModel.getTaskListResponse(dt.getStr("user_id") as String)
    }
}