package com.example.busticketingapp.LoginAndSignup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busticketingapp.Cart.cart;
import com.example.busticketingapp.Home.Home_Page;
import com.example.busticketingapp.Payment.PaymentWaiting;
import com.example.busticketingapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginUserActivity extends AppCompatActivity {

    EditText phoneNumber;
    EditText password;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_user);

        phoneNumber = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
    }

    public void sign(View view) {

        if (phoneNumber.getText().toString().equals("") || password.getText().toString().equals("")) {
            Toast.makeText(this.getApplicationContext(), "필수정보를 입력하세요", Toast.LENGTH_SHORT).show();
        } else {
            if (password.getText().length() < 4) {
                Toast.makeText(this.getApplicationContext(), "비밀번호는 네자리이상 입력해야합니다.", Toast.LENGTH_SHORT).show();
            } else {
                String phone = phoneNumber.getText().toString();
                String pwd = password.getText().toString();

                String Id = phone+":"+pwd;

                myRef.child(Id).child("Information").child("PhoneNumber").setValue(phone);
                myRef.child(Id).child("Information").child("Password").setValue(pwd);

                //로그인시 비회원홈화면으로 넘어가야한다.
                Intent intent = new Intent(getApplicationContext(), Home_Page.class);
                intent.putExtra("Id",Id);
                intent.putExtra("Member",(boolean)false);
                startActivity(intent);
            }

        }
    }

    public void signUp(View view) {
        // 주헌 : 장바구니 테스트
        Intent intent = new Intent(getApplicationContext(), cart.class);
        startActivity(intent);
    }
}
