package com.ois.onistech.gmb;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {
EditText et1;
ListView l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        et1=(EditText)findViewById(R.id.search);
        l1=(ListView)findViewById(R.id.list);
        et1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                  //  performSearch();
                    String s= et1.getText().toString();
                    Intent intent = new Intent (SearchActivity.this, MainScreen.class);
                    //intent.putExtra("EXTRA", "openFragment");
                    intent.putExtra("fragmentNumber",1);
                    intent.putExtra("childname",s);
                    startActivity(intent);
                    finish();
                    //Toast.makeText(SearchActivity.this, ""+s, Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
       // Toast.makeText(this, "cvdf", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
