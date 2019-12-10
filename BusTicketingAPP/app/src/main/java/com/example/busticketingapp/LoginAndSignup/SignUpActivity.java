package com.example.busticketingapp.LoginAndSignup;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busticketingapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText nameValue;
    EditText passwordValue;
    EditText passwordValueSave;
    EditText emailValue;
    EditText personNumValue;
    EditText phoneNumValue;
    Button register;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("Member").child(emailValue+"");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_sign_up);
    }

    public void signUpInfo(View view) {
        nameValue = (EditText) findViewById(R.id.nameValue);
        passwordValue = (EditText) findViewById(R.id.passwordValue);
        passwordValueSave = (EditText) findViewById(R.id.passwordValueSave);
        emailValue = (EditText) findViewById(R.id.emailValue);
        personNumValue = (EditText) findViewById(R.id.personNumValue);
        phoneNumValue = personNumValue = (EditText) findViewById(R.id.phoneNumValue);
        String passwordVal = passwordValue.getText().toString();
        if (!passwordValue.getText().toString().equals(passwordValueSave.getText().toString())){
            Toast.makeText(this, "비밀번호가 일치하지 않습니다. 다시 입력하세요", Toast.LENGTH_LONG).show();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    passwordValue.setText(null);
                    passwordValueSave.setText(null);
                }
            });

        }
        else{
            Log.v("Subin","Register Accept");

            myRef.child("Friend").setValue("");
            myRef.child("Notification").setValue("");
            myRef.child("Ticket").setValue("");
            myRef.child("Cart").setValue("");
            myRef.child("Information").child("PhoneNumber").setValue(phoneNumValue+"");
            myRef.child("Information").child("Name").setValue(nameValue+"");
            myRef.child("Information").child("Password").setValue(passwordValue+"");
            myRef.child("Information").child("Identification").setValue(personNumValue+"");

        }
    }

}
