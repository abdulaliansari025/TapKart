package com.ois.onistech.gmb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
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

public class SignUp extends AppCompatActivity {
    ProgressDialog progressDialog;
EditText e1,e2,e3,e4;
String name,email,pass,mobile;
    SessionManager session;
    String serverurl ="http://mcmltraders.com/admin/ami/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        session = new SessionManager(getApplicationContext());
        e1=(EditText)findViewById(R.id.et21);
        e2=(EditText)findViewById(R.id.et22);
        e3=(EditText)findViewById(R.id.et23);
        e4=(EditText)findViewById(R.id.et24);


    }

    public void signin(View view) {
        if(getIntent().getExtras()!=null)
        {
            if(getIntent().getExtras().getString(Constant.INTENT).equals(ContantValue.INTENT_VALUE_SHOWPRO))
            {
                Toast.makeText(SignUp.this, ""+getIntent().getExtras().getString(Constant.CHILD_ID), Toast.LENGTH_LONG).show();
                startActivity(new Intent(SignUp.this, Login.class)
                        .putExtra(Constant.INTENT,ContantValue.INTENT_VALUE_SHOWPRO)
                        .putExtra(Constant.CHILD_ID,getIntent().getExtras().getString(Constant.CHILD_ID)));
                finish();
            }

          else if(getIntent().getExtras().getString(Constant.INTENT).equals(ContantValue.INTENT_VALUE_SINGLEITEM))
            {
                Toast.makeText(SignUp.this, ""+getIntent().getExtras().getString(Constant.CHILD_ID), Toast.LENGTH_LONG).show();
                startActivity(new Intent(SignUp.this, Login.class)
                        .putExtra(Constant.INTENT,ContantValue.INTENT_VALUE_SINGLEITEM)
                        .putExtra(Constant.CHILD_ID,getIntent().getExtras().getString(Constant.CHILD_ID)));
                finish();
            }
        }
        else {
            startActivity(new Intent(SignUp.this, Login.class));
            finish();
            //  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();
        }
    }

    private void getdata()
    {
        name=e1.getText().toString().trim();
        mobile=e2.getText().toString().trim();
        email=e3.getText().toString().trim();
        pass=e4.getText().toString().trim();

        if(name.equals("") || mobile.equals("") || email.equals("") || pass.equals(""))
        {
            Toast.makeText(this, "Field can't be blank", Toast.LENGTH_SHORT).show();
        }
        else
        {
            getvolley();
        }


    }

    private void getvolley() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, serverurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            progressDialog.dismiss();
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject= jsonArray.getJSONObject(0);
                            String code=jsonObject.getString("code");
                            String message=jsonObject.getString("message");
                            if(code.equals("0"))
                            {

                                e2.setText("");
                                e3.setText("");
                                Toast.makeText(SignUp.this, ""+message, Toast.LENGTH_LONG).show();
                            }
                            else if(code.equals("1"))
                            {

                                String id= jsonObject.getString("id");
                                String name= jsonObject.getString("name");
                                String email= jsonObject.getString("email");
                                String mobile= jsonObject.getString("mobile");
                                // Toast.makeText(Login.this, "1", Toast.LENGTH_SHORT).show();
                                session.createLoginSession(name, id,email,mobile);
                               // progressDialog.dismiss();
                        //        Toast.makeText(SignUp.this, ""+message, Toast.LENGTH_LONG).show();
//                                startActivity(new Intent(SignUp.this,Login.class));
//                                finish();


                                if(getIntent().getExtras()!=null)
                                {
                                    if(getIntent().getExtras().getString(Constant.INTENT).equals(ContantValue.INTENT_VALUE_SHOWPRO))
                                    {
                                     //   Toast.makeText(SignUp.this, ""+getIntent().getExtras().getString(Constant.CHILD_ID), Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(SignUp.this, MainScreen.class)
                                                .putExtra(Constant.INTENT,ContantValue.INTENT_VALUE_SHOWPRO)
                                                .putExtra(Constant.CHILD_ID,getIntent().getExtras().getString(Constant.CHILD_ID))
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                        finish();
                                    }
                                }
                                else {
                                    startActivity(new Intent(SignUp.this, MainScreen.class));
                                    finish();
                                  //  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();
                                }





                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(SignUp.this, error.toString(), Toast.LENGTH_SHORT).show();

            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("u_name",name);
                params.put("u_mail",email);
                params.put("u_mobile",mobile);
                params.put("u_pass",pass);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SignUp.this);
        requestQueue.add(stringRequest);
         //startActivity(new Intent(getContext().getApplicationContext(),MainActivity.class));
       progressDialog = new ProgressDialog(SignUp.this,R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Signing Up....");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void register(View view) {
        getdata();
       // getvolley();
    }
}
