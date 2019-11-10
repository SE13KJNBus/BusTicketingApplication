package com.example.busticketingapp.BusList;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.busticketingapp.R;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Intent gotoDepartureActivity;
    Intent gotoDestinationActivity;

    Button setDeparture;
    Button setDestination;
    Button setDate;
    Button submit;
    CalendarView calendarView;
    public static final int REQUEST_1 = 1111;
    public static final int REQUEST_2 = 2222;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buslist_select_bus_info);
        Log.v("Subin","main");
        setDeparture = findViewById(R.id.goto_selectDeparture);
        setDeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Subin","select departure button");
                gotoDepartureActivity = new Intent(MainActivity.this, SelectDepartureActivity.class);
                startActivityForResult(gotoDepartureActivity, REQUEST_1);
            }
        });

        setDestination = findViewById(R.id.goto_selectDestination);
        setDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Subin","select destination button");
                gotoDestinationActivity = new Intent(MainActivity.this, SelectDestinationActivity.class);
                startActivityForResult(gotoDestinationActivity, REQUEST_2);
            }
        });

        setDate = findViewById(R.id.goto_selectDate);

        final Calendar cal = Calendar.getInstance();

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        String msg = String.format("%d/%d/%d", year, month+1, date);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        setDate.setText(msg);
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                dialog.getDatePicker().setMinDate(new Date().getTime());
                dialog.show();
            }
        });
        /*
        String date = new SimpleDateFormat("yyyy/MM/dd").format(calendarView.getDate());
        setDate.setText(date);
        */
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!setDeparture.getText().equals("출발지 선택") && !setDestination.getText().equals("도착지 선택") && !setDate.getText().equals("출발 날짜 선택")){
                    Intent gotoTerminal = new Intent(MainActivity.this, TerminalActivity.class);
                    gotoTerminal.putExtra("Departure",setDeparture.getText());
                    gotoTerminal.putExtra("Destination",setDestination.getText());
                    gotoTerminal.putExtra("Date",setDate.getText());
                    startActivity(gotoTerminal);
                }
                else if(setDeparture.getText().equals("출발지 선택")){
                    Toast.makeText(MainActivity.this, "출발지를 선택하세요.",Toast.LENGTH_SHORT).show();
                }else if(setDestination.getText().equals("도착지 선택")){
                    Toast.makeText(MainActivity.this, "도착지를 선택하세요.",Toast.LENGTH_SHORT).show();
                }else if(setDate.getText().equals("출발 날짜 선택")){
                    Toast.makeText(MainActivity.this, "출발 날짜를 선택하세요.",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_1){
            if(resultCode==RESULT_OK){
                String departureName = data.getStringExtra("Departure");
                setDeparture.setText(departureName==null?"출발지 선택":departureName);
            }
        }else if(requestCode == REQUEST_2){
            if(resultCode==RESULT_OK){
                String destinationName = data.getStringExtra("Destination");
                setDestination.setText(destinationName==null?"도착지 선택":destinationName);
            }
        }
    }



}

