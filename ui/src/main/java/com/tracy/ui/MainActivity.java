package com.tracy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication.getInstance().addActivity(this);
    }
    public void Register(View v){

        startActivity(new Intent(this, Register.class));


    }
    public void Login(View v) {
        startActivity(new Intent(this, Login.class));
    }
}
