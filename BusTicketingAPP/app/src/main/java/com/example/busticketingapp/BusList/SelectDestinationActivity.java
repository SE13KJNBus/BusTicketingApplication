package com.example.busticketingapp.BusList;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.busticketingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class SelectDestinationActivity extends AppCompatActivity {

    private ArrayList<String> mArrayList;
    private CustomAdapter_forTerminal mAdapter;
    private int count = -1;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String DepartureTerminal;
    DatabaseReference myRef = database.getReference().child("Bus");

    ArrayList<String> entireLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buslist_select_departure);

        DepartureTerminal =getIntent().getStringExtra("Departure");
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_location_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        Log.v("Subin",DepartureTerminal);

        mArrayList = new ArrayList<>();
        entireLocation = new ArrayList<>();

        myRef.child(DepartureTerminal).addValueEventListener(valueEventListener);

        mAdapter = new CustomAdapter_forTerminal(mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new CustomAdapter_forTerminal.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                String returnString = mArrayList.get(pos);
                Intent backToBus = new Intent(SelectDestinationActivity.this, MainActivity.class);
                backToBus.putExtra("Destination",returnString);
                setResult(RESULT_OK, backToBus);
                finish();
            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        final Button entire = (Button) findViewById(R.id.entire_location);
        entire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                for (int i = 0; i < entireLocation.size(); i++) {
                    mArrayList.add(entireLocation.get(i));
                }
                mAdapter.notifyDataSetChanged();
                Log.v("Subin", mAdapter.getItemCount() + "");
            }
        });
        final Button seoul = (Button) findViewById(R.id.seoul_location);
        seoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                for (int i = 0; i < entireLocation.size(); i++) {
                    String[] splitString = entireLocation.get(i).split(":");

                    if(splitString[0].equals("서울")) mArrayList.add(entireLocation.get(i));
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        Button incheon = (Button) findViewById(R.id.incheon_location);
        incheon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                for (int i = 0; i < entireLocation.size(); i++) {
                    String[] splitString = entireLocation.get(i).split(":");
                    if(splitString[0].equals("인천")) mArrayList.add(entireLocation.get(i));
                }
                mAdapter.notifyDataSetChanged();
                Log.v("Subin", mAdapter.getItemCount() + "");
            }
        });
        Button gangwon = (Button) findViewById(R.id.gangwon_location);
        gangwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                for (int i = 0; i < entireLocation.size(); i++) {
                    String[] splitString = entireLocation.get(i).split(":");
                    if(splitString[0].equals("강원")) mArrayList.add(entireLocation.get(i));
                }
                mAdapter.notifyDataSetChanged();
                Log.v("Subin", mAdapter.getItemCount() + "");
            }
        });
        Button daejeon = (Button) findViewById(R.id.daejeon_location);
        daejeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                for (int i = 0; i < entireLocation.size(); i++) {
                    String[] splitString = entireLocation.get(i).split(":");
                    if(splitString[0].equals("대전")) mArrayList.add(entireLocation.get(i));
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        Button chungnam = (Button) findViewById(R.id.chungnam_location);
        chungnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                for (int i = 0; i < entireLocation.size(); i++) {
                    String[] splitString = entireLocation.get(i).split(":");
                    if(splitString[0].equals("충남")) mArrayList.add(entireLocation.get(i));
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        Button chungbuk = (Button) findViewById(R.id.chungbuk_location);
        chungbuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();for (int i = 0; i < entireLocation.size(); i++) {
                    String[] splitString = entireLocation.get(i).split(":");
                    if(splitString[0].equals("충북")) mArrayList.add(entireLocation.get(i));
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        Button gwangju = (Button) findViewById(R.id.gwangju_location);
        gwangju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                for (int i = 0; i < entireLocation.size(); i++) {
                    String[] splitString = entireLocation.get(i).split(":");
                    if(splitString[0].equals("광주")) mArrayList.add(entireLocation.get(i));
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        Button jeonbuk = (Button) findViewById(R.id.jeonbuk_location);
        jeonbuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                for (int i = 0; i < entireLocation.size(); i++) {
                    String[] splitString = entireLocation.get(i).split(":");
                    if(splitString[0].equals("전북")) mArrayList.add(entireLocation.get(i));
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        Button busan = (Button) findViewById(R.id.busan_location);
        busan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                for (int i = 0; i < entireLocation.size(); i++) {
                    String[] splitString = entireLocation.get(i).split(":");
                    if(splitString[0].equals("부산")) mArrayList.add(entireLocation.get(i));
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        Button daegu = (Button) findViewById(R.id.daegu_location);
        daegu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                for (int i = 0; i < entireLocation.size(); i++) {
                    String[] splitString = entireLocation.get(i).split(":");
                    if(splitString[0].equals("대구")) mArrayList.add(entireLocation.get(i));
                }
                mAdapter.notifyDataSetChanged();
            }
        });


    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            entireLocation.clear();
            mArrayList.clear();
            Log.v("Subin","onData");
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Log.v("Subin", "input");
                entireLocation.add(snapshot.getKey());
                mArrayList.add(snapshot.getKey());
            }
            mAdapter.notifyDataSetChanged();
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

}
