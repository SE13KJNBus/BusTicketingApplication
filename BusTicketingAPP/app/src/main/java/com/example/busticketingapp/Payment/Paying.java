package com.example.busticketingapp.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busticketingapp.Home.Home_Page;
import com.example.busticketingapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Paying extends AppCompatActivity {

    String getId;
    String getName;
    boolean getCart;
    boolean getMember;
    ArrayList<String> getList;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("Payment");

    ArrayList<String> creditCardInfo;
    HashMap<String, String> phoneInfo;

    int idx;
    Spinner PhoneCompanySpinner;
    EditText inputPhoneNum;
    EditText CertificationNum;
    Button btnConfirmPhone;
    Button btnConfirm;

    boolean confirm;
    Spinner CardCompanySpinner;
    EditText cardValidity;
    EditText cardPassword;
    EditText cardNum;

    // HashMap<String, String> creditCardInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_paying);

        creditCardInfo = new ArrayList<>();
        phoneInfo = new HashMap<>();

        getId = getIntent().getStringExtra("Id");
        getMember = getIntent().getBooleanExtra("Member", false);
        getCart = getIntent().getBooleanExtra("Cart", false);

        getName = getIntent().getStringExtra("UserName");
        Log.v("Name", "In Paying_success : " + getName);

        getList = getIntent().getStringArrayListExtra("TicketList");

        for (int i = 0; i < getList.size(); i++) {
            Log.v("Test", getList.get(i));
        }
        //myRef.addValueEventListener(valueEventListener);
        myRef.addChildEventListener(childEventListener);


        inputPhoneNum = findViewById(R.id.edit_PhoneNum);
        CertificationNum = findViewById(R.id.edit_CertificationNum);
        btnConfirmPhone = findViewById(R.id.btnConfirm);
        btnConfirmPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = PhoneCompanySpinner.getSelectedItem().toString() + "/" + inputPhoneNum.getText().toString();
                if (phoneInfo.get(key) != null) {
                    Toast.makeText(Paying.this, "인증번호 : " + phoneInfo.get(key), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Paying.this, "확인되지 않은 사용자", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnConfirm = findViewById(R.id.confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = phoneInfo.get(PhoneCompanySpinner.getSelectedItem().toString() + "/" + inputPhoneNum.getText().toString());
                Log.v("Payment", "key : " + key + ", input: " + CertificationNum.getText().toString());
                if (key.equals(CertificationNum.getText().toString())) {
                    Toast.makeText(Paying.this, "인증성공", Toast.LENGTH_SHORT).show();
                    confirm = true;
                } else {
                    Toast.makeText(Paying.this, "결제오류: 인증번호틀림", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Home_Page.class);
                    intent.putExtra("Id", getId);
                    intent.putExtra("Member", true);
                    intent.putExtra("UserName", getName);
                    startActivity(intent);
                    finish();
                }
            }
        });

        cardValidity = findViewById(R.id.edit_cardValidity);
        cardPassword = findViewById(R.id.edit_cardPassword);
        cardNum = findViewById(R.id.edit_cardNum);

        idx = 0;
        changeView();

        // Spinner
        {
            PhoneCompanySpinner = (Spinner) findViewById(R.id.sel_PhoneCompany);
            ArrayAdapter PhoneCompanyAdapter = ArrayAdapter.createFromResource(this,
                    R.array.PhoneCompany, android.R.layout.simple_spinner_item);
            PhoneCompanyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            PhoneCompanySpinner.setAdapter(PhoneCompanyAdapter);

            CardCompanySpinner = (Spinner) findViewById(R.id.sel_cardCompany);
            ArrayAdapter CardCompanyAdapter = ArrayAdapter.createFromResource(this,
                    R.array.cardCompany, android.R.layout.simple_spinner_item);
            CardCompanyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            CardCompanySpinner.setAdapter(CardCompanyAdapter);
        }

        Button card = (Button) findViewById(R.id.btn_creditCardPayment);
        card.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                idx = 1;
                changeView();
            }
        });

        Button phone = (Button) findViewById(R.id.btn_phonePayment);
        phone.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                idx = 0;
                changeView();
            }
        });

    }

    private void changeView() {

        RelativeLayout phone = (RelativeLayout) findViewById(R.id.paying_phone);
        RelativeLayout card = (RelativeLayout) findViewById(R.id.paying_creditCard);

        switch (idx) {
            case 0:
                phone.setVisibility(View.VISIBLE);
                card.setVisibility(View.INVISIBLE);
                break;
            case 1:
                card.setVisibility(View.VISIBLE);
                phone.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void cancel(View view) {
        Intent intent = new Intent(getApplicationContext(), Home_Page.class);
        intent.putExtra("Id", getId);
        intent.putExtra("Member", getMember);
        intent.putExtra("UserName", getName);
        startActivity(intent);
    }


    public void payment(View view) {
        String key;
        if (idx == 1) {
            if (cardValidity.getText().toString().equals("") || cardPassword.getText().toString().equals("") || cardNum.getText().toString().equals("")) {
                Toast.makeText(Paying.this, "필수정보를 모두 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            key = CardCompanySpinner.getSelectedItem().toString() + "/" + cardNum.getText().toString() + "/" + cardValidity.getText().toString().replace("/", ":") + "/" + cardPassword.getText().toString();
            if (!creditCardInfo.contains(key)) {
                Toast.makeText(Paying.this, "결제실패 " + key + "   " + creditCardInfo.size(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), Home_Page.class);
                intent.putExtra("Id", getId);
                intent.putExtra("Member", true);
                intent.putExtra("UserName", getName);
                startActivity(intent);
                finish();

            }
        } else {
            key = PhoneCompanySpinner.getSelectedItem().toString() + "/" + inputPhoneNum.getText().toString();
            if (inputPhoneNum.getText().toString().equals("")) {
                Toast.makeText(Paying.this, "필수정보를 모두 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!confirm) {
                Toast.makeText(Paying.this, "인증이 필요합니다.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Intent gotoSuccess = new Intent(getApplicationContext(), Paying_success.class);
        gotoSuccess.putExtra("TicketList", getList);
        gotoSuccess.putExtra("Id", getId);
        gotoSuccess.putExtra("Member", getMember);
        gotoSuccess.putExtra("UserName", getName);
        gotoSuccess.putExtra("Key", key);
        gotoSuccess.putExtra("Card", idx == 1 ? true : false);
        startActivity(gotoSuccess);
    }

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Log.v("Payment", "Key : " + dataSnapshot.getKey().toString());
            //
            //
            String creditCardCompany;
            String creditCardNum;
            String availableTime;

            String Company;
            String phoneNum;
            String pw;
            if (dataSnapshot.getKey().toString().equals("CreditCard")) {
                creditCardInfo.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    creditCardCompany = snapshot.getKey().toString();
                    Log.v("Payment", "company : " + snapshot.getKey().toString());
                    for (DataSnapshot shot : snapshot.getChildren()) {
                        creditCardNum = shot.getKey().toString();
                        Log.v("Payment", "cardNum : " + shot.getKey().toString());

                        for (DataSnapshot sss : shot.getChildren()) {
                            availableTime = sss.getKey().toString();
                            pw = "";
                            Log.v("Payment", "avaliable : " + sss.getKey().toString());

                            for (DataSnapshot ss : sss.getChildren()) {
                                Log.v("Payment", "pw : " + ss.getKey().toString());
                                pw = ss.getKey().toString();
                            }

                            Log.v("Payment", creditCardCompany + "/" + creditCardNum + "/" + availableTime + "/" + pw);
                            creditCardInfo.add(creditCardCompany + "/" + creditCardNum + "/" + availableTime + "/" + pw);
                        }
                    }
                }
            } else {
                phoneInfo.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Company = snapshot.getKey().toString();
                    for (DataSnapshot shot : snapshot.getChildren()) {
                        phoneNum = shot.getKey().toString();
                        Random random = new Random();
                        int tempR = random.nextInt(10000);
                        Log.v("Payment", Company + "/" + phoneNum);
                        phoneInfo.put(Company + "/" + phoneNum, tempR + "");

                    }
                }
            }

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
