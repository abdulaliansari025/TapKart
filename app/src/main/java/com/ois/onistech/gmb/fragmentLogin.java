package com.ois.onistech.gmb;


import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragmentLogin extends Fragment {

    ProgressDialog progressDialog;

    LinearLayout container;
    String serverurl ="http://bollywoodcity.in/kart/login.php";
    Button b1;

    // EditText e1,e2;
    Button cont,cont1;
    String email,pass;
    // Session Manager Class
    SessionManager session;
    public fragmentLogin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View v= inflater.inflate(R.layout.fragment_fragment_login, container, false);



        return v;
    }

}
