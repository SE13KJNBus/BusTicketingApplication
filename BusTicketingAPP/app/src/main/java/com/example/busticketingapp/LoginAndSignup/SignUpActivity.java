package com.example.busticketingapp.LoginAndSignup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busticketingapp.BusList.MainActivity;
import com.example.busticketingapp.BusList.SelectDepartureActivity;
import com.example.busticketingapp.BusList.SelectSeatActivity_General;
import com.example.busticketingapp.Home.Home_Page;
import com.example.busticketingapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    EditText nameValue;
    EditText passwordValue;
    EditText passwordValueSave;
    EditText emailValue;
    EditText personNumValue;
    EditText phoneNumValue;
    Button register;
    ArrayList<String> emailList =new ArrayList<String>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_sign_up);
        //myRef.addValueEventListener(valueEventListener);
        myRef.addChildEventListener(childEventListener);


    }

    public void signUpInfo(View view) {

        nameValue = (EditText) findViewById(R.id.nameValue);
        passwordValue = (EditText) findViewById(R.id.passwordValue);
        passwordValueSave = (EditText) findViewById(R.id.passwordValueSave);
        emailValue = (EditText) findViewById(R.id.emailValue);
        personNumValue = (EditText) findViewById(R.id.personNumValue);
        phoneNumValue = (EditText) findViewById(R.id.phoneNumValue);
        String passwordVal = passwordValue.getText().toString();

        if (!passwordValue.getText().toString().equals(passwordValueSave.getText().toString())){
            Toast.makeText(this, "비밀번호가 일치하지 않습니다. 다시 입력하세요", Toast.LENGTH_LONG).show();
            /*
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    passwordValue.setText(null);
                    passwordValueSave.setText(null);
                }
            });
            */

        }

        else{

            Log.v("Subin","Register Accept");
            String emailString = emailValue.getText().toString().replace('.',':');

            String nameString = nameValue.getText().toString()+"";
            String phoneNumString = phoneNumValue.getText().toString()+"";
            String personNumString = personNumValue.getText().toString()+"";
            String passwordString = passwordValue.getText().toString()+"";

            //myRef.addChildEventListener(childEventListener);

            if(!emailList.contains(emailString)){
                Log.v("Subin","Register Accept");
                Log.v("Subin","Register pw : "+passwordString);

                myRef.child("Member").child(emailString).child("Friend").setValue("");
                myRef.child("Member").child(emailString).child("Notification").setValue("");
                myRef.child("Member").child(emailString).child("Ticket").setValue("");
                myRef.child("Member").child(emailString).child("Cart").setValue("");
                myRef.child("Member").child(emailString).child("Information").child("PhoneNumber").setValue(phoneNumString);
                myRef.child("Member").child(emailString).child("Information").child("Name").setValue(nameString);
                myRef.child("Member").child(emailString).child("Information").child("Password").setValue(passwordString);
                myRef.child("Member").child(emailString).child("Information").child("Identification").setValue(personNumString);
                Log.v("Subin",System.currentTimeMillis()+" in SignUp");

                Intent gotoLogin = new Intent(SignUpActivity.this, LoginMemberActivity.class);
                startActivity(gotoLogin);

            }else{
                Log.v("Subin", "중복");
                Toast.makeText(SignUpActivity.this, "이미 가입된 회원입니다.", Toast.LENGTH_SHORT).show();
            }




        }
    }
    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Log.v("Subin","key : "+dataSnapshot.getKey());
            if(dataSnapshot.getKey().toString().equals("Member")){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Log.v("Subin", snapshot.getKey()+" in childEventListener");
                    if(!emailList.contains(snapshot.getKey())) emailList.add(snapshot.getKey());
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
