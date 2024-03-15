package com.learning.agrovision.Activity

import android.app.Activity
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.learning.agrovision.R

open class BaseActivity : AppCompatActivity() {
    var dialog: Dialog?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun Toast(activity: Activity, message:String)
    {
        Toast.makeText(activity,message, Toast.LENGTH_LONG).show()
    }

    fun showProgressbar(activity: Activity)
    {
        dialog= Dialog(this)
        dialog!!.setContentView(R.layout.progress_bar)
        dialog!!.show()

    }
    fun cancelProgressbar()
    {
        if(dialog!=null)
        {
            dialog!!.cancel()
            dialog=null
        }
    }
}