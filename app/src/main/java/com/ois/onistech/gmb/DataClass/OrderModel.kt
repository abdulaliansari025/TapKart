package com.ois.onistech.gmb.DataClass

data class OrderModel(val om_id:String, val order_id:String,val o_pid:String,val im_id:String,
                      val im_item:String,val o_img:String,val brand_name:String,val weight_name:String,val o_qty:String,val o_amount:String,
                      val o_status:String,val rating:String,val date:String) {
    override fun toString(): String {
        return "OrderModel(om_id='$om_id', order_id='$order_id', o_pid='$o_pid', im_id='$im_id', im_item='$im_item', o_img='$o_img', brand_name='$brand_name', weight_name='$weight_name', o_qty='$o_qty', o_amount='$o_amount', o_status='$o_status', rating='$rating', date='$date')"
    }
}