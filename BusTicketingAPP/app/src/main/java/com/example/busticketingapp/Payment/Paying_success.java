package com.example.busticketingapp.Payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.busticketingapp.Home.Home_Page;
import com.example.busticketingapp.R;

public class Paying_success extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_paying_success);

        Handler handler = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                Intent intent = new Intent(getApplicationContext(),Home_Page.class);
                Toast.makeText(getApplicationContext(),"결제성공",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(0,3000);
    }
}
