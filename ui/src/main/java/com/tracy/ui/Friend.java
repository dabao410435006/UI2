package com.tracy.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class Friend extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        MyApplication.getInstance().addActivity(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.friend);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("好友");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
