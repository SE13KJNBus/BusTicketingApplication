package com.example.busticketingapp.BusList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.busticketingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class TerminalActivity extends AppCompatActivity {
    Intent gotoSelectSeat;

    String departureTerminal;
    String destinationTerminal;
    String departureDateString;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("Bus");
    ArrayList<Bus> busArrayList = new ArrayList<Bus>();

    TextView departureTerminalName;
    TextView destinationTerminalName;
    TextView departureDate;

    private CustomAdapter_forBusList mAdapter;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    private int count = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buslist_select_bus);
        departureTerminalName = findViewById(R.id.DepartureTermianal);
        destinationTerminalName = findViewById(R.id.DestinationTermianal);
        departureDate = findViewById(R.id.Date);

        departureTerminal = getIntent().getStringExtra("Departure");
        destinationTerminal = getIntent().getStringExtra("Destination");
        departureDateString = getIntent().getStringExtra("Date").replaceAll("/", "");
        Log.v("Subin", "Date check : " + departureDateString);

        departureTerminalName.setText(departureTerminal.split(":")[1]);
        destinationTerminalName.setText(destinationTerminal.split(":")[1]);
        departureDate.setText(departureDateString);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_bus_list);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        mAdapter = new CustomAdapter_forBusList(busArrayList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new CustomAdapter_forBusList.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Bus returnBus = busArrayList.get(pos);

                gotoSelectSeat = new Intent(TerminalActivity.this, SelectSeatActivity_General.class);
                gotoSelectSeat.putExtra("Departure", returnBus.getDepartureTerminal());
                gotoSelectSeat.putExtra("Destination", returnBus.getDestinationTerminal());
                gotoSelectSeat.putExtra("Date", returnBus.getDepartureDate());
                gotoSelectSeat.putExtra("Time", returnBus.getDepartureTime());
                gotoSelectSeat.putExtra("Company", returnBus.getBusCompany());
                gotoSelectSeat.putExtra("SeatNum", returnBus.getRemainSeat());

                startActivity(gotoSelectSeat);

            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        myRef.child(departureTerminal).child(destinationTerminal).child(departureDateString).addValueEventListener(valueEventListener);

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            busArrayList.clear();
            Log.v("Subin", "dataSnapshot getKey : " + dataSnapshot.getKey());
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Log.v("Subin", snapshot.getKey());

                String busCompany = snapshot.getKey();
                for (DataSnapshot snapshotchild : snapshot.getChildren()) {
                    String time = snapshotchild.getKey();
                    int seatNum = 0;
                    for (DataSnapshot snapshotSeatNum : snapshotchild.getChildren()) {
                        if (snapshotSeatNum.getValue().equals(false)) seatNum++;
                    }
                    Bus bus = new Bus();
                    bus.setDepartureTerminal(departureTerminal);
                    bus.setDestinationTerminal(destinationTerminal);
                    bus.setDepartureDate(departureDateString);
                    bus.setBusCompany(busCompany);
                    bus.setDepartureTime(time);
                    bus.setRemainSeat(seatNum);

                    busArrayList.add(bus);
                    Log.v("Subin", "busArrayList Size : " + busArrayList.size());
                }
            }
            Log.v("Subin", "busArrayList Size : " + busArrayList.size());

            mAdapter.notifyDataSetChanged();
            Log.v("Subin", "mAdapter Item Count : " + mAdapter.getItemCount());
        }


        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


}

