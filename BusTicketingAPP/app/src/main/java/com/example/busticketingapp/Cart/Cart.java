package com.example.busticketingapp.Cart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busticketingapp.Payment.PaymentWaiting_Cart;
import com.example.busticketingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class Cart extends AppCompatActivity {

    ListView listView;
    CartAdapter adapter;
    CartData cartData;
    ArrayList<CartData> cart_itemArrayList = new ArrayList<>();
    String getId;
    String getName;
    DatabaseReference mReference;
    DatabaseReference referenceGetBus;

    TextView totalMoney;
    ArrayList<String> cartArrayList;
    HashMap<String, Integer> getNopayNum;
    HashMap<String, ArrayList> getSeatNum;

    int totalNum = 0;
    boolean update = true;
    boolean refresh = true;
    int count;
    int flag =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_cart);
        totalNum = 0;
        listView = (ListView) findViewById(R.id.listview1);
        totalMoney = (TextView) findViewById(R.id.totalmoney);

        getId = getIntent().getStringExtra("Id");
        getName = getIntent().getStringExtra("UserName");
        cartArrayList = new ArrayList<>();
        getSeatNum = new HashMap<>();
        getNopayNum = new HashMap<>();

        cartData = null;

        adapter = new CartAdapter(cart_itemArrayList,this);
        listView.setAdapter(adapter);

        mReference = FirebaseDatabase.getInstance().getReference("Member").child(getId).child("Cart");// 변경값을 확인할 child 이름
        referenceGetBus =FirebaseDatabase.getInstance().getReference().child("Bus");


        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.v("Cart", "refresh is " + refresh);
                if (refresh) {
                    cart_itemArrayList.clear();
//                    totalNum = 0;
                    getNopayNum.clear();
                    for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                        Log.v("Cart", "messageData.getKey : " + messageData.getKey());
                        Log.v("Cart", "messageData : " + messageData.toString());

                        boolean ch = true;
                        Log.v("Cart", "messageData.getKey : " + messageData.getKey());
                        Log.v("Cart", "messageData : " + messageData.toString());

                        String stRev = messageData.toString();
                        String[] arrRev = stRev.split("@");

                        String departure = arrRev[0].split("= ")[1];
                        String destination = arrRev[1];
                        String date = arrRev[2];

                        String[] time = arrRev[3].split("-");
                        String startTime = time[0];
                        String endTime = time[1];

                        String company = arrRev[4].split(",")[0];


                        long now = System.currentTimeMillis();
                        Date datetime = new Date(now);
                        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyyMMdd@HH:mm");
                        String formatDate = sdfNow.format(datetime);
                        int day = Integer.parseInt(formatDate.split("@")[0]);
                        int h = Integer.parseInt(formatDate.split("@|:")[1]);
                        int m = Integer.parseInt(formatDate.split("@|:")[2]);

                        Log.i("date: ",date +"   day: "+day);
                        if (Integer.parseInt(date) < day) {

                            Log.i("옛날: ",date +"   day: "+day);
                            messageData.child("인원수").getRef().removeValue();
                            ch=false;
                        } else {

                            if (Integer.parseInt(date) == day) {
                                Log.i("같은날: ",date +"   day: "+day);
                                int eH = Integer.parseInt(endTime.split(":")[0]);
                                int eM = Integer.parseInt(endTime.split(":")[1]);

                                if (eH < h || ((eH == h) && eM < m)) {
                                    Log.i("지난시간: ",date +"   day: "+day);
                                    messageData.child("인원수").getRef().removeValue();
                                    ch=false;
                                }






                            }
                        }

                        if(ch){
                            int revNum = Integer.parseInt(messageData.child("인원수").getValue().toString());
//                        totalNum = totalNum + (revNum * 6900);

                            referenceGetBus.child("temp").setValue("temp");
                            referenceGetBus.child("temp").removeValue();
                            getNopayNum.put(messageData.getKey(), revNum);
                            Log.i("a.stRev", stRev);
                            for (int i = 0; i < revNum; i++) {
                                //임시로 10000으로 측정
                                cartData = new CartData(departure, destination, date, startTime, endTime, company, false);
                                cart_itemArrayList.add(cartData);
                            }

                            adapter.notifyDataSetChanged();
                        }

                        //adapter = new CartAdapter(cart_itemArrayList);
                        //listView.setAdapter(adapter);
                        refresh = false;

                    }

                }
            }@Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        referenceGetBus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.v("Cart", "Loading Bus ....");
                getSeatNum.clear();

                ArrayList<String> selectedSeat = new ArrayList<>();
                for (int i=0;i<cart_itemArrayList.size();i++){
                    String startPlace = cart_itemArrayList.get(i).startPlace;
                    String arrivePlace = cart_itemArrayList.get(i).arrivePlace;
                    String date = cart_itemArrayList.get(i).date;
                    String time = cart_itemArrayList.get(i).startTime+"-"+cart_itemArrayList.get(i).arriveTime;
                    String company = cart_itemArrayList.get(i).busCompany;
                    String key = startPlace+"@"+arrivePlace+"@"+date+"@"+time+"@"+company;
                    ArrayList availSeat = new ArrayList();
                    int itcount =0;

                    for(DataSnapshot snapshot : dataSnapshot.child(startPlace).child(arrivePlace).child(date).child(time).child(company).getChildren()){
                        if(snapshot.getValue().toString().equals("true") ){
                            if( !selectedSeat.contains(key+"@"+Integer.parseInt(snapshot.getKey().toString()))){
                                Log.v("Cart", "available seat! "+key+"@"+Integer.parseInt(snapshot.getKey().toString()));
                                availSeat.add(Integer.parseInt(snapshot.getKey().toString()));
                            }
                            itcount++;
                        }
                    }
                    Log.v("Name", "Key : "+key);
                    Log.v("Name", "Snapshot : "+dataSnapshot.getKey()+" // "+dataSnapshot.getValue());
                    Random rnd = new Random();



                    Log.v("Cart", "available size : "+availSeat.size());
                    Log.i("a.key", getNopayNum.get(key)+"");


                    if(itcount < getNopayNum.get(key)){
//                        int randSeatNum =  (int)availSeat.get(rnd.nextInt(availSeat.size()));
                        cart_itemArrayList.get(i).seatNum = 0;
                        selectedSeat.add(key+"@"+0);
                        getSeatNum.put(key,availSeat);

//                        totalNum = totalNum-(6900);
                    }else{
                        int randSeatNum =  (int)availSeat.get(rnd.nextInt(availSeat.size()));
                        cart_itemArrayList.get(i).seatNum = randSeatNum;
                        selectedSeat.add(key+"@"+randSeatNum);
                        getSeatNum.put(key,availSeat);
                    }

                }

//                totalMoney.setText(adapter.total + "원");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void total(int total_M){
        totalMoney.setText(total_M + "원");
    }

    public void cartRemove(View view) {
        this.cart_itemArrayList = adapter.getCart_itemArrayList();
        boolean exist = false;
        String Dataname = "";
        String stPl;
        String enPl;
        String date;
        String stime;

        int num=0;

        count = 0;
        if (cart_itemArrayList.isEmpty()) {
            Toast.makeText(this, "삭제할 항목이 없습니다.", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < cart_itemArrayList.size(); ) {
                if (cart_itemArrayList.get(i).checkBoxVal) {

                    CartData reData = cart_itemArrayList.get(i);
                    Dataname = reData.startPlace + "@" + reData.arrivePlace + "@" + reData.date + "@" + reData.startTime + "-" + reData.arriveTime + "@" + reData.busCompany;

                    update = true;
                    Log.i("Wls", update + "");

                    totalNum = totalNum - 6900;
                    count++;
                    stPl = reData.startPlace;
                    enPl = reData.arrivePlace;
                    date = reData.date;
                    stime = reData.startTime;
                    cart_itemArrayList.remove(i);
                    exist = true;
                    num++;
                } else {
                    stPl = cart_itemArrayList.get(i).startPlace;
                    enPl = cart_itemArrayList.get(i).arrivePlace;
                    date = cart_itemArrayList.get(i).date;
                    stime = cart_itemArrayList.get(i).startTime;
                    i++;
                }

                Log.v("Cart", "ID is " + getId);
                Log.v("Cart", "Dataname is " + Dataname);
                if (i == cart_itemArrayList.size()) {
                    mReference = FirebaseDatabase.getInstance().getReference("Member").child(getId).child("Cart").child(Dataname);// 변경값을 확인할 child 이름

                    mReference.addValueEventListener(valueEventListener);

                } else if (!stPl.equals(cart_itemArrayList.get(i).startPlace) || !enPl.equals(cart_itemArrayList.get(i).arrivePlace)
                        || !date.equals(cart_itemArrayList.get(i).date) || !stime.equals(cart_itemArrayList.get(i).startTime)) {

                    mReference = FirebaseDatabase.getInstance().getReference("Member").child(getId).child("Cart").child(Dataname);// 변경값을 확인할 child 이름
                    //mReference.addValueEventListener(valueEventListener);
                }


            }

            if(num==0){
                Toast.makeText(this, "삭제할 항목을 선택하세요.", Toast.LENGTH_SHORT).show();
            }else if (exist) {
                Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                adapter.total=0;
                update = true;
                adapter.setCart_itemArrayList(cart_itemArrayList);
                listView.setAdapter(adapter);


            }


        }
    }

    public void btn_refresh(View view) {
        /*수정필요*/
        mReference = FirebaseDatabase.getInstance().getReference("Member").child(getId).child("Cart");// 변경값을 확인할 child 이름
        HashMap<String, Integer> paths = new HashMap<>();
        for (int i = 0; i < cart_itemArrayList.size(); i++) {
            CartData cartItem = cart_itemArrayList.get(i);
            String getPath = cartItem.startPlace + "@" + cartItem.arrivePlace + "@" + cartItem.date + "@" + cartItem.startTime + "-" + cartItem.arriveTime + "@" + cartItem.busCompany;
            if (paths.containsKey(getPath)) paths.put(getPath, paths.get(getPath) + 1);
            else paths.put(getPath, 1);
        }
        for (Iterator iterator = paths.keySet().iterator(); iterator.hasNext(); ) {
            String getPath = (String) iterator.next();
            Log.v("Cart", "btn refresh -> HashMap getPath : " + getPath);
            mReference.child(getPath).child("인원수").setValue(paths.get(getPath));
            mReference.child(getPath).child("인원수").setValue(paths.get(getPath) + "");

        }
        refresh = true;

    }

    public void cartPay(View view) {
        if (cart_itemArrayList.isEmpty()) {
            Toast.makeText(this, "결제할 항목이 없습니다.", Toast.LENGTH_SHORT).show();
        } else {

            for (int i = 0; i < cart_itemArrayList.size(); i++) {

                String departure = cart_itemArrayList.get(i).startPlace;
                String destination = cart_itemArrayList.get(i).arrivePlace;
                String date = cart_itemArrayList.get(i).date;
                String time = cart_itemArrayList.get(i).startTime;
                String movingTime = cart_itemArrayList.get(i).movingTime;
                String arriveTime = cart_itemArrayList.get(i).arriveTime;
                String company = cart_itemArrayList.get(i).busCompany;
                int seatNum = cart_itemArrayList.get(i).seatNum;
                if (cart_itemArrayList.get(i).checkBoxVal) {
                    String temp = departure + "@" + destination + "@" + date + "@" + time + "@" + movingTime + "@" + arriveTime + "@" + company + "@" + seatNum;
                    cartArrayList.add(temp);
                }
            }

            Intent intent = new Intent(this, PaymentWaiting_Cart.class);
            intent.putExtra("CartList", cartArrayList);
            intent.putExtra("Id", getId);
            intent.putExtra("Member", true);
            intent.putExtra("UserName", getName);
            startActivity(intent);
        }
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Log.v("Cart", dataSnapshot.getKey());
            if (update) {
                int num = Integer.parseInt(dataSnapshot.child("인원수").getValue().toString());

                if (count < num) {
                    mReference.child("인원수").setValue(num - count);
                } else {
                    mReference = FirebaseDatabase.getInstance().getReference().child("Member").child(getId).child("Cart");
                    mReference.setValue("");
                }
                update = false;
                count = 0;
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
