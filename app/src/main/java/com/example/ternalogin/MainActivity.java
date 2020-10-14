package com.example.ternalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button TAbutton, SSbutton;
    private TextView tname,tdept,tsub,temail;
    private ImageButton imgbutton1;
    private FirebaseAuth mAuth;
    private DatabaseReference dataRef;
    String CurrentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        CurrentUserId = mAuth.getCurrentUser().getUid();
        dataRef = FirebaseDatabase.getInstance().getReference("Faculty").child(CurrentUserId);


        TAbutton = findViewById(R.id.TAButton);
        SSbutton = findViewById(R.id.SSButton);
        imgbutton1 = findViewById(R.id.fac_toolbar_logout);
        tname = findViewById(R.id.teach_name);
        tdept = findViewById(R.id.teach_dept);
        tsub = findViewById(R.id.teach_sub);
        temail = findViewById(R.id.teach_email);

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString().toUpperCase();
                    String dept = snapshot.child("department").getValue().toString();
                    String sub = snapshot.child("subject").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();

                        tname.setText(name);
                        tdept.setText(dept);
                        tsub.setText(sub);
                        temail.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imgbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                SendUserToLoginActivity();
            }

            private void SendUserToLoginActivity() {
                Intent LoginIntent = new Intent(MainActivity.this, Login_Activity.class);
                LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(LoginIntent);
                overridePendingTransition(0,0);
                finish();
            }
        });


        TAbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(MainActivity.this, AttendanceSubject.class);
                startActivity(MainIntent);
            }
        });

        SSbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(MainActivity.this, seeAttendanceSubject.class);
                startActivity(MainIntent);
            }
        });

    }
}