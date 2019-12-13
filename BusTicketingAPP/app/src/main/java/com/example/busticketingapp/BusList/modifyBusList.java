package com.example.busticketingapp.BusList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.busticketingapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class modifyBusList extends AppCompatActivity {

    Intent gotoSelectSeat;
    String getId;
    boolean getMember;
    String getName;

    String departureTerminal;
    String destinationTerminal;
    String departureDateString;
    boolean standardArrive;
    int seatNum1;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("Bus");
    ArrayList<Bus> newBusList = new ArrayList<Bus>();

    TextView departureTerminalName;
    TextView destinationTerminalName;
    TextView departureDate;
    Button sortingStandard;

    private CustomAdapter_forBusList mAdapter;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    private int count = -1;


    String arriveTime;
    String arrivePlace;
    String startTime;
    String startPlace;
    String busCompany;
    String newDate;
    String date;


    String prevarriveTime;
    String prevarrivePlace;
    String prevstartTime;
    String prevstartPlace;
    String prevbusCompany;

    String prevdate;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2;
    Button sorting;
    ArrayList<Bus> sortingBusList = new ArrayList<>();
    CustomAdapter_forBusList bAdapter = new CustomAdapter_forBusList(newBusList);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_bus_list);

        sorting = (Button) findViewById(R.id.Sorting);
        arriveTime = getIntent().getStringExtra("arriveTime");
        prevarriveTime = arriveTime;
        arrivePlace = getIntent().getStringExtra("arrivePlace");
        prevarrivePlace = arrivePlace;
        startTime = getIntent().getStringExtra("startTime");
        prevstartTime = startTime;
        startPlace = getIntent().getStringExtra("startPlace");
        prevstartPlace = startPlace;
        busCompany = getIntent().getStringExtra("busCompany");
        prevbusCompany = busCompany;
        seatNum1 = getIntent().getIntExtra("seatNum", 1);

        date = getIntent().getStringExtra("date");
        newDate = getIntent().getStringExtra("newDate");

        date = date.replace("/", "");
        newDate = newDate.replace("/", "");
        prevdate = date;
        Log.d("!!!!!!!!!1", date);
        TextView startPlaceText = (TextView) findViewById(R.id.DepartureTermianal);
        startPlaceText.setText(startPlace);
        TextView endPlaceText = (TextView) findViewById(R.id.DestinationTermianal);
        endPlaceText.setText(arrivePlace);
        departureDate = findViewById(R.id.Date);
        departureDate.setText(newDate);

        Log.v("번경==", prevstartPlace + " @ " + prevarrivePlace + " @ " + prevdate + " @ " + prevstartTime + " - " + prevarriveTime + " @ " + seatNum1);

        databaseReference = FirebaseDatabase.getInstance().getReference("Bus").child(startPlace).child(arrivePlace).child(date);
        int i = 0;
        databaseReference.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Toast.makeText(getApplicationContext(), "bye: " + dataSnapshot.getKey().toString(), Toast.LENGTH_SHORT).show();
//                if (!dataSnapshot.getKey().toString().equals(startTime.concat("-").concat(arriveTime))){
                int seatNum = 0;
                Log.d("!!!!!!!!!1", dataSnapshot.getKey().toString());
                Bus bus;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.v("snapshot의 키", snapshot.getKey());
                    if (!snapshot.getKey().toString().equals(busCompany)) {
                        Log.v("이거 안들어감", snapshot.getKey());
                        return;
                    } else {
                        seatNum = 0;
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            Log.v("뭐징..", snap.getValue().toString());
                            if (!snap.getValue().equals("false")) {
                                seatNum++;
                            }
                            Log.d("SeatNum ", new Integer(seatNum).toString());
                        }

                        Log.v("이거 들어감", snapshot.getKey());
                    }

                    bus = new Bus();
                    bus.departureTime = dataSnapshot.getKey();
                    bus.busCompany = busCompany;
                    bus.departureDate = newDate;
                    bus.destinationTerminal = arrivePlace.split(":")[1];
                    bus.departureTerminal = startPlace.split(":")[1];
                    bus.remainSeat = seatNum;
                    bus.standardArrive = standardArrive;
                    newBusList.add(bus);
                }


                mAdapter.mList = newBusList;
                mAdapter.notifyDataSetChanged();
            }
//            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        departureTerminalName = findViewById(R.id.DepartureTermianal);
        destinationTerminalName = findViewById(R.id.DestinationTermianal);

        sortingStandard = findViewById(R.id.Sorting);
        sortingStandard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (standardArrive) { // 현재 도착순 정렬 서비스 이용중 -> 출발순
                    ((Button) v).setText("도착순 정렬");
                    for (int i = 0; i < newBusList.size(); i++) {
                        newBusList.get(i).standardArrive = false;
                    }
                    standardArrive = false;
                } else { // 현재 출발순정렬 서비스 이용중 -> 도착순
                    ((Button) v).setText("출발순 정렬");
                    for (int i = 0; i < newBusList.size(); i++) {
                        newBusList.get(i).standardArrive = true;
                    }
                    standardArrive = true;
                }
                Collections.sort(newBusList);
                mAdapter.notifyDataSetChanged();
            }
        });

        departureTerminal = getIntent().getStringExtra("Departure");
        destinationTerminal = getIntent().getStringExtra("Destination");
//        departureDateString = getIntent().getStringExtra("Date").replaceAll("/", "");
        getId = getIntent().getStringExtra("getId");
        getMember = getIntent().getBooleanExtra("getMember", false);
        getName = getIntent().getStringExtra("getName");


        departureTerminalName.setText(startPlace.split(":")[1]);
        destinationTerminalName.setText(arrivePlace.split(":")[1]);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_bus_list);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        mAdapter = new CustomAdapter_forBusList(newBusList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new CustomAdapter_forBusList.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Bus returnBus = newBusList.get(pos);
                Log.v("~~~~~~~~~~~~~~~", "################ " + getId);
                String s = startPlace.concat("@").concat(arrivePlace).concat("@");
                s = s.concat(returnBus.departureDate).concat("@").concat(returnBus.departureTime).concat("@").concat(returnBus.busCompany);
                databaseReference1 = FirebaseDatabase.getInstance().getReference("Member").child(getId).child("Ticket").child(s);
                databaseReference2 = FirebaseDatabase.getInstance().getReference("User").child(getId).child("Ticket").child(s);
                Log.v("~~~~~~~~~~~~~~~", "################ " + databaseReference1.getKey().toString());
//                databaseReference1.removeValue();
                TextView clickedBusTime = (TextView) v.findViewById(R.id.BusTime);
                String time = startTime + "-" + arriveTime;
                gotoSelectSeat = new Intent(modifyBusList.this, modifySeatActivityForModify.class);
                gotoSelectSeat.putExtra("Departure", returnBus.departureTerminal);
                gotoSelectSeat.putExtra("Destination", returnBus.destinationTerminal);
                gotoSelectSeat.putExtra("Date", returnBus.departureDate);
                Log.d("$$$$$$$$$$$$$", returnBus.departureDate);
                Log.d("$$$$$$$$$$$$$", newDate);
                gotoSelectSeat.putExtra("Time", returnBus.departureTime);
                gotoSelectSeat.putExtra("Company", returnBus.busCompany);
                gotoSelectSeat.putExtra("UserSeatNumReal", seatNum1);


                gotoSelectSeat.putExtra("prevDeparture", prevstartPlace);
                gotoSelectSeat.putExtra("prevDestination", prevarrivePlace);
                gotoSelectSeat.putExtra("prevDate", date);
                gotoSelectSeat.putExtra("prevTime", prevstartTime + "-" + prevarriveTime);
                gotoSelectSeat.putExtra("prevCompany", prevbusCompany);

                gotoSelectSeat.putExtra("Id", getId);
                gotoSelectSeat.putExtra("getMember", getMember);
                gotoSelectSeat.putExtra("UserName", getName);
                startActivityForResult(gotoSelectSeat, 1111);
                finish();
            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        databaseReference.child(startPlace).child(arrivePlace).child(date).addValueEventListener(valueEventListener);

    }

    public void arriveSorting(View view) {
        if (sorting.getText().toString().equals("도착순 정렬")) {
            sorting.setText("출발순 정렬");
            for (int i = 0; i < newBusList.size(); i++) {
                Bus bus = newBusList.get(i);
                int value = new Integer(bus.departureTime.split(":")[0].concat(bus.departureTime.split(":")[1]));
                Log.d("Juheon", bus.departureTime.split(":")[0].concat(bus.departureTime.split(":")[1]));
                newBusList.get(i).standardArrive = false;
            }
        } else {
            sorting.setText("도착순 정렬");
            for (int i = 0; i < newBusList.size(); i++) {
                newBusList.get(i).standardArrive = true;
            }
        }
        bAdapter.mList = newBusList;
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            Collections.sort(newBusList);
            mAdapter.notifyDataSetChanged();
            Log.v("Subin", "mAdapter Item Count : " + mAdapter.getItemCount());
        }


        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

}




