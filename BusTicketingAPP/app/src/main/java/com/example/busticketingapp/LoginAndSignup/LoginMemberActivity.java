package com.example.busticketingapp.LoginAndSignup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busticketingapp.Home.Home_Page;
import com.example.busticketingapp.Payment.PaymentWaiting;
import com.example.busticketingapp.Payment.PaymentWaiting_Cart;
import com.example.busticketingapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class LoginMemberActivity extends AppCompatActivity {

    HashMap<String, User> emailList = new HashMap<String, User>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    EditText inputEmailEdit;
    EditText inputPasswordEdit;

    private class User {
        String email;
        String pw;
        String userName;

        public User(String email, String pw, String userName) {
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
        //myRef.addValueEventListener(valueEventListener);
        myRef.addChildEventListener(childEventListener);

        inputEmailEdit = (EditText) findViewById(R.id.inputEmail);
        inputPasswordEdit = (EditText) findViewById(R.id.inputPassword);
    }

    public void sign(View view) {
        //로그인시 회원홈화면으로 넘어가야한다.
        if (inputEmailEdit.getText().toString().equals("") || inputPasswordEdit.getText().toString().equals("")) {
            return;
        }
        Log.v("Subin", emailList.size() + "");
        //Log.v("Subin",inputEmailEdit.getText().toString());
        String emailString = inputEmailEdit.getText().toString().replace('.', ':');
        String password = inputPasswordEdit.getText().toString();
        Log.v("Subin", "" + emailString);

        User user = emailList.get(emailString);
        if (user != null) {
            if (user.email.equals(emailString)) {
                if (user.pw.equals(password)) {

                    Log.v("Subin", "인증 성공");
                    Toast.makeText(LoginMemberActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Home_Page.class);
                    intent.putExtra("Id", emailString);
                    intent.putExtra("Member", true);
                    intent.putExtra("UserName", user.userName);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginMemberActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginMemberActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(LoginMemberActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
        }
    }

    public void userLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginUserActivity.class);
        startActivity(intent);
    }

    public void signUp(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            if (dataSnapshot.getKey().toString().equals("Member")) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.v("Subin", snapshot.getKey());
                    Log.v("Subin", snapshot.child("Information").child("Password").getValue() + "");
                    String pw = snapshot.child("Information").child("Password").getValue().toString();
                    String name = snapshot.child("Information").child("Name").getValue().toString();
                    emailList.put(snapshot.getKey(), new User(snapshot.getKey(), pw, name));
                }
            }

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

}
