package com.example.busticketingapp.Payment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
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
    TextView modifySeatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_waiting_cart);
        getId =getIntent().getStringExtra("Id");
        getMember = getIntent().getBooleanExtra("Member",false);
        getName= getIntent().getStringExtra("UserName");
        cartList = getIntent().getStringArrayListExtra("CartList");

        ArrayList<WaitingTicketData> oData = new ArrayList<>();

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
        /*
        String position = (String) oParentView.getTag();

        AlertDialog.Builder oDialog = new AlertDialog.Builder(this,
                android.R.style.Theme_DeviceDefault_Light_Dialog);

        String strMsg = "선택한 아이템의 position 은 "+position+" 입니다.\nTitle 텍스트 :" + oTextTitle.getText();
        oDialog.setMessage(strMsg)
                .setPositiveButton("확인", null)
                .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
                .show();
                departure = getIntent().getStringExtra("Departure");
        destination = getIntent().getStringExtra("Destination");
        date = getIntent().getStringExtra("Date");
        time = getIntent().getStringExtra("Time");
        company = getIntent().getStringExtra("Company");
        */
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
        Intent intent = new Intent(getApplicationContext(), Paying.class);
        startActivity(intent);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_1){
            if(resultCode==RESULT_OK){
                String seat = data.getStringExtra("Seat");
                String movingTime = modifySeatView.getText().toString().split(", ")[0];
                modifySeatView.setText(movingTime+", "+"좌석번호:"+seat);
            }
        }
    }

}
