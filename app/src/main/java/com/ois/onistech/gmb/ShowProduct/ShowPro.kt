package com.ois.onistech.gmb.ShowProduct

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitcoin.Api.RetrofitClient
import com.ois.onistech.gmb.*
import com.ois.onistech.gmb.Const.Constant
import com.ois.onistech.gmb.Const.ContantValue
import com.ois.onistech.gmb.DataClass.CartModel
import com.ois.onistech.gmb.DataClass.Show_pro
import com.ois.onistech.gmb.Interfaces.ProductClick
import com.ois.onistech.gmb.Utils.hideProgress
import com.ois.onistech.gmb.Utils.showProgress
import kotlinx.android.synthetic.main.fragment_show_pro.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShowPro.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowPro : Fragment(),ProductClick {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var child_id: String? = null
    var sess_id: String? = null
    lateinit var mem_id :String
    lateinit var adapter :ProductAdapter
    lateinit var sessionManager: SessionManager
     lateinit var myprogress :ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // getIntent
        child_id = arguments?.getString("childdata")

          //  Toast.makeText(context,child_id,Toast.LENGTH_LONG).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_show_pro, container, false)
         //    val myprogress=v.findViewById(R.id.myprogress)
         myprogress = v.findViewById<ProgressBar>(R.id.myprogress)
       // val lblLabel = v.findViewById(R.id.myprogress) as ProgressBar


        //     return  v;
        return v;
    }


    override fun onResume() {
        super.onResume()
        sessionManager = SessionManager(context)
        val user: HashMap<String, String> = sessionManager.userDetails

        mem_id = user[SessionManager.KEY_ID].toString()
        System.out.println(mem_id)


        showProductServer()
    }

    fun showProductServer() {
      /*  if(!sessionManager.isLoggedIn)
        {
            sess_id =""
        }*/
      //  myprogress.hideProgress(this!!.activity!!)
        RetrofitClient.instance.showProduct(child_id,mem_id)
                .enqueue(object : Callback<List<Show_pro>> {
                    override fun onFailure(call: Call<List<Show_pro>>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                        // showToast(R.string.server_error,applicationContext)
                    }

                    override fun onResponse(call: Call<List<Show_pro>>, response: Response<List<Show_pro>>) {


                        //print(response.body().toString())
                        //   Toast.makeText(applicationContext,"" + response.body()!!.string(), Toast.LENGTH_LONG).show()

                        try {
                            myprogress.hideProgress(activity!!)
                            //System.out.println("onResponse.toString(): "+response.body());
//              List<DefaultResponse> dr= (List<DefaultResponse>) response.body();
                            //   val dr: List<DefaultResponse>? = response.body()

                            val dr: List<Show_pro>? = response.body()
                            System.out.println(dr)

                            val layoutManager = LinearLayoutManager(context)
                            layoutManager.orientation = LinearLayoutManager.VERTICAL

                            recycle_pro.layoutManager = layoutManager

                             adapter = ProductAdapter(context, dr, this@ShowPro)
                            recycle_pro.adapter = adapter

                        } catch (e: Exception) {
                            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                            // dismissProgress()
                            //   showToast(R.string.catch_exp,applicationContext)
                        }

                    }

                })
    }


    override fun OnProductClick(sess_id: String?, pmm_id: String?, qty: String?,position :String?) {
     //   Toast.makeText(context, position, Toast.LENGTH_LONG).show()


        if (sessionManager.isLoggedIn) {


            AddToCart(sess_id,pmm_id,qty,position)
        }
        else
        {
            val intent = Intent (getActivity(), Login::class.java)
            intent.putExtra(Constant.INTENT, ContantValue.INTENT_VALUE_SHOWPRO)
            intent.putExtra(Constant.CHILD_ID, child_id)
            getActivity()?.startActivity(intent)
        }
    }

    fun AddToCart(sess_id: String?,pmm_id: String?,qty: String?, position: String?)
    {
        myprogress.showProgress(activity!!)
        RetrofitClient.instance.addCart(sess_id,pmm_id,qty)
                .enqueue(object : Callback<List<CartModel>> {
                    override fun onFailure(call: Call<List<CartModel>>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                        // showToast(R.string.server_error,applicationContext)
                    }

                    override fun onResponse(call: Call<List<CartModel>>, response: Response<List<CartModel>>) {


                        //print(response.body().toString())
                        //   Toast.makeText(applicationContext,"" + response.body()!!.string(), Toast.LENGTH_LONG).show()

                        try {

                            //System.out.println("onResponse.toString(): "+response.body());
//              List<DefaultResponse> dr= (List<DefaultResponse>) response.body();
                            //   val dr: List<DefaultResponse>? = response.body()

                           val dr: List<CartModel>? = response.body()
                            System.out.println(dr)

                            if(dr?.get(0)?.status == true)
                            {

                                (activity as MainScreen).setupBadge(dr?.get(0)?.cartbag.toString())
                                if (position != null) {
                                    adapter.notifyItemChanged(position.toInt())

                                }
                                else
                                {
                                    Toast.makeText(context, "null position", Toast.LENGTH_LONG).show()
                                }
                            }

                            myprogress.hideProgress(activity!!)
                        } catch (e: Exception) {
                            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                            myprogress.hideProgress(activity!!)
                            // dismissProgress()
                            //   showToast(R.string.catch_exp,applicationContext)
                        }

                    }

                })
    }


    /*override fun onSaveInstanceState(state: Bundle) {
        super.onSaveInstanceState(state)
        state.putParcelable("LIST_STATE_KEY", layoutManager.onSaveInstanceState())
    }*/
}



