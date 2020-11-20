package com.ois.onistech.gmb.CartPackage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.ois.onistech.gmb.DataClass.Show_cart_Model
import com.ois.onistech.gmb.Interfaces.ItemClick
import com.ois.onistech.gmb.Interfaces.ProductClick
import com.ois.onistech.gmb.R
import com.ois.onistech.gmb.SessionManager
import kotlinx.android.synthetic.main.cart_layout.view.*
import java.util.*


class CartAdapter(var context: Context?, val products: ArrayList<Show_cart_Model>?, var callback: ProductClick?,var onitemClick: ItemClick?) : RecyclerView.Adapter<CartAdapter.MyViewHolder>() {
    var row: String? =null
    var btn_status: String? =null
    var new_qty :Int = 0
    lateinit var mem_id :String
    lateinit var sessionManager: SessionManager
    //var object btnStatus
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        sessionManager = SessionManager(context)
        val view = LayoutInflater.from(context).inflate(R.layout.cart_layout,parent,false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {

        return products?.size!!
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //holder.itemView.t1

        //  val position: Int = getAdapterPosition()
        val pro = products?.get(position)

            Glide.with(this!!.context!!)
                    .load(products?.get(position)?.ct_img)
                    .apply(RequestOptions().disallowHardwareConfig()
                            .override(Target.SIZE_ORIGINAL) //.centerCrop()
                            .dontTransform()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .dontAnimate()
                            .placeholder(R.drawable.loadinggif)
                            .format(DecodeFormat.PREFER_ARGB_8888))
                    .into(holder.image)
       // }

        holder.pro_name.setText(pro?.im_item)
        holder.pro_desc.setText(pro?.weight_name +" by "+"${pro?.brand_name}")
        holder.price.setText("Rs ${pro?.ct_price} /-")
        holder.qty.setText(pro?.ct_qty)
        /*holder.btn_add.setOnClickListener {
            //  val args = Bundle()
            //args.putString("position", pro?.im_item)
            // holder.btn_add.getAbsoluteAdapterPosition()
            //  var pos= position.toString()
            callback?.OnProductClick("1",pro?.pmm_id,"1", position.toString())
            row =position.toString()
        }*/

        holder.btn_add.setOnClickListener {

            new_qty= pro?.ct_qty?.toInt()?.plus(1)!!

          //  val final = new_qty?.plus(1)
            if (new_qty != null)
            {

                val user: HashMap<String, String> = sessionManager.userDetails
                mem_id = user[SessionManager.KEY_ID].toString()
              //  System.out.println(mem_id)

                callback?.OnProductClick(mem_id,pro?.ct_id, new_qty.toString(), pro?.ct_price)

                btn_status = "add"
                row =position.toString()
           //     Toast.makeText(context, new_qty.toString(),Toast.LENGTH_LONG).show()
            }
            else
            {
                Toast.makeText(context, "null ",Toast.LENGTH_LONG).show()
            }

        }



        holder.btn_minus.setOnClickListener {

           // System.out.println(mem_id)

            if(pro?.ct_qty?.toString()?.equals("1")!!)
            {
                Toast.makeText(context, "Quantity cant be less than 1",Toast.LENGTH_LONG).show()
            }
            else
            {
                new_qty= pro?.ct_qty?.toInt()?.minus(1)!!

                //  val final = new_qty?.plus(1)
                if (new_qty != null) {
                    val user: HashMap<String, String> = sessionManager.userDetails
                    mem_id = user[SessionManager.KEY_ID].toString()
                    callback?.OnProductClick(mem_id,pro?.ct_id, new_qty.toString(),pro?.ct_price)
                    btn_status = "minus"
                    row =position.toString()
                    //     Toast.makeText(context, new_qty.toString(),Toast.LENGTH_LONG).show()
                }
                else
                {
                    Toast.makeText(context, "null ",Toast.LENGTH_LONG).show()
                }
            }


        }

        holder.delete.setOnClickListener {
            val user: HashMap<String, String> = sessionManager.userDetails
            mem_id = user[SessionManager.KEY_ID].toString()
            onitemClick?.onItemClick(mem_id, pro?.ct_id,position.toString(),"delete")
            btn_status="delete"
            row =position.toString()


        }


       /* if(row?.toInt() == position && btn_status.equals("delete"))
        {
            try{

                products?.removeAt(holder.adapterPosition)
                notifyItemRemoved(holder.adapterPosition);
                notifyItemRangeChanged(holder.adapterPosition, products?.size!!);
            }
            catch (e :Exception)
            {
                //Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
                System.out.println("Error "+e.message)
            }
        }*/

        Log.e("ITEM REMOVED","ITEM REMOVED")
    }


    inner class MyViewHolder ( itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val  image :ImageView = itemView.image1
        val delete:ImageView =itemView.delete
        val btn_minus :Button = itemView.add2
        val btn_add = itemView.add4
        val price :TextView = itemView.tv12
        val pro_desc :TextView = itemView.sp11
        val pro_name :TextView = itemView.tv11
        val qty :TextView = itemView.add3

        //  itemView.t1
        //   itemView

    }

//    fun swap(datas: ArrayList<Show_cart_Model?>?) {
//        products?.clear()
//        products?.addAll(datas)
//        notifyDataSetChanged()
//    }

    fun setDataSet(newList: ArrayList<Show_cart_Model>?){
        System.out.println("new list"+newList)
        products?.clear();
        products?.addAll(newList!!);
      //  loadStatus = 1
       // notifyItemChanged(0,products?.size);
        notifyDataSetChanged()
    }





}
