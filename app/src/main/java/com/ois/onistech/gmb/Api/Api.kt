package com.example.bitcoin.Api


import com.ois.onistech.gmb.DataClass.CartModel
import com.ois.onistech.gmb.DataClass.OrderModel
import com.ois.onistech.gmb.DataClass.Show_cart_Model
import com.ois.onistech.gmb.DataClass.Show_pro
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface Api {

   /* @FormUrlEncoded
    @POST("register.php")
    fun createUser(
            @Field("u_email") email:String,
            @Field("a_password") password:String,
            @Field("u_mobile") mobile:String,
            @Field("u_name") name:String
    ):Call<ResponseBody>

   // @FormUrlEncoded
    @GET("login.php")
    fun loginUser(
           @Query("u_mobile") mobile:String,
           @Query("u_pass") password:String
    ):Call<List<LoginResponse>>

    @GET("ticker")
    fun bitcoinUser(
            @Query("key") key:String,
            @Query("ids") ids:String,
            @Query("interval") interval:String,
            @Query("convert") convert:String
    ):Call<List<BitcoinResponse>>

    @GET("fetchtime.php")
    fun TimerValue():Call<List<TimerResponse>>*/

    @GET("showproduct.php")
    fun showProduct(
            @Query("cat_id") cat_id: String?,
            @Query("ses_id") sess_id: String?
    ):Call<List<Show_pro>>


    @GET("AddCart.php")
    fun addCart(
            @Query("ses_id") ses_id: String?,
            @Query("pmm_id") pmm_id: String?,
            @Query("qty") qty: String?
    ):Call<List<CartModel>>


    @GET("showCart.php")
    fun showCart(
            @Query("ses_id") sess_id: String?
    ):Call<List<Show_cart_Model>>


    @GET("UpdateQuantity.php")
    fun updateQuantity(
            @Query("ses_id") ses_id: String?,
            @Query("ct_id") ct_id: String?,
            @Query("qty") qty: String?,
            @Query("ct_price") ct_price: String?
    ):Call<List<Show_cart_Model>>




    @GET("show_Single_pro.php")
    fun show_Single_pro(
            @Query("ses_id") sess_id: String?,
            @Query("pmm_id") pmm_id: String?
    ):Call<List<Show_pro>>


    @GET("add_Single_pro.php")
    fun add_Single_pro(
            @Query("ct_id") ct_id: String?,
            @Query("pmm_id") pmm_id: String?,
            @Query("ses_id") ses_id: String?,
            @Query("qty") qty: String?
    ):Call<ResponseBody>

    @GET("updateQuantityHome.php")
    fun updateQuantityHome(
            @Query("ses_id") ses_id: String?,
            @Query("ct_id") ct_id: String?,
            @Query("qty") qty: String?,
            @Query("ct_price") ct_price: String?
    ):Call<List<CartModel>>

    @GET("deleteCart.php")
    fun deleteCart(
            @Query("ses_id") ses_id: String?,
            @Query("ct_id") ct_id: String
    ):Call<ResponseBody>

    @GET("checkout.php")
    fun checkout(
            @Query("ses_id") ses_id: String?
    ):Call<ResponseBody>

    @GET("updatePayStatus.php")
    fun updatestatus(
            @Query("ses_id") ses_id: String?,
            @Query("order_id") order_id: String?,
            @Query("payment_id") payment_id: String?,
            @Query("result") result: String?
    ):Call<ResponseBody>


    @GET("SingleCheckout.php")
    fun signleCheckout(
            @Query("ses_id") ses_id: String?,
            @Query("pid") pid: String?,
            @Query("size") size: String?,
            @Query("qty") qty: String?,
            @Query("price") price: String?,
            @Query("tax") tax: String?
    ):Call<ResponseBody>


    @GET("showOrder.php")
    fun showOrder(
            @Query("ses_id") ses_id: String?
    ):Call<List<OrderModel>>


    @GET("sendRating.php")
    fun sendRating(
            @Query("ses_id") ses_id: String?,
            @Query("pid") pid: String?,
            @Query("om_id") om_id: String?,
            @Query("rating") rating: String?
    ):Call<ResponseBody>

}