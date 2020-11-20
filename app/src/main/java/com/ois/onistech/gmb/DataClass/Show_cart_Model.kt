package com.ois.onistech.gmb.DataClass

class Show_cart_Model(var ct_id :String, var ses_id:String, var ct_pid:String, var ct_purid:String, var im_id:String, var im_item:String, var pgm_category:String, var pmm_id:String,var brand_name:String,var weight_name:String, var ct_price:String
,var ct_img:String, var ct_qty:  String,var ct_weight:String,var ct_tt:String, var ct_tax:String,var ct_total: String) {
    override fun toString(): String {
        return "Show_cart_Model(ct_id='$ct_id', ses_id='$ses_id', ct_pid='$ct_pid', ct_purid='$ct_purid', im_id='$im_id', im_item='$im_item', pgm_category='$pgm_category', pmm_id='$pmm_id', brand_name='$brand_name', weight_name='$weight_name', ct_price='$ct_price', ct_img='$ct_img', ct_qty='$ct_qty', ct_weight='$ct_weight', ct_tt='$ct_tt', ct_tax='$ct_tax', ct_total='$ct_total')"
    }

}