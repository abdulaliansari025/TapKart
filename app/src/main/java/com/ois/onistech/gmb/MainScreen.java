package com.ois.onistech.gmb;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ois.onistech.gmb.Const.Constant;
import com.ois.onistech.gmb.Const.ContantValue;
import com.ois.onistech.gmb.DataClass.Category;
import com.ois.onistech.gmb.DataClass.SubCategory;
import com.ois.onistech.gmb.Order.MyOrders;
import com.ois.onistech.gmb.ShowProduct.ShowPro;
//import com.ois.onistech.gmb.Cart.ShowCart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ois.onistech.gmb.CartPackage.ShowCart;
import com.ois.onistech.gmb.SinglePro.SingleItem;

public class MainScreen extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    Button b1;
    TextView textCartItemCount,t1;
    String name="",id="";
    String name1="",id1="",image1="",childname="",email="",status="";
    EditText search;
    private int lastExpandedPosition = -1;
    ExpandableListAdapter adapter;
    ExpandableListView expListView;

    List<Header> listDataHeader;
    List<List<List<Category>>> listDataHeader1;
    int j=1;
    HashMap<String, List<Subcategory>> listDataChild;

    HashMap<String, List<SubCategory>> listDataChild1;

    ArrayList<TopCategory> countryList=new ArrayList<>();
    CustomGridViewActivity adapterViewAndroid;
    private static FragmentManager fragmentManager;
    List<Category> list1 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       // b1 = (Button) findViewById(R.id.btn);
        t1 = (TextView) findViewById(R.id.head);
        search = (EditText) findViewById(R.id.et26);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        fragmentManager = getSupportFragmentManager();//Get Fragment Manager
        search.setTypeface(Typeface.SERIF);
        search.setTextSize(15);
        // preparing list data

        prepareListData1();

      //  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Product()).commit();
      //  prepareListData();
        adapter = new ExpandableListAdapter(this, list1, listDataChild1);
        expListView.setAdapter(adapter);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


       NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
       navigationView.setNavigationItemSelectedListener(this);

        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav);
        navigationView1.setNavigationItemSelectedListener(this);



       /* if(getIntent().getExtras()!=null) {

            if (getIntent().getIntExtra("fragmentNumber", 0) == 1) {
               /* String childname = getIntent().getStringExtra("childname");
                String type = "2";
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Product()).commit();
                Bundle args = new Bundle();
                args.putString("childdata", childname);
                args.putString("type", type);
                Product fragment = new Product();
                fragment.setArguments(args);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                //  transaction.addToBackStack(null);
                transaction.commit();*/
                //  getSupportActionBar().setTitle("Fragment Activity B");
                //set the desired fragment as current fragment to fragment pager
           /* } else if (getIntent().getExtras().getString(Constant.INTENT).equals(ContantValue.INTENT_VALUE_SHOWPRO)) {
                String val = getIntent().getExtras().getString(Constant.CHILD_ID);
                Toast.makeText(this, ""+ val, Toast.LENGTH_SHORT).show();
                System.out.println("Child val " + val);

           /* ShowPro argumentFragment = new ShowPro();//Get Fragment Instance
            Bundle data = new Bundle();//Use bundle to pass data
            data.putString("childdata",childname );
            // data.putString("type",type);//put string, int, etc in bundle with a key value
            argumentFragment.setArguments(data);//Finally set argument bundle to fragment

            fragmentManager.beginTransaction().replace(R.id.fragment_container, argumentFragment).addToBackStack(null).commit();*/
          /*  }
            else {
               // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Product()).commit();
            }

        }
        else {
          //   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();
        }*/

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();

      /*    if(getIntent().getExtras()!=null)
          {
              if(getIntent().getExtras().getString(Constant.INTENT).equals(ContantValue.INTENT_VALUE_SHOWPRO))
              {
                  ShowPro argumentFragment = new ShowPro();//Get Fragment Instance
                  Bundle data = new Bundle();//Use bundle to pass data
                  data.putString("childdata",getIntent().getExtras().getString(Constant.CHILD_ID) );
                  // data.putString("type",type);//put string, int, etc in bundle with a key value
                  argumentFragment.setArguments(data);//Finally set argument bundle to fragment

                  fragmentManager.beginTransaction().replace(R.id.fragment_container, argumentFragment).commit();//now replace t
                  //Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
                //  Toast.makeText(this, "my child "+getIntent().getExtras().getString(Constant.CHILD_ID), Toast.LENGTH_SHORT).show();
              }


             else if(getIntent().getExtras().getString(Constant.INTENT).equals(ContantValue.INTENT_VALUE_SINGLEITEM))
              {

                startActivity(new Intent(MainScreen.this, SingleItem.class)
                        .putExtra(Constant.INTENT,ContantValue.INTENT_VALUE_SINGLEITEM)
                .putExtra("pmm_id",getIntent().getExtras().getString(Constant.CHILD_ID)));
              }
          }
          else {
       //       Toast.makeText(this, "main fragment", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();
          }*/

        expand_collapse();
        childClick();

    }

    private void prepareListData1() {
        final String url = "http://mcmltraders.com/admin/ami/catogory.php";
        listDataChild1 = new HashMap();
        listDataHeader1 = new ArrayList<>();
        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Log.d(TAG,"working"+response);
                try {
                    listDataHeader1.clear();
//                    progressDialog.dismiss();
                    if (isJSONValid(response)) {

                     //   System.out.println(response);
//                        Type fooType = new TypeToken<List<Header>>() {}.getType();
//                        Gson gson = new GsonBuilder().create();
//                        final List<Header> headers = gson.fromJson(response, fooType);
//                        listDataHeader = headers;

                        Gson gson = new GsonBuilder().create();
                        Type fooType = new TypeToken<List<List<List<Category>>>>() {}.getType();
                        List<List<List<Category>>> modelCountTeams = gson.fromJson(response.toString(), fooType);
                        listDataHeader1 = modelCountTeams;

                      //  System.out.println("onResponse: "+modelCountTeams);

                try {


                    for (int i = 0; i < modelCountTeams.size(); i++) {

                        for (int j = 0; j <= i; j++) {

                            for (int k = 0; j <= k; k++) {

                                List<SubCategory> list = new ArrayList<>();
                               // System.out.println("CAT_NAME " + modelCountTeams.get(0).get(0).get(k).getCat_name());

                                list1.add(modelCountTeams.get(0).get(0).get(k));
                                        //  int o=;

                                for (int l = 0; l < modelCountTeams.get(0).get(0).get(k).getSub_category().size(); l++) {

                                //    System.out.println("SUB_CAT_NAME " + modelCountTeams.get(0).get(0).get(k).getSub_category().get(l).getCat_name());
                                    if (modelCountTeams.get(0).get(0).get(k).getSub_category().get(l).getCat_parent().equals(modelCountTeams.get(0).get(0).get(k).getCat_id())) {
                                        list.add(modelCountTeams.get(0).get(0).get(k).getSub_category().get(l));
                                    }
                                }

                                listDataChild1.put(modelCountTeams.get(0).get(0).get(k).getCat_id(), list);

                            }


                        }

                    }

                }
                catch (Exception e)
                {
                    for (Category a1 : list1) {
                      //  System.out.println("List1 "+a1);
                    }


                    for (String i : listDataChild1.keySet()) {
                    //    System.out.println("key: " + i + " value: " + listDataChild1.get(i));
                    }

                    for (List a : listDataHeader1) {
                      //  System.out.println("List "+a);
                    }


                 //   System.out.println("GROUP COUNT "+list1.size());
                    expListView.setAdapter(new ExpandableListAdapter(MainScreen.this, list1, listDataChild1));
                 //   System.out.println("HEllooo");
                }

                    }
                } catch (Exception e) {

                    e.printStackTrace();
//                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainScreen.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                // Log.d(TAG, "onErrorResponse: headers "+error);
//                progressDialog.dismiss();
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);


    }

    public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {

            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }

        }
        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_cart);

        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);
        //extCartItemCount.setText("1");


        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_search) {
          //  startActivity(new Intent(MainScreen.this, SearchResultActivity.class));
            Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
            return true;}*/
        if (id == R.id.action_cart) {
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BlankFragment()).commit();

            //Toast.makeText(this, "cart", Toast.LENGTH_SHORT).show();



           // startActivity(new Intent(MainScreen.this, ShowCart.class));
           /* NotificationCountSetClass.setAddToCart(MainActivity.this, item, notificationCount);
            invalidateOptionsMenu();*/
            startActivity(new Intent(MainScreen.this, ShowCart.class));

           /* notificationCount=0;//clear notification count
            invalidateOptionsMenu();*/
            return true;
        } else {
          //  startActivity(new Intent(MainActivity.this, Show.class));
            Toast.makeText(this, "notify", Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_account) {

            //Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
         //  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new fragmentLogin()).commit();
       // startActivity(new Intent(MainScreen.this,Login.class));
           // drawer.closeDrawer(GravityCompat.START);
           //  return true;
            // Handle the camera action

            drawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(MainScreen.this, MyAccount.class));

            //   startActivity(new Intent(MainScreen.this, MyOrders.class));
          //  Toast.makeText(this, "dc", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.my_orders)
        {
           // getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.fragment_container,new Vpro()).addToBackStack(null).commit();
            drawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(MainScreen.this, MyOrders.class));



        }

        /*else if (id == R.id.nav_search) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.fragment_container,new Filter()).addToBackStack(null).commit();
            //  getFragmentManager().popBackStackImmediate();
            drawer.closeDrawer(GravityCompat.START);

        }

        else if (id == R.id.nav_old) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.fragment_container,new OldEvent()).addToBackStack(null).commit();
            //  getFragmentManager().popBackStackImmediate();
            drawer.closeDrawer(GravityCompat.START);

        }

        else if (id == R.id.nav_change) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.fragment_container,new ChangeEvent()).addToBackStack(null).commit();
            //  getFragmentManager().popBackStackImmediate();
            drawer.closeDrawer(GravityCompat.START);

        }

        else if (id == R.id.nav_enquiry) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).replace(R.id.fragment_container,new myenquiry()).addToBackStack(null).commit();
            //  getFragmentManager().popBackStackImmediate();
            drawer.closeDrawer(GravityCompat.START);
            // Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
        }*/



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        expListView.setIndicatorBounds(expListView.getRight()- 150, expListView.getWidth());
    }

    //=========================================================================================//
    // To collapse all group except selected group

    public void expand_collapse()
    {
    expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
        @Override
        public void onGroupExpand(int groupPosition) {
            if (lastExpandedPosition != -1
                    && groupPosition != lastExpandedPosition) {
                expListView.collapseGroup(lastExpandedPosition);
            }
            lastExpandedPosition = groupPosition;
        }
    });
}


    public void childClick()
    {

    expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

            childname= listDataChild1.get(list1.get(groupPosition).cat_id).get(childPosition).cat_id;

            //Toast.makeText(MainScreen.this, ""+childname, Toast.LENGTH_SHORT).show();
          //  String type="1";
            ShowPro argumentFragment = new ShowPro();//Get Fragment Instance
            Bundle data = new Bundle();//Use bundle to pass data
            data.putString("childdata",childname );
           // data.putString("type",type);//put string, int, etc in bundle with a key value
            argumentFragment.setArguments(data);//Finally set argument bundle to fragment


            fragmentManager.beginTransaction().replace(R.id.fragment_container, argumentFragment).commit();//now replace the argument fragment

               /* Toast.makeText(MainScreen.this, " "+listDataChild.get(listDataHeader.get(groupPosition).id).get(childPosition).sid
                        +" "+listDataChild.get(listDataHeader.get(groupPosition).id).get(childPosition).sname, Toast.LENGTH_SHORT).show();*/
            drawer.closeDrawers();
            return false;
        }
    });

    }

    public void hide(View view) {

        //expListView.setVisibility(View.VISIBLE);
        //  TransitionManager.beginDelayedTransition(expListView);
        // expListView.setVisibility(View.VISIBLE);
        // expListView.setGroupIndicator(MainScreen.this.getResources().getDrawable(
        //     R.drawable.ic_add));
        //getexpListView().setGroupIndicator(null);
        if(j%2!=0)
        {

            if(expListView.getVisibility()== View.VISIBLE)// Toast.makeText(Main5Activity.this, "123", Toast.LENGTH_SHORT).show();
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    //TransitionManager.beginDelayedTransition(cardView);
                    expListView.setVisibility(View.GONE);
                    //   imageView.setBackgroundResource(R.drawable.ic_plus);

                    // linearLayout.removeView(tableLayout);
                }
            }
            else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                // TransitionManager.beginDelayedTransition(cardView);
                expListView.setVisibility(View.VISIBLE);
                // imageView.setBackgroundResource(R.drawable.ic_minus);
                //linearLayout.addView(tableLayout);
                j++;
            }

        }
        else if(j%2==0)
        {
            if(expListView.getVisibility()== View.VISIBLE)// Toast.makeText(Main5Activity.this, "123", Toast.LENGTH_SHORT).show();
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    // TransitionManager.beginDelayedTransition(cardView);
                    expListView.setVisibility(View.GONE);
                    //imageView.setBackgroundResource(R.drawable.ic_plus);

                    // linearLayout.removeView(tableLayout);
                }
            }
            else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //TransitionManager.beginDelayedTransition(cardView);
                expListView.setVisibility(View.VISIBLE);
                //imageView.setBackgroundResource(R.drawable.ic_minus);
                //linearLayout.addView(tableLayout);
                j++;
            }

        }
    }

    public void setupBadge(String a)
    {
        //textCartItemCount.setText(a);

        if(a.equalsIgnoreCase("0"))
        {
            textCartItemCount.setVisibility(View.GONE);
        }
        else
        {
            textCartItemCount.setVisibility(View.VISIBLE);
            textCartItemCount.setText(a);
        }
    }

    public void search(View view) {
        startActivity(new Intent(MainScreen.this,SearchActivity.class));
        //finish();
    }

   /* @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "hello ji", Toast.LENGTH_SHORT).show();
    }*/

    //========================================================================================//


}