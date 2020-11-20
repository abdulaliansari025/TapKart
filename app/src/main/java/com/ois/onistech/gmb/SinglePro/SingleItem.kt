package com.ois.onistech.gmb.SinglePro

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bitcoin.Api.RetrofitClient
import com.ois.onistech.gmb.Const.Constant
import com.ois.onistech.gmb.Const.ContantValue
import com.ois.onistech.gmb.DataClass.Show_pro
import com.ois.onistech.gmb.Login
import com.ois.onistech.gmb.MainScreen
import com.ois.onistech.gmb.R
import com.ois.onistech.gmb.SessionManager
import com.ois.onistech.gmb.Utils.PaymentGatway
import com.ois.onistech.gmb.Utils.hideProgress
import com.ois.onistech.gmb.Utils.showProgress
import com.razorpay.PaymentResultListener
import kotlinx.android.synthetic.main.activity_single_item.*
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SingleItem : AppCompatActivity(),PaymentResultListener {

    lateinit var pmm_id :String

    lateinit var qty :String

    var ct_id :String?=null
    lateinit var session: SessionManager

    lateinit var mem_id :String
    lateinit var myprogress :ProgressBar
    var dr: List<Show_pro>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_item)
        //  ButterKnife.bind(this);
        myprogress = findViewById<ProgressBar>(R.id.myprogress)
        session = SessionManager(applicationContext)
        val user: HashMap<String, String> = session.userDetails
        mem_id = user[SessionManager.KEY_ID].toString()

        add.setOnClickListener {

            if(session.isLoggedIn)
            {
                Add_single_pro_item()
            }

            else
            {
                val intent = Intent (applicationContext, Login::class.java)
               // startActivity(intent)
                intent.putExtra(Constant.INTENT, ContantValue.INTENT_VALUE_SINGLEITEM)
                intent.putExtra(Constant.CHILD_ID, pmm_id)
                startActivity(intent)
            }

        }

        add4.setOnClickListener {
            try {
              var added=  add3.text.toString().toInt() + 1
                add3.setText(added.toString())

            }
            catch (e:Exception)
            {
                System.out.println(e.message)
            }


        }


        add2.setOnClickListener {
            try {
                if ( add3.text.toString().equals("1"))
                {
                    Toast.makeText(applicationContext, "Quantity can't be less than 1", Toast.LENGTH_LONG).show()
                }
                else
                {
                    var min=  add3.text.toString().toInt() - 1
                    add3.setText(min.toString())
                }


            }
            catch (e:Exception)
            {
                System.out.println(e.message)
            }


        }

            var c : Context
        c= this

        text_action_bottom2.setOnClickListener {

          //  payment(c)
            if(session.isLoggedIn)
            {
                //Add_single_pro_item()

  PaymentGatway.createOrder(c,myprogress,user[SessionManager.KEY_ID].toString(),user[SessionManager.KEY_EMAIL].toString(),user[SessionManager.KEY_MOBILE].toString(), dr?.get(0)?.pgm_rate.toString(),dr?.get(0)?.pmm_id.toString(),dr?.get(0)?.weight_name.toString(),add3.text.toString(),"tax")
              //  PaymentGatway.payment(c,user[SessionManager.KEY_EMAIL].toString(),user[SessionManager.KEY_MOBILE].toString(), dr?.get(0)?.pgm_rate.toString())
            }

            else
            {
                val intent = Intent (applicationContext, Login::class.java)
                // startActivity(intent)
                intent.putExtra(Constant.INTENT, ContantValue.INTENT_VALUE_SINGLEITEM)
                intent.putExtra(Constant.CHILD_ID, pmm_id)
                startActivity(intent)
            }


        }


    }


    override fun onResume() {
        super.onResume()

//        val intent = intent
//        val action = intent.action
//        val data = intent.data
       // System.out.println("URI",uri)
        CheckNull()
    }

    fun CheckNull()
    {
        try {
            if (intent.extras != null) {

                val uri :Uri? =intent.data;

                if(uri !=null)
                {
                    var params :List<String> = uri.pathSegments
                    pmm_id= params.get(params.size-1)
                    showSingleItemServer()
                    //Toast.makeText(applicationContext,""+id,Toast.LENGTH_LONG).show()
                }
                else
                {
                    pmm_id = intent.extras.getString("pmm_id")
                    showSingleItemServer()
                   // Toast.makeText(applicationContext,"nukkkk",Toast.LENGTH_LONG).show()
                }


                /*pmm_id = intent.extras.getString("pmm_id")

                if (pmm_id.equals("")) {
                    Toast.makeText(applicationContext, "id is zero", Toast.LENGTH_LONG).show()
                } else {
                    showSingleItemServer()
                }*/
            }
            else
            {

                //Toast.makeText(applicationContext,""+uri.toString(),Toast.LENGTH_LONG).show()
                Toast.makeText(applicationContext, "no value id available", Toast.LENGTH_LONG).show()
            }
        }
        catch (e:Exception)
        {
            Toast.makeText(applicationContext, "exception"+e.message, Toast.LENGTH_LONG).show()
        }
    }


    fun showSingleItemServer() {

        RetrofitClient.instance.show_Single_pro(mem_id,pmm_id)
                .enqueue(object : Callback<List<Show_pro>> {
                    override fun onFailure(call: Call<List<Show_pro>>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        // showToast(R.string.server_error,applicationContext)
                    }

                    override fun onResponse(call: Call<List<Show_pro>>, response: Response<List<Show_pro>>) {


                        //print(response.body().toString())
                        //   Toast.makeText(applicationContext,"" + response.body()!!.string(), Toast.LENGTH_LONG).show()

                        try {
                            dr= response.body()
                            System.out.println("Show SIngle "+dr)
                            activity_item_details.visibility =View.VISIBLE
                            pro_name.setText(dr?.get(0)?.im_item)
                            pro_price.setText("Rs. "+dr?.get(0)?.pgm_rate)
                            ct_id =dr?.get(0)?.ct_id
                            if(dr?.get(0)?.ct_qty==null || dr?.get(0)?.ct_qty=="")
                            {
                                add3.setText("1")
                            }
                            else
                            {
                                add.setText("UPDATE CART")
                                add3.setText(dr?.get(0)?.ct_qty)
                            }
                            myprogress.hideProgress(this@SingleItem!!)
                      //      Toast.makeText(applicationContext,dr?.get(0)?.status , Toast.LENGTH_LONG).show()
                        } catch (e: Exception) {
                            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                            // dismissProgress()
                            //   showToast(R.string.catch_exp,applicationContext)
                        }

                    }

                })
    }

    fun Add_single_pro_item()
    {
        myprogress.showProgress(this@SingleItem!!)
        RetrofitClient.instance.add_Single_pro(ct_id,pmm_id,mem_id,add3.text.toString())
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

                            var rootobj = root.getJSONObject(0).getString("status")
                           ct_id= root.getJSONObject(0).getString("ct_id")
                            System.out.println(rootobj)
                            System.out.println(ct_id)

                            Toast.makeText(applicationContext,rootobj , Toast.LENGTH_LONG).show()
                            myprogress.hideProgress(this@SingleItem!!)
                        } catch (e: Exception) {
                            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                            // dismissProgress()
                            //   showToast(R.string.catch_exp,applicationContext)
                        }

                    }

                })
    }

    override fun onBackPressed() {
        if (intent.extras != null) {
         if (intent.extras.getString(Constant.INTENT) == ContantValue.INTENT_VALUE_SINGLEITEM) {
             // Toast.makeText(Login.this, "extra "+getIntent().getExtras().getString(Constant.CHILD_ID), Toast.LENGTH_SHORT).show();
             startActivity(Intent(this@SingleItem, MainScreen::class.java))
             finish()
         }
            else
         {
             super.onBackPressed()
         }
        }
    }


    override fun onPaymentError(p0: Int, p1: String?) {
      //  Log.e("PAYMENT", "FAIILLEDD")
        PaymentGatway.OrderStatus(PaymentGatway.order_id, p1.toString(),ContantValue.ORDER_STATUS_FAILED)
       /* try {
           // Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show()
            PaymentGatway.OrderStatus(PaymentGatway.order_id, p1.toString(),ContantValue.ORDER_STATUS_FAILED)
            //  Toast.makeText(this, "Payment error please try again $i", Toast.LENGTH_SHORT).show()
        } catch (e: java.lang.Exception) {
            Log.e("OnPaymentError", "Exception in onPaymentError", e)
        }*/



    }

    override fun onPaymentSuccess(p0: String?) {
        Log.e("PAYMENT", "SUCCESSSSS")

        PaymentGatway.OrderStatus(PaymentGatway.order_id, p0.toString(),ContantValue.ORDER_STATUS_PLACED)
    }

}