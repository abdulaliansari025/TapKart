package com.ois.onistech.gmb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ois.onistech.gmb.Const.Constant;
import com.ois.onistech.gmb.Const.ContantValue;
import com.ois.onistech.gmb.ShowProduct.ShowPro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    ProgressDialog progressDialog;

    LinearLayout container;
    String serverurl ="http://mcmltraders.com/admin/ami/login.php";
    Button b1;
    final Context c = this;
    EditText e1,e2;
    Button cont,cont1;
    String mob,pass;
    // Session Manager Class
    SessionManager session;
    AlertDialog alertDialogAndroid;

  /*  @BindView(R.id.et23)
    EditText e1;

    @BindView(R.id.et24)
    EditText e2;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
      //  ButterKnife.bind(this);
        session = new SessionManager(getApplicationContext());
        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(Login.this, MainScreen.class);
            startActivity(intent);
            finish();
        }
      e1=(EditText)findViewById(R.id.mob);
        e2=(EditText)findViewById(R.id.password);

        /*e1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    v.setBackgroundResource(R.drawable.editboxxx);
                }
                else {
                    v.setBackgroundResource(R.drawable.lostfocus);
                }
            }
        });*/
    }


    private void getdata()
    {  mob=e1.getText().toString().trim();
        pass=e2.getText().toString().trim();


        if(mob.equals("") || pass.equals(""))
        {
            Toast.makeText(this, "Field can't be blank", Toast.LENGTH_SHORT).show();
        }
        else
        {
            getvolley();
        }


    }

    public void login(View view)
    {
        getdata();
       // getvolley();
//         startActivity(new Intent(Login.this,MainScreen.class));
//         finish();
    }

    private void getvolley() {
        StringRequest stringRequest =  new StringRequest(Request.Method.POST, serverurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d(TAG,"working"+response);
                 progressDialog.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);  //get value of first index
                    String code = jsonObject.getString("code");
                    //String message = jsonObject.getString("code");
                 //   String check = jsonObject.getString("check");//we should write key name

                 //   Toast.makeText(Login.this, "code +"+code, Toast.LENGTH_SHORT).show();
                    if(code.equals("0"))
                    {
                        //String message = jsonObject.getString("code");
                        progressDialog.dismiss();
                      //  e1.setText("");
                       // e2.setText("");
                      //  Toast.makeText(Login.this, ""+message, Toast.LENGTH_SHORT).show();
                    }

                    else if(code.equals("1"))
                    //  else if(code.equals("login_success"))
                    {
                        progressDialog.dismiss();
                        String id= jsonObject.getString("id");
                        String name= jsonObject.getString("name");
                        String email= jsonObject.getString("email");
                        String mobile= jsonObject.getString("mobile");
                        // Toast.makeText(Login.this, "1", Toast.LENGTH_SHORT).show();
                        session.createLoginSession(name, id,email,mobile);


                        if(getIntent().getExtras()!=null)
                        {
                           // Toast.makeText(Login.this, "check", Toast.LENGTH_SHORT).show();
                            if(getIntent().getExtras().getString(Constant.INTENT).equals(ContantValue.INTENT_VALUE_SHOWPRO))
                            {
                               // Toast.makeText(Login.this, "extra "+getIntent().getExtras().getString(Constant.CHILD_ID), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this, MainScreen.class)
                                        .putExtra(Constant.INTENT,ContantValue.INTENT_VALUE_SHOWPRO)
                                .putExtra(Constant.CHILD_ID,getIntent().getExtras().getString(Constant.CHILD_ID))
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();
                            }

                           else if(getIntent().getExtras().getString(Constant.INTENT).equals(ContantValue.INTENT_VALUE_SINGLEITEM))
                            {
                                // Toast.makeText(Login.this, "extra "+getIntent().getExtras().getString(Constant.CHILD_ID), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this, MainScreen.class)
                                        .putExtra(Constant.INTENT,ContantValue.INTENT_VALUE_SINGLEITEM)
                                        .putExtra(Constant.CHILD_ID,getIntent().getExtras().getString(Constant.CHILD_ID)));
                                finish();
                            }
                        }
                        else
                            {

                            startActivity(new Intent(Login.this, MainScreen.class));
                            finish();

                             }
                       // progressDialog.dismiss();

                        }

                } catch (JSONException e) {
                    Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this,""+error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String,String>();
                params.put("u_mobile",mob);
                params.put("u_pass",pass);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        requestQueue.add(stringRequest);
        //initialize the progress dialog and show it
        progressDialog = new ProgressDialog(Login.this,R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Authenticating....");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    public void signup(View view) {
        if(getIntent().getExtras()!=null)
        {
            // Toast.makeText(Login.this, "check", Toast.LENGTH_SHORT).show();
            if(getIntent().getExtras().getString(Constant.INTENT).equals(ContantValue.INTENT_VALUE_SHOWPRO))
            {
                // Toast.makeText(Login.this, "extra "+getIntent().getExtras().getString(Constant.CHILD_ID), Toast.LENGTH_SHORT).show();
               startActivity(new Intent(Login.this, SignUp.class)
                        .putExtra(Constant.INTENT,ContantValue.INTENT_VALUE_SHOWPRO)
                        .putExtra(Constant.CHILD_ID,getIntent().getExtras().getString(Constant.CHILD_ID)));
                finish();
            }
          else  if(getIntent().getExtras().getString(Constant.INTENT).equals(ContantValue.INTENT_VALUE_SINGLEITEM))
            {
              //  Toast.makeText(Login.this, "extra "+getIntent().getExtras().getString(Constant.CHILD_ID), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this, SignUp.class)
                        .putExtra(Constant.INTENT,ContantValue.INTENT_VALUE_SINGLEITEM)
                        .putExtra(Constant.CHILD_ID,getIntent().getExtras().getString(Constant.CHILD_ID)));
                finish();
            }
        }
        else
        {
            startActivity(new Intent(Login.this,SignUp.class));
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
