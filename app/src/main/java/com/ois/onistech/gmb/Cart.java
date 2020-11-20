package com.ois.onistech.gmb;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {
    String pname="", price="", image="",pid="",status="",unit="",qty="",rid;
    String url="http://greenmorningbasket.com/android/cart.php";
    ArrayList<ModelCart> arr = new ArrayList<ModelCart>();
    RecyclerView recycle;
    CartAdapter recyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recycle=(RecyclerView)findViewById(R.id.recyclerview);
        getvolley();
    }


    private void getvolley() {

        //String url = "http://greenmorningbasket.com/android/productlist.php?childname="+strtext+"";
        //String url = "http://bollywoodcity.in/kart/category.php?cat="+strtext+"";
        StringRequest stringRequest =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d(TAG,"working"+response);
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(i);  //get value of first index
                        // String code = jsonObject.getString("code");
                        //String message = jsonObject.getString("code"); //we should write key name
                        // sr = jsonObject.getString("sr");
                        rid = jsonObject.getString("rid");
                        pid = jsonObject.getString("pid");
                        pname = jsonObject.getString("pname");
                        price = jsonObject.getString("price");
                        image = jsonObject.getString("image");
                        unit = jsonObject.getString("unit");
                        qty = jsonObject.getString("qty");
                        status = jsonObject.getString("status");

                     //   Toast.makeText(Cart.this, ""+rid, Toast.LENGTH_SHORT).show();

                        recyclerAdapter = new CartAdapter(Ali(), Cart.this);
                        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                        recycle.setLayoutManager(layoutManager);


                        recycle.setAdapter(recyclerAdapter);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Cart.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                // progressDialog.dismiss();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(Cart.this);
        requestQueue.add(stringRequest);

       /* progressDialog = new ProgressDialog(Product.this,R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Signing Up....");
        progressDialog.setCancelable(false);
        progressDialog.show();*/
    }

    public ArrayList<ModelCart> Ali() {
        ModelCart m1 = new ModelCart(pid,pname,price,image,unit,qty,status,rid);
        arr.add(m1);
        return arr;

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        startActivity(new Intent(Cart.this,MainScreen.class));
        finish();
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Cart.this,MainScreen.class));
        finish();
    }
}
