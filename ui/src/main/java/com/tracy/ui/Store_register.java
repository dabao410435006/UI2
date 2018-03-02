package com.tracy.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Store_register extends AppCompatActivity {
    EditText editTextEmail, editTextPassword,storeName,storeCellphone,taxID,storePhone;
    Spinner storeLocate;
    FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    private String userUID;
    public class Store {
        private String Name,taxID,Cellphone,Phone,Locate;


        public Store() {
        }

        public Store(String Name,String taxID,String Cellphone,String Phone,String Locate) {
            this.Name = Name;
            this.taxID = taxID;
            this.Cellphone = Cellphone;
            this.Phone = Phone;
            this.Locate = Locate;
        }
        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
        public String getTaxID() {
            return taxID;
        }

        public void setTaxID(String taxID) {
            this.taxID = taxID;
        }

        public String getCellphone() {
            return Cellphone;
        }

        public void setCellphone(String Cellphone) {
            this.Cellphone = Cellphone;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public String getLocate() {
            return Locate;
        }

        public void setLocate(String Locate) {
            this.Locate = Locate;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_register);
        MyApplication.getInstance().addActivity(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("  ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextEmail = (EditText) findViewById(R.id.store_email);
        editTextPassword = (EditText) findViewById(R.id.store_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        storeName = (EditText) findViewById(R.id.store_ID);
        taxID = (EditText) findViewById(R.id.store_taxID);
        storeCellphone = (EditText) findViewById(R.id.store_cellphone);
        storePhone = (EditText) findViewById(R.id.store_phone);
        storeLocate = (Spinner) findViewById(R.id.spinner);

        mAuth = FirebaseAuth.getInstance();
        Firebase.setAndroidContext(this);
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

    }


    public void Success(View v){
        //Toast.makeText(this,"註冊成功，轉至主頁面",Toast.LENGTH_SHORT).show();
       // startActivity(new Intent(this, Store_navigation.class));
        registerStore();
    }
    private void registerStore() {
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
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    addStoreAccount();
                    Toast.makeText(getApplicationContext(), "註冊成功，轉至主頁面", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("data" , MODE_PRIVATE);
                    sharedPreferences.edit().putInt("index", 1).apply();
                    finish();
                    startActivity(new Intent(Store_register.this, Store_navigation.class));
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "此帳號已經存在", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "註冊失敗", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authListener);
    }
    private void addStoreAccount(){
        //取出DB物件
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = db.getReference("Store");
        Store store = new Store(storeName.getText().toString(),taxID.getText().toString(),storeCellphone.getText().toString(),storePhone.getText().toString(),storeLocate.getSelectedItem().toString());
        usersRef.child(userUID).setValue(store);

    }
}
