package com.example.busticketingapp.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busticketingapp.LoginAndSignup.LoginMemberActivity;
import com.example.busticketingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Modifying extends AppCompatActivity {

    DatabaseReference myRef;
    Button btnModify;
    Button btnCancel;
    TextView inputPw;
    TextView confirmPw;

    String getId;
    String getName;
    String getPW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_modifying);

        getId = getIntent().getStringExtra("Id");
        getName = getIntent().getStringExtra("UserName");
        getPW = getIntent().getStringExtra("prePW");
        myRef = FirebaseDatabase.getInstance().getReference().child("Member").child(getId).child("Information");

        inputPw = findViewById(R.id.inputNewPassword);
        confirmPw = findViewById(R.id.confirmPassword);
        btnCancel = findViewById(R.id.btnCancelModify);
        btnCancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent gotoLogin = new Intent(Modifying.this, LoginMemberActivity.class);
                startActivity(gotoLogin);
            }
        });
        btnModify = findViewById(R.id.btnModifyInfo);
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!inputPw.getText().toString().equals(confirmPw.getText().toString())) {
                    Toast.makeText(Modifying.this, "비밀번호가 다릅니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(inputPw.getText().toString().equals(getPW)){
                    Toast.makeText(Modifying.this, "새로운 비밀번호를 입력하세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                String newPW = inputPw.getText().toString();
                myRef.child("Password").setValue(newPW);

                Intent gotoLogin = new Intent(Modifying.this, LoginMemberActivity.class);
                startActivity(gotoLogin);
            }
        });
    }
}
