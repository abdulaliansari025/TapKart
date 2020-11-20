package com.ois.onistech.gmb;


import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bitcoin.Api.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ois.onistech.gmb.DataClass.CartModel;
import com.ois.onistech.gmb.DataClass.Featured_Cat;
import com.ois.onistech.gmb.Interfaces.ProductClick;
import com.ois.onistech.gmb.Interfaces.ProductDropdown;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Product extends Fragment {
    RecyclerView recycle;
    ProgressDialog progressDialog;
    String name="", price="", image="",id="",aprice="",status="",kg_pc="",data="",type="",url="",weight="";
    ArrayList<Model> arr = new ArrayList<Model>();
    RequestQueue mRequestQueue;
    SessionManager session;
    SearchView sv;
    String strtext="";
    RecyclerAdapter recyclerAdapter;
    String ses_id;
    private Boolean check = false;
    public Product() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_product, container, false);

            recycle=(RecyclerView)v.findViewById(R.id.recyclerview);
            session = new SessionManager(getContext());
              HashMap<String,String> user =session.getUserDetails();

         ses_id=  user.get(SessionManager.KEY_ID);
      // strtext = getArguments().getString("edttext");
         /*strtext = getArguments().getString("childdata");
            String type = getArguments().getString("type");
            if(type.equals("1"))
            {
                 url = "http://greenmorningbasket.com/android/productlist.php?childname="+strtext+"";
            }
            else
            {
                //url = "http://greenmorningbasket.com/android/searchbar.php?childname="+strtext+"";

                url = "http://oursports11.com/ami/featured.php";
            }*/
      // Toast.makeText(getContext().getApplicationContext(), ""+ses_id, Toast.LENGTH_SHORT).show();
      //  Toast.makeText(getContext().getApplicationContext(), ""+strtext, Toast.LENGTH_SHORT).show();

        getvolley();

        return v;

    }

    private void getvolley() {
        url = "http://mcmltraders.com/admin/ami/featured.php?ses_id="+ses_id+"";
        //String url = "http://greenmorningbasket.com/android/productlist.php?childname="+strtext+"";
        //String url = "http://bollywoodcity.in/kart/category.php?cat="+strtext+"";
        StringRequest stringRequest =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d(TAG,"working"+response);
                try {
                    Gson gson = new GsonBuilder().create();
                    Type footype = new TypeToken<List<Featured_Cat>>(){}.getType();
                    ArrayList<Featured_Cat> featured_cats = gson.fromJson(response.toString(),footype);
                    System.out.println("featured_cats" +featured_cats);
                    if(check)
                    {
                        System.out.println("CHECK "+check);
                        recyclerAdapter.setDataSet(featured_cats);
                    }
                    else
                    {
                        System.out.println("CHECK "+check);
                        recyclerAdapter = new RecyclerAdapter(featured_cats, getContext(), new ProductDropdown() {
                            @Override
                            public void OnSpinClick(String sess_id, String pmm_id, String qty, String holder_pos, String spin_pos) {

                                AddToCart(sess_id, pmm_id, qty, holder_pos, spin_pos);
                                //   System.out.println(spin_pos);
                            }
                        }, new ProductClick() {
                            @Override
                            public void OnProductClick(@Nullable String sess_id, @Nullable String ct_id, @Nullable String qty, @Nullable String ct_price) {
                                System.out.println(sess_id);
                                System.out.println(ct_id);
                                System.out.println(qty);
                                System.out.println(ct_price);

                                AddQuantityHome(sess_id,ct_id,qty,ct_price);
                            }
                        });

                        //  AddToCart(sess_id,pmm_id,qty,position);
                       // recycle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
                        recycle.setLayoutManager(layoutManager);
                        recycle.setAdapter(recyclerAdapter);
                    }

                }



                catch (Exception e) {
                    System.out.println("Exception" +e.getMessage());
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext().getApplicationContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                // progressDialog.dismiss();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

       /* progressDialog = new ProgressDialog(Product.this,R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Signing Up....");
        progressDialog.setCancelable(false);
        progressDialog.show();*/
    }

    private void AddToCart(String ses_id,String pmm_id,String qty,String holder_pos,String spin_pos)
    {
        Call<List<CartModel>> call = RetrofitClient.INSTANCE.getInstance().addCart(ses_id,pmm_id,qty);
        call.enqueue(new Callback<List<CartModel>>() {
            @Override
            public void onResponse(Call<List<CartModel>> call, retrofit2.Response<List<CartModel>> response) {
                //System.out.println("onResponse.toString(): "+response.body());
//              List<DefaultResponse> dr= (List<DefaultResponse>) response.body();


                List<CartModel> dr =response.body();

                System.out.println("RESPONSE"+dr);
             //   recyclerAdapter.notifyItemChanged(Integer.parseInt(holder_pos));

                check = true;

                getvolley();
                try {
                    //   Toast.makeText(MainActivity.this, "" +dr., Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CartModel>> call, Throwable t) {
                Toast.makeText(getContext(), "error"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void AddQuantityHome(String ses_id,String ct_id,String qty,String ct_price)
    {
        Call<List<CartModel>> call = RetrofitClient.INSTANCE.getInstance().updateQuantityHome(ses_id,ct_id,qty,ct_price);
        call.enqueue(new Callback<List<CartModel>>() {
            @Override
            public void onResponse(Call<List<CartModel>> call, retrofit2.Response<List<CartModel>> response) {
                //System.out.println("onResponse.toString(): "+response.body());
//              List<DefaultResponse> dr= (List<DefaultResponse>) response.body();




              //  getvolley();
                try {
                    List<CartModel> dr =response.body();

                    System.out.println("RESPONSE"+dr);

                    //   recyclerAdapter.notifyItemChanged(Integer.parseInt(holder_pos));

                    check = true;

                    getvolley();
                    //   Toast.makeText(MainActivity.this, "" +dr., Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(getContext(), "catch "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CartModel>> call, Throwable t) {
                Toast.makeText(getContext(), "error "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



}
