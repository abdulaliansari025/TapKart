package com.ois.onistech.gmb.Order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitcoin.Api.RetrofitClient
import com.ois.onistech.gmb.CartPackage.CartAdapter
import com.ois.onistech.gmb.DataClass.OrderModel
import com.ois.onistech.gmb.DataClass.Show_cart_Model
import com.ois.onistech.gmb.Interfaces.ItemClick
import com.ois.onistech.gmb.Interfaces.OrderRating
import com.ois.onistech.gmb.Interfaces.ProductClick
import com.ois.onistech.gmb.R
import com.ois.onistech.gmb.SessionManager
import com.ois.onistech.gmb.Utils.hideProgress
import com.ois.onistech.gmb.Utils.showProgress
import com.ois.onistech.gmb.databinding.ActivityMyOrdersBinding

import kotlinx.android.synthetic.main.activity_my_orders.*
import kotlinx.android.synthetic.main.activity_show_cart.*
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.ArrayList
import java.util.HashMap

class MyOrders : AppCompatActivity(),OrderRating {
    lateinit var myprogress :ProgressBar
    private lateinit var binding : ActivityMyOrdersBinding
    lateinit var sessionManager: SessionManager
    var dr :ArrayList<OrderModel>? = null
    lateinit var orderAdapter: OrderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_my_orders)

        binding =DataBindingUtil.setContentView(this,R.layout.activity_my_orders)


        myprogress = findViewById<ProgressBar>(R.id.myprogress)
        sessionManager = SessionManager(applicationContext)
        val user: HashMap<String, String> = sessionManager.userDetails
       val mem_id :String = user[SessionManager.KEY_ID].toString()
        showOrders(mem_id)

    }
    fun showOrders(mem_id:String)
    {
        //  var sess_id="1"
        RetrofitClient.instance.showOrder(mem_id)
                .enqueue(object : Callback<List<OrderModel>> {
                    override fun onFailure(call: Call<List<OrderModel>>, t: Throwable) {
                        myprogress.hideProgress(this@MyOrders!!)
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        // showToast(R.string.server_error,applicationContext)
                    }

                    override fun onResponse(call: Call<List<OrderModel>>, response: Response<List<OrderModel>>) {


                        //print(response.body().toString())
                        //   Toast.makeText(applicationContext,"" + response.body()!!.string(), Toast.LENGTH_LONG).show()

                        try {

                            //System.out.println("onResponse.toString(): "+response.body());
//              List<DefaultResponse> dr= (List<DefaultResponse>) response.body();
                            //   val dr: List<DefaultResponse>? = response.body()

                            dr  = response.body() as ArrayList<OrderModel>?
                            if(dr?.get(0)?.om_id != null)
                            {
                                System.out.println(dr)
                                val layoutManager = LinearLayoutManager(this@MyOrders)
                                layoutManager.orientation = LinearLayoutManager.VERTICAL

                              //  binding.recycleOrder.layoutManager=layoutManager
                                recycle_order.layoutManager = layoutManager

                                orderAdapter = OrderAdapter(this@MyOrders, dr as ArrayList<OrderModel>?,this@MyOrders)
                                recycle_order.adapter = orderAdapter
                           //     var sum:Double= 0.0


                            }

                            myprogress.hideProgress(this@MyOrders!!)


                        } catch (e: Exception) {
                            myprogress.hideProgress(this@MyOrders!!)
                            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                            // dismissProgress()
                            //   showToast(R.string.catch_exp,applicationContext)
                        }

                    }

                })
    }
    override fun setRating(ses_id: String?, pid: String?, om_id: String?, rating_val: String,pos:Int) {
        System.out.println(ses_id)
        System.out.println(pid)
        System.out.println(om_id)
        System.out.println(rating_val)
        System.out.println(pos)

    updateRating(ses_id!!, pid!!, om_id!!,rating_val,pos)
    }


    fun updateRating(mem_id:String,pid:String,om_id:String,rating:String,pos: Int)
    {
        //  var sess_id="1"
        myprogress.showProgress(this@MyOrders!!)
        RetrofitClient.instance.sendRating(mem_id,pid,om_id,rating)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        myprogress.hideProgress(this@MyOrders!!)
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        // showToast(R.string.server_error,applicationContext)
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {


                        //print(response.body().toString())
                        //   Toast.makeText(applicationContext,"" + response.body()!!.string(), Toast.LENGTH_LONG).show()

                        try {

                            //System.out.println("onResponse.toString(): "+response.body());
//              List<DefaultResponse> dr= (List<DefaultResponse>) response.body();
                            //   val dr: List<DefaultResponse>? = response.body()

                            var root = JSONArray(response.body()?.string())

                            var rootobj = root.getJSONObject(0).getBoolean("code")

                            System.out.println(rootobj);
                            if (rootobj == true) {

                                Toast.makeText(applicationContext,"Thanks For Rating !!!",Toast.LENGTH_LONG).show()

                              /*  if (pos != null) {
                                    orderAdapter.notifyItemChanged(pos)

                                }
                                else
                                {
                                    Toast.makeText(applicationContext, "null position", Toast.LENGTH_LONG).show()
                                }*/



                            }

                            myprogress.hideProgress(this@MyOrders!!)


                        } catch (e: Exception) {
                            myprogress.hideProgress(this@MyOrders!!)
                            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                            // dismissProgress()
                            //   showToast(R.string.catch_exp,applicationContext)
                        }

                    }

                })
    }

}