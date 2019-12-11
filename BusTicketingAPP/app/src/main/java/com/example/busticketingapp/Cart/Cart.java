package com.example.busticketingapp.Cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busticketingapp.Payment.PaymentWaiting_Cart;
import com.example.busticketingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class Cart extends AppCompatActivity {

    ListView listView;
    CartAdapter adapter;
    CartData cartData;
    ArrayList<CartData> cart_itemArrayList = new ArrayList<>();
    String getId;
    String getName;
    DatabaseReference mReference;

    TextView totalMoney;

    int totalNum = 0;
    boolean update = true;
    boolean refresh = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_cart);
        totalNum = 0;
        listView = (ListView) findViewById(R.id.listview1);
        totalMoney = (TextView) findViewById(R.id.totalmoney);

        getId = getIntent().getStringExtra("Id");
        getName = getIntent().getStringExtra("UserName");

        cartData = null;

        mReference = FirebaseDatabase.getInstance().getReference("Member").child(getId).child("Cart");// 변경값을 확인할 child 이름
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (refresh) {
                    for (DataSnapshot messageData : dataSnapshot.getChildren()) {
//                    // child 내에 있는 데이터만큼 반복합니다.
                        String stRev = messageData.toString();
                        String[] arrRev = stRev.split("@");

                        String departure = arrRev[0].split("= ")[1];
                        String destination = arrRev[1];
                        String date = arrRev[2];

                        String[] time = arrRev[3].split("-");
                        String startTime = time[0];
                        String endTime = time[1];

                        String company = arrRev[4].split(",")[0];

                        int revNum = Integer.parseInt(messageData.child("인원수").getValue().toString());
                        totalNum = totalNum + (revNum * 6900);


                        Random rnd = new Random();


                        for (int i = 0; i < revNum; i++) {
                            //임시로 10000으로 측정
                            int num = rnd.nextInt(100);
                            cartData = new CartData(departure, destination, date, startTime, endTime, company, num, false);
                            cart_itemArrayList.add(cartData);
                        }
                    }

                    totalMoney.setText(totalNum + "원");
                    adapter = new CartAdapter(cart_itemArrayList);
                    listView.setAdapter(adapter);
                    refresh = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void cartRemove(View view) {
        this.cart_itemArrayList = adapter.getCart_itemArrayList();
        boolean exist = false;
        if (cart_itemArrayList.isEmpty()) {
            Toast.makeText(this, "삭제할 항목이 없습니다.", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < cart_itemArrayList.size(); ) {
                if (cart_itemArrayList.get(i).checkBoxVal) {

                    CartData reData = cart_itemArrayList.get(i);
                    String Dataname = "";

                    Dataname = reData.startPlace + "@" + reData.arrivePlace + "@" + reData.date + "@" + reData.startTime + "-" + reData.arriveTime + "@" + reData.busCompany;
                    update = true;

                    mReference = FirebaseDatabase.getInstance().getReference("Member").child(getId).child("Cart").child(Dataname);// 변경값을 확인할 child 이름
                    mReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (update) {
                                int num = Integer.parseInt(dataSnapshot.child("인원수").getValue().toString());

                                if (1 < num) {
                                    mReference.child("인원수").setValue(num - 1);
                                } else {
                                    mReference.child("인원수").removeValue();
                                }
                                update = false;
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    cart_itemArrayList.remove(i);
                    totalNum=totalNum-6900;
                    exist = true;
                } else {
                    i++;
                }
                totalMoney.setText(totalNum+ "원");
            }

            if (exist) {
                Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                update = true;
                adapter.setCart_itemArrayList(cart_itemArrayList);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(this, "삭제할 항목을 선택하세요.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void btn_refresh(View view) {
        /*수정필요*/
//        refresh = true;
//        cartData = null;

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
