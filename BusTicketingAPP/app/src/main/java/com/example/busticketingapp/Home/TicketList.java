package com.example.busticketingapp.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.busticketingapp.BusList.modifyBusListOne;
import com.example.busticketingapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

                    Log.v("CC",dataSnapshot.getKey()+"///"+dataSnapshot.getValue());

                    for (DataSnapshot messageData : dataSnapshot.getChildren()) {//Date

//                    // child 내에 있는 데이터만큼 반복합니다.
                        Log.v("CC",messageData.getKey()+"///"+messageData.getValue());

                        String getKey = messageData.getKey().toString();
                        String[] splitData = getKey.split("@");

                        for (DataSnapshot snapshot : messageData.getChildren()) {

                            Log.v("CC", snapshot.getKey());

                            long now = System.currentTimeMillis();
                            Date date = new Date(now);
                            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd@HH:mm");
                            String formatDate = sdfNow.format(date);
                            int day = Integer.parseInt(formatDate.split("-|@")[2]);
                            int h = Integer.parseInt(formatDate.split("-|@|:")[3]);
                            int m = Integer.parseInt(formatDate.split("-|@|:")[4]);

                            if (Integer.parseInt(splitData[2].substring(6)) < day) {
                                if (getMember) {
                                    mReference = FirebaseDatabase.getInstance().getReference("Member").child(getId).child("Ticket").child(getKey);// 변경값을 확인할 child 이름
                                } else {
                                    mReference = FirebaseDatabase.getInstance().getReference("User").child(getId).child("Ticket").child(getKey);// 변경값을 확인할 child 이름
                                }
                                mReference.removeValue();

                            } else {
                                TicketData oItem = new TicketData();
                                oItem.ing = "";
                                if (Integer.parseInt(splitData[2].substring(6)) == day) {
                                    int sH = Integer.parseInt(splitData[3].split("-|:")[0]);
                                    int sM = Integer.parseInt(splitData[3].split("-|:")[1]);
                                    int eH = Integer.parseInt(splitData[3].split("-|:")[2]);
                                    int eM = Integer.parseInt(splitData[3].split("-|:")[3]);

                                    if(eH < h || ((eH==h)&& eM < m)){

                                        if (getMember) {
                                            mReference = FirebaseDatabase.getInstance().getReference("Member").child(getId).child("Ticket").child(getKey);// 변경값을 확인할 child 이름
                                        } else {
                                            mReference = FirebaseDatabase.getInstance().getReference("User").child(getId).child("Ticket").child(getKey);// 변경값을 확인할 child 이름
                                        }
                                        mReference.removeValue();
                                    }else{

                                        if(sH < h && h < eH){
                                            oItem.ing = "운행중";
                                        } else if(sH == h){
                                            if((sH==eH)&& m <= eM){
                                                oItem.ing = "운행중";
                                            }else if(sH!=eH){
                                                oItem.ing = "운행중";
                                            }
                                        }else if(h == eH){
                                            if(m <= eM){
                                                oItem.ing = "운행중";
                                            }
                                        }

                                        oItem.startPlace = splitData[0];
                                        oItem.arrivePlace = splitData[1];

                                        oItem.startTime = splitData[3].split("-")[0];
                                        oItem.endTime = splitData[3].split("-")[1];

                                        oItem.company = splitData[4];

                                        int start = Integer.parseInt(splitData[3].split("-")[0].split(":")[0]) * 60 + Integer.parseInt(splitData[3].split("-")[0].split(":")[1]);
                                        int end = Integer.parseInt(splitData[3].split("-")[1].split(":")[0]) * 60 + Integer.parseInt(splitData[3].split("-")[1].split(":")[1]);

                                        String movingTime = String.format("%02d:%02d", ((int) (end - start) / 60), (end - start) % 60);

                                        oItem.time = movingTime;
                                        oItem.date = splitData[2];
                                        oItem.seatNum = snapshot.getKey();


                                        oItem.onClickListener = (View.OnClickListener) TicketList.this;
                                        oData.add(oItem);
                                    }

                                }else{
                                    oItem.startPlace = splitData[0];
                                    oItem.arrivePlace = splitData[1];
                                    oItem.ing = "";

                                    oItem.startTime = splitData[3].split("-")[0];
                                    oItem.endTime = splitData[3].split("-")[1];

                                    oItem.company = splitData[4];

                                    int start = Integer.parseInt(splitData[3].split("-")[0].split(":")[0]) * 60 + Integer.parseInt(splitData[3].split("-")[0].split(":")[1]);
                                    int end = Integer.parseInt(splitData[3].split("-")[1].split(":")[0]) * 60 + Integer.parseInt(splitData[3].split("-")[1].split(":")[1]);

                                    String movingTime = String.format("%02d:%02d", ((int) (end - start) / 60), (end - start) % 60);

                                    oItem.time = movingTime;
                                    oItem.date = splitData[2];
                                    oItem.seatNum = snapshot.getKey();


                                    oItem.onClickListener = (View.OnClickListener) TicketList.this;
                                    oData.add(oItem);
                                }
                            }
                        }
                    }

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

        View oParentView = (View) v.getParent(); // parents의 View를 가져온다.
        final String position = (String) oParentView.getTag();
        final TicketData reData = oData.get(Integer.parseInt(position));

        if(reData.ing.equals("운행중")){
            Toast.makeText(this,"운행중인 티켓은 변경과 취소가 불가능합니다.",Toast.LENGTH_SHORT).show();
        }else{
            if (v.getId() == R.id.btn_change) {


//                AlertDialog.Builder oDialog = new AlertDialog.Builder(this,
//                        android.R.style.Theme_DeviceDefault_Light_Dialog);
//
//                String strMsg = "예매변경으로 선택한 아이템의 position 은 " + position + " 입니다.\nTitle 텍스트 :";
//                oDialog.setMessage(strMsg)
//                        .setPositiveButton("확인", null)
//                        .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
//                        .show();

                View parentView = (View) v.getParent();
                TextView company = (TextView) parentView.findViewById(R.id.company);
                TextView endTime = (TextView) parentView.findViewById(R.id.endTime);
                TextView startTime = (TextView) parentView.findViewById(R.id.startTime);
                TextView startPlace = (TextView) parentView.findViewById(R.id.startPlace);
                TextView arrivePlace = (TextView) parentView.findViewById(R.id.arrivePlace);
                TextView date = (TextView) parentView.findViewById(R.id.date);
                TextView seatNum = (TextView) parentView.findViewById(R.id.seatNum);

                Intent modifyBusList = new Intent(this, modifyBusListOne.class);
                modifyBusList.putExtra("getId", getId);
                modifyBusList.putExtra("getName", getName);
                modifyBusList.putExtra("getMember", getMember);
                modifyBusList.putExtra("busCompany", company.getText().toString());
                modifyBusList.putExtra("arriveTime", endTime.getText().toString());
                modifyBusList.putExtra("startTime", startTime.getText().toString());
                modifyBusList.putExtra("arrivePlace", arrivePlace.getText().toString());
                modifyBusList.putExtra("startPlace", startPlace.getText().toString());
                modifyBusList.putExtra("seatNum", new Integer(seatNum.getText().toString()));
                modifyBusList.putExtra("date", date.getText().toString());

                startActivity(modifyBusList);
                finish();

            } else if (v.getId() == R.id.btn_cancel) {

//                AlertDialog.Builder oDialog = new AlertDialog.Builder(this,
//                        android.R.style.Theme_DeviceDefault_Light_Dialog);
//
//                oDialog.setMessage("예매취소하시겠습니까?")
//                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // TODO Auto-generated method stub

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
//                        })
//                        .setNegativeButton("취소", null)
//                        .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
//                        .show();
//            }

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}