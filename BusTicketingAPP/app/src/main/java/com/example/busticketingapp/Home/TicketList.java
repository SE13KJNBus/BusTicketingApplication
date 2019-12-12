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

    TicketListAdapter oAdapter;
    boolean update = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_ticket_data);

        getId = getIntent().getStringExtra("Id");
        getMember = getIntent().getBooleanExtra("Member", false);
        getName = getIntent().getStringExtra("UserName");

        oData = new ArrayList<>();
        m_oListView = (ListView) findViewById(R.id.ticket_list);
        oAdapter = new TicketListAdapter(oData);
        m_oListView.setAdapter(oAdapter);

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
                    oData.clear();
                    int nDatCnt = 0;
                    Log.v("CC",dataSnapshot.getKey()+"///"+dataSnapshot.getValue());

                    for (DataSnapshot messageData : dataSnapshot.getChildren()) {//Date

//                    // child 내에 있는 데이터만큼 반복합니다.
                        Log.v("CC",messageData.getKey()+"///"+messageData.getValue());

                        String getKey = messageData.getKey().toString();
                        String[] splitData = getKey.split("@");




                        for(DataSnapshot snapshot : messageData.getChildren()){
                            Log.v("CC", snapshot.getKey());
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
                            oItem.seatNum = snapshot.getKey();
                            oItem.onClickListener = (View.OnClickListener) TicketList.this;
                            oData.add(oItem);


                        }


                    }
                    oAdapter = new TicketListAdapter(oData);
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
                            oAdapter = new TicketListAdapter(oData);
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