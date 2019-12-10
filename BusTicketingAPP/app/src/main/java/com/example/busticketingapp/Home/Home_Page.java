package com.example.busticketingapp.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.busticketingapp.BusList.MainActivity;
import com.example.busticketingapp.Cart.cart;
import com.example.busticketingapp.Payment.PaymentWaiting;
import com.example.busticketingapp.R;

import org.w3c.dom.Text;

public class Home_Page extends AppCompatActivity {

    // 마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    // 첫 번째 뒤로가기 버튼을 누를때 표시
    private Toast toast;
    private TextView userName;
    private Button btn_cart;
    private Button btn_mailBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        String getId = getIntent().getStringExtra("Id");
        boolean getMember = getIntent().getBooleanExtra("Member", false);

        btn_cart = (Button)findViewById(R.id.cart);
        btn_mailBox = (Button)findViewById(R.id.mailBox);

//        Log.i("Id",getId);

        userName = (TextView) findViewById(R.id.userName);

        if (getMember) {
            btn_cart.setVisibility(View.VISIBLE);
            btn_mailBox.setVisibility(View.VISIBLE);
            userName.setText("곽주헌");
        }else {
            btn_cart.setVisibility(View.INVISIBLE);
            btn_mailBox.setVisibility(View.INVISIBLE);
            userName.setText("비회원");
        }

    }

    public void DoReserve(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void identifyList(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void goCart(View view) {
        Intent intent = new Intent(getApplicationContext(), cart.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // 기존 뒤로가기 버튼의 기능을 막기위해 주석처리 또는 삭제
        // super.onBackPressed();

        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지났으면 Toast Show
        // 2000 milliseconds = 2 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        // 현재 표시된 Toast 취소
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
//            finish();
//            toast.cancel();
            finishAffinity();
        }
    }
}
