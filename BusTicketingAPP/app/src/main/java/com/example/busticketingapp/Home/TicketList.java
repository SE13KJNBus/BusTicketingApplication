package com.example.busticketingapp.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private String getName;
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("User");
    ArrayList<TicketData> oData;

    boolean update = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_ticket_data);

        getId = getIntent().getStringExtra("Id");
        getMember = getIntent().getBooleanExtra("Member", false);
        getName = getIntent().getStringExtra("UserName");

//        myRef.child(getId).child("Ticket").child("20191210").setValue("12:00-16:00");

// ListView, Adapter 생성 및 연결 ------------------------
        if (getMember)
            mReference = FirebaseDatabase.getInstance().getReference("Member").child(getId).child("Ticket");// 변경값을 확인할 child 이름
        else
            mReference = FirebaseDatabase.getInstance().getReference("User").child(getId).child("Ticket");// 변경값을 확인할 child 이름

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (update) {
                    oData = new ArrayList<>();
                    int nDatCnt = 0;

                    for (DataSnapshot messageData : dataSnapshot.getChildren()) {//Date

//                    // child 내에 있는 데이터만큼 반복합니다.
                        String getKey = messageData.getKey().toString();
                        String[] splitData = getKey.split("@");
                        TicketData oItem = new TicketData();
                        oItem.startPlace = splitData[0];
                        oItem.arrivePlace = splitData[1];

                        oItem.startTime = splitData[3].split("-")[0];
                        oItem.endTime = splitData[3].split("-")[1];

                        oItem.company = splitData[4];

                        int start = Integer.parseInt(splitData[3].split("-")[0].split(":")[0])*60+Integer.parseInt(splitData[3].split("-")[0].split(":")[1]);
                        int end = Integer.parseInt(splitData[3].split("-")[1].split(":")[0])*60+Integer.parseInt(splitData[3].split("-")[1].split(":")[1]);

                        String movingTime = String.format("%02d:%02d",((int)(end-start)/60),(end-start)%60);

                        oItem.time = movingTime;
                        oItem.date = splitData[2];
                        for(DataSnapshot snapshot : messageData.getChildren()){
                            oItem.seatNum = snapshot.getKey();
                        }

                        oItem.onClickListener = (View.OnClickListener) TicketList.this;
                        oData.add(oItem);
                        /*
                        for (DataSnapshot startT : messageData.getChildren()) {//출발시간
                            for (DataSnapshot endT : startT.getChildren()) {//도착시간
                                for (DataSnapshot startPl : endT.getChildren()) {//출발지역
                                    for (DataSnapshot endPl : startPl.getChildren()) {//도착지역
                                        for (DataSnapshot com : endPl.getChildren()) {//회사
                                            for (DataSnapshot seat : com.getChildren()) {//좌석

                                                TicketData oItem = new TicketData();
                                                oItem.startPlace = startPl.getKey().toString();
                                                oItem.arrivePlace = endPl.getKey().toString();

                                                oItem.startTime = startT.getKey().toString();
                                                oItem.endTime = endT.getKey().toString();

                                                oItem.company = com.getKey().toString();
                                                oItem.time = "Re";
                                                oItem.date = messageData.getKey().toString();
                                                oItem.seatNum = seat.getKey().toString();

                                                oItem.onClickListener = (View.OnClickListener) TicketList.this;
                                                oData.add(oItem);

                                            }
                                        }
                                    }
                                }
                            }
                        }*/
                    }

                    m_oListView = (ListView) findViewById(R.id.ticket_list);
                    TicketListAdapter oAdapter = new TicketListAdapter(oData);
                    m_oListView.setAdapter(oAdapter);

                    update = false;
                }

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
            String position = (String) oParentView.getTag();

            AlertDialog.Builder oDialog = new AlertDialog.Builder(this,
                    android.R.style.Theme_DeviceDefault_Light_Dialog);

            String strMsg = "예매변경으로 선택한 아이템의 position 은 " + position + " 입니다.\nTitle 텍스트 :";
            oDialog.setMessage(strMsg)
                    .setPositiveButton("확인", null)
                    .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
                    .show();
        } else if (v.getId() == R.id.btn_cancel) {
            View oParentView = (View) v.getParent(); // parents의 View를 가져온다.


            final String position = (String) oParentView.getTag();
            AlertDialog.Builder oDialog = new AlertDialog.Builder(this,
                    android.R.style.Theme_DeviceDefault_Light_Dialog);

            oDialog.setMessage("예매취소하시겠습니까?")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub


                            TicketData reData = oData.get(Integer.parseInt(position));
                            String Dataname = reData.startPlace + "@" + reData.arrivePlace + "@" + reData.date + "@" + reData.startTime + "-" + reData.endTime + "@" + reData.company;

                            if (getMember) {
                                mReference = FirebaseDatabase.getInstance().getReference("Member").child(getId).child("Ticket").child(Dataname).child(reData.seatNum);// 변경값을 확인할 child 이름
                            } else {
                                mReference = FirebaseDatabase.getInstance().getReference("User").child(getId).child("Ticket").child(Dataname).child(reData.seatNum);// 변경값을 확인할 child 이름
                            }
                            mReference.removeValue();
                            oData.remove(Integer.parseInt(position));
                            TicketListAdapter oAdapter = new TicketListAdapter(oData);
                            m_oListView.setAdapter(oAdapter);

                            mReference = FirebaseDatabase.getInstance().getReference("Bus").child(reData.startPlace).child(reData.arrivePlace).child(reData.date).child(reData.startTime + "-" + reData.endTime).child(reData.company).child(reData.seatNum);// 변경값을 확인할 child 이름
                            mReference.setValue("true");

                            Toast.makeText(TicketList.this, "예매가 취소되었습니다.\n7일이내 환불처리될 예정입니다.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("취소", null)
                    .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
                    .show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}