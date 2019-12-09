package com.example.busticketingapp.Cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busticketingapp.Payment.PaymentWaiting_Cart;
import com.example.busticketingapp.R;

import java.util.ArrayList;

public class cart extends AppCompatActivity {

    ListView listView;
    CartAdapter adapter;
    ArrayList<CartData> cart_itemArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_cart);
        listView = (ListView) findViewById(R.id.listview1);

        CartData cartData = null;
        cartData = new CartData("대전", "동서울", "09 : 00", "12 : 00", "대전고속", 33, true);
        cart_itemArrayList.add(cartData);

        cartData = new CartData("청주", "대전", "12 : 00", "13 : 00", "서울고속", 28, false);
        cart_itemArrayList.add(cartData);
        cartData = new CartData("청주", "대전", "12 : 00", "13 : 00", "서울고속", 28, false);
        cart_itemArrayList.add(cartData);
        cartData = new CartData("청주", "대전", "12 : 00", "13 : 00", "서울고속", 28, false);
        cart_itemArrayList.add(cartData);
        cartData = new CartData("청주", "대전", "12 : 00", "13 : 00", "서울고속", 28, false);
        cart_itemArrayList.add(cartData);cartData = new CartData("청주", "대전", "12 : 00", "13 : 00", "서울고속", 28, false);
        cart_itemArrayList.add(cartData);
        cartData = new CartData("청주", "대전", "12 : 00", "13 : 00", "서울고속", 28, false);
        cart_itemArrayList.add(cartData);
        cartData = new CartData("청주", "대전", "12 : 00", "13 : 00", "서울고속", 28, false);
        cart_itemArrayList.add(cartData);cartData = new CartData("청주", "대전", "12 : 00", "13 : 00", "서울고속", 28, false);
        cart_itemArrayList.add(cartData);
        cartData = new CartData("청주", "대전", "12 : 00", "13 : 00", "서울고속", 28, false);
        cart_itemArrayList.add(cartData);




        cartData = new CartData("창원", "동해", "15 : 00", "18 : 50", "창원고속", 3, true);
        cart_itemArrayList.add(cartData);

        cartData = new CartData("서울", "평택", "15 : 00", "18 : 50", "창원고속", 3, true);
        cart_itemArrayList.add(cartData);

        cartData = new CartData("공주", "대전", "17 : 00", "17 : 50", "공주고속", 15, false);
        cart_itemArrayList.add(cartData);

        cartData = new CartData("대전유성", "부산", "09 : 00", "12 : 50", "부산고속", 41, false);
        cart_itemArrayList.add(cartData);

        adapter = new CartAdapter(cart_itemArrayList);
        listView.setAdapter(adapter);
    }

    public void cartRemove(View view) {
        this.cart_itemArrayList = adapter.getCart_itemArrayList();

        if (cart_itemArrayList.isEmpty()) {
            Toast.makeText(this, "삭제할 항목이 없습니다.", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < cart_itemArrayList.size(); ) {
                if (cart_itemArrayList.get(i).checkBoxVal) {
                    cart_itemArrayList.remove(i);
                } else {
                    i++;
                }
            }
            Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
            adapter.setCart_itemArrayList(cart_itemArrayList);
            listView.setAdapter(adapter);
        }
    }


    public void cartPay(View view) {
        if (cart_itemArrayList.isEmpty()) {
            Toast.makeText(this, "결제할 항목이 없습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, PaymentWaiting_Cart.class);
            startActivity(intent);
        }
    }
}
