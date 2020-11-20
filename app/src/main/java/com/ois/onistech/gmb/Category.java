package com.ois.onistech.gmb;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;
public class Category extends AppCompatActivity {
    private int lastExpandedPosition = -1;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    String flag="abc";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();
// Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
             /*   Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();*/

                String i =  listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);

                if(i.equals("Shirts"))
                {
                    flag="28";
                    Intent intent = new Intent (Category.this, MainScreen.class);
                    //intent.putExtra("EXTRA", "openFragment");
                    intent.putExtra("fragmentNumber",1);
                    intent.putExtra("get",flag);
                    startActivity(intent);
                   /* Bundle args = new Bundle();
                    args.putString("edttext",flag);
                    Product fragment = new Product();
                    fragment.setArguments(args);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frag, fragment);
                    transaction.commit();*/
                   // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Product()).commit();
                   // Toast.makeText(Category.this, ""+flag, Toast.LENGTH_SHORT).show();
                }
                else if(i.equals("Jeans")) {
                    flag="41";
                    Intent intent = new Intent (Category.this, MainScreen.class);
                    //intent.putExtra("EXTRA", "openFragment");
                    intent.putExtra("fragmentNumber",2);
                    intent.putExtra("get",flag);
                    startActivity(intent);
                    //Toast.makeText(Category.this, "" + flag, Toast.LENGTH_SHORT).show();
                }
                else if(i.equals("Shoes")) {
                    flag="45";
                    Toast.makeText(Category.this, "" + flag, Toast.LENGTH_SHORT).show();
                }
                else if(i.equals("Kurtis")) {
                    flag="4";
                    Toast.makeText(Category.this, "" + flag, Toast.LENGTH_SHORT).show();
                }
                else if(i.equals("Legins")) {
                    flag="5";
                    Toast.makeText(Category.this, "" + flag, Toast.LENGTH_SHORT).show();
                }
                else if(i.equals("Shoes")) {

                    flag="6";
                    Toast.makeText(Category.this, "" + flag, Toast.LENGTH_SHORT).show();
                }
                else if(i.equals("Tops")) {
                    flag="7";
                    Toast.makeText(Category.this, "" + flag, Toast.LENGTH_SHORT).show();
                }
                else if(i.equals("T-Shirts")) {
                    flag="8";
                    Toast.makeText(Category.this, "" + flag, Toast.LENGTH_SHORT).show();
                }
                else if(i.equals("Pants")) {
                    flag="9";
                    Toast.makeText(Category.this, "" + flag, Toast.LENGTH_SHORT).show();
                }
                else if(i.equals("Toys")) {
                    flag="10";
                    Toast.makeText(Category.this, "" + flag, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Category.this, "No Item Selected", Toast.LENGTH_SHORT).show();
                }



                return false;
            }
        });

        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    expListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
        //listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
       // expListView.setAdapter(listAdapter);

}
    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data

        listDataHeader.add("Men");
        listDataHeader.add("Women");
        listDataHeader.add("Kids");
        // listDataHeader.add("Electronics");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("Shirts");
        top250.add("Jeans");
        top250.add("Shoes");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("Kurtis");
        nowShowing.add("Legins");
        nowShowing.add("Boots");
        nowShowing.add("Tops");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("T-Shirts");
        comingSoon.add("Pants");
        comingSoon.add("Toys");


     /*   List<String> danger = new ArrayList<String>();
        danger.add("2 Guns");
        danger.add("The Smurfs 2");
        danger.add("The Spectacular Now");
        danger.add("The Canyons");
        danger.add("Europa Report");*/

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
        // listDataChild.put(listDataHeader.get(2), danger);
    }

}
