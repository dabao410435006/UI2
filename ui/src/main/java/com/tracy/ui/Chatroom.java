package com.tracy.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class Chatroom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        MyApplication.getInstance().addActivity(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.chatroom);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("聊天室");
        //讓他有返回鍵
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
