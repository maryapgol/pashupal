package com.aztechz.probeez.util

import android.content.Context
import android.widget.ArrayAdapter
import com.aztechz.probeez.R

class SpinnerAdapters(val context: Context) {


    val maritalStatusAdapter = ArrayAdapter<String>(
        context,
        R.layout.spinner_item_layout,
        arrayOf(
            context.getString(R.string.marital_status),
            "Single",
            "Married"
        )
    )

    val highestEducationAdapter = ArrayAdapter<String>(
        context,
        R.layout.spinner_item_layout,
        arrayOf(
            context.getString(R.string.qualification),
            "Uneducated",
            "Pre School / SSC",
            "Under Graduate",
            "Graduate",
            "Post Graduate",
            "Professional Graduate",
            "Medical Professional Graduate"
        )
    )

    val vendorAdapter = ArrayAdapter<String>(
        context,
        R.layout.spinner_item_layout,
        arrayOf(
            "Select Vendor",
            "Sumit Kotal",
            "Samadhan Ghadage",
        )
    )




}