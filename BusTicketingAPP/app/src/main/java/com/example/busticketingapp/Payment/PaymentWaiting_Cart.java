package com.example.busticketingapp.Payment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busticketingapp.R;

import java.util.ArrayList;

public class PaymentWaiting_Cart extends AppCompatActivity implements View.OnClickListener{

    private ListView m_oListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_waiting_cart);

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
            oItem.onClickListener = this;
            oData.add(oItem);

            if (nDatCnt >= strDate.length) nDatCnt = 0;
        }

// ListView, Adapter 생성 및 연결 ------------------------
        m_oListView = (ListView)findViewById(R.id.ticket_list);
        WaitingTicketCartListAdapter oAdapter = new WaitingTicketCartListAdapter(oData);
        m_oListView.setAdapter(oAdapter);

    }

    @Override
    public void onClick(View v){
        View oParentView = (View)v.getParent(); // parents의 View를 가져온다.
        TextView oTextTitle = (TextView) oParentView.findViewById(R.id.area);
        String position = (String) oParentView.getTag();

        AlertDialog.Builder oDialog = new AlertDialog.Builder(this,
                android.R.style.Theme_DeviceDefault_Light_Dialog);

        String strMsg = "선택한 아이템의 position 은 "+position+" 입니다.\nTitle 텍스트 :" + oTextTitle.getText();
        oDialog.setMessage(strMsg)
                .setPositiveButton("확인", null)
                .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
                .show();

    }

    public void payment(View view){
        Intent intent = new Intent(getApplicationContext(), Paying.class);
        startActivity(intent);
    }

}
