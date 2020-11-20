package com.ois.onistech.gmb.DataClass

data class Show_pro(var pmm_id:String,var pgm_product:String,var pgm_category:String, var pgm_sale:String,var pgm_main :String,var brand_name:String,
var weight_name:String,var pgm_img:String,var im_id:String,var im_item:String, var pgm_rate:String, var status:String, var ct_id:String, var ct_qty:String)
{
    override fun toString(): String {
        return "Show_pro(pmm_id='$pmm_id', pgm_product='$pgm_product', pgm_category='$pgm_category', pgm_sale='$pgm_sale', pgm_main='$pgm_main', brand_name='$brand_name', weight_name='$weight_name', pgm_img='$pgm_img', im_id='$im_id', im_item='$im_item', pgm_rate='$pgm_rate', status='$status', ct_id='$ct_id', ct_qty='$ct_qty')"
    }
}