package com.example.busticketingapp.BusList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.busticketingapp.Payment.PaymentWaiting_Cart;
import com.example.busticketingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class modifySeatActivityForModify extends AppCompatActivity {

    Button seat1, seat2, seat3, seat4, seat5, seat6, seat7, seat8, seat9, seat10;
    Button seat11, seat12, seat13, seat14, seat15, seat16, seat17, seat18, seat19, seat20;
    Button seat21, seat22, seat23, seat24, seat25, seat26, seat27, seat28, seat29, seat30;
    Button seat31, seat32, seat33, seat34, seat35, seat36, seat37, seat38, seat39, seat40;
    Button seat41, seat42, seat43, seat44, seat45;

    TextView printSeat;

    String departure;
    String destination;
    String date;
    String time;
    String company;
    int remainSeatNumberString;


    String prevarriveTime;
    String prevarrivePlace;
    String prevstartTime;
    String prevstartPlace;
    String prevbusCompany;
    String prevdate;


    ArrayList<String> availableList = new ArrayList<String>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    ArrayList<Button> seatList;
    ArrayList<String> selectedSeat;
    Button submit;
    //Button peopleNum;
    //Button btnReservation;
    TextView totalMoney;
    int userSeatNum;
    int userSeatNumReal;
    TextView remainNum;

    String getId;
    boolean getMember;
    String getName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_seat_for_modify);

        printSeat = findViewById(R.id.selected_seat);
        seatList = new ArrayList<Button>();
        selectedSeat = new ArrayList<String>();
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(btnSubmit);
        totalMoney = findViewById(R.id.total_money);
        remainNum = findViewById(R.id.remain_num);


        prevstartPlace = getIntent().getStringExtra("prevDeparture");
        prevarrivePlace = getIntent().getStringExtra("prevDestination");
        prevdate = getIntent().getStringExtra("prevDate");
        String aaa = getIntent().getStringExtra("prevTime");
        if (aaa != null) {
            prevstartTime = getIntent().getStringExtra("prevTime").split("-")[0];
            prevarriveTime = getIntent().getStringExtra("prevTime").split("-")[1];
        }
        prevbusCompany = getIntent().getStringExtra("prevCompany");

        Log.i("변경 : ", aaa + "");


        departure = getIntent().getStringExtra("Departure");
        destination = getIntent().getStringExtra("Destination");
        date = getIntent().getStringExtra("Date");
        date = date.replace("/", "");
        time = getIntent().getStringExtra("Time");
        company = getIntent().getStringExtra("Company");
        userSeatNumReal = getIntent().getIntExtra("UserSeatNumReal", 0);

        Log.i("변경: ", time + "");
        Log.v("번경==", prevstartPlace + " @ " + prevarrivePlace + " @ " + prevdate + " @ " + prevstartTime + " - " + prevarriveTime + " @ " + userSeatNumReal);

        getId = getIntent().getStringExtra("Id");
        getMember = getIntent().getBooleanExtra("getMember", false);
        getName = getIntent().getStringExtra("getName");

        Log.i("회원: ", getMember + "");

        seat1 = (Button) findViewById(R.id.mSeat1);
        seatList.add(seat1);
        seat2 = (Button) findViewById(R.id.mSeat2);
        seatList.add(seat2);
        seat3 = (Button) findViewById(R.id.mSeat3);
        seatList.add(seat3);
        seat4 = (Button) findViewById(R.id.mSeat4);
        seatList.add(seat4);
        seat5 = (Button) findViewById(R.id.mSeat5);
        seatList.add(seat5);
        seat6 = (Button) findViewById(R.id.mSeat6);
        seatList.add(seat6);
        seat7 = (Button) findViewById(R.id.mSeat7);
        seatList.add(seat7);
        seat8 = (Button) findViewById(R.id.mSeat8);
        seatList.add(seat8);
        seat9 = (Button) findViewById(R.id.mSeat9);
        seatList.add(seat9);
        seat10 = (Button) findViewById(R.id.mSeat10);
        seatList.add(seat10);
        seat11 = (Button) findViewById(R.id.mSeat11);
        seatList.add(seat11);
        seat12 = (Button) findViewById(R.id.mSeat12);
        seatList.add(seat12);
        seat13 = (Button) findViewById(R.id.mSeat13);
        seatList.add(seat13);
        seat14 = (Button) findViewById(R.id.mSeat14);
        seatList.add(seat14);
        seat15 = (Button) findViewById(R.id.mSeat15);
        seatList.add(seat15);
        seat16 = (Button) findViewById(R.id.mSeat16);
        seatList.add(seat16);
        seat17 = (Button) findViewById(R.id.mSeat17);
        seatList.add(seat17);
        seat18 = (Button) findViewById(R.id.mSeat18);
        seatList.add(seat18);
        seat19 = (Button) findViewById(R.id.mSeat19);
        seatList.add(seat19);
        seat20 = (Button) findViewById(R.id.mSeat20);
        seatList.add(seat20);
        seat21 = (Button) findViewById(R.id.mSeat21);
        seatList.add(seat21);
        seat22 = (Button) findViewById(R.id.mSeat22);
        seatList.add(seat22);
        seat23 = (Button) findViewById(R.id.mSeat23);
        seatList.add(seat23);
        seat24 = (Button) findViewById(R.id.mSeat24);
        seatList.add(seat24);
        seat25 = (Button) findViewById(R.id.mSeat25);
        seatList.add(seat25);
        seat26 = (Button) findViewById(R.id.mSeat26);
        seatList.add(seat26);
        seat27 = (Button) findViewById(R.id.mSeat27);
        seatList.add(seat27);
        seat28 = (Button) findViewById(R.id.mSeat28);
        seatList.add(seat28);
        seat29 = (Button) findViewById(R.id.mSeat29);
        seatList.add(seat29);
        seat30 = (Button) findViewById(R.id.mSeat30);
        seatList.add(seat30);
        seat31 = (Button) findViewById(R.id.mSeat31);
        seatList.add(seat31);
        seat32 = (Button) findViewById(R.id.mSeat32);
        seatList.add(seat32);
        seat33 = (Button) findViewById(R.id.mSeat33);
        seatList.add(seat33);
        seat34 = (Button) findViewById(R.id.mSeat34);
        seatList.add(seat34);
        seat35 = (Button) findViewById(R.id.mSeat35);
        seatList.add(seat35);
        seat36 = (Button) findViewById(R.id.mSeat36);
        seatList.add(seat36);
        seat37 = (Button) findViewById(R.id.mSeat37);
        seatList.add(seat37);
        seat38 = (Button) findViewById(R.id.mSeat38);
        seatList.add(seat38);
        seat39 = (Button) findViewById(R.id.mSeat39);
        seatList.add(seat39);
        seat40 = (Button) findViewById(R.id.mSeat40);
        seatList.add(seat40);
        seat41 = (Button) findViewById(R.id.mSeat41);
        seatList.add(seat41);
        seat42 = (Button) findViewById(R.id.mSeat42);
        seatList.add(seat42);
        seat43 = (Button) findViewById(R.id.mSeat43);
        seatList.add(seat43);
        seat44 = (Button) findViewById(R.id.mSeat44);
        seatList.add(seat44);
        seat45 = (Button) findViewById(R.id.mSeat45);
        seatList.add(seat45);
        for (int i = 0; i < 45; i++) {
            ((Button) seatList.get(i)).setOnClickListener(btnListener);
        }
        Log.v("SubinTest", "출발지 : " + departure);
        Log.v("SubinTest", "도착지 : " + destination);
        Log.v("SubinTest", "날짜 : " + date);
        Log.v("SubinTest", "회사 : " + company);
        Log.v("SubinTest", "시간 : " + time);


        myRef.child("Bus").child(prevstartPlace).child(prevarrivePlace).child(date).child(time).child(company).addValueEventListener(valueEventListener);
    }

    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Log.v("Subin", userSeatNum + "  " + selectedSeat.size());
            String seatString = (String) ((Button) v).getText();
            Button seatButton = (seatList.get(Integer.parseInt(seatString) - 1));
            Log.v("Subin", "Available Size : " + availableList.size());
            if (!availableList.contains(seatString)) {
                Log.v("Subin", "Nothing To Select");
                return;
            }

            if (!selectedSeat.contains((String) seatString)) {
                if (userSeatNum == 0 || userSeatNum <= selectedSeat.size()) {
                    if (userSeatNumReal == 0) {
                        Toast.makeText(modifySeatActivityForModify.this, "인원 수를 확인하세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                selectedSeat.add(seatString);
                seatButton.setBackground(ContextCompat.getDrawable(modifySeatActivityForModify.this, R.drawable.bus_seat_choose));
                seatButton.invalidate();

            } else {
                selectedSeat.remove(seatString);
                seatButton.setBackground(ContextCompat.getDrawable(modifySeatActivityForModify.this, R.drawable.bus_seat_ok));
                seatButton.invalidate();

            }


            String printString = "";
            for (int i = 0; i < selectedSeat.size(); i++) {
                if (i == selectedSeat.size() - 1) printString = printString + selectedSeat.get(i);
                else printString = printString + selectedSeat.get(i) + ", ";
            }
            printString = printString + "번 좌석";
            printSeat.setText(printString);

            totalMoney.setText(6900 * selectedSeat.size() + "");

        }
    };
    View.OnClickListener btnSubmit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (userSeatNum == selectedSeat.size()) {
                Toast.makeText(modifySeatActivityForModify.this, "좌석을 확인하세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            String getSeatList = "";
            for (int i = 0; i < selectedSeat.size(); i++) {
                getSeatList += selectedSeat.get(i) + ":";
            }
            Log.d("번경==", new Integer(userSeatNumReal).toString());
            if (userSeatNumReal != 0) {
                String s;
                if (getSeatList.length() != 0) {
                    getSeatList = getSeatList.substring(0, getSeatList.length() - 1);
                }
                Log.d("이거일까", getSeatList);
                s = prevstartPlace.concat("@").concat(prevarrivePlace).concat("@").concat(prevdate).concat("@").concat(prevstartTime + "-" + prevarriveTime).concat("@").concat(prevbusCompany);

                if (getId.charAt(0) != '0') {
                    DatabaseReference data1 = database.getReference().child("Member").child(getId).child("Ticket").child(s).child(new Integer(userSeatNumReal).toString());
                    data1.removeValue();
                    s = prevstartPlace.concat("@").concat(prevarrivePlace).concat("@").concat(date).concat("@").concat(time).concat("@").concat(company);
                    data1 = database.getReference().child("Member").child(getId).child("Ticket").child(s);
                    data1.child(getSeatList).setValue(true);
                } else {
                    DatabaseReference data1 = database.getReference().child("User").child(getId).child("Ticket").child(s).child(new Integer(userSeatNumReal).toString());
                    Log.d("#########33", getId);
                    data1.removeValue();
                    s = prevstartPlace.concat("@").concat(prevarrivePlace).concat("@").concat(date).concat("@").concat(time).concat("@").concat(company);
                    data1 = database.getReference().child("User").child(getId).child("Ticket").child(s);
                    data1.child(getSeatList).setValue(true);
                }
                DatabaseReference data2 = database.getReference().child("Bus").child(prevstartPlace).child(prevarrivePlace).child(date).child(time).child(company);
                DatabaseReference data4 = database.getReference().child("Bus").child(prevstartPlace).child(prevarrivePlace).child(prevdate).child(prevstartTime + "-" + prevarriveTime).child(company);
                data2.child(getSeatList).setValue("false");
                data4.child(new Integer(userSeatNumReal).toString()).setValue("true");

                finish();
            }
            Intent gotoPayment = new Intent(modifySeatActivityForModify.this, PaymentWaiting_Cart.class);
            gotoPayment.putExtra("Seat", getSeatList);
            setResult(RESULT_OK, gotoPayment);
            finish();
        }
    };


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            //Log.v("SubinTest","snapshot " + dataSnapshot.getKey());
            availableList.clear();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Log.v("번경==", "Seat Value : " + snapshot.getValue());

                if (snapshot.getValue().toString().equals("false")) {
                    Button seat = (Button) seatList.get(Integer.parseInt(snapshot.getKey()) - 1);
                    //seat.setForeground(Drawable.createFromPath("@drawable/bus_seat_no"));
                    seat.setBackground(ContextCompat.getDrawable(modifySeatActivityForModify.this, R.drawable.bus_seat_no));
                    seat.invalidate();
                    Log.v("SubinTest", "Seat Num : " + (snapshot.getKey()));
                } else {
                    availableList.add(snapshot.getKey());
                    Log.v("SubinTest", "Value : " + snapshot.getValue().toString().equals("false"));
                }
            }
            remainNum.setText(availableList.size() + "석");
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    };


}