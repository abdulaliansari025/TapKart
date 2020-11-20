package com.example.bitcoin.Api

import android.widget.ProgressBar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

  //  private val AUTH = "Basic "+ Base64.encodeToString("belalkhan:123456".toByteArray(), Base64.NO_WRAP)
  var serverurl: String? = "http://bollywoodcity.in/kart/register.php"
    private const val BASE_URL = "http://mcmltraders.com/admin/ami/"

    private const val BITCOIN_URL = "https://api.nomics.com/v1/currencies/"

//    object myCompanionName {
//        lateinit var IMAGE_URL: String
//    }
  /*  private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()

                val requestBuilder = original.newBuilder()
                        .addHeader("Authorization", AUTH)
                        .method(original.method(), original.body())

                val request = requestBuilder.build()
                chain.proceed(request)
            }.build()*/

    val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

    val instance: Api by lazy{
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        retrofit.create(Api::class.java)
    }

    val instance1: Api by lazy{
        val retrofit = Retrofit.Builder()
                .baseUrl(BITCOIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                  .client(okHttpClient)
                .build()

        retrofit.create(Api::class.java)
    }

}