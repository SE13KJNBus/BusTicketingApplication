package com.example.busticketingapp.Payment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.busticketingapp.Home.Home_Page;
import com.example.busticketingapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Paying_success extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    String getId;
    String getName;
    String getKey;
    boolean getCart;
    boolean getCard;
    boolean getMember;
    ArrayList<String> getList;
    HashMap<String, Integer> moneyMap;
    HashMap<String,Integer> cartMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_paying_success);
        cartMap = new HashMap<>();
        getId = getIntent().getStringExtra("Id");
        getMember = getIntent().getBooleanExtra("Member",false);
        getName = getIntent().getStringExtra("UserName");
        getList = getIntent().getStringArrayListExtra("TicketList");
        getKey = getIntent().getStringExtra("Key");
        getCard = getIntent().getBooleanExtra("Card",false);
        getCart = getIntent().getBooleanExtra("Cart",false);

        moneyMap = new HashMap<>();
        myRef.addValueEventListener(valueEventListener);



        // pay();


        Handler handler = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                Intent intent = new Intent(getApplicationContext(),Home_Page.class);
                Toast.makeText(getApplicationContext(),"결제성공",Toast.LENGTH_SHORT).show();
                intent.putExtra("Id", getId);
                intent.putExtra("Member",getMember);
                intent.putExtra("UserName",getName);
                startActivity(intent);
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(0,3000);

    }

    ValueEventListener valueEventListener =new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            String creditCardCompany;
            String creditCardNum;
            String availableTime;

            String Company;
            String phoneNum;
            String pw;
            moneyMap = new HashMap<>();
            cartMap = new HashMap<>();
            for (DataSnapshot snapshot : dataSnapshot.child("Member").child(getId).child("Cart").getChildren()){
                Log.v("CC","MEMBER : "+snapshot.getKey()+"  //  "+snapshot.getValue());
                for(int i=0;i<getList.size();i++){
                    String[] info = getList.get(i).split("@");
                    String keyTicket = info[0]+"@"+info[1]+"@"+info[2]+"@"+info[3]+"@"+info[4];
                    Log.v("Test",getList.get(i).toString()+"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

                    Log.v("Test",snapshot.getKey().toString()+"))))))))))))))))))))))");
                    if(keyTicket.toString().equals(snapshot.getKey().toString())){
                        cartMap.put(snapshot.getKey().toString(),Integer.parseInt(snapshot.child("인원수").getValue().toString()));
                    }
                }
            }
            for (DataSnapshot snapshot : dataSnapshot.child("Payment").child("CreditCard").getChildren()){

                creditCardCompany = snapshot.getKey().toString();
                Log.v("Payment","company : "+snapshot.getKey().toString());
                for(DataSnapshot shot : snapshot.getChildren()){
                    creditCardNum = shot.getKey().toString();
                    Log.v("Payment","cardNum : "+shot.getKey().toString());

                    for (DataSnapshot sss : shot.getChildren()){
                        availableTime = sss.getKey().toString();
                        pw = "";
                        String m="";
                        Log.v("Payment","avaliable : "+sss.getKey().toString());

                        for (DataSnapshot ss : sss.getChildren()){
                            Log.v("Payment","pw : "+ss.getKey().toString());
                            pw = ss.getKey().toString();
                            m = ss.getValue().toString();
                        }

                        Log.v("Payment",creditCardCompany+"/"+creditCardNum+"/"+availableTime+"/"+pw +"   "+moneyMap.size());
                        moneyMap.put(creditCardCompany+"/"+creditCardNum+"/"+availableTime+"/"+pw,Integer.parseInt(m));
                    }
                }
            }
            for (DataSnapshot snapshot : dataSnapshot.child("Payment").child("PhonePay").getChildren()){
                Company = snapshot.getKey().toString();
                for(DataSnapshot shot : snapshot.getChildren()){
                    phoneNum = shot.getKey().toString();
                    Random random = new Random();
                    int tempR = random.nextInt(10000);
                    String m = shot.getValue().toString();
                    Log.v("Payment",Company+"/"+phoneNum+"   "+moneyMap.size());
                    moneyMap.put(Company+"/"+phoneNum, Integer.parseInt(m));

                }
            }
            pay();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    public void pay(){

        if(getList.size()!=0){
            String[] paymentInfo = getKey.split("/");
            String keyPayment;
            int remain=0;
            if(getCard) {
                keyPayment = paymentInfo[0]+"/"+paymentInfo[1]+"/"+paymentInfo[2]+"/"+paymentInfo[3];

                Log.v("Payment","momeymap ---------"+moneyMap.size()+"---------"+moneyMap.get(keyPayment.toString()));
                remain = moneyMap.get(keyPayment)-(getList.size()*6900);
                if(remain < 0){
                    Intent intent = new Intent(getApplicationContext(),Home_Page.class);
                    Toast.makeText(getApplicationContext(),"잔액부족",Toast.LENGTH_SHORT).show();
                    intent.putExtra("Id", getId);
                    intent.putExtra("Member",getMember);
                    intent.putExtra("UserName",getName);
                    startActivity(intent);
                    finish();
                }
                else {
                    myRef.child("Payment").child("CreditCard").child(paymentInfo[0]).child(paymentInfo[1]).child(paymentInfo[2]).child(paymentInfo[3]).setValue(remain);
                }
            }else{
                keyPayment = paymentInfo[0]+"/"+paymentInfo[1];
                remain = moneyMap.get(keyPayment)-(getList.size()*6900);
                if(remain < 0){
                    Intent intent = new Intent(getApplicationContext(),Home_Page.class);
                    Toast.makeText(getApplicationContext(),"잔액부족",Toast.LENGTH_SHORT).show();
                    intent.putExtra("Id", getId);
                    intent.putExtra("Member",getMember);
                    intent.putExtra("UserName",getName);
                    startActivity(intent);
                    finish();
                }
                else {
                    myRef.child("Payment").child("PhonePay").child(paymentInfo[0]).child(paymentInfo[1]).setValue(remain);
                }
            }

            if(remain<0) return;
            Log.v("Test",getList.size()+"");

            for(int i=0;i<getList.size();i++){

                String[] info = getList.get(i).split("@");

                String keyTicket=info[0]+"@"+info[1]+"@"+info[2]+"@"+info[3]+"@"+info[4];

                if(getMember){
                    Log.v("CC", getList.get(i).toString());
                    Log.v("CC","cart : "+keyTicket);
                    Log.v("CC",info[5]+"");

                    myRef.child("Bus").child(info[0]).child(info[1]).child(info[2]).child(info[3]).child(info[4]).child(info[5]).setValue("false");
                    myRef.child("Member").child(getId).child("Ticket").child(keyTicket).child(info[5]).setValue(true);
                    if(getCart){
                        Log.v("Test", keyTicket);
                        if(cartMap.get(keyTicket)!=1){
                            int num = cartMap.get(keyTicket);
                            myRef.child("Member").child(getId).child("Cart").child(keyTicket).child("인원수").setValue((num-1)+"");
                            cartMap.put(keyTicket,num-1);
                        }
                        else myRef.child("Member").child(getId).child("Cart").setValue("");
                    }

                } else{
                    myRef.child("Bus").child(info[0]).child(info[1]).child(info[2]).child(info[3]).child(info[4]).child(info[5]).setValue("false");
                    myRef.child("User").child(getId).child("Ticket").child(keyTicket).child(info[5]).setValue(true);

                }

            }
            getList.clear();
        }
    }
}
