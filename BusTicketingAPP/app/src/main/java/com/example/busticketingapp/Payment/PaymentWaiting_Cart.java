package com.example.busticketingapp.Payment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busticketingapp.BusList.modifySeatActivity;
import com.example.busticketingapp.R;

import java.util.ArrayList;

public class PaymentWaiting_Cart extends AppCompatActivity implements View.OnClickListener{

    private ListView m_oListView = null;
    public static final int REQUEST_1 = 1111;

    String getId;
    String getName;
    boolean getMember;
    ArrayList<String> cartList;
    ArrayList<String> ticketList;
    TextView modifySeatView;
    TextView totalValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_waiting_cart);
        getId =getIntent().getStringExtra("Id");
        getMember = getIntent().getBooleanExtra("Member",false);
        getName= getIntent().getStringExtra("UserName");
        Log.v("Name", "In Paying_success : "+getName);

        cartList = getIntent().getStringArrayListExtra("CartList");
        totalValue = findViewById(R.id.totalValue);
        ArrayList<WaitingTicketData> oData = new ArrayList<>();
        ticketList = new ArrayList<>();

        for (int i=0; i<cartList.size(); ++i)
        {
            WaitingTicketData oItem = new WaitingTicketData();
            String[] info = cartList.get(i).split("@");

            oItem.Area = info[0]+" -> "+info[1];
            oItem.Date = info[2]+" "+info[3]+"-"+info[5];
            oItem.SeatNum = info[4]+"소요, "+"좌석번호:"+info[7];
            oItem.Company = info[6];
            oItem.onClickListener = this;
            oData.add(oItem);
        }
        totalValue.setText((cartList.size()*6900)+" 원");

// ListView, Adapter 생성 및 연결 ------------------------
        m_oListView = (ListView)findViewById(R.id.ticket_list);
        WaitingTicketCartListAdapter oAdapter = new WaitingTicketCartListAdapter(oData);
        m_oListView.setAdapter(oAdapter);

    }

    @Override
    public void onClick(View v){
        View oParentView = (View)v.getParent(); // parents의 View를 가져온다.
        TextView oTextArea = (TextView) oParentView.findViewById(R.id.area);
        TextView oTextDate = (TextView) oParentView.findViewById(R.id.date);
        TextView oTextSeat = (TextView) oParentView.findViewById(R.id.seatNum);
        TextView oTextCompany = (TextView) oParentView.findViewById(R.id.company);

        modifySeatView = oTextSeat;

        String departure = oTextArea.getText().toString().split(" -> ")[0];
        String destination =oTextArea.getText().toString().split(" -> ")[1];
        String date = oTextDate.getText().toString().split(" ")[0];
        String time = oTextDate.getText().toString().split(" ")[1];
        String company = oTextCompany.getText().toString();
        Intent modifySeat = new Intent(PaymentWaiting_Cart.this, modifySeatActivity.class);
        modifySeat.putExtra("Departure", departure);
        modifySeat.putExtra("Destination", destination);
        modifySeat.putExtra("Date", date);
        modifySeat.putExtra("Time", time);
        modifySeat.putExtra("Company", company);
        startActivityForResult(modifySeat, REQUEST_1);
    }

    public void payment(View view){

        for (int i=0; i<cartList.size(); ++i)
        {
            View oParentView = (View)view.getParent(); // parents의 View를 가져온다.
            TextView oTextArea = (TextView) oParentView.findViewById(R.id.area);
            TextView oTextDate = (TextView) oParentView.findViewById(R.id.date);
            TextView oTextSeat = (TextView) oParentView.findViewById(R.id.seatNum);
            TextView oTextCompany = (TextView) oParentView.findViewById(R.id.company);

            String departure = oTextArea.getText().toString().split(" -> ")[0];
            String destination =oTextArea.getText().toString().split(" -> ")[1];
            String date = oTextDate.getText().toString().split(" ")[0];
            String time = oTextDate.getText().toString().split(" ")[1];
            String company = oTextCompany.getText().toString();
            String seatnum = oTextSeat.getText().toString().split(":")[2];

            String temp = departure + "@" + destination + "@" + date + "@" + time + "@" + company + "@" + seatnum;
            ticketList.add(temp);
        }

        Intent gotoPayment = new Intent(getApplicationContext(), Paying.class);
        gotoPayment.putExtra("TicketList", cartList);
        gotoPayment.putExtra("Id", getId);
        gotoPayment.putExtra("Member", getMember);
        gotoPayment.putExtra("UserName", getName);
        startActivity(gotoPayment);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_1){
            if(resultCode==RESULT_OK){
                String seat = data.getStringExtra("Seat");
                String movingTime = modifySeatView.getText().toString().split(", ")[0];
                modifySeatView.setText(movingTime+", "+"좌석번호:"+seat.split(":")[0]);
            }
        }
    }

}
