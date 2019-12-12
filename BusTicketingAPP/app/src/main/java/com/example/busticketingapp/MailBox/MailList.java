package com.example.busticketingapp.MailBox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.busticketingapp.Home.Home_Page;
import com.example.busticketingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MailList extends AppCompatActivity implements View.OnClickListener {

    private ListView m_oListView = null;
    String getId;
    String getName;
    DatabaseReference mReference;

    ArrayList<MailData> oData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mailbox_mail_list);

        getId = getIntent().getStringExtra("Id");
        getName = getIntent().getStringExtra("UserName");

        int nDatCnt = 0;

        mReference = FirebaseDatabase.getInstance().getReference("Member").child(getId).child("Notification");// 변경값을 확인할 child 이름
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                oData = new ArrayList<>();
                int nDatCnt = 0;

                for (DataSnapshot DateData : dataSnapshot.getChildren()) {//Date
                    for (DataSnapshot messageData : DateData.getChildren()) {//message
                        if (1 < messageData.getChildrenCount()) { //표양도
                            MailData oItem = new MailData();
                            for (DataSnapshot sender : messageData.getChildren()) {//보낸이 정보
                                if (sender.getKey().toString().equals("Ticket")) {
                                    for (DataSnapshot ticketInfo : sender.getChildren()) {//Date
                                        for (DataSnapshot seat : ticketInfo.getChildren()) {//좌석

                                            String getKey = ticketInfo.getKey().toString();
                                            String[] splitData = getKey.split("@");


                                            oItem.startPlace = splitData[0];
                                            oItem.arrivePlace = splitData[1];
                                            oItem.date = splitData[2];

                                            oItem.startTime = splitData[3].split("-")[0];
                                            oItem.endTime = splitData[3].split("-")[1];


                                            oItem.company = splitData[4];
                                            oItem.seatNum = seat.getKey().toString();

                                            int start = Integer.parseInt(splitData[3].split("-")[0].split(":")[0]) * 60 + Integer.parseInt(splitData[3].split("-")[0].split(":")[1]);
                                            int end = Integer.parseInt(splitData[3].split("-")[1].split(":")[0]) * 60 + Integer.parseInt(splitData[3].split("-")[1].split(":")[1]);

                                            oItem.time = ((int) (end - start) / 60) + ":" + (end - start) % 60;

                                        }
                                    }
                                } else{
                                    oItem.senderEmail = sender.getKey().toString().replace(":", ".");
                                    oItem.senderName = sender.getValue().toString();
                                    oItem.senddate = DateData.getKey().toString().replace("@", "  ");
                                    oItem.message = messageData.getKey().toString();
                                    oItem.onClickListener = (View.OnClickListener) MailList.this;

                                    oData.add(oItem);
                                }
                            }
                        } else if(messageData.getKey().toString().equals("표양도거절")){
                            for (DataSnapshot sender : messageData.getChildren()) {//보낸이 정보
                                MailData oItem = new MailData();
                                oItem.senderEmail = sender.getKey().toString().replace(":", ".");
                                oItem.senderName = sender.getValue().toString();
                                oItem.senddate = DateData.getKey().toString().replace("@", "  ");
                                oItem.message = messageData.getKey().toString();
                                oItem.onClickListener = (View.OnClickListener) MailList.this;

                                oData.add(oItem);
                            }
                        } else { //친구요청
                            for (DataSnapshot sender : messageData.getChildren()) {//보낸이 정보
                                MailData oItem = new MailData();
                                oItem.senderEmail = sender.getKey().toString().replace(":", ".");
                                oItem.senderName = sender.getValue().toString();
                                oItem.senddate = DateData.getKey().toString().replace("@", "  ");
                                oItem.message = messageData.getKey().toString();
                                oItem.onClickListener = (View.OnClickListener) MailList.this;

                                oData.add(oItem);
                            }
                        }
                    }
//

                }
                m_oListView = (ListView) findViewById(R.id.mail_list);

                MailAdapter oAdapter = new MailAdapter(oData);
                m_oListView.setAdapter(oAdapter);
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
        MailData maData = oData.get(Integer.parseInt(position));

        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference("Member").child(getId).child("Notification").child(maData.senddate.replace("  ","@")).child(maData.message);

        if (v.getId() == R.id.btn_accept) {

            if (maData.message.equals("친구요청")) {

                DatabaseReference meRe = FirebaseDatabase.getInstance().getReference("Member").child(getId).child("Friend");
                DatabaseReference youRe = FirebaseDatabase.getInstance().getReference("Member").child(maData.senderEmail.replace(".", ":")).child("Friend");

                meRe.child(maData.senderEmail.replace(".", ":")).setValue(maData.senderName);
                youRe.child(getId).setValue(getName);

                mReference.removeValue();

                oData.remove(Integer.parseInt(position));
                MailAdapter oAdapter = new MailAdapter(oData);
                m_oListView.setAdapter(oAdapter);

            }else {
                String Dataname = maData.startPlace + "@" + maData.arrivePlace + "@" + maData.date + "@" + maData.startTime + "-" + maData.endTime + "@" + maData.company;

                DatabaseReference meRe = FirebaseDatabase.getInstance().getReference("Member").child(getId).child("Ticket").child(Dataname).child(maData.seatNum);
                DatabaseReference youRe = FirebaseDatabase.getInstance().getReference("Member").child(maData.senderEmail.replace(".", ":")).child("Ticket").child(Dataname).child(maData.seatNum);

                meRe.setValue("true");
                youRe.removeValue();

                mReference.removeValue();
                oData.remove(Integer.parseInt(position));
                MailAdapter oAdapter = new MailAdapter(oData);
                m_oListView.setAdapter(oAdapter);
            }

        } else if (v.getId() == R.id.btn_reject) {

            if (maData.message.equals("친구요청")) {

                mReference.removeValue();

                oData.remove(Integer.parseInt(position));
                MailAdapter oAdapter = new MailAdapter(oData);
                m_oListView.setAdapter(oAdapter);

            } else {
                String Dataname = maData.startPlace + "@" + maData.arrivePlace + "@" + maData.date + "@" + maData.startTime + "-" + maData.endTime + "@" + maData.company;

                DatabaseReference youRe = FirebaseDatabase.getInstance().getReference("Member").child(maData.senderEmail.replace(".", ":")).child("Ticket").child(Dataname).child(maData.seatNum);

                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd@HH:mm:ss");
                String formatDate = sdfNow.format(date);

                DatabaseReference youReNo = FirebaseDatabase.getInstance().getReference("Member").child(maData.senderEmail.replace(".", ":")).child("Notification").child(formatDate).child("표양도거절").child(getId);
                youReNo.setValue(getName);
                youRe.setValue("true");
                mReference.removeValue();

                oData.remove(Integer.parseInt(position));
                MailAdapter oAdapter = new MailAdapter(oData);
                m_oListView.setAdapter(oAdapter);
            }
        }else{
            mReference.removeValue();

            oData.remove(Integer.parseInt(position));
            MailAdapter oAdapter = new MailAdapter(oData);
            m_oListView.setAdapter(oAdapter);
        }
        Intent intent = new Intent(getApplicationContext(), Home_Page.class);
        intent.putExtra("Id", getId);
        intent.putExtra("Member",true);
        intent.putExtra("UserName",getName);
        startActivity(intent);
        finish();
    }

}