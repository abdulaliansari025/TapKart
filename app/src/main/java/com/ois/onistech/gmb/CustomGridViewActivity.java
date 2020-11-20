package com.ois.onistech.gmb;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomGridViewActivity  extends BaseAdapter {
    TextView textViewAndroid;
    private Context mContext;
   // private final String[] gridViewString;
   // private final int[] gridViewImageId;


    ArrayList<TopCategory> countryList;
    LayoutInflater inflter;

    public CustomGridViewActivity(Context context,ArrayList<TopCategory> countryListobj) {
        mContext = context;
       // this.gridViewImageId = gridViewImageId;
        this.countryList = countryListobj;

       /* for(int k=0;k<countryList.size();k++) {
            String s = countryList.get(k).getId().toString();
            String s1 = countryList.get(k).getImage().toString();

            Toast.makeText(mContext, ""+s, Toast.LENGTH_SHORT).show();
            Toast.makeText(mContext, ""+s1, Toast.LENGTH_SHORT).show();
        }*/
    }


    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

       // if (convertView == null) {

            gridViewAndroid = new View(mContext);
            gridViewAndroid = inflater.inflate(R.layout.customgrid, null);
             textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_text);
            ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.android_gridview_image);

           // ArrayList<String> str =new ArrayList<>();
           //for(int k=0;k<countryList.size();k++) {

              // String s = countryList.get(k).getId().toString();

               //countryList.get(k).getId();//
               textViewAndroid.setText(countryList.get(i).getId());
                   //imageView.setText(web[position]);
                   //int color = Integer.parseInt("006622", 16)+0xFF000000;
                   //imageView.setTextColor(color);
                //   textViewAndroid.setText(countryList.get(k).getId());

               // textViewAndroid.setText(countryList.get(k).getId());
          //  }


       /* } else {
            gridViewAndroid = (View) convertView;
        }*/

        return gridViewAndroid;
    }
}
