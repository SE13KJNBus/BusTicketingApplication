package com.example.busticketingapp.LoginAndSignup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busticketingapp.Home.Home_Page;
import com.example.busticketingapp.Payment.PaymentWaiting;
import com.example.busticketingapp.Payment.PaymentWaiting_Cart;
import com.example.busticketingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class LoginMemberActivity extends AppCompatActivity {

    HashMap<String, User> emailList =new  HashMap<String, User>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("Member");

    EditText inputEmailEdit;
    EditText inputPasswordEdit;

    private class User{
        String email;
        String pw;
        String userName;
        public User(String email, String pw, String userName){
            this.email = email;
            this.pw = pw;
            this.userName = userName;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inputEmailEdit = (EditText) findViewById(R.id.inputEmail);
        //inputPasswordEdit = (EditText) findViewById(R.id.inputPassword);

        setContentView(R.layout.login_member);
        myRef.addValueEventListener(valueEventListener);


        inputEmailEdit = (EditText) findViewById(R.id.inputEmail);
        inputPasswordEdit=(EditText) findViewById(R.id.inputPassword);
    }

    public void sign(View view){
        //로그인시 회원홈화면으로 넘어가야한다.

        Log.v("Subin",emailList.size()+"");
        //Log.v("Subin",inputEmailEdit.getText().toString());
        String emailString = inputEmailEdit.getText().toString().replace('.',':');
        String password = inputPasswordEdit.getText().toString();
        Log.v("Subin",""+emailString);

        User user = emailList.get(emailString);

        Log.v("Subin","user : "+user.email);
        Log.v("Subin","pw : "+ user.pw);
        Log.v("Subin",""+password);
        if(user.pw.equals(password)){

            Log.v("Subin","인증 성공");
            Toast.makeText(LoginMemberActivity.this, "로그인 성공",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Home_Page.class);
            intent.putExtra("Id",emailString);
            intent.putExtra("Member",true);
            intent.putExtra("UserName",user.userName);
            startActivity(intent);
        }else{
            Toast.makeText(LoginMemberActivity.this, "로그인 실패",Toast.LENGTH_SHORT).show();
        }

    }

    public void userLogin(View view){
        Intent intent = new Intent(getApplicationContext(), LoginUserActivity.class);
        startActivity(intent);
    }

    public void signUp(View view){
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                Log.v("Subin", snapshot.getKey());

                Log.v("Subin", snapshot.child("Information").child("Password").getValue().toString());
                String pw = snapshot.child("Information").child("Password").getValue().toString();
                String name = snapshot.child("Information").child("Name").getValue().toString();
                emailList.put(snapshot.getKey(),new User(snapshot.getKey(),pw,name));
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
