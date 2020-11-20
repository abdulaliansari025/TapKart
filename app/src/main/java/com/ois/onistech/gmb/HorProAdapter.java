package com.ois.onistech.gmb;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;


public  class HorProAdapter extends RecyclerView.Adapter<HorProAdapter.MyViewHolder> {
    ProgressDialog progressDialog;
    ArrayList<Modelhorpro> arrayList;
    static Context context;
    LayoutInflater inflater;
    //  String str="";
    String abc="",gm="",Kg="";
    String[] type;
    //  private static int count = 0;
    // private static int count1 = 0;

    public HorProAdapter()
    {

    }


    public HorProAdapter(ArrayList<Modelhorpro> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        inflater = LayoutInflater.from(context);
//        for (int i=0;i<arrayList.size();i++)
//        {
//            Toast.makeText(context, ""+arrayList.get(i).getSname(), Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

         View v=inflater.inflate(R.layout.horcardlayout,parent,false);
        final HorProAdapter.MyViewHolder myViewHolder = new HorProAdapter.MyViewHolder(v);

        return myViewHolder;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Modelhorpro model = arrayList.get(position);
        Glide.with(context)
                .load(model.getImg())
                .apply(new RequestOptions().disallowHardwareConfig()
                        .override(Target.SIZE_ORIGINAL)
                        //.centerCrop()
                        .dontTransform()

                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .dontAnimate()

                        .placeholder(R.drawable.loadinggif)
                        .format(DecodeFormat.PREFER_ARGB_8888))
                .into(holder.mImageView);
        String my= arrayList.get(position).getPname();
        String upperString = my.substring(0,1).toUpperCase() + my.substring(1);
        holder.name.setText(upperString);



    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,price,aprice,qty,weight;
        ImageView mImageView;
        CardView c1;
        Spinner s1;
        LinearLayout linearLayout,l1;

        public MyViewHolder(final View itemView)
        {
            super(itemView);
            name=(itemView).findViewById(R.id.tv);

            mImageView = (ImageView) itemView.findViewById(R.id.iv);



        }



    }




}

















