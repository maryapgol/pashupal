package com.aztechz.probeez.ui.profile.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aztechz.probeez.R
import com.aztechz.probeez.data.Topic
import com.aztechz.probeez.model.profile.OnInterestSelectListener
import com.aztechz.probeez.utils.Utility
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.rv_interest_list.view.*

class InterestAdapter(
    private val activity: Activity,
    private val arrList: ArrayList<Topic?>?,
    private val onItemSelectedListener: OnInterestSelectListener
): RecyclerView.Adapter<InterestAdapter.InterestViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_interest_list,parent,false)
        return InterestViewHolder(view)
    }

    override fun onBindViewHolder(holder: InterestViewHolder, position: Int) {
        val topic = arrList?.get(position)
        Glide.with(activity)
            .asBitmap()
            .load(topic?.imageUrl)
            .placeholder(R.drawable.course_image_placeholder)
            .into(
                holder.itemView.imgInterest
            )
        holder.itemView.txtCourse.text = topic?.name
        if(topic?.courses ?:0 > 1)
        {
            holder.itemView.txtUser.text = "${topic?.courses.toString()} users"
        }else{
            holder.itemView.txtUser.text = "${topic?.courses.toString()} user"
        }

        if(topic?.isSelected == false)
        {
            holder.itemView.conSelected.visibility = View.GONE
        }else{
            holder.itemView.conSelected.visibility = View.VISIBLE


        }

        holder.itemView.setOnClickListener {
            onItemSelectedListener.onInterestSelected(position)
        }

        //set fonts
        holder.itemView.txtCourse.typeface = Utility.fontMedium
        holder.itemView.txtUser.typeface = Utility.fontRegular

    }

    override fun getItemCount(): Int {
       return arrList?.size ?:0
    }

    class InterestViewHolder(viewHolder: View): RecyclerView.ViewHolder(viewHolder)

}