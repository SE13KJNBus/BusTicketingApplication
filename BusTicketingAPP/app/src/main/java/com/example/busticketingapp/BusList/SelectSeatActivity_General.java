package com.example.busticketingapp.BusList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;


import com.example.busticketingapp.Home.Home_Page;
import com.example.busticketingapp.LoginAndSignup.LoginMemberActivity;
import com.example.busticketingapp.Payment.PaymentWaiting;
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
import java.util.List;

public class SelectSeatActivity_General extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    Button seat1, seat2, seat3, seat4, seat5, seat6, seat7, seat8, seat9, seat10;
    Button seat11, seat12, seat13, seat14, seat15, seat16, seat17, seat18, seat19, seat20;
    Button seat21, seat22, seat23, seat24, seat25, seat26, seat27, seat28, seat29, seat30;
    Button seat31, seat32, seat33, seat34, seat35, seat36, seat37, seat38, seat39, seat40;
    Button seat41, seat42, seat43, seat44, seat45;

    TextView printSeat;
    String getId;
    boolean getMember;
    String getName;

    String departure;
    String destination;
    String date;
    String time;
    String company;
    int remainSeatNumberString;

    ArrayList<String> availableList = new ArrayList<String>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    ArrayList<Button> seatList;
    ArrayList<String> selectedSeat;
    Button submit;
    Button peopleNum;
    Button btnReservation;
    TextView totalMoney;
    int userSeatNum=0;
    TextView remainNum;

    int checkNum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.buslist_select_seat_genaral);

        seatList= new ArrayList<Button>();
        selectedSeat = new ArrayList<String>();
        printSeat = findViewById(R.id.selected_seat);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(btnSubmit);
        totalMoney = findViewById(R.id.total_money);
        remainNum = findViewById(R.id.remain_num);
        peopleNum = findViewById(R.id.seat_number);
        peopleNum.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                show();
            }
        });
        btnReservation = findViewById(R.id.reservation);
        btnReservation.setOnClickListener(btnReserv);


        getId = getIntent().getStringExtra("Id");
        getMember = getIntent().getBooleanExtra("Member", false);
        getName = getIntent().getStringExtra("UserName");
        if(!getMember){
            btnReservation.setVisibility(View.INVISIBLE);
        }
        departure = getIntent().getStringExtra("Departure");
        destination = getIntent().getStringExtra("Destination");
        date = getIntent().getStringExtra("Date");
        time = getIntent().getStringExtra("Time");
        company = getIntent().getStringExtra("Company");
        remainSeatNumberString =getIntent().getIntExtra("SeatNum",0);
        remainNum.setText(remainSeatNumberString==0?remainNum.getText():remainSeatNumberString+" 석");

        seat1 = (Button) findViewById(R.id.seat1);
        seatList.add(seat1);
        seat2 = (Button) findViewById(R.id.seat2);
        seatList.add(seat2);
        seat3 = (Button) findViewById(R.id.seat3);
        seatList.add(seat3);
        seat4 = (Button) findViewById(R.id.seat4);
        seatList.add(seat4);
        seat5 = (Button) findViewById(R.id.seat5);
        seatList.add(seat5);
        seat6 = (Button) findViewById(R.id.seat6);
        seatList.add(seat6);
        seat7 = (Button) findViewById(R.id.seat7);
        seatList.add(seat7);
        seat8 = (Button) findViewById(R.id.seat8);
        seatList.add(seat8);
        seat9 = (Button) findViewById(R.id.seat9);
        seatList.add(seat9);
        seat10 = (Button) findViewById(R.id.seat10);
        seatList.add(seat10);
        seat11 = (Button) findViewById(R.id.seat11);
        seatList.add(seat11);
        seat12 = (Button) findViewById(R.id.seat12);
        seatList.add(seat12);
        seat13 = (Button) findViewById(R.id.seat13);
        seatList.add(seat13);
        seat14 = (Button) findViewById(R.id.seat14);
        seatList.add(seat14);
        seat15 = (Button) findViewById(R.id.seat15);
        seatList.add(seat15);
        seat16 = (Button) findViewById(R.id.seat16);
        seatList.add(seat16);
        seat17 = (Button) findViewById(R.id.seat17);
        seatList.add(seat17);
        seat18 = (Button) findViewById(R.id.seat18);
        seatList.add(seat18);
        seat19 = (Button) findViewById(R.id.seat19);
        seatList.add(seat19);
        seat20 = (Button) findViewById(R.id.seat20);
        seatList.add(seat20);
        seat21 = (Button) findViewById(R.id.seat21);
        seatList.add(seat21);
        seat22 = (Button) findViewById(R.id.seat22);
        seatList.add(seat22);
        seat23 = (Button) findViewById(R.id.seat23);
        seatList.add(seat23);
        seat24 = (Button) findViewById(R.id.seat24);
        seatList.add(seat24);
        seat25 = (Button) findViewById(R.id.seat25);
        seatList.add(seat25);
        seat26 = (Button) findViewById(R.id.seat26);
        seatList.add(seat26);
        seat27 = (Button) findViewById(R.id.seat27);
        seatList.add(seat27);
        seat28 = (Button) findViewById(R.id.seat28);
        seatList.add(seat28);
        seat29 = (Button) findViewById(R.id.seat29);
        seatList.add(seat29);
        seat30 = (Button) findViewById(R.id.seat30);
        seatList.add(seat30);
        seat31 = (Button) findViewById(R.id.seat31);
        seatList.add(seat31);
        seat32 = (Button) findViewById(R.id.seat32);
        seatList.add(seat32);
        seat33 = (Button) findViewById(R.id.seat33);
        seatList.add(seat33);
        seat34 = (Button) findViewById(R.id.seat34);
        seatList.add(seat34);
        seat35 = (Button) findViewById(R.id.seat35);
        seatList.add(seat35);
        seat36 = (Button) findViewById(R.id.seat36);
        seatList.add(seat36);
        seat37 = (Button) findViewById(R.id.seat37);
        seatList.add(seat37);
        seat38 = (Button) findViewById(R.id.seat38);
        seatList.add(seat38);
        seat39 = (Button) findViewById(R.id.seat39);
        seatList.add(seat39);
        seat40 = (Button) findViewById(R.id.seat40);
        seatList.add(seat40);
        seat41 = (Button) findViewById(R.id.seat41);
        seatList.add(seat41);
        seat42 = (Button) findViewById(R.id.seat42);
        seatList.add(seat42);
        seat43 = (Button) findViewById(R.id.seat43);
        seatList.add(seat43);
        seat44 = (Button) findViewById(R.id.seat44);
        seatList.add(seat44);
        seat45 = (Button) findViewById(R.id.seat45);
        seatList.add(seat45);
        for (int i = 0; i < 45; i++) {
            ((Button) seatList.get(i)).setOnClickListener(btnListener);
        }
        Log.v("SubinTest", "출발지 : "+ departure);
        Log.v("SubinTest", "도착지 : "+ destination);
        Log.v("SubinTest", "날짜 : "+ date);
        Log.v("SubinTest", "회사 : "+ company);
        Log.v("SubinTest", "시간 : "+ time);


        myRef.child("Bus").child(departure).child(destination).child(date).child(time).child(company).addValueEventListener(valueEventListener);
    }

    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int year = Integer.parseInt(date.substring(0,4));
            int month = Integer.parseInt(date.substring(4,6));
            int day = Integer.parseInt(date.substring(6,8));
            int hour = Integer.parseInt(time.split("-")[0].split(":")[0]);
            int min = Integer.parseInt(time.split("-")[0].split(":")[1]);
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd@kk:mm");
            String formatDate = sdfNow.format(date);
            int nowYear = Integer.parseInt(formatDate.split("-|@")[0])%24;
            int nowMonth =Integer.parseInt(formatDate.split("-|@")[1]);
            int nowDay = Integer.parseInt(formatDate.split("-|@")[2]);
            int nowHour = Integer.parseInt(formatDate.split("-|@|:")[3]);
            int nowMin = Integer.parseInt(formatDate.split("-|@|:")[4]);

            Log.v("Test", "현재 : "+ nowYear+"-"+nowMonth+"-"+nowDay+"//"+nowHour+":"+nowMin+":");
            Log.v("Test", "버스 : "+ year+"-"+month+"-"+day+"//"+hour+":"+min+":");

            if(nowYear > year){
                Toast.makeText(SelectSeatActivity_General.this, "출발시간이 지났습니다.",Toast.LENGTH_SHORT).show();
                return;
            }else if(nowYear==year){
                if(nowMonth > month){
                    Toast.makeText(SelectSeatActivity_General.this, "출발시간이 지났습니다.",Toast.LENGTH_SHORT).show();
                    return;
                }else if(nowMonth==month){
                    if(nowDay > day){
                        Toast.makeText(SelectSeatActivity_General.this, "출발시간이 지났습니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }else if(nowDay==day){
                        int totalTimeBus = hour*60+min;
                        int totalTimeNow = nowHour*60+nowMin;
                        if(totalTimeBus < totalTimeNow){
                            Toast.makeText(SelectSeatActivity_General.this, "출발시간이 지났습니다.",Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }
                }
            }

            Log.v("Subin",userSeatNum+"  "+selectedSeat.size());
            String seatString = (String) ((Button)v).getText();
            Button seatButton = (seatList.get(Integer.parseInt(seatString)-1));
            if(!availableList.contains(seatString)) return;

            if(!selectedSeat.contains((String)seatString)) {
                if(userSeatNum==0 || userSeatNum <= selectedSeat.size()) {
                    Toast.makeText(SelectSeatActivity_General.this, "인원 수를 확인하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                selectedSeat.add(seatString);
                seatButton.setBackground(ContextCompat.getDrawable(SelectSeatActivity_General.this, R.drawable.bus_seat_choose));
                seatButton.invalidate();

            }else{
                selectedSeat.remove(seatString);
                seatButton.setBackground(ContextCompat.getDrawable(SelectSeatActivity_General.this, R.drawable.bus_seat_ok));
                seatButton.invalidate();
            }


            String printString = "";
            for (int i = 0; i < selectedSeat.size(); i++) {
                if(i == selectedSeat.size()-1) printString = printString + selectedSeat.get(i);
                else printString = printString + selectedSeat.get(i)+", ";
            }
            printString = printString + "번 좌석";
            printSeat.setText(printString);

            totalMoney.setText(6900 * selectedSeat.size()+"");

        }
    };
    View.OnClickListener btnSubmit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(userSeatNum!=selectedSeat.size()) {
                Toast.makeText(SelectSeatActivity_General.this, "인원 수를 확인하세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            String getSeatList = "";
            for(int i=0;i<selectedSeat.size();i++){
                getSeatList += selectedSeat.get(i)+":";
            }
            Intent gotoPayment = new Intent(SelectSeatActivity_General.this, PaymentWaiting.class);
            gotoPayment.putExtra("Departure",departure);
            gotoPayment.putExtra("Destination",destination);
            gotoPayment.putExtra("SeatNum",getSeatList);
            gotoPayment.putExtra("BusCompany", company);
            gotoPayment.putExtra("DepartureDate",date);
            gotoPayment.putExtra("DepartureTime", time);
            gotoPayment.putExtra("Id", getId);
            gotoPayment.putExtra("Member", getMember);
            gotoPayment.putExtra("UserName",getName);
            startActivity(gotoPayment);
        }
    };
    View.OnClickListener btnReserv = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(SelectSeatActivity_General.this,"예약시 선택한 좌석번호는 무효처리됩니다.",Toast.LENGTH_LONG).show();
            checkNum = userSeatNum;
            String cartName = departure+"@"+destination+"@"+date+"@"+time+"@"+company;
            final DatabaseReference mReference = FirebaseDatabase.getInstance().getReference("Member").child(getId).child("Cart").child(cartName).child("인원수");// 변경값을 확인할 child 이름
            mReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(checkNum!=0){
                        if(dataSnapshot.exists()){
                            String stnum = dataSnapshot.getValue().toString();

                            int num = Integer.parseInt(stnum);
                            if(num+checkNum < 45){
                                //미결제좌석수랑 비교하는 걸로 바꿔도 됨
                                mReference.setValue(num+checkNum);
                            }else{
                                mReference.setValue(checkNum);
                            }

                        }else{
                            mReference.setValue(checkNum);
                        }
                        checkNum = 0;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            Log.v("SubinTest2", "Success Reservation");
            Intent gotoHome = new Intent(SelectSeatActivity_General.this, Home_Page.class);

            gotoHome.putExtra("Id", getId);
            gotoHome.putExtra("Member", getMember);
            gotoHome.putExtra("UserName", getName);
            startActivity(gotoHome);
        }
    };

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Toast.makeText(SelectSeatActivity_General.this, "Submit", Toast.LENGTH_SHORT).show();
    }
    public void show(){
        final Dialog dialog = new Dialog(SelectSeatActivity_General.this);
        dialog.setTitle("인원 수 선택");
        dialog.setContentView(R.layout.buslist_dialog_numpick);
        Button submit_peopleNum = (Button) dialog.findViewById(R.id.submit_peopleNum);
        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        final NumberPicker np = (NumberPicker) dialog.findViewById(R.id.numberPicker);
        np.setMaxValue(45);
        np.setMinValue(1);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);
        submit_peopleNum.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(selectedSeat.size()+np.getValue() >= 45) {
                    Toast.makeText(SelectSeatActivity_General.this, "다시 선택하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                userSeatNum = np.getValue();
                Log.v("Subin", userSeatNum+"");
                peopleNum.setText(String.valueOf(np.getValue()));
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Log.v("SubinTest","snapshot " + dataSnapshot.getKey());

            availableList.clear();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                Log.v("SubinTest","Seat Value : "+snapshot.getValue());

                if(snapshot.getValue().toString().equals("false")){
                    Button seat = (Button) seatList.get(Integer.parseInt(snapshot.getKey())-1);
                    //seat.setForeground(Drawable.createFromPath("@drawable/bus_seat_no"));
                    seat.setBackground(ContextCompat.getDrawable(SelectSeatActivity_General.this, R.drawable.bus_seat_no));
                    seat.invalidate();
                    Log.v("SubinTest","Seat Num : "+(snapshot.getKey()));
                }else{
                    availableList.add(snapshot.getKey());
                    Log.v("SubinTest","Value : "+snapshot.getValue().toString().equals("false"));
                }

            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    };


}
