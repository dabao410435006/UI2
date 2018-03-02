package com.tracy.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by angoo on 2018/1/24.
 */

public class User_login extends Fragment {
    Button btn;
    TextView register;
    EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener authListener;
    private String userUID;
    ProgressBar progressBar;
    boolean isuserUID = false;

    public User_login() {
        // Required empty public constructor

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    //第一次顯示區塊會呼叫的，利用LayoutInflater產生畫面元件回傳
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_login, container, false);
        btn = (Button) v.findViewById(R.id.user_login);
        register = (TextView) v.findViewById(R.id.textView3);

        editTextEmail = (EditText) v.findViewById(R.id.user_login_email);
        editTextPassword = (EditText) v.findViewById(R.id.user_login_password);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        Firebase.setAndroidContext(getActivity());
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(
                    @NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null) {
                    Log.d("onAuthStateChanged", "登入:"+
                            user.getUid());
                    userUID =  user.getUid();

                }else{
                    Log.d("onAuthStateChanged", "已登出");
                }
            }
        };


        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                startActivity(new Intent(getActivity(), Register.class));
            }
         });

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userLogin();
                // TODO Auto-generated method stub
                //Toast.makeText(getActivity(), "登入成功，轉至主頁面", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getActivity(), Navigation.class));
            }
        });
    }
    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("請輸入email");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("請輸入包含@的email格式");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("請輸入密碼");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("至少要6個字元");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    getUserAccount();
                    if(isuserUID == false)
                        Toast.makeText(getActivity(), "此帳號非一般使用者", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "登入失敗，帳號或密碼錯誤", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authListener);
    }

    private void getUserAccount() {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = db.getReference("User");
        usersRef.addChildEventListener(new com.google.firebase.database.ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().contains(userUID)) {
                    Log.d("鍵值", "true");
                    isuserUID = true;
                    if(isuserUID) {
                        Toast.makeText(getActivity(), "登入成功，轉至主頁面", Toast.LENGTH_SHORT).show();

                        //產生XML來存取資料以Key-Value方式
                        //MODE_PRIVATE : 只有此程式可以存取
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data" , getActivity().MODE_PRIVATE);
                        sharedPreferences.edit().putInt("index", 0).apply();

                        getActivity().finish();
                        startActivity(new Intent(getActivity(), Navigation.class));
                    }

                }
                else {
                    Log.d("鍵值", "false");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*//將Firebase實體化後，把在Firebase官網上的資料庫網址貼上
        Firebase firebase = new Firebase("https://fir-1-2b746.firebaseio.com/User");
        //建立監聽事件，當資料庫當中的欄位被增加/刪除/移動等等就會進行觸發
        //當資料庫有欄位異動，就會同步刷新listview
        firebase.addValueEventListener(new ValueEventListener(){


            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                if(userUID == null) {
                    Toast.makeText(getActivity(),"此帳號不是般使用者",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/
    }
}

