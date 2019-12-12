package com.example.busticketingapp.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busticketingapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PaymentWaiting extends AppCompatActivity {

    private ListView m_oListView = null;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("Bus");
    String getId;
    String getName;
    boolean getMember;
    String Departure;
    String Destination;
    String Date;
    String Time;
    String Company;
    String SeatList;
    TextView totalValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_waiting);

        getId = getIntent().getStringExtra("Id");
        getMember = getIntent().getBooleanExtra("Member", false);
        getName = getIntent().getStringExtra("UserName");
        Departure = getIntent().getStringExtra("Departure");
        Destination = getIntent().getStringExtra("Destination");
        Date = getIntent().getStringExtra("DepartureDate");
        Time = getIntent().getStringExtra("DepartureTime");
        SeatList = getIntent().getStringExtra("SeatNum");
        Company = getIntent().getStringExtra("BusCompany");
        totalValue = findViewById(R.id.totalValue);
        ArrayList<WaitingTicketData> oData = new ArrayList<>();

        String[] splitList = SeatList.split(":");
        for (int i = 0; i < splitList.length; i++) {
            WaitingTicketData oItem = new WaitingTicketData();

            oItem.Area = Departure + " -> " + Destination;
            oItem.Date = Date.substring(0, 4) + "-" + Date.substring(4, 6) + "-" + Date.substring(6, 8) + " " + Time;

            String departureTime = Time.split("-")[0];
            String arriveTime = Time.split("-")[1];

            int intTime = Integer.parseInt(departureTime.split(":")[0]) * 60 + Integer.parseInt(departureTime.split(":")[1]);
            int intTime2 = Integer.parseInt(arriveTime.split(":")[0]) * 60 + Integer.parseInt(arriveTime.split(":")[1]);
            String tempTimeInfo = (int) ((intTime2 - intTime) / 60) + "시간 " + ((intTime2 - intTime) % 60) + "분 소요";
            oItem.SeatNum = tempTimeInfo + ", " + "좌석번호:" + splitList[i];
            oData.add(oItem);
        }
        totalValue.setText((splitList.length * 6900) + " 원");


// ListView, Adapter 생성 및 연결 ------------------------
        m_oListView = (ListView) findViewById(R.id.ticket_list);
        WaitingTicketListAdapter oAdapter = new WaitingTicketListAdapter(oData);
        m_oListView.setAdapter(oAdapter);

    }

    public void payment(View view) {
        Intent gotoPayment = new Intent(getApplicationContext(), Paying.class);
        ArrayList<String> TicketList = new ArrayList<>();
        String departure = Departure;
        String destination = Destination;
        String date = Date;
        String time = Time.split("-")[0];
        String arriveTime = Time.split("-")[1];
        int start = Integer.parseInt(time.split(":")[0])*60+Integer.parseInt(time.split(":")[1]);
        int end = Integer.parseInt(arriveTime.split(":")[0])*60+Integer.parseInt(arriveTime.split(":")[1]);
        String movingTime = ((int)(end-start)/60)+":"+(end-start)%60;
        String company = Company;
        String[] splitList = SeatList.split(":");
        for (int i = 0; i < splitList.length; i++) {
            String temp = departure + "@" + destination + "@" + date + "@" + time + "-" + arriveTime + "@" + company + "@" + splitList[i];
            TicketList.add(temp);
        }

        gotoPayment.putExtra("TicketList", TicketList);
        gotoPayment.putExtra("Id", getId);
        gotoPayment.putExtra("Member", getMember);
        gotoPayment.putExtra("UserName", getName);
        gotoPayment.putExtra("Cart",false);
        startActivity(gotoPayment);
    }
}
