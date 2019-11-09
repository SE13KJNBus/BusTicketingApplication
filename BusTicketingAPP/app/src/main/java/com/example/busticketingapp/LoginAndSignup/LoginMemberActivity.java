package com.example.busticketingapp.LoginAndSignup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busticketingapp.Payment.PaymentWaiting;
import com.example.busticketingapp.Payment.PaymentWaiting_Cart;
import com.example.busticketingapp.R;


public class LoginMemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_member);
    }

    public void sign(View view){
        //로그인시 회원홈화면으로 넘어가야한다.
        Intent intent = new Intent(getApplicationContext(), PaymentWaiting_Cart.class);
        startActivity(intent);
    }

    public void userLogin(View view){
        Intent intent = new Intent(getApplicationContext(), LoginUserActivity.class);
        startActivity(intent);
    }

    public void signUp(View view){
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }
}
