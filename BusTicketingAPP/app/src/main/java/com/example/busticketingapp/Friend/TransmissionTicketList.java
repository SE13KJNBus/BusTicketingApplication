package com.example.busticketingapp.Friend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.busticketingapp.Home.Home_Page;
import com.example.busticketingapp.Home.TicketData;
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

public class TransmissionTicketList extends AppCompatActivity implements View.OnClickListener {

    private ListView m_oListView = null;
    private String getId;
    private String getName;
    private String getfriendId;
    private String getfriendName;

    private DatabaseReference mReference;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("User");
    ArrayList<TicketData> oData;

    boolean update = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_transmission_ticket_list);

        getId = getIntent().getStringExtra("Id");
        getName = getIntent().getStringExtra("UserName");
        getfriendId = getIntent().getStringExtra("friendId");
        getfriendName = getIntent().getStringExtra("friendUserName");

//        myRef.child(getId).child("Ticket").child("20191210").setValue("12:00-16:00");

// ListView, Adapter 생성 및 연결 ------------------------
        mReference = FirebaseDatabase.getInstance().getReference("Member").child(getId).child("Ticket");// 변경값을 확인할 child 이름

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (update) {
                    oData = new ArrayList<>();
                    int nDatCnt = 0;

                    for (DataSnapshot messageData : dataSnapshot.getChildren()) {//Date

//                    // child 내에 있는 데이터만큼 반복합니다.
                        String getKey = messageData.getKey().toString();
                        for (DataSnapshot snapshot : messageData.getChildren()) {

                            String[] splitData = getKey.split("@");

                            long now = System.currentTimeMillis();
                            Date date = new Date(now);
                            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd@HH:mm");
                            String formatDate = sdfNow.format(date);
                            int day = Integer.parseInt(formatDate.split("-|@")[2]);
                            int h = Integer.parseInt(formatDate.split("-|@|:")[3]);
                            int m = Integer.parseInt(formatDate.split("-|@|:")[4]);

                            if (Integer.parseInt(splitData[2].substring(6)) < day) {

                                mReference = FirebaseDatabase.getInstance().getReference("Member").child(getId).child("Ticket").child(getKey);// 변경값을 확인할 child 이름

                                mReference.removeValue();

                            } else {
                                TicketData oItem = new TicketData();

                                if (Integer.parseInt(splitData[2].substring(6)) == day) {
                                    int sH = Integer.parseInt(splitData[3].split("-|:")[0]);
                                    int sM = Integer.parseInt(splitData[3].split("-|:")[1]);
                                    int eH = Integer.parseInt(splitData[3].split("-|:")[2]);
                                    int eM = Integer.parseInt(splitData[3].split("-|:")[3]);

                                    if (eH < h || ((eH == h) && eM < m)) {

                                        mReference = FirebaseDatabase.getInstance().getReference("Member").child(getId).child("Ticket").child(getKey);// 변경값을 확인할 child 이름

                                        mReference.removeValue();
                                    } else {

                                        oItem.ing = "";
                                        if (sH < h && h < eH) {
                                            oItem.ing = "운행중";
                                        } else if (sH == h) {
                                            if ((sH == eH) && m <= eM) {
                                                oItem.ing = "운행중";
                                            } else if (sM<=m) {
                                                oItem.ing = "운행중";
                                            }
                                        } else if (h == eH) {
                                            if (m <= eM) {
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


                                        oItem.onClickListener = (View.OnClickListener) TransmissionTicketList.this;
                                        oData.add(oItem);
                                    }

                                } else {
                                    oItem.ing = "";
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


                                    oItem.onClickListener = (View.OnClickListener) TransmissionTicketList.this;
                                    oData.add(oItem);
                                }
                            }
                        }
                    }

                    m_oListView = (ListView) findViewById(R.id.friend_transmission_ticketList);
                    TransmissionTicketListAdapter oAdapter = new TransmissionTicketListAdapter(oData);
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
        String position = (String) oParentView.getTag();
        TicketData ticketData = oData.get(Integer.parseInt(position));

        if(ticketData.ing.equals("운행중")){
            Toast.makeText(this,"운행중인 티켓은 양도할 수 없습니다.",Toast.LENGTH_SHORT).show();
        }else{
            String Dataname = ticketData.startPlace + "@" + ticketData.arrivePlace + "@" + ticketData.date + "@" + ticketData.startTime + "-" + ticketData.endTime + "@" + ticketData.company;

            DatabaseReference mReference = FirebaseDatabase.getInstance().getReference("Member").child(getId).child("Ticket").child(Dataname).child(ticketData.seatNum);
            mReference.setValue("false");

            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd@HH:mm:ss");
            String formatDate = sdfNow.format(date);

            DatabaseReference youReNo = FirebaseDatabase.getInstance().getReference("Member").child(getfriendId.replace(".", ":")).child("Notification").child(formatDate).child("표양도");
            youReNo.child(getId).setValue(getName);
            youReNo.child("Ticket").child(Dataname).child(ticketData.seatNum).setValue("true");

            Intent intent = new Intent(getApplicationContext(), Home_Page.class);
            Toast.makeText(getApplicationContext(),"표양도 알림을 보냈습니다.",Toast.LENGTH_SHORT).show();
            intent.putExtra("Id", getId);
            intent.putExtra("Member",true);
            intent.putExtra("UserName",getName);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
