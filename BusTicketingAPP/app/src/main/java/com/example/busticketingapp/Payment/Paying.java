package com.example.busticketingapp.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busticketingapp.Home.Home_Page;
import com.example.busticketingapp.R;

public class Paying extends AppCompatActivity {

    String getId;
    String getName;
    boolean getMember;
    String Departure;
    String Destination;
    String Date;
    String Time;
    String Company;
    String SeatList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_paying);

        getId = getIntent().getStringExtra("Id");
        getMember = getIntent().getBooleanExtra("Member",false);
        getName = getIntent().getStringExtra("UserName");
        Departure = getIntent().getStringExtra("Departure");
        Destination = getIntent().getStringExtra("Destination");
        Date = getIntent().getStringExtra("DepartureDate");
        Time = getIntent().getStringExtra("DepartureTime");
        SeatList = getIntent().getStringExtra("SeatNum");
        Company = getIntent().getStringExtra("BusCompany");
        changeView(0);

        // Spinner
        {
            Spinner PhoneCompanySpinner = (Spinner) findViewById(R.id.sel_PhoneCompany);
            ArrayAdapter PhoneCompanyAdapter = ArrayAdapter.createFromResource(this,
                    R.array.PhoneCompany, android.R.layout.simple_spinner_item);
            PhoneCompanyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            PhoneCompanySpinner.setAdapter(PhoneCompanyAdapter);

            Spinner CardCompanySpinner = (Spinner) findViewById(R.id.sel_cardCompany);
            ArrayAdapter CardCompanyAdapter = ArrayAdapter.createFromResource(this,
                    R.array.cardCompany, android.R.layout.simple_spinner_item);
            CardCompanyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            CardCompanySpinner.setAdapter(CardCompanyAdapter);
        }

        Button card = (Button)findViewById(R.id.btn_creditCardPayment);
        card.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                changeView(1);
            }
        });

        Button phone = (Button)findViewById(R.id.btn_phonePayment);
        phone.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                changeView(0);
            }
        });

    }

    private void changeView(int idx){

        RelativeLayout phone = (RelativeLayout)findViewById(R.id.paying_phone);
        RelativeLayout card = (RelativeLayout)findViewById(R.id.paying_creditCard);

        switch (idx){
            case 0:
                phone.setVisibility(View.VISIBLE);
                card.setVisibility(View.INVISIBLE);
                break;
            case 1:
                card.setVisibility(View.VISIBLE);
                phone.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void cancel(View view){
        Intent intent = new Intent(getApplicationContext(),Home_Page.class);
        startActivity(intent);
    }

    public void payment(View view){
        Intent intent = new Intent(getApplicationContext(),Paying_success.class);
        startActivity(intent);
    }

}
