package com.tracy.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication.getInstance().addActivity(this);

        mAuth = FirebaseAuth.getInstance();




        //Log.d("0是user,1是store", toString().valueOf(ID));

    }
    public void Register(View v){

        startActivity(new Intent(this, Register.class));


    }
    public void Login(View v) {
        startActivity(new Intent(this, Login.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {

            //產生XML來存取資料以Key-Value方式
            //MODE_PRIVATE : 只有此程式可以存取
            SharedPreferences sharedPreferences = getSharedPreferences("data" , MODE_PRIVATE);

            int index = sharedPreferences.getInt("index" , 0);
            Log.d("0是user,1是store", toString().valueOf(index));
            if(index==0) {
                finish();
                startActivity(new Intent(this, Navigation.class));
            }
            else {
                finish();
                startActivity(new Intent(this, Store_navigation.class));
            }


            //
        }

    }
}
