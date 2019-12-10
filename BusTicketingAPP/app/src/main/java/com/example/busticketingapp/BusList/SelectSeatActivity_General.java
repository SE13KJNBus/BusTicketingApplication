package com.example.busticketingapp.BusList;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.busticketingapp.Payment.PaymentWaiting;
import com.example.busticketingapp.R;

import java.util.ArrayList;

public class SelectSeatActivity_General extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    Button seat1, seat2, seat3, seat4, seat5, seat6, seat7, seat8, seat9, seat10;
    Button seat11, seat12, seat13, seat14, seat15, seat16, seat17, seat18, seat19, seat20;
    Button seat21, seat22, seat23, seat24, seat25, seat26, seat27, seat28, seat29, seat30;
    Button seat31, seat32, seat33, seat34, seat35, seat36, seat37, seat38, seat39, seat40;
    Button seat41, seat42, seat43, seat44, seat45;
    TextView printSeat;

    ArrayList<Button> seatList;
    ArrayList<String> selectedSeat;
    Button submit;
    Button peopleNum;
    TextView totalMoney;
    int userSeatNum=0;
    TextView remainNum;

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

        int remainSeatNumberString =getIntent().getIntExtra("SeatNum",0);
        remainNum.setText(remainSeatNumberString==0?remainNum.getText():remainSeatNumberString+" 석");

        peopleNum = findViewById(R.id.seat_number);
        peopleNum.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                show();
            }
        });

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
    }

    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(userSeatNum==0 || userSeatNum <= selectedSeat.size()) {
                Toast.makeText(SelectSeatActivity_General.this, "인원 수를 확인하세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            Log.v("Subin",userSeatNum+"  "+selectedSeat.size());
            String seatString = (String) ((Button)v).getText();
            if(!selectedSeat.contains((String)seatString)) {
                selectedSeat.add(seatString);
            }else{
                selectedSeat.remove(seatString);
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
            Toast.makeText(SelectSeatActivity_General.this, "Submit", Toast.LENGTH_SHORT).show();
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
}
