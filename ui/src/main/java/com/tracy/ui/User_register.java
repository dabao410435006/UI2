package com.tracy.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.util.Calendar;

public class User_register extends AppCompatActivity {

    private TextView date;
    private int mYear, mMonth, mDay;

    EditText editTextEmail, editTextPassword,userName,userCellphone;
    Spinner userLocate;
    FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    private String userUID;
    public class User {
        private String Name,Date,Locate,Cellphone;


        public User() {
        }

        public User(String Name,String Date,String Cellphone,String Locate) {
            this.Name = Name;
            this.Date = Date;
            this.Cellphone = Cellphone;
            this.Locate = Locate;
        }
        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
        public String getCellphone() {
            return Cellphone;
        }

        public void setCellphone(String Cellphone) {
            this.Cellphone = Cellphone;
        }
        public String getDate() {
            return Date;
        }

        public void setDate(String Date) {
            this.Date = Date;
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
        setContentView(R.layout.activity_user_register);
        MyApplication.getInstance().addActivity(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.user_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("  ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        date = (TextView) findViewById(R.id.user_date);

        editTextEmail = (EditText) findViewById(R.id.user_register_email);
        editTextPassword = (EditText) findViewById(R.id.user_register_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        userName = (EditText) findViewById(R.id.user_ID);

        userCellphone = (EditText) findViewById(R.id.user_cellphone);
        userLocate = (Spinner) findViewById(R.id.user_spinner);


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
    public void date(View v){
        showDatePickerDialog();
    }

    public void showDatePickerDialog() {
        // 設定初始日期
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // 跳出日期選擇器
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // 完成選擇，顯示日期
                        date.setText(year + "-" + (monthOfYear + 1) + "-"
                                + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    public void Success(View v){
       // Toast.makeText(this,"註冊成功，轉至主頁面",Toast.LENGTH_SHORT).show();
       // startActivity(new Intent(this, Navigation.class));
        registerUser();
    }

    private void registerUser() {
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

        if(userName.getText().toString().trim().isEmpty()) {
            userName.setError("請輸入暱稱");
            userName.requestFocus();
            return;
        }
        if(date.getText().toString().isEmpty()) {
            date.setError("請輸入或填入生日");
            date.requestFocus();
            return;
        }
        if(userCellphone.getText().toString().trim().isEmpty()) {
            userCellphone.setError("請輸入手機號碼");
            userCellphone.requestFocus();
            return;
        }
        if(userLocate.getSelectedItem().toString()=="請選擇居住地") {
            ((TextView)userLocate.getSelectedView()).setError("請選擇居住地");
            userLocate.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    addUserAccount();
                    Toast.makeText(getApplicationContext(), "註冊成功，轉至主頁面", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("data" , MODE_PRIVATE);
                    sharedPreferences.edit().putInt("index", 0).apply();

                    finish();
                    startActivity(new Intent(User_register.this, Navigation.class));
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
    private void addUserAccount(){
        //取出DB物件
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = db.getReference("User");
        User user = new User(userName.getText().toString(),date.getText().toString(),userCellphone.getText().toString(),userLocate.getSelectedItem().toString());
        usersRef.child(userUID).setValue(user);

    }
}
