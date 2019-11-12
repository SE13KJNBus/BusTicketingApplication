package com.example.busticketingapp.LoginAndSignup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busticketingapp.Cart.cart;
import com.example.busticketingapp.Payment.PaymentWaiting;
import com.example.busticketingapp.R;


public class LoginUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_user);
    }
    public void sign(View view){
        //로그인시 비회원홈화면으로 넘어가야한다.
        Intent intent = new Intent(getApplicationContext(), PaymentWaiting.class);
        startActivity(intent);
    }

    public void signUp(View view) {
        // 주헌 : 장바구니 테스트
        Intent intent = new Intent(getApplicationContext(), cart.class);
        startActivity(intent);
    }
}
