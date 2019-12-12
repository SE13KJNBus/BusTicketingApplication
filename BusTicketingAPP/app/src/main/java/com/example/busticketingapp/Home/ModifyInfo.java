package com.example.busticketingapp.Home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busticketingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ModifyInfo extends AppCompatActivity {

    DatabaseReference myRef;
    Button btnModify;
    TextView name;
    TextView email;
    TextView phoneNum;

    String getId;
    String getName;

    String userName;
    String userPhoneNum;
    String prePassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_infomation);

        getId = getIntent().getStringExtra("Id");
        getName = getIntent().getStringExtra("UserName");

        name = findViewById(R.id.MemberName);
        email = findViewById(R.id.MemberEmailAddress);
        email.setText(getId.replace(':','.'));
        phoneNum = findViewById(R.id.MemberPhoneNum);
        btnModify = findViewById(R.id.btnModifyInfo);
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoModify = new Intent(ModifyInfo.this, Modifying.class);
                gotoModify.putExtra("Id",getId);
                gotoModify.putExtra("UserName",getName);
                gotoModify.putExtra("prePW", prePassWord);
                startActivity(gotoModify);
            }
        });

        myRef = FirebaseDatabase.getInstance().getReference().child("Member").child(getId).child("Information");
        myRef.addValueEventListener(valueEventListener);

    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Log.v("Subin", "%%%%%%%%%%%%%%%%%     "+dataSnapshot.getKey() );
            userName = dataSnapshot.child("Name").getValue().toString();
            name.setText(userName);
            userPhoneNum = dataSnapshot.child("PhoneNumber").getValue().toString();
            phoneNum.setText(userPhoneNum);
            prePassWord = dataSnapshot.child("Password").getValue().toString();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
