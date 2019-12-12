package com.example.busticketingapp.Friend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.busticketingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class Search_member extends AppCompatActivity {

    String getId;
    String getName;

    EditText memberEmail;
    DatabaseReference mReference;
    String searchEmail = "";
    boolean found = false;
    boolean search = false;
    Button addFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_search_member);

        memberEmail = (EditText) findViewById(R.id.memberEmail);
        addFriend = (Button) findViewById(R.id.addFriend);
        memberEmail.setEnabled(true);
        addFriend.setVisibility(View.INVISIBLE);

        getId = getIntent().getStringExtra("Id");
        getName = getIntent().getStringExtra("UserName");

    }

    public void addFriend(View view) {

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd@HH:mm:ss");
        String formatDate = sdfNow.format(date);


        searchEmail = memberEmail.getText().toString().replace('.', ':');

        DatabaseReference mr = FirebaseDatabase.getInstance().getReference("Member").child(searchEmail).child("Notification");// 변경값을 확인할 child 이름
        mr.child(formatDate).child("친구요청").child(getId).setValue(getName);
    }

    public void search(View view) {

        searchEmail = memberEmail.getText().toString().replace('.', ':');
        search = true;

        mReference = FirebaseDatabase.getInstance().getReference("Member");// 변경값을 확인할 child 이름
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                found = false;
                if (search) {
                    search = false;
                    for (DataSnapshot messageData : dataSnapshot.getChildren()) {

                        if (getId.equals(messageData.getKey().toString())) {
                            Iterator<DataSnapshot> child = messageData.child("Friend").getChildren().iterator();
                            //users의 모든 자식들의 key값과 value 값들을 iterator로 참조합니다.

                            while (child.hasNext()) {
                                //찾고자 하는 ID값은 key로 존재하는 값
                                if (child.next().getKey().equals(searchEmail)) {
                                    Toast.makeText(Search_member.this, "이 회원과 이미 친구사이입니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        } else if (messageData.getKey().toString().equals(searchEmail)) {
                            found = true;
                        }
                    }

                    if (!found) {
                        Toast.makeText(Search_member.this, "존재하지않는 회원입니다.", Toast.LENGTH_SHORT).show();
                        addFriend.setVisibility(View.INVISIBLE);
                    } else {
                        memberEmail.setEnabled(false);
                        addFriend.setVisibility(View.VISIBLE);
                        return;
                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
