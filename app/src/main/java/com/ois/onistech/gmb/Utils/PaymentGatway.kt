package com.ois.onistech.gmb.Utils

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.bitcoin.Api.RetrofitClient
import com.ois.onistech.gmb.Const.ContantValue
import com.ois.onistech.gmb.MainScreen
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.android.synthetic.main.activity_show_cart.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentGatway :PaymentResultListener {


    companion object myCompanionName {
        var order_id: Int = 0
        lateinit var myview: ProgressBar
        lateinit var context: Activity
        lateinit var mem_id: String
        fun startPayment(c: Activity, email: String, mobile: String, price: String) {
            Checkout.preload(c);


            val activity: Activity = c
            val co = Checkout()
            try {
                val options = JSONObject()
                options.put("name", "My Basket")
                options.put("description", "App Payment")
                //  options.put("order_id", "order_DBJOWzybf0aman");//from response of step 3.
                //You can omit the image option to fetch the image from dashboard
                options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png")
                options.put("currency", "INR")
                //  Log.e("SUM",num.toString());
                //val payment: String = "1.5"
                // amount is in paise so please multiple it by 100
                //Payment failed Invalid amount (should be passed in integer paise. Minimum value is 100 paise, i.e. ₹ 1)
                var total = price.toDouble()
                total = total * 100
                options.put("amount", total)
                val preFill = JSONObject()
                preFill.put("email", email)
                preFill.put("contact", mobile)
                options.put("prefill", preFill)
                co.open(activity, options)
            } catch (e: java.lang.Exception) {
                Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }


        fun createOrder(c: Activity, myprogress: ProgressBar, mem_id: String, email: String, mobile: String, price: String, pmm_id: String, weight: String, qty: String, tax: String) {
            myprogress.showProgress(c)
            myview = myprogress
            context = c;
            this.mem_id = mem_id
            RetrofitClient.instance.signleCheckout(mem_id, pmm_id, weight, qty, price, tax)
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Toast.makeText(c, t.message, Toast.LENGTH_LONG).show()
                            // showToast(R.string.server_error,applicationContext)
                        }

                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {


                            //print(response.body().toString())
                            //   Toast.makeText(applicationContext,"" + response.body()!!.string(), Toast.LENGTH_LONG).show()

                            try {

                                var root = JSONArray(response.body()?.string())

                                var rootobj = root.getJSONObject(0).getBoolean("code")

                                System.out.println(rootobj);
                                if (rootobj == true) {

                                    order_id = root.getJSONObject(0).getInt("order_id")
                                    var ses_id = root.getJSONObject(0).getString("ses_id")
                                    var total = root.getJSONObject(0).getString("total")


                                    if (ses_id.equals(mem_id)) {
                                        // checkPaymentStatus=true
                                        startPayment(c, email, mobile, total)

                                    } else {
                                        Toast.makeText(c, "User id not exist", Toast.LENGTH_LONG).show()
                                    }

                                } else {
                                    Toast.makeText(c, "Something went wrong !!!", Toast.LENGTH_LONG).show()
                                }

//                            val layoutManager = LinearLayoutManager(this@ShowCart)
//                            layoutManager.orientation = LinearLayoutManager.VERTICAL
//
//                            recycler_cart.layoutManager = layoutManager

                                myprogress.hideProgress(c)
                            } catch (e: Exception) {
                                Toast.makeText(c, e.message, Toast.LENGTH_LONG).show()
                                // dismissProgress()
                                //   showToast(R.string.catch_exp,applicationContext)
                            }

                        }
                    })

        }

        fun OrderStatus(order_id: Int, payment_id: String, status: String) {
            //recycler_cart.visibility = View.GONE
            //   myprogress.showProgress(this@ShowCart!!)
              myview.showProgress(context)
            RetrofitClient.instance.updatestatus(mem_id, order_id.toString(), payment_id, status)
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            //recycler_cart.visibility = View.VISIBLE
                            Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                            // showToast(R.string.server_error,applicationContext)
                        }

                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                            try {

                                var root = JSONArray(response.body()?.string())

                                var rootobj = root.getJSONObject(0).getBoolean("code")

                                System.out.println(rootobj);

                                if (rootobj == true) {

                                   // Toast.makeText(context, "true", Toast.LENGTH_LONG).show()
                                    /*context.startActivity(Intent(this@ShowCart, MainScreen::class.java)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                                context.finish()*/
                                }
                                else
                                {
                                    //   recycler_cart.visibility = View.VISIBLE
                                    Toast.makeText(context, "Something went wrong !!!", Toast.LENGTH_LONG).show()
                                }

//                            val layoutManager = LinearLayoutManager(this@ShowCart)
//                            layoutManager.orientation = LinearLayoutManager.VERTICAL
//
//                            recycler_cart.layoutManager = layoutManager

                                myview.hideProgress(context)
                            } catch (e: Exception) {
                                //  recycler_cart.visibility = View.VISIBLE
                                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                                // dismissProgress()
                                //   showToast(R.string.catch_exp,applicationContext)
                            }

                        }

                    })

        }

    }

    override fun onPaymentError(p0: Int, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun onPaymentSuccess(p0: String?) {
        TODO("Not yet implemented")
    }
}