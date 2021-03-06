package com.aztechz.probeez.ui.task.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aztechz.probeez.R
import com.aztechz.probeez.model.task.OnTaskSelectListener
import com.aztechz.probeez.model.task.TaskListResponseData
import com.aztechz.probeez.utils.Utility
import java.text.SimpleDateFormat
import java.util.*

class TaskListAdapter(private val arrListTaskListResponseModel: List<TaskListResponseData>, private val onTaskSelectListener: OnTaskSelectListener): RecyclerView.Adapter<TaskListAdapter.TaskListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_task_list, parent, false)
        return TaskListHolder(view)
    }

    override fun onBindViewHolder(holder: TaskListHolder, position: Int) {
        val task = arrListTaskListResponseModel[position]
        val txtTaskName = holder.itemView.findViewById<TextView>(R.id.txtTaskName)
        val txtVendor = holder.itemView.findViewById<TextView>(R.id.txtVendor)
        val txtDateTime = holder.itemView.findViewById<TextView>(R.id.txtDateTime)
        txtTaskName.text = task.title
        txtVendor.text = task.type
        txtDateTime.text = getFormattedDate(task.taskDate.toString())
        txtTaskName.typeface = Utility.fontMedium
        txtVendor.typeface = Utility.fontRegular
        txtDateTime.typeface = Utility.fontRegular
        holder.itemView.setBackgroundColor(if (task.isSelected == true) Color.CYAN else Color.WHITE)

        holder.itemView.setOnClickListener {
            onTaskSelectListener.onTaskSelected(position)

        }

    }

    private fun getFormattedDate(inputValue: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        val date = inputFormat.parse(inputValue)
        return outputFormat.format(date)
    }

    override fun getItemCount(): Int {
        return arrListTaskListResponseModel.size
    }

    class TaskListHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}