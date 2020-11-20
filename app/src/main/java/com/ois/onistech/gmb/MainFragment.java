package com.ois.onistech.gmb;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bitcoin.Api.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ois.onistech.gmb.DataClass.Banner;
import com.ois.onistech.gmb.DataClass.CartModel;
import com.ois.onistech.gmb.DataClass.Featured_Cat;
import com.ois.onistech.gmb.DataClass.Home_Bnner_Cat;
import com.ois.onistech.gmb.DataClass.ShowProMainFrag;
import com.ois.onistech.gmb.Interfaces.ProductClick;
import com.ois.onistech.gmb.Interfaces.ProductDropdown;
import com.ois.onistech.gmb.MainPage.MarketAdapter;
import com.ois.onistech.gmb.Utils.Utility;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    ViewPager viewPager;
    List<SliderUtils> sliderImg;
    customSwipeAdapter adapter1;
    RecyclerView recycler_market;
    RecyclerView recthree;
    ProgressDialog progressDialog;
    //...
    int currentPage = 0;
    Timer timer;
    String sid="",cid="",sname="",image="";
    String pid="",pname="",img="",catid="";
    ArrayList<Modelhorcat> arr = new ArrayList<Modelhorcat>();
    ArrayList<Modelhorpro> array = new ArrayList<Modelhorpro>();
    final long DELAY_MS = 500; //delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000;
    String request_url="http://mcmltraders.com/admin/ami/banner.php";
    String url1="http://greenmorningbasket.com/android/mshorcat.php";
    String url2="http://greenmorningbasket.com/android/latestpro.php";
    HorCatAdapter adapt;
    HorProAdapter adapter2;

ProgressBar myProgress;

    RecyclerView recycle;
    String name="", price="",id="",aprice="",status="",kg_pc="",data="",type="",url="",weight="";
   // ArrayList<Model> arr = new ArrayList<Model>();
    RequestQueue mRequestQueue;
    SessionManager session;
    SearchView sv;
    String strtext="";
    RecyclerAdapter recyclerAdapter;
    MarketAdapter marketAdapter;
    String ses_id;
    ScrollView root_layout;
    private Boolean check = false;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_main, container, false);
        viewPager =(ViewPager)v.findViewById(R.id.view_pager);
        myProgress=(ProgressBar)v.findViewById(R.id.myprogress);
        root_layout=(ScrollView)v.findViewById(R.id.root_layout);
        recycler_market=(RecyclerView)v.findViewById(R.id.recycler_market);
      //  recthree=(RecyclerView)v.findViewById(R.id.recycethree);
        sliderImg= new ArrayList<>();
        request_url="http://mcmltraders.com/admin/ami/banner.php";
      //  sendRequest();
        //getvolley();
        //getvolley1();


        recycle=(RecyclerView)v.findViewById(R.id.recyclerview);
        session = new SessionManager(getContext());
        HashMap<String,String> user =session.getUserDetails();

        ses_id=  user.get(SessionManager.KEY_ID);


        getProducts();
        sendRequest();
        return  v;
    }
    private void getProducts() {
        url = "http://mcmltraders.com/admin/ami/featured.php?ses_id="+ses_id+"";
        //String url = "http://greenmorningbasket.com/android/productlist.php?childname="+strtext+"";
        //String url = "http://bollywoodcity.in/kart/category.php?cat="+strtext+"";
        StringRequest stringRequest =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d(TAG,"working"+response);
                try {
                    Utility.hideProJava(myProgress,getActivity());
                    Gson gson = new GsonBuilder().create();
                    Type footype = new TypeToken<List<ShowProMainFrag>>(){}.getType();
                    ArrayList<ShowProMainFrag> showProMainFrags = gson.fromJson(response.toString(),footype);
                    System.out.println("featured_cats" +showProMainFrags);
                    ArrayList<Featured_Cat> featured_pro = new ArrayList<>();
                    ArrayList<Featured_Cat> market = new ArrayList<>();
                    for(int j=0;j<showProMainFrags.get(0).getData().size();j++)
                    {
                        featured_pro.add(new Featured_Cat(showProMainFrags.get(0).getData().get(j).getIm_item(),
                                showProMainFrags.get(0).getData().get(j).getPgm_market(),
                                showProMainFrags.get(0).getData().get(j).getFeatured(),
                                showProMainFrags.get(0).getData().get(j).getPgm_fresh(),
                                showProMainFrags.get(0).getData().get(j).getPgm_img(),
                                showProMainFrags.get(0).getData().get(j).getSub_data()));


                        if( showProMainFrags.get(0).getData().get(j).getPgm_market().equals("1"))
                        {
                            market.add(new Featured_Cat(showProMainFrags.get(0).getData().get(j).getIm_item(),
                                    showProMainFrags.get(0).getData().get(j).getPgm_market(),
                                    showProMainFrags.get(0).getData().get(j).getFeatured(),
                                    showProMainFrags.get(0).getData().get(j).getPgm_fresh(),
                                    showProMainFrags.get(0).getData().get(j).getPgm_img(),
                                    showProMainFrags.get(0).getData().get(j).getSub_data()));
                        }

                    }

                    ((MainScreen) getContext()).setupBadge(showProMainFrags.get(0).getCart());
                 /*   ArrayList<Featured_Cat> market = new ArrayList<>();
                    for(int i =0;i<featured_cats.size();i++)
                    {
                        if(featured_cats.get(i).getPgm_market().equals("1"))
                        {
                            market.add(new Featured_Cat(featured_cats.get(i).getIm_item(),featured_cats.get(i).getPgm_market(),
                                    featured_cats.get(i).getFeatured(),featured_cats.get(i).getPgm_fresh(),featured_cats.get(i).getPgm_img(),featured_cats.get(i).getCount(),featured_cats.get(i).getSub_data()));
                        }
                        else
                        {

                        }
                    }*/

                System.out.println("MARKET"+market.size());

                    System.out.println("FEATURED"+featured_pro.size());
                    if(check)
                    {
                        System.out.println("CHECK "+check);
                        recyclerAdapter.setDataSet(featured_pro);
                        marketAdapter.setDataSet(market);
                    }
                    else
                    {
                        System.out.println("CHECK "+check);
                        recyclerAdapter = new RecyclerAdapter(featured_pro, getContext(), new ProductDropdown() {
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



                        marketAdapter = new MarketAdapter(market, getContext(), new ProductDropdown() {
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
                        StaggeredGridLayoutManager layoutManager1 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
                        recycler_market.setLayoutManager(layoutManager1);
                        recycler_market.setAdapter(marketAdapter);

                        root_layout.setVisibility(View.VISIBLE);
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
        if (session.isLoggedIn()) {
            Utility.showProJava(myProgress,getActivity());
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

                    getProducts();
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
        else
        {
         startActivity(new Intent(getContext(),Login.class));
        }


    }

    private void AddQuantityHome(String ses_id,String ct_id,String qty,String ct_price)
    {
        Utility.showProJava(myProgress,getActivity());
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

                    getProducts();
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

    public void sendRequest()
    {
        //  JSONArrayRequest jsonArrayRequest= new JSONArrayRe
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                viewPager.setOffscreenPageLimit(response.length());
//                for(int i =0;i<response.length();i++)
//                {
//                    SliderUtils sliderUtils= new SliderUtils();
                    try {
                    //    JSONObject jsonObject = response.getJSONObject(i);
                      /*  String id =jsonObject.getString("image");
                        String[] words=id.split(",");
                        for (int j = 0; j < words.length; j++) {
                            sliderUtils.setSliderImageurls(words[j]);
                            sliderImg.add(sliderUtils);
                        }*/

                        //Toast.makeText(MainScreen.this, "", Toast.LENGTH_SHORT).show();
                    //    sliderUtils.setSliderImageurls(jsonObject.getString("b_image"));

                        Gson gson = new GsonBuilder().create();
                        Type footype = new TypeToken<List<Home_Bnner_Cat>>(){}.getType();
                        ArrayList<Home_Bnner_Cat> home_bnner_cats = gson.fromJson(response.toString(),footype);

                        List<Banner> banners = home_bnner_cats.get(0).getBanner();
                        //sliderUtils.setSliderImageurls(jsonObject.getString("image1"));
                        adapter1=new customSwipeAdapter(banners,getContext().getApplicationContext());
                        viewPager.setAdapter(adapter1);
                    }

                    catch (Exception e) {
                        e.printStackTrace();
                    }
                  //  sliderImg.add(sliderUtils);
            //    }


                /*After setting the adapter use the timer */
                final Handler handler = new Handler();
                final Runnable Update = new Runnable() {
                    public void run() {
                        if (currentPage == response.length()-1) {
                            currentPage = 0;
                        }
                        viewPager.setCurrentItem(currentPage++, true);
                    }
                };

                timer = new Timer(); // This will create a new Thread
                timer.schedule(new TimerTask() { // task to be scheduled
                    @Override
                    public void run() {
                        handler.post(Update);
                    }
                }, DELAY_MS, PERIOD_MS);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext().getApplicationContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }
    private void getvolley() {
      //  String url = "http://swarsangeet.in/event/find.php?city="+value+"&service="+facility+"&date="+date[0]+"&date2="+date1[0]+"";
        // String url = "http://bollywoodcity.in/event/index.php?start_date="+date[0]+"&end_date="+date1[0]+"&start_time="+time[0]+"&end_time="+time1[0]+"";
        //String url = "http://bollywoodcity.in/event/index.php?date="+date[0]+"&date2="+date1[0]+"&start_time="+time[0]+"&end_time="+time1[0]+"";
        StringRequest stringRequest =  new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d(TAG,"working"+response);
               progressDialog.dismiss();
                try {
                    arr.clear();
                    for (int i = 0; i < response.length(); i++) {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //get value of first index
                        // String code = jsonObject.getString("code");
                        //String message = jsonObject.getString("code"); //we should write key name;

                        sid = jsonObject.getString("sid");
                        cid = jsonObject.getString("cid");
                        sname = jsonObject.getString("sname");
                        image = jsonObject.getString("image");


                        //  Toast.makeText(getContext().getApplicationContext(), ""+img, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getContext().getApplicationContext(), ""+status, Toast.LENGTH_SHORT).show();

                        adapt = new HorCatAdapter(Ali(), getContext());
                      //  rectwo.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                       // rectwo.setAdapter(adapt);
                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();

                    // Toast.makeText(getContext().getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressDialog.dismiss();
                Toast.makeText(getContext().getApplicationContext(),"Sorry ,Something went Wrong Or No Connection !! "+error.getMessage(), Toast.LENGTH_SHORT).show();
                // progressDialog.dismiss();
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
        requestQueue.add(stringRequest);

        //initialize the progress dialog and show it
        progressDialog = new ProgressDialog(getActivity(),R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Loading Details....");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void getvolley1() {

        StringRequest stringRequest =  new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d(TAG,"working"+response);
              //  progressDialog.dismiss();
                try {
                    array.clear();
                    //progressDialog.dismiss();
                    for (int i = 0; i < response.length(); i++) {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        //get value of first index
                        // String code = jsonObject.getString("code");
                        //String message = jsonObject.getString("code"); //we should write key name;

                        pid = jsonObject.getString("pid");
                        catid = jsonObject.getString("catid");
                        pname = jsonObject.getString("pname");
                        img = jsonObject.getString("img");


//                         Toast.makeText(getContext().getApplicationContext(), ""+pid, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext().getApplicationContext(), ""+catid, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext().getApplicationContext(), ""+pname, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext().getApplicationContext(), ""+img, Toast.LENGTH_SHORT).show();

                       adapter2 = new HorProAdapter(Aman(), getContext());
                        recthree.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                        recthree.setAdapter(adapter2);
                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();

                    // Toast.makeText(getContext().getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressDialog.dismiss();
                Toast.makeText(getContext().getApplicationContext(),"Sorry ,Something went Wrong Or No Connection !! "+error.getMessage(), Toast.LENGTH_SHORT).show();
                // progressDialog.dismiss();
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
        requestQueue.add(stringRequest);

        //initialize the progress dialog and show it
      /*  progressDialog = new ProgressDialog(getActivity(),R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Loading Details....");
        progressDialog.setCancelable(false);
        progressDialog.show();*/
    }

    public ArrayList<Modelhorcat> Ali() {
        Modelhorcat m1 = new Modelhorcat(sid,cid,sname,image);
        arr.add(m1);
        return arr;

    }

    public ArrayList<Modelhorpro> Aman() {
        Modelhorpro m1 = new Modelhorpro(pid,catid,pname,img);
        array.add(m1);
        return array;

    }
}
