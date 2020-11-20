package com.ois.onistech.gmb.Utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import com.ois.onistech.gmb.ShowProduct.ShowPro

fun View.hideProgress(c: Activity)
{
   // c.v.
   // v.visibility = View.GONE
    this.visibility = View.GONE
    c.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

}

fun View.showProgress(c:Activity)
{
    this.visibility = View.VISIBLE
    c.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun payment(c:Activity)
{
    Toast.makeText(c,"hello",Toast.LENGTH_LONG).show()
}