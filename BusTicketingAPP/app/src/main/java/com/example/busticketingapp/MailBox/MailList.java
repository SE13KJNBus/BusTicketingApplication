package com.example.busticketingapp.MailBox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.busticketingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
                                            oItem.arrivePlace= splitData[1];
                                            oItem.date = splitData[2];

                                            oItem.startTime = splitData[3].split("-")[0];
                                            oItem.endTime = splitData[3].split("-")[1];


                                            oItem.company = splitData[4];
                                            oItem.seatNum=seat.getKey().toString();

                                            int start = Integer.parseInt(splitData[3].split("-")[0].split(":")[0])*60+Integer.parseInt(splitData[3].split("-")[0].split(":")[1]);
                                            int end = Integer.parseInt(splitData[3].split("-")[1].split(":")[0])*60+Integer.parseInt(splitData[3].split("-")[1].split(":")[1]);

                                            oItem.time = ((int)(end-start)/60)+":"+(end-start)%60;

                                        }
                                    }
                                } else {
                                    oItem.senderEmail = sender.getKey().toString().replace(":",".");
                                    oItem.senderName = sender.getValue().toString();
                                    oItem.senddate = DateData.getKey().toString().replace("@", "  ");
                                    oItem.message = messageData.getKey().toString();
                                    oItem.onClickListener = (View.OnClickListener) MailList.this;

                                    oData.add(oItem);
                                }
                            }
                        } else { //친구요청
                            for (DataSnapshot sender : messageData.getChildren()) {//보낸이 정보
                                MailData oItem = new MailData();
                                oItem.senderEmail = sender.getKey().toString().replace(":",".");
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

        if (v.getId() == R.id.btn_accept) {
            View oParentView = (View) v.getParent(); // parents의 View를 가져온다.
            String position = (String) oParentView.getTag();

            String strMsg = "예매변경으로 선택한 아이템의 position 은 " + position + " 입니다.\nTitle 텍스트 :";

            Toast.makeText(this,strMsg,Toast.LENGTH_SHORT).show();

        } else if (v.getId() == R.id.btn_reject) {
//            View oParentView = (View) v.getParent(); // parents의 View를 가져온다.
////            TextView oTextTitle = (TextView) oParentView.findViewById(R.id.area);
//            final String position = (String) oParentView.getTag();
//
//            AlertDialog.Builder oDialog = new AlertDialog.Builder(this,
//                    android.R.style.Theme_DeviceDefault_Light_Dialog);
//
//            oDialog.setMessage("예매취소하시겠습니까?")
//                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // TODO Auto-generated method stub
//
//
//                            TicketData reData = oData.get(Integer.parseInt(position));
//                            if (getMember) {
//                                mReference = FirebaseDatabase.getInstance().getReference("Member").child(getId).child("Ticket").child(reData.date).child(reData.startTime).child(reData.endTime).child(reData.startPlace).child(reData.arrivePlace).child(reData.company).child(reData.seatNum);// 변경값을 확인할 child 이름
//                            } else {
//                                mReference = FirebaseDatabase.getInstance().getReference("User").child(getId).child("Ticket").child(reData.date).child(reData.startTime).child(reData.endTime).child(reData.startPlace).child(reData.arrivePlace).child(reData.company).child(reData.seatNum);// 변경값을 확인할 child 이름
//                            }
//                            mReference.removeValue();
//                            oData.remove(Integer.parseInt(position));
//                            TicketListAdapter oAdapter = new TicketListAdapter(oData);
//                            m_oListView.setAdapter(oAdapter);
//
//                            mReference = FirebaseDatabase.getInstance().getReference("Bus").child(reData.startPlace).child(reData.arrivePlace).child(reData.date).child(reData.startTime + "-" + reData.endTime).child(reData.company).child(reData.seatNum);// 변경값을 확인할 child 이름
//                            mReference.setValue("true");
//
//                            Toast.makeText(TicketList.this, "예매가 취소되었습니다.\n7일이내 환불처리될 예정입니다.", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .setNegativeButton("취소", null)
//                    .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
//                    .show();
        }

    }
}