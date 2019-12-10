package com.example.busticketingapp.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.busticketingapp.R;

import java.util.ArrayList;

public class PaymentWaiting extends AppCompatActivity {

    private ListView m_oListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_waiting);

        int peopleNum = getIntent().getIntExtra("peopleNum",0);
        Log.i("결제 인원수: ",peopleNum+"");

        String[] seatList = getIntent().getStringArrayExtra("seatNum");

        String seatNum="";

        for(int i=0;i < seatList.length;i++){
            seatNum = seatNum+seatList[i]+", ";
        }

        Log.i("좌석",seatNum);

        String[] strArea = {"대전복합 -> 서울", "남서울 -> 부산", "공주 -> 청주", "유성 -> 공주"};
        String[] strDate = {"2017-01-03 09:30 ~ 10:30", "2017-01-03 18:30 ~ 19:35", "2017-01-04 19:30 ~ 20:30", "2017-02-03 19:45 ~ 20:45"};
        String[] strSeat = {"1시간 소요    좌석번호:30","1시간 5분 소요    좌석번호:16","1시간 소요    좌석번호:22","1시간 소요    좌석번호:1"};
        int nDatCnt=0;
        ArrayList<WaitingTicketData> oData = new ArrayList<>();

        for (int i=0; i<4; ++i)
        {
            WaitingTicketData oItem = new WaitingTicketData();
            oItem.Area = strArea[nDatCnt];
            oItem.Date = strDate[nDatCnt];
            oItem.SeatNum = strSeat[nDatCnt++];

            oData.add(oItem);

            if (nDatCnt >= strDate.length) nDatCnt = 0;
        }

// ListView, Adapter 생성 및 연결 ------------------------
        m_oListView = (ListView)findViewById(R.id.ticket_list);
        WaitingTicketListAdapter oAdapter = new WaitingTicketListAdapter(oData);
        m_oListView.setAdapter(oAdapter);

    }

    public void payment(View view){
        Intent intent = new Intent(getApplicationContext(), Paying.class);
        startActivity(intent);
    }
}
