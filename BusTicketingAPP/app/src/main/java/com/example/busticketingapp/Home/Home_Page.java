package com.example.busticketingapp.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.busticketingapp.BusList.MainActivity;
import com.example.busticketingapp.Cart.Cart;
import com.example.busticketingapp.Friend.FriendList;
import com.example.busticketingapp.R;

public class Home_Page extends AppCompatActivity {

    // 마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    // 첫 번째 뒤로가기 버튼을 누를때 표시
    private Toast toast;
    private TextView userName;
    private Button btn_cart;
    private Button btn_mailBox;
    private Button btn_friend;

    String getId ;
    boolean getMember;
    String getName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        getId = getIntent().getStringExtra("Id");
        getMember = getIntent().getBooleanExtra("Member", false);
        getName = getIntent().getStringExtra("UserName");

        btn_cart = (Button)findViewById(R.id.cart);
        btn_mailBox = (Button)findViewById(R.id.mailBox);
        btn_friend = (Button)findViewById(R.id.btn_friend);


//        Log.i("Id",getId);

        userName = (TextView) findViewById(R.id.userName);

        if (getMember) {
            btn_cart.setVisibility(View.VISIBLE);
            btn_mailBox.setVisibility(View.VISIBLE);
            btn_friend.setVisibility(View.VISIBLE);

//            userName.setText("곽주헌");
            userName.setText(getName);
        }else {
            btn_cart.setVisibility(View.INVISIBLE);
            btn_mailBox.setVisibility(View.INVISIBLE);
            btn_friend.setVisibility(View.INVISIBLE);
            userName.setText(getName);
        }

    }

    public void DoReserve(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("Id",getId);
        intent.putExtra("Member",(boolean)getMember);
        intent.putExtra("UserName",getName);
        startActivity(intent);
    }

    public void identifyList(View view) {
        Intent intent = new Intent(getApplicationContext(), TicketList.class);
        intent.putExtra("Id",getId);
        intent.putExtra("Member",(boolean)getMember);
        intent.putExtra("UserName",getName);
        startActivity(intent);
    }

    public void goCart(View view) {
        Intent intent = new Intent(getApplicationContext(), Cart.class);
        intent.putExtra("Id",getId);
//        intent.putExtra("Member",(boolean)getMember);
        intent.putExtra("UserName",getName);
        startActivity(intent);
    }


    public void friendList(View view){
        Intent intent = new Intent(getApplicationContext(), FriendList.class);
        intent.putExtra("Id",getId);
        intent.putExtra("UserName",getName);
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
