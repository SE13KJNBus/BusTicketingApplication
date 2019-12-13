package com.example.busticketingapp.BusList;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.busticketingapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class modifyBusListOne extends AppCompatActivity {

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
    private CustomAdapter_forBusList mAdapter;
    private int count = -1;


    Button setDeparture;
    Button setDestination;

    String arriveTime;
    String arrivePlace;
    String startTime;
    String startPlace;
    String busCompany;

    String date;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2;
    Button setDate;
    Button submit;
    ArrayList<Bus> sortingBusList = new ArrayList<>();
    CustomAdapter_forBusList bAdapter = new CustomAdapter_forBusList(newBusList);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_bus_list_one);
        arriveTime = getIntent().getStringExtra("arriveTime");
        arrivePlace = getIntent().getStringExtra("arrivePlace");
        startTime = getIntent().getStringExtra("startTime");
        startPlace = getIntent().getStringExtra("startPlace");
        busCompany = getIntent().getStringExtra("busCompany");
        seatNum1 = getIntent().getIntExtra("seatNum", 1);

        getId = getIntent().getStringExtra("getId");
        getMember = getIntent().getBooleanExtra("getMember", false);
        getName = getIntent().getStringExtra("getName");

        setDeparture = (Button) findViewById(R.id.goto_selectDeparture);
        setDestination = (Button) findViewById(R.id.goto_selectDestination);

        date = getIntent().getStringExtra("date");
        setDate = (Button) findViewById(R.id.goto_selectDate);
        final Calendar cal = Calendar.getInstance();
        setDeparture.setText(startPlace.split(":")[1]);
        setDestination.setText(arrivePlace.split(":")[1]);
        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(modifyBusListOne.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        String msg = String.format("%d/%d/%d", year, month+1, date);
                        Toast.makeText(modifyBusListOne.this, msg, Toast.LENGTH_SHORT).show();
                        setDate.setText(msg);
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                dialog.getDatePicker().setMinDate(new Date().getTime());
                dialog.show();
            }
        });

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!setDeparture.getText().equals("출발지 선택") && !setDestination.getText().equals("도착지 선택") && !setDate.getText().equals("출발 날짜 선택")){
                    Intent modifyBusList = new Intent(modifyBusListOne.this, modifyBusList.class);
                    modifyBusList.putExtra("getId", getId);
                    modifyBusList.putExtra("getName", getName);
                    modifyBusList.putExtra("getMember", getMember);
                    modifyBusList.putExtra("busCompany", busCompany);
                    modifyBusList.putExtra("arriveTime", arriveTime);
                    modifyBusList.putExtra("startTime", startTime);
                    modifyBusList.putExtra("arrivePlace", arrivePlace);
                    modifyBusList.putExtra("startPlace", startPlace);
                    modifyBusList.putExtra("seatNum", seatNum1);
                    modifyBusList.putExtra("date", date);
                    modifyBusList.putExtra("newDate", setDate.getText().toString());

                    Log.d("..........", setDate.getText().toString());

                    startActivity(modifyBusList);
                    finish();
                }
                else if(setDeparture.getText().equals("출발지 선택")){
                    Toast.makeText(modifyBusListOne.this, "출발지를 선택하세요.",Toast.LENGTH_SHORT).show();
                }else if(setDestination.getText().equals("도착지 선택")){
                    Toast.makeText(modifyBusListOne.this, "도착지를 선택하세요.",Toast.LENGTH_SHORT).show();
                }else if(setDate.getText().equals("출발 날짜 선택")){
                    Toast.makeText(modifyBusListOne.this, "출발 날짜를 선택하세요.",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
