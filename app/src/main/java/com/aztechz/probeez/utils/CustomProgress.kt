package com.aztechz.probeez.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import com.aztechz.probeez.R

object CustomProgress {
    private var mDialog: Dialog? = null

/*    public static CustomProgress getInstance() {
        if (customProgress == null) {
            customProgress = new CustomProgress();
        }
        return customProgress;
    }*/

    fun showProgress(context: Context,  message: String,  cancelable: Boolean) {
        mDialog =  Dialog(context, R.style.TranslucentDialog)
        // no tile for the dialog
        mDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog?.setContentView(R.layout.progress_dialog)
        mDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val mProgressBar =  mDialog?.findViewById<ProgressBar>(R.id.progress_bar)
        //  mProgressBar.getIndeterminateDrawable().setColorFilter(context.getResources()
        // .getColor(R.color.material_blue_gray_500), PorterDuff.Mode.SRC_IN);

        mProgressBar?.visibility = View.VISIBLE;
        // you can change or add this line according to your need
        mProgressBar?.isIndeterminate = true
        mDialog?.setCancelable(cancelable)
        mDialog?.setCanceledOnTouchOutside(cancelable)
        mDialog?.show()
    }

    fun hideProgress() {
        if (mDialog != null) {
            mDialog?.dismiss()
            mDialog = null
        }
    }



}