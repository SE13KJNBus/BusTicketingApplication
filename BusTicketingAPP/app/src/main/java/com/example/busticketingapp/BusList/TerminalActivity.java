package com.example.busticketingapp.BusList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.busticketingapp.R;

import java.util.ArrayList;


public class TerminalActivity extends AppCompatActivity {
    Intent gotoSelectSeat;

    TextView departureTerminalName;
    TextView destinationTerminalName;
    TextView departureDate;

    private ArrayList<Bus> mArrayList;
    private CustomAdapter_forBusList mAdapter;
    private int count = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buslist_select_bus);
        departureTerminalName = findViewById(R.id.DepartureTermianal);
        destinationTerminalName = findViewById(R.id.DestinationTermianal);
        departureDate = findViewById(R.id.Date);

        String departureTerminal = getIntent().getStringExtra("Departure");
        String destinationTerminal = getIntent().getStringExtra("Destination");
        String departureDateString = getIntent().getStringExtra("Date");

        departureTerminalName.setText(departureTerminal);
        destinationTerminalName.setText(destinationTerminal);
        departureDate.setText(departureDateString);


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_bus_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        mArrayList = new ArrayList<>();
        mAdapter = new CustomAdapter_forBusList(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new CustomAdapter_forBusList.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Bus returnBus = mArrayList.get(pos);

                gotoSelectSeat = new Intent(TerminalActivity.this, SelectSeatActivity_General.class);
                gotoSelectSeat.putExtra("Departure",returnBus.getDepartureTerminal());
                gotoSelectSeat.putExtra("Destination",returnBus.getDestinationTerminal());
                gotoSelectSeat.putExtra("Date",returnBus.getDepartureDate());
                gotoSelectSeat.putExtra("Time",returnBus.getDepartureDate());
                gotoSelectSeat.putExtra("Company",returnBus.getDepartureDate());
                gotoSelectSeat.putExtra("SeatNum",returnBus.getRemainSeat());

                startActivity(gotoSelectSeat);
            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        /*
        * API로 버스 리스트 받아와 mArrayList<Bus>에 삽입
        *
        String departureTerminal = getIntent().getStringExtra("Departure");
        String destinationTerminal = getIntent().getStringExtra("Destination");
        String departureDateString = getIntent().getStringExtra("Date");
        에 맞는 것만 가져오기
        *
        *
        *  mAdapter.notifyDataSetChanged();

        * */

        Bus tempBus = new Bus();
        tempBus.setDepartureTerminal(departureTerminal);
        tempBus.setDestinationTerminal(destinationTerminal);
        tempBus.setDepartureDate(departureDateString);
        tempBus.setBusCompany("금호고속");
        tempBus.setDepartureTime("22:30");
        tempBus.setRemainSeat(25);

        Bus tempBus2 = new Bus();
        tempBus2.setDepartureTerminal(departureTerminal);
        tempBus2.setDestinationTerminal(destinationTerminal);
        tempBus2.setDepartureDate(departureDateString);
        tempBus2.setBusCompany("전북고속");
        tempBus2.setDepartureTime("23:30");
        tempBus2.setRemainSeat(20);
        mArrayList.add(tempBus);
        mArrayList.add(tempBus2);
        mAdapter.notifyDataSetChanged();

    }


}

