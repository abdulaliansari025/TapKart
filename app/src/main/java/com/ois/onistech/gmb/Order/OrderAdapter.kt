package com.ois.onistech.gmb.Order


import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.ois.onistech.gmb.DataClass.OrderModel
import com.ois.onistech.gmb.Interfaces.OrderRating
import com.ois.onistech.gmb.R
import com.ois.onistech.gmb.SessionManager
import kotlinx.android.synthetic.main.cart_layout.view.image1
import kotlinx.android.synthetic.main.cart_layout.view.sp11
import kotlinx.android.synthetic.main.cart_layout.view.tv11
import kotlinx.android.synthetic.main.order_layout.view.*
import java.util.*


class OrderAdapter(var context: Context?, val orders: ArrayList<OrderModel>?,val orderCall:OrderRating) : RecyclerView.Adapter<OrderAdapter.MyViewHolder>(){
    lateinit var mem_id :String
    lateinit var sessionManager: SessionManager
    //var object btnStatus
    var row: String? =null

    var ratingValue: String? =null

    var check:Int=0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        sessionManager = SessionManager(context)
        val view = LayoutInflater.from(context).inflate(R.layout.order_layout,parent,false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {

        return orders?.size!!
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        check++
        //holder.itemView.t1

        //  val position: Int = getAdapterPosition()
        val pro = orders?.get(position)

       Glide.with(this!!.context!!)
                .load(orders?.get(position)?.o_img)
                .apply(RequestOptions().disallowHardwareConfig()
                        .override(Target.SIZE_ORIGINAL) //.centerCrop()
                        .dontTransform()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .dontAnimate()
                        .placeholder(R.drawable.loadinggif)
                        .format(DecodeFormat.PREFER_ARGB_8888))
                .into(holder.image)
        // }

        holder.datetv.setText(pro?.date)
        holder.pro_name.setText(pro?.im_item)

//val abc:Float= 10.2F
      //  holder.ratingBar.rating()
       // holder.ratingBar.rating(Float.parseFloat("2.0"));
        try {
            holder.ratingBar.setRating(orders?.get(position)?.rating?.toFloat()!!);
        }
        catch (e:Exception)
        {
          //  Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
        }

        if(orders?.get(position)?.rating?.toFloat()!! >0)
        {
            holder.review.text="Write Review"
            holder.review.setTextColor(Color.parseColor("#4d79ff"));
            holder.review.setTypeface(null, Typeface.BOLD);

        }

        //if (check==0) {
            holder.ratingBar.setOnRatingBarChangeListener(OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                val ratingVal = rating as Float
                val ratingvalue = holder.ratingBar.getRating() as Float
                val user: HashMap<String, String> = sessionManager.userDetails
                mem_id = user[SessionManager.KEY_ID].toString()
                orderCall.setRating(mem_id, orders?.get(position)?.o_pid, orders?.get(position)?.om_id, ratingvalue.toString(), position)
                row = position.toString()
                ratingValue = ratingvalue.toString()

            })
        //}

      /*  if(row?.toInt() == position)
        {

            holder.ratingBar.setRating(ratingValue?.toFloat()!!);
           // holder.btn_add.visibility =View.GONE
            //holder.btn_added_green.visibility =View.VISIBLE
        }*/



    }


    inner class MyViewHolder ( itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val  image :ImageView = itemView.image1
//
        val ratingBar :RatingBar = itemView.text_ratings
        val pro_name :TextView = itemView.sp11
        val datetv :TextView = itemView.tv11
        val review :TextView = itemView.tv13


       /* override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {

            var position:Int =adapterPosition
            val ratingVal = p0 as Float
            val ratingvalue = ratingBar.getRating() as Float
            val user: HashMap<String, String> = sessionManager.userDetails
            mem_id = user[SessionManager.KEY_ID].toString()
            orderCall.setRating(mem_id, orders?.get(position)?.o_pid, orders?.get(position)?.om_id, ratingvalue.toString(), position)
            row = position.toString()
            ratingValue = ratingvalue.toString()

        }*/

        //  itemView.t1
        //   itemView

    }


}
