package com.ois.onistech.gmb.ShowProduct

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.ois.onistech.gmb.DataClass.Show_pro
import com.ois.onistech.gmb.HorCatAdapter
import com.ois.onistech.gmb.Interfaces.ProductClick
import com.ois.onistech.gmb.R
import com.ois.onistech.gmb.SessionManager
import com.ois.onistech.gmb.SinglePro.SingleItem
import kotlinx.android.synthetic.main.layout_show_products.view.*
import java.util.*

class ProductAdapter(var context: Context?, val products: List<Show_pro>?, var callback: ProductClick?) : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {
    var row: String? =null
    lateinit var mem_id :String
    lateinit var sessionManager: SessionManager
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        sessionManager = SessionManager(context)
       val view = LayoutInflater.from(context).inflate(R.layout.layout_show_products,parent,false)
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
                .load(products?.get(position)?.pgm_img)
                .apply(RequestOptions().disallowHardwareConfig()
                        .override(Target.SIZE_ORIGINAL) //.centerCrop()
                        .dontTransform()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .dontAnimate()
                        .placeholder(R.drawable.loadinggif)
                        .format(DecodeFormat.PREFER_ARGB_8888))
                .into(holder.image)

        holder.pro_name.setText(pro?.im_item)
        holder.pro_desc.setText(pro?.weight_name +" by "+"${pro?.brand_name}")
        holder.price.setText("Rs ${pro?.pgm_rate} /-")

        holder.layout_click.setOnClickListener {

            val intent = Intent(context, SingleItem::class.java)
            intent.putExtra("pmm_id",pro?.pmm_id )
            context!!.startActivity(intent)
         //   context?.startActivity(Intent(context,SingleItem.class))
            //  val args = Bundle()
            //args.putString("position", pro?.im_item)
            // holder.btn_add.getAbsoluteAdapterPosition()
            //  var pos= position.toString()
           // callback?.OnProductClick("1",pro?.pmm_id,"1", position.toString())
         //   row =position.toString()
        }


        holder.btn_add.setOnClickListener {
          //  val args = Bundle()
            //args.putString("position", pro?.im_item)
           // holder.btn_add.getAbsoluteAdapterPosition()
         //  var pos= position.toString()
            val user: HashMap<String, String> = sessionManager.userDetails
            mem_id = user[SessionManager.KEY_ID].toString()
            System.out.println(mem_id)



            callback?.OnProductClick(mem_id,pro?.pmm_id,"1", position.toString())
            row =position.toString()
        }
        if(pro?.status.equals("1"))
        {
            holder.btn_add.visibility =View.GONE
            holder.btn_added_green.visibility =View.VISIBLE
        }

        if(row?.toInt() == position)
        {
            holder.btn_add.visibility =View.GONE
            holder.btn_added_green.visibility =View.VISIBLE
        }
      //  Log.i("on bind","on bind runninng")
       // Toast.makeText(context, pro?.im_item,Toast.LENGTH_LONG).show()
    }

   inner class MyViewHolder ( itemView : View) : RecyclerView.ViewHolder(itemView)
    {
            val image:ImageView=itemView.image1
        val btn_add :Button = itemView.add
        val btn_added_green = itemView.added
        val price :TextView = itemView.tv13
        val pro_desc :TextView = itemView.tv12
        val pro_name :TextView = itemView.tv11
        val layout_click:LinearLayout = itemView.layout_item
      //  itemView.t1
     //   itemView

    }

}