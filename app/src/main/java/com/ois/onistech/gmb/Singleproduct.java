package com.ois.onistech.gmb;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Singleproduct extends Fragment {
    String a="37";
    ViewPager viewPager;
    TextView t1;
    ArrayList<String> picArray = new ArrayList<String>();
    customSwipeAdapter adapter;
    List<SliderUtils> sliderImg;
    String request_url;
    //String request_url="http://bollywoodcity.in/kart/singleproduct.php?pid="+a+"";
    public Singleproduct() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_singleproduct, container, false);
       String value=getArguments().getString("location");
         request_url="http://bollywoodcity.in/kart/singleproduct.php?pid="+value+"";

        Toast.makeText(getContext().getApplicationContext(), ""+value, Toast.LENGTH_SHORT).show();


        viewPager =(ViewPager)v.findViewById(R.id.view_pager);
        sliderImg= new ArrayList<>();
        // adapter=new customSwipeAdapter(this,imageUrls);
        viewPager.setAdapter(adapter);
        sendRequest();

        t1=(TextView)v.findViewById(R.id.add);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
        return  v;
    }

    public void sendRequest()
    {
        //  JSONArrayRequest jsonArrayRequest= new JSONArrayRe
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i =0;i<response.length();i++)
                {
                    SliderUtils sliderUtils= new SliderUtils();
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                      /*  String id =jsonObject.getString("image");
                        String[] words=id.split(",");
                        for (int j = 0; j < words.length; j++) {
                            sliderUtils.setSliderImageurls(words[j]);
                            sliderImg.add(sliderUtils);
                        }*/
                       // sliderUtils.setSliderImageurls(jsonObject.getString("image"));
                        //sliderUtils.setSliderImageurls(jsonObject.getString("image1"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    sliderImg.add(sliderUtils);
                }
//                adapter=new customSwipeAdapter(sliderImg,getContext().getApplicationContext());
//                viewPager.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }


    public void add()
    {

    }

}
