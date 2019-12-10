package com.example.busticketingapp.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.busticketingapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TicketList extends AppCompatActivity implements View.OnClickListener {

    private ListView m_oListView = null;
    private String getId;
    private boolean getMember;
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_ticket_data);

        getId = getIntent().getStringExtra("Id");
        getMember = getIntent().getBooleanExtra("Member", false);


//        myRef.child(getId).child("Ticket").child("20191210").setValue("12:00-16:00");

        String[] strArea = {"대전복합 -> 서울", "남서울 -> 부산", "공주 -> 청주", "유성 -> 공주"};
        String[] strDate = {"2017-01-03 09:30 ~ 10:30", "2017-01-03 18:30 ~ 19:35", "2017-01-04 19:30 ~ 20:30", "2017-02-03 19:45 ~ 20:45"};
        String[] strSeat = {"1시간 소요    좌석번호:30", "1시간 5분 소요    좌석번호:16", "1시간 소요    좌석번호:22", "1시간 소요    좌석번호:1"};

//        ArrayList<TicketData> oData = new ArrayList<>();

//        for (int i = 0; i < 4; ++i) {
//            TicketData oItem = new TicketData();
//            oItem.Area = strArea[nDatCnt];
//            oItem.Date = strDate[nDatCnt];
//            oItem.SeatNum = strSeat[nDatCnt++];
//            oItem.onClickListener = this;
//            oData.add(oItem);
//
//            if (nDatCnt >= strDate.length) nDatCnt = 0;
//        }

        initDatabase();

// ListView, Adapter 생성 및 연결 ------------------------


        mReference = FirebaseDatabase.getInstance().getReference("User").child(getId).child("Ticket");// 변경값을 확인할 child 이름
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<TicketData> oData = new ArrayList<>();
                int nDatCnt = 0;

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
//                    // child 내에 있는 데이터만큼 반복합니다.
                    TicketData oItem = new TicketData();
                    oItem.Area =messageData.child("출발지").getValue().toString()+" -> "+messageData.child("도착지").getValue().toString();
                    oItem.Date = messageData.child("날짜").getValue().toString()+" "+messageData.child("출발시간").getValue().toString()+"~"+messageData.child("도착시간").getValue().toString();
                    oItem.SeatNum = "소요시간: "+messageData.child("소요시간").getValue().toString()+"    좌석번호: "+messageData.child("좌석").getValue().toString();
                    oItem.onClickListener = (View.OnClickListener)TicketList.this;
                    oData.add(oItem);
                }

                m_oListView = (ListView) findViewById(R.id.ticket_list);
                TicketListAdapter oAdapter = new TicketListAdapter(oData);
                m_oListView.setAdapter(oAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_change) {
            View oParentView = (View) v.getParent(); // parents의 View를 가져온다.
            TextView oTextTitle = (TextView) oParentView.findViewById(R.id.area);
            String position = (String) oParentView.getTag();

            AlertDialog.Builder oDialog = new AlertDialog.Builder(this,
                    android.R.style.Theme_DeviceDefault_Light_Dialog);

            String strMsg = "예매변경으로 선택한 아이템의 position 은 " + position + " 입니다.\nTitle 텍스트 :" + oTextTitle.getText();
            oDialog.setMessage(strMsg)
                    .setPositiveButton("확인", null)
                    .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
                    .show();
        } else if (v.getId() == R.id.btn_cancel) {
            View oParentView = (View) v.getParent(); // parents의 View를 가져온다.
            TextView oTextTitle = (TextView) oParentView.findViewById(R.id.area);
            String position = (String) oParentView.getTag();

            AlertDialog.Builder oDialog = new AlertDialog.Builder(this,
                    android.R.style.Theme_DeviceDefault_Light_Dialog);

            String strMsg = "예매취소로 선택한 아이템의 position 은 " + position + " 입니다.\nTitle 텍스트 :" + oTextTitle.getText();
            oDialog.setMessage(strMsg)
                    .setPositiveButton("확인", null)
                    .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
                    .show();
        }


    }

    private void initDatabase() {

        mReference = FirebaseDatabase.getInstance().getReference(getId).child("Ticket");

//        mReference.child("log").setValue("check");

        mChild = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mReference.addChildEventListener(mChild);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mReference.removeEventListener(mChild);
    }

}
