package com.ois.onistech.gmb;

import android.app.ProgressDialog;
import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
ProgressDialog progressDialog;
    static ArrayList<ModelCart> arrayList;
    ArrayList<ModelCart> filterList;
    static Context context;
    LayoutInflater inflater;
    //  String str="";
//    RecyclerAdapter.onItemClickListner onItemClickListner;
    String abc="",gm="",Kg="";
    String[] type;

    public CartAdapter()
    {

    }

    public CartAdapter(ArrayList<ModelCart> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v=inflater.inflate(R.layout.cart_layout,parent,false);
        CartAdapter.MyViewHolder myViewHolder = new CartAdapter.MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ModelCart model = arrayList.get(position);
        Glide.with(context)
                .load(model.getImage())
                .apply(new RequestOptions().disallowHardwareConfig()
                        .override(Target.SIZE_ORIGINAL)
                        //.centerCrop()
                        .dontTransform()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .placeholder(R.drawable.loadinggif)
                        .format(DecodeFormat.PREFER_ARGB_8888))
                         .into(holder.mImageView);


        holder.name.setText(arrayList.get(position).getPname());
        String  unit= arrayList.get(position).getUnit();
       holder.sp11.setText(unit.replaceAll("([^\\d-]?)(-?[\\d\\.]+)([^\\d]?)", "$1 $2 $3").replaceAll(" +", " "));
        holder.price.setText("Rs "+arrayList.get(position).getPrice());
        holder.qty.setText(arrayList.get(position).getQty());



        holder.b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                int s=0;
              //  Toast.makeText(context, "add", Toast.LENGTH_SHORT).show();
                String id=arrayList.get(position).getRid();
                String s1 =holder.qty.getText().toString();
                Toast.makeText(context, ""+s1, Toast.LENGTH_SHORT).show();
                 s= Integer.parseInt(s1);

                 int s3=s + 1;
                     Toast.makeText(context, ""+s3, Toast.LENGTH_SHORT).show();
                    String s4= String.valueOf(s3);
                    holder.countadd(id,s4);


            }
        });

        holder.b3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                int s=0;
                //  Toast.makeText(context, "add", Toast.LENGTH_SHORT).show();
                String id=arrayList.get(position).getRid();
                String s1 =holder.qty.getText().toString();
                Toast.makeText(context, ""+s1, Toast.LENGTH_SHORT).show();
                s= Integer.parseInt(s1);

                int s3=s - 1;
                Toast.makeText(context, ""+s3, Toast.LENGTH_SHORT).show();
                String s4= String.valueOf(s3);
                holder.countminus(id,s4);


            }
        });


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }





    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,price,qty,weight,sp11;
        ImageView mImageView;
        CardView c1;

        LinearLayout linearLayout,l1;
        Button b1,b2,b3,b4;
        int count=0;    // kg
        int count1=0;  // 500gm
        int count2=0;  //250gm
        int count3=0;  // other
        int count4=0;  // 100gm
        int count5=0; //pcs
        //Creating the ArrayAdapter instance having the country list

        public MyViewHolder(final View itemView)
        {
            super(itemView);
            name=(itemView).findViewById(R.id.tv11);
           // price=(itemView).findViewById(R.id.tv12);
            price=(itemView).findViewById(R.id.tv13);
            weight=(itemView).findViewById(R.id.tv14);
            c1=(itemView).findViewById(R.id.cardview);
            sp11=(itemView).findViewById(R.id.sp11);
            qty=(itemView).findViewById(R.id.add3);
            b2=(itemView).findViewById(R.id.add4);
            b3=(itemView).findViewById(R.id.add2);
            mImageView = (ImageView) itemView.findViewById(R.id.image1);
            //price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            c1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    String a= arrayList.get(pos).getPid();

                }
            });

        }

        public void countadd(final String rid, final String quant) {


            String url = "http://greenmorningbasket.com/android/cartupdate.php";
            StringRequest stringRequest =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Log.d(TAG,"working"+response);
                    progressDialog.dismiss();
                    // arrayList.clear();
                    Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                    qty.setText(quant);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(context, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

                    // progressDialog.dismiss();
                }
            })
            {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<String,String>();
                  //  params.put("id","abc");
                    params.put("rid",rid);
                    params.put("qty",quant);
                   // params.put("unit",n);
                    //  params.put("rs",price);
                    //  params.put("u_pass",pass);
                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    20000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

            progressDialog = new ProgressDialog(context,R.style.AppCompatAlertDialogStyle);
            progressDialog.setMessage("Updating Cart....");
            progressDialog.setCancelable(false);
            progressDialog.show();

            }

        public void countminus(final String rid, final String quant){


            String url = "http://greenmorningbasket.com/android/cartupdate.php";
            StringRequest stringRequest =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Log.d(TAG,"working"+response);
                    progressDialog.dismiss();
                    // arrayList.clear();
                    Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                    qty.setText(quant);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(context, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

                    // progressDialog.dismiss();
                }
            })
            {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<String,String>();
                    //  params.put("id","abc");
                    params.put("rid",rid);
                    params.put("qty",quant);
                    // params.put("unit",n);
                    //  params.put("rs",price);
                    //  params.put("u_pass",pass);
                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    20000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

            progressDialog = new ProgressDialog(context,R.style.AppCompatAlertDialogStyle);
            progressDialog.setMessage("Updating Cart....");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }
            }



}








