package com.ois.onistech.gmb.CartPackage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitcoin.Api.RetrofitClient
import com.ois.onistech.gmb.Const.Constant
import com.ois.onistech.gmb.Const.ContantValue
import com.ois.onistech.gmb.DataClass.Show_cart_Model
import com.ois.onistech.gmb.Interfaces.ItemClick
import com.ois.onistech.gmb.Interfaces.ProductClick
import com.ois.onistech.gmb.Login
import com.ois.onistech.gmb.MainScreen
import com.ois.onistech.gmb.R
import com.ois.onistech.gmb.SessionManager
import com.ois.onistech.gmb.Utils.hideProgress
import com.ois.onistech.gmb.Utils.showProgress
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.android.synthetic.main.activity_show_cart.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*


class ShowCart : AppCompatActivity(), ProductClick,ItemClick, PaymentResultListener {
    lateinit var cartAdapter :CartAdapter

   // var sum :Double = 0.0
   lateinit var mem_id :String
    lateinit var email :String
    lateinit var phone :String
    lateinit var sessionManager: SessionManager
    lateinit var myprogress :ProgressBar
    var  order_id :Int = 0
    var dr :ArrayList<Show_cart_Model>? = null

    var checkPaymentStatus :Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_cart)
        myprogress = findViewById<ProgressBar>(R.id.myprogress)
        sessionManager = SessionManager(applicationContext)
        val user: HashMap<String, String> = sessionManager.userDetails
        mem_id = user[SessionManager.KEY_ID].toString()
         //  Toast.makeText(applicationContext,"" + mem_id.toString(), Toast.LENGTH_LONG).show()
        email = user[SessionManager.KEY_EMAIL].toString()
        phone = user[SessionManager.KEY_MOBILE].toString()
        Checkout.preload(applicationContext);
        pay_now.setOnClickListener {
            var sum:Double= 0.0
            for(item in dr!!){

                sum = sum + item.ct_total.toFloat()

                //  System.out.println("TOTAL "+sum)
                // println(item.ct_price)
            }
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING
            val num=  df.format(sum)



            if (sessionManager.isLoggedIn) {


                createOrderId(num.toString())
            }
            else
            {
                startActivity(Intent(this@ShowCart, Login::class.java))
                finish()
            }
        }
    }


    override fun onResume() {
        super.onResume()
         showCartServer()
    }

    fun showCartServer()
    {
      //  var sess_id="1"
        RetrofitClient.instance.showCart(mem_id)
                .enqueue(object : Callback<List<Show_cart_Model>> {
                    override fun onFailure(call: Call<List<Show_cart_Model>>, t: Throwable) {
                        myprogress.hideProgress(this@ShowCart!!)
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        // showToast(R.string.server_error,applicationContext)
                    }

                    override fun onResponse(call: Call<List<Show_cart_Model>>, response: Response<List<Show_cart_Model>>) {


                        //print(response.body().toString())
                        //   Toast.makeText(applicationContext,"" + response.body()!!.string(), Toast.LENGTH_LONG).show()

                        try {

                            //System.out.println("onResponse.toString(): "+response.body());
//              List<DefaultResponse> dr= (List<DefaultResponse>) response.body();
                            //   val dr: List<DefaultResponse>? = response.body()

                            dr  = response.body() as ArrayList<Show_cart_Model>?
                            if(dr?.get(0)?.ct_id != null)
                            {
                                System.out.println(dr)
                                val layoutManager = LinearLayoutManager(this@ShowCart)
                                layoutManager.orientation = LinearLayoutManager.VERTICAL

                                recycler_cart.layoutManager = layoutManager

                                cartAdapter = CartAdapter(this@ShowCart, dr as ArrayList<Show_cart_Model>?, this@ShowCart,this@ShowCart)
                                recycler_cart.adapter = cartAdapter
                                var sum:Double= 0.0
                                for(item in dr!!){

                                    sum = sum + item.ct_total.toFloat()

                                    //  System.out.println("TOTAL "+sum)
                                    // println(item.ct_price)
                                }
                                bottom.visibility= View.VISIBLE
                                val df = DecimalFormat("#.##")
                                df.roundingMode = RoundingMode.CEILING
                                val num=  df.format(sum)
                                total.setText(num.toString() + " /-")

                            }

                            myprogress.hideProgress(this@ShowCart!!)


                        } catch (e: Exception) {
                            myprogress.hideProgress(this@ShowCart!!)
                            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                            // dismissProgress()
                            //   showToast(R.string.catch_exp,applicationContext)
                        }

                    }

                })
    }

    override fun OnProductClick(sess_id: String?, ct_id: String?, qty: String?, ct_price: String?) {
        AddQuantity(sess_id,ct_id,qty,ct_price)
    }


    fun AddQuantity(sess_id: String?,ct_id: String?,qty: String?, ct_price: String?)
    {
        myprogress.showProgress(this@ShowCart!!)
        RetrofitClient.instance.updateQuantity(sess_id,ct_id,qty,ct_price)
                .enqueue(object : Callback<List<Show_cart_Model>> {
                    override fun onFailure(call: Call<List<Show_cart_Model>>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        // showToast(R.string.server_error,applicationContext)
                    }

                    override fun onResponse(call: Call<List<Show_cart_Model>>, response: Response<List<Show_cart_Model>>) {


                        //print(response.body().toString())
                        //   Toast.makeText(applicationContext,"" + response.body()!!.string(), Toast.LENGTH_LONG).show()

                        try {

                            //System.out.println("onResponse.toString(): "+response.body());
//              List<DefaultResponse> dr= (List<DefaultResponse>) response.body();
                            //   val dr: List<DefaultResponse>? = response.body()

                          dr = response.body() as ArrayList<Show_cart_Model>?
                            System.out.println(dr)

//                            val layoutManager = LinearLayoutManager(this@ShowCart)
//                            layoutManager.orientation = LinearLayoutManager.VERTICAL
//
//                            recycler_cart.layoutManager = layoutManager

                            cartAdapter.setDataSet((dr as ArrayList<Show_cart_Model>?))

                            var sum:Double= 0.0
                            for(item in dr!!){

                                sum = sum + item.ct_total.toDouble()

                                //  System.out.println("TOTAL "+sum)
                                // println(item.ct_price)
                            }
                            bottom.visibility= View.VISIBLE
//
                            val df = DecimalFormat("#.##")
                            df.roundingMode = RoundingMode.CEILING
                            val num=  df.format(sum)
                          //  val result = java.lang.String.format("%.2f", sum)
                            total.setText(num.toString() + " /-")
                         //   total.setText(sum.toString() + " /-")
                           // cartAdapter.notifyDataSetChanged()
                           // cartAdapter = CartAdapter(this@ShowCart, dr, this@ShowCart)

                          //  recycler_cart.adapter = cartAdapter
                            //recycler_cart.setDataSet(newDataList)
                            myprogress.hideProgress(this@ShowCart!!)
                        } catch (e: Exception) {
                            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                            // dismissProgress()
                            //   showToast(R.string.catch_exp,applicationContext)
                        }

                    }

                })
    }



    fun deleteCart(sess_id: String?,ct_id: String?,position: String, ct_price: String?)
    {
        myprogress.showProgress(this@ShowCart!!)
        RetrofitClient.instance.deleteCart(sess_id, ct_id!!)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        // showToast(R.string.server_error,applicationContext)
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {


                        //print(response.body().toString())
                        //   Toast.makeText(applicationContext,"" + response.body()!!.string(), Toast.LENGTH_LONG).show()

                        try {

                            var root = JSONArray(response.body()?.string())

                           var  rootobj = root.getJSONObject(0).getBoolean("status")

                            System.out.println(rootobj);
                            if(rootobj == true)
                            {

                                dr?.removeAt(position.toInt())
                                cartAdapter.notifyItemRemoved(position.toInt());
                                cartAdapter.notifyItemRangeChanged(position.toInt(), dr?.size!!);


                                var sum:Double= 0.0
                                for(item in dr!!){

                                    sum = sum + item.ct_total.toDouble()

                                    //  System.out.println("TOTAL "+sum)
                                    // println(item.ct_price)
                                }
                                bottom.visibility= View.VISIBLE
//
                                val df = DecimalFormat("#.##")
                                df.roundingMode = RoundingMode.CEILING
                                val num=  df.format(sum)
                                //  val result = java.lang.String.format("%.2f", sum)
                                if(sum == 0.0)
                                {
                                    bottom.visibility =View.GONE
                                }
                                else
                                {
                                    total.setText(num.toString() + " /-")
                                }


                            }

//                            val layoutManager = LinearLayoutManager(this@ShowCart)
//                            layoutManager.orientation = LinearLayoutManager.VERTICAL
//
//                            recycler_cart.layoutManager = layoutManager

                           myprogress.hideProgress(this@ShowCart!!)
                        } catch (e: Exception) {
                            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                            // dismissProgress()
                            //   showToast(R.string.catch_exp,applicationContext)
                        }

                    }

                })
    }


    override fun onItemClick(sess_id: String, ct_id: String?, position: String, status: String) {
      //  Toast.makeText(applicationContext, position, Toast.LENGTH_LONG).show()
        deleteCart(sess_id,ct_id,position,status )
    }



    fun createOrderId(num:String)
    {
        myprogress.showProgress(this@ShowCart!!)
        RetrofitClient.instance.checkout(mem_id)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        // showToast(R.string.server_error,applicationContext)
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {


                        //print(response.body().toString())
                        //   Toast.makeText(applicationContext,"" + response.body()!!.string(), Toast.LENGTH_LONG).show()

                        try {

                            var root = JSONArray(response.body()?.string())

                            var  rootobj = root.getJSONObject(0).getBoolean("code")

                            System.out.println(rootobj);
                            if(rootobj == true)
                            {

                                  order_id = root.getJSONObject(0).getInt("order_id")
                                var  ses_id = root.getJSONObject(0).getString("ses_id")


                                if(ses_id.equals(mem_id))
                                {
                                    checkPaymentStatus=true
                                    startPayment(num)
                                }
                                else
                                {
                                    Toast.makeText(applicationContext,"User id not exist",Toast.LENGTH_LONG).show()
                                }

                            }
                            else
                            {
                                Toast.makeText(applicationContext,"Something went wrong !!!",Toast.LENGTH_LONG).show()
                            }

//                            val layoutManager = LinearLayoutManager(this@ShowCart)
//                            layoutManager.orientation = LinearLayoutManager.VERTICAL
//
//                            recycler_cart.layoutManager = layoutManager

                            myprogress.hideProgress(this@ShowCart!!)
                        } catch (e: Exception) {
                            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                            // dismissProgress()
                            //   showToast(R.string.catch_exp,applicationContext)
                        }

                    }

                })
    }


    fun startPayment(num :String)  {
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        val activity: Activity = this
        val co = Checkout()
        try {
            val options = JSONObject()
            options.put("name", "My Basker")
            options.put("description", "App Payment")
          //  options.put("order_id", "order_DBJOWzybf0aman");//from response of step 3.
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png")
            options.put("currency", "INR")
          //  Log.e("SUM",num.toString());
            //val payment: String = "1.5"
            // amount is in paise so please multiple it by 100
            //Payment failed Invalid amount (should be passed in integer paise. Minimum value is 100 paise, i.e. ₹ 1)
            var total = num.toDouble()
            total = total * 100
            options.put("amount", total)
            val preFill = JSONObject()
            preFill.put("email", email)
            preFill.put("contact", phone)
            options.put("prefill", preFill)
            co.open(activity, options)
        } catch (e: java.lang.Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }


     override fun  onPaymentSuccess(s: String) {
        // payment successfull pay_DGU19rDsInjcF2
        Log.e("PAYMENT", " payment successfull $s")
     //   Toast.makeText(this, "Payment successfully done! $s", Toast.LENGTH_SHORT).show()
         OrderStatus(order_id,s,ContantValue.ORDER_STATUS_PLACED)
    }

    override fun onPaymentError(i: Int, s: String) {
        Log.e("PAYMENT", "error code $i -- Payment failed $s")
        try
        {
            Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show()
            OrderStatus(order_id,s,ContantValue.ORDER_STATUS_FAILED)
          //  Toast.makeText(this, "Payment error please try again $i", Toast.LENGTH_SHORT).show()
        }
        catch (e: java.lang.Exception)
        {
            Log.e("OnPaymentError", "Exception in onPaymentError", e)
        }
    }


    fun OrderStatus(order_id:Int,payment_id:String,status: String)
    {
        recycler_cart.visibility = View.GONE
        myprogress.showProgress(this@ShowCart!!)
        RetrofitClient.instance.updatestatus(mem_id,order_id.toString(),payment_id,status)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        recycler_cart.visibility = View.VISIBLE
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        // showToast(R.string.server_error,applicationContext)
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                        try {

                            var root = JSONArray(response.body()?.string())

                            var  rootobj = root.getJSONObject(0).getBoolean("code")

                            System.out.println(rootobj);
                            if(rootobj == true)
                            {


                                startActivity(Intent(this@ShowCart, MainScreen::class.java)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                                finish()
                            }
                            else
                            {
                                recycler_cart.visibility = View.VISIBLE
                                Toast.makeText(applicationContext,"Something went wrong !!!",Toast.LENGTH_LONG).show()
                            }

//                            val layoutManager = LinearLayoutManager(this@ShowCart)
//                            layoutManager.orientation = LinearLayoutManager.VERTICAL
//
//                            recycler_cart.layoutManager = layoutManager

                            myprogress.hideProgress(this@ShowCart!!)
                        } catch (e: Exception) {
                            recycler_cart.visibility = View.VISIBLE
                            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                            // dismissProgress()
                            //   showToast(R.string.catch_exp,applicationContext)
                        }

                    }

                })

    }

}