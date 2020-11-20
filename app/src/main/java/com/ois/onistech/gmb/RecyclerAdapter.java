package com.ois.onistech.gmb;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Paint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.ois.onistech.gmb.DataClass.Featured_Cat;
import com.ois.onistech.gmb.Interfaces.ProductClick;
import com.ois.onistech.gmb.Interfaces.ProductDropdown;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;

/**
 * Created by asus-pc on 5/7/2018.
 */

public  class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
ProgressDialog progressDialog;
     ArrayList<Featured_Cat> arrayList;
    ArrayList<Model> filterList;
     Context context;
    Context context1;
    LayoutInflater inflater;
    String row=null,row1=null;
    String sp=null;
  //  String str="";
    //onItemClickListner onItemClickListner;
    String abc="",gm="",Kg="";
    String[] type;
    SessionManager sessionManager;
    ProductDropdown onclick;
    ProductClick onCall;
    int newQty;
  //  private static int count = 0;
   // private static int count1 = 0;
  Map<Integer, Integer> mSpinnerSelectedItem = new HashMap<Integer, Integer>();
    Map<Integer, Integer> myMap = new HashMap<Integer, Integer>();
    SessionManager session;
    public RecyclerAdapter()
    {

    }


    public RecyclerAdapter(ArrayList<Featured_Cat> arrayList, Context context, ProductDropdown oncallback ,ProductClick onCall) {
        this.arrayList = arrayList;
        this.context = context;
        this.onclick = oncallback;
        this.onCall = onCall;
        sessionManager = new SessionManager(context);
        session = new SessionManager(context);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v=inflater.inflate(R.layout.showproducts,parent,false);
        final RecyclerAdapter.MyViewHolder myViewHolder = new RecyclerAdapter.MyViewHolder(v);

        return myViewHolder;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
     //   holder.setIsRecyclable(false);
        Featured_Cat model = arrayList.get(position);

//        if (mSpinnerSelectedItem.containsKey(position)) {
//            holder.s1.setSelection(mSpinnerSelectedItem.get(position));
//        }

        holder.name.setText(arrayList.get(position).getIm_item());

        Glide.with(context)
                    .load(arrayList.get(position).getPgm_img())
                    .apply(new RequestOptions().disallowHardwareConfig()
                .override(Target.SIZE_ORIGINAL) //.centerCrop()
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontAnimate()
                .placeholder(R.drawable.loadinggif)
                .format(DecodeFormat.PREFER_ARGB_8888))
                .into(holder.image1);
      //  holder.aprice.setText("Rs. "+arrayList.get(position).getSub_data().get(0).getPgm_rate()+ " /-");

        ArrayList<String> spin = new ArrayList<>();

        for(int i=0;i<arrayList.get(position).getSub_data().size();i++)
        {
            spin.add(arrayList.get(position).getSub_data().get(i).getWeight_name());


        }
        ArrayAdapter aa = new ArrayAdapter(context,R.layout.spinner_text, spin);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Sp0inner
        holder.s1.setAdapter(aa);


        int mypos =position;
      //  int sp;

        if (myMap.containsKey(position)) {
            int a= myMap.get(position);
            holder.s1.setSelection(a);
            System.out.println("A value "+a);
        }
        holder.s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int spin_pos, long id)
            {
              //  mSpinnerSelectedItem.put(mypos, spin_pos);
                myMap.put(mypos, spin_pos);

               String ser = parent.getItemAtPosition(spin_pos).toString(); //this is your selected item

              //  Toast.makeText(context, ""+ser, Toast.LENGTH_SHORT).show();
               String pmm_id= arrayList.get(mypos).getSub_data().get(spin_pos).getPmm_id();

                holder.aprice.setText("Rs. "+arrayList.get(mypos).getSub_data().get(spin_pos).getPgm_rate()+ " /-");

           // holder.qty.setText(arrayList.get(mypos).getSub_data().get(spin_pos).getCt_qty());

              //  System.out.println("proid "+(arrayList.get(mypos).getSub_data().get(spin_pos).getStatus()));
                    if(arrayList.get(mypos).getSub_data().get(spin_pos).getStatus() == 1)
                    {
                            holder.addbtn.setVisibility(GONE);
                            holder.linearLayout.setVisibility(View.VISIBLE);
                            holder.qty.setText(arrayList.get(mypos).getSub_data().get(spin_pos).getCt_qty());
                    }
                    else
                    {
                        holder.addbtn.setVisibility(View.VISIBLE);
                        holder.linearLayout.setVisibility(GONE);


                    }


                holder.addbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HashMap<String,String> user =sessionManager.getUserDetails();

                        String ses_id =  user.get(SessionManager.KEY_ID);
                        onclick.OnSpinClick(ses_id,pmm_id,"1",String.valueOf(mypos),String.valueOf(spin_pos));

                      row= String.valueOf(mypos);
                      row1 = String.valueOf(spin_pos);
                      sp = String.valueOf(spin_pos);
                    }
                });

                holder.btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                       newQty= Integer.parseInt(arrayList.get(mypos).getSub_data().get(spin_pos).getCt_qty() )+ 1;

                        HashMap<String,String> user =session.getUserDetails();

                      String ses_id=  user.get(SessionManager.KEY_ID);
                      if(ses_id !=null || ses_id !="")
                      {
                          onCall.OnProductClick(ses_id,arrayList.get(mypos).getSub_data().get(spin_pos).getCt_id(),String.valueOf(newQty),
                                  arrayList.get(mypos).getSub_data().get(spin_pos).getCt_price());
                         // onCall?.OnProductClick(mem_id,pro?.ct_id, new_qty.toString(),pro?.ct_price)
                      }
                      else
                      {
                          Toast.makeText(context, "missing user_id", Toast.LENGTH_SHORT).show();
                      }

                    }
                });


                holder.btn_minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        newQty= Integer.parseInt(arrayList.get(mypos).getSub_data().get(spin_pos).getCt_qty() ) - 1;

                        HashMap<String,String> user =session.getUserDetails();

                        String ses_id=  user.get(SessionManager.KEY_ID);
                        if(ses_id !=null || ses_id !="")
                        {
                            onCall.OnProductClick(ses_id,arrayList.get(mypos).getSub_data().get(spin_pos).getCt_id(),String.valueOf(newQty),
                                    arrayList.get(mypos).getSub_data().get(spin_pos).getCt_price());
                            // onCall?.OnProductClick(mem_id,pro?.ct_id, new_qty.toString(),pro?.ct_price)
                        }
                        else
                        {
                            Toast.makeText(context, "missing user_id", Toast.LENGTH_SHORT).show();
                        }

                    }
                });



                   /* try
                    {
                        if(Integer.parseInt(row) == mypos && Integer.parseInt(row1) == spin_pos)
                        {
                            Toast.makeText(context, "inner", Toast.LENGTH_SHORT).show();
                            holder.s1.setSelection(spin_pos);
                            holder.addbtn.setVisibility(GONE);
                           // holder.aprice.setText("Rs. "+arrayList.get(Integer.parseInt(row)).getSub_data().get(Integer.parseInt(row1)).getPgm_rate()+ " /-");

                            holder.linearLayout.setVisibility(View.VISIBLE);
                        }

                    }
                    catch (Exception e)
                    {

                    }*/


            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });




    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,price,aprice,qty,weight;
        ImageView mImageView,image1;
        CardView c1;
        Spinner s1;
        LinearLayout linearLayout,l1;
        Button b1,b2,b3,b4,addbtn,btn_add,btn_minus;
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
            aprice=(itemView).findViewById(R.id.tv13);
            image1=(itemView).findViewById(R.id.image1);
            weight=(itemView).findViewById(R.id.tv14);
            c1=(itemView).findViewById(R.id.cardview);
            s1=(itemView).findViewById(R.id.sp11);
            addbtn=(itemView).findViewById(R.id.addbtn);
            linearLayout=(itemView).findViewById(R.id.add1);
            qty=(itemView).findViewById(R.id.add3);
            btn_add=(itemView).findViewById(R.id.add4);
            btn_minus=(itemView).findViewById(R.id.add2);
            mImageView = (ImageView) itemView.findViewById(R.id.image1);
         //   price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

               c1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition();
                       // String a= arrayList.get(pos).getId();

                    }
                });

            }

            public void add(final String selected, final String pid, final String price)
             {
         // Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
         // Toast.makeText(context, ""+qty.setText(I.valueOf(count++)), Toast.LENGTH_SHORT).show();
          String url = "http://greenmorningbasket.com/android/cartadd.php";
          StringRequest stringRequest =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                  //Log.d(TAG,"working"+response);
                  try {
                    progressDialog.dismiss();
                      // arrayList.clear();
                      for (int i = 0; i < response.length(); i++) {
                          JSONArray jsonArray = new JSONArray(response);
                          JSONObject jsonObject = jsonArray.getJSONObject(i);  //get value of first index
                          // String code = jsonObject.getString("code");
                          //String message = jsonObject.getString("code"); //we should write key name
                          // sr = jsonObject.getString("sr");
                          //  id = jsonObject.getString("id");
                          String value = jsonObject.getString("value");
                         // Toast.makeText(context, ""+value, Toast.LENGTH_SHORT).show();

                          ((MainScreen) context).setupBadge(value);

                          b1.setVisibility(GONE);
                          l1.setVisibility(View.VISIBLE);
                          if(selected.equals("Kg")) {
                              count = count + 1;
                              qty.setText(String.valueOf(count));
                          }
                          else if(selected.equals("500gm")) {
                              count1 = count1 + 1;
                              qty.setText(String.valueOf(count1));
                          }
                          else if(selected.equals("250gm")) {
                              count2 = count2 + 1;
                              qty.setText(String.valueOf(count2));
                          }
                          else if(selected.equals("other"))
                          {
                              count3 = count3 + 1;
                              qty.setText(String.valueOf(count3));
                          }
                          else if(selected.equals("100gm"))
                          {
                              count4 = count4 + 1;
                              qty.setText(String.valueOf(count4));

                          }

                          else if(selected.equals("pcs"))
                          {
                              count5 = count5 + 1;
                              qty.setText(String.valueOf(count5));

                          }
                         // Toast.makeText(context, ""+String.valueOf(count++), Toast.LENGTH_SHORT).show();


                      }
                  } catch (JSONException e) {
                      progressDialog.dismiss();
                      e.printStackTrace();
                  }

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
                  params.put("id","abc");
                  params.put("pid",pid);
                  params.put("qty","1");
                  params.put("unit",selected);
                  params.put("rs",price);
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
        progressDialog.setMessage("Adding to cart....");
        progressDialog.setCancelable(false);
        progressDialog.show();
      }

      public void countadd(String n,String pid){
           // String pid="abc";
            if(n.equals("Kg")){
             count = count+1;
             String counter=String.valueOf(count);
               countinc(counter,pid,n);
             qty.setText(String.valueOf(count));

            }

          if(n.equals("500gm")){
              count1 = count1+1;
              String counter=String.valueOf(count1);
              countinc(counter,pid,n);
              qty.setText(String.valueOf(count1));

          }

          if(n.equals("250gm")){
              count2 = count2+1;
              String counter=String.valueOf(count2);
              countinc(counter,pid,n);
              qty.setText(String.valueOf(count2));

          }

          if(n.equals("other"))
          {
              count3 = count3+1;
              String counter=String.valueOf(count3);
              countinc(counter,pid,n);
              qty.setText(String.valueOf(count3));
          }

          if(n.equals("100gm"))
          {
              count4 = count4+1;
              String counter=String.valueOf(count4);
              countinc(counter,pid,n);
              qty.setText(String.valueOf(count4));
          }
          if(n.equals("pcs"))
          {
              count5 = count5+1;
              String counter=String.valueOf(count5);
              countinc(counter,pid,n);
              qty.setText(String.valueOf(count5));
          }


      }

      public void countinc(final String counter, final String pid, final String n)
      {
//           Toast.makeText(context, "count value "+counter, Toast.LENGTH_SHORT).show();
//           Toast.makeText(context, "id "+pid, Toast.LENGTH_SHORT).show();
//          Toast.makeText(context, "unit "+n, Toast.LENGTH_SHORT).show();


          String url = "http://greenmorningbasket.com/android/updatecart.php";
          StringRequest stringRequest =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                  //Log.d(TAG,"working"+response);
                  progressDialog.dismiss();
                  // arrayList.clear();
                  Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();

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
                  params.put("id","abc");
                  params.put("pid",pid);
                  params.put("qty",counter);
                  params.put("unit",n);
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
          progressDialog.setMessage("Adding to cart....");
          progressDialog.setCancelable(false);
          progressDialog.show();

      }

      public void countminus(String m,String pid){


          if(m.equals("Kg")){
              if(count==1)
              {
                  Toast.makeText(context, "Quantity can't be less than 1", Toast.LENGTH_SHORT).show();
              }
              else {
                  count = count - 1;
                  String counter=String.valueOf(count);
                  countdsc(counter,pid,m);
                  // Toast.makeText(context, ""+count, Toast.LENGTH_SHORT).show();
                  qty.setText(String.valueOf(count));
              }

          }

          if(m.equals("500gm")){
              if(count1==1)
              {
                  Toast.makeText(context, "Quantity can't be less than 1", Toast.LENGTH_SHORT).show();
              }
              else {
                  count1 = count1 - 1;
                  String counter=String.valueOf(count1);
                  countdsc(counter,pid,m);
                  // Toast.makeText(context, ""+count, Toast.LENGTH_SHORT).show();
                  qty.setText(String.valueOf(count1));
              }

          }

          if(m.equals("250gm")){
              if(count2==1)
              {
                  Toast.makeText(context, "Quantity can't be less than 1", Toast.LENGTH_SHORT).show();
              }
              else {
                  count2 = count2 - 1;
                  String counter=String.valueOf(count2);
                  countdsc(counter,pid,m);
                  // Toast.makeText(context, ""+count, Toast.LENGTH_SHORT).show();
                  qty.setText(String.valueOf(count2));
              }

          }

          if(m.equals("other")){
              if(count3==1)
              {
                  Toast.makeText(context, "Quantity can't be less than 1", Toast.LENGTH_SHORT).show();
              }
              else {
                  count3 = count3 - 1;
                  String counter=String.valueOf(count3);
                  countdsc(counter,pid,m);
                  qty.setText(String.valueOf(count3));
              }

          }

          if(m.equals("100gm")){
              if(count4==1)
              {
                  Toast.makeText(context, "Quantity can't be less than 1", Toast.LENGTH_SHORT).show();
              }
              else {
                  count4 = count4 - 1;
                  String counter=String.valueOf(count4);
                  countdsc(counter,pid,m);
                  qty.setText(String.valueOf(count4));
              }

          }

          if(m.equals("pcs")){
              if(count5==1)
              {
                  Toast.makeText(context, "Quantity can't be less than 1", Toast.LENGTH_SHORT).show();
              }
              else {
                  count5 = count5 - 1;
                  String counter=String.valueOf(count5);
                  countdsc(counter,pid,m);
                  qty.setText(String.valueOf(count5));
              }

          }

      }

      public void countdsc(final String counter, final String pid, final String n)
      {
//           Toast.makeText(context, "count value "+counter, Toast.LENGTH_SHORT).show();
//           Toast.makeText(context, "id "+pid, Toast.LENGTH_SHORT).show();
//          Toast.makeText(context, "unit "+n, Toast.LENGTH_SHORT).show();


          String url = "http://greenmorningbasket.com/android/updatecart.php";
          StringRequest stringRequest =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                  //Log.d(TAG,"working"+response);
                  progressDialog.dismiss();
                  // arrayList.clear();
                  Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();

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
                  params.put("id","abc");
                  params.put("pid",pid);
                  params.put("qty",counter);
                  params.put("unit",n);
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
          progressDialog.setMessage("Adding to cart....");
          progressDialog.setCancelable(false);
          progressDialog.show();
      }

    }


    public void setDataSet(ArrayList<Featured_Cat> list){
        System.out.println("new list"+list);
        arrayList.clear();
        arrayList.addAll(list);
       // products?.addAll(newList!!);

      //  notifyItemChanged(0,list.size());
        notifyDataSetChanged();
    }




    }







