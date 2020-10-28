package com.example.ternalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class studentProfile extends AppCompatActivity {

    private ImageButton imgbutton1;
    private FirebaseAuth mAuth;
    private Button SSbutton;
    Spinner spinner;
    FirebaseDatabase database;
    DatabaseReference stdRef, stdID, databaseRef;
    List<String> SubjectList = new ArrayList<>();;
    ArrayAdapter<String> adapter;
    private TextView Sname,Sdept,Sroll,Semail;
    String Subject = "Graphics";
    String StudentID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        mAuth = FirebaseAuth.getInstance();
        String CurrentUserId = mAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        stdID = database.getReference("studentID").child(CurrentUserId);
        databaseRef = database.getReference("subjects");


        imgbutton1 = findViewById(R.id.fac_toolbar_logout);
        SSbutton = findViewById(R.id.SSButton);
        spinner = findViewById(R.id.spinnerSub);
        Sname = findViewById(R.id.teach_name);
        Sdept = findViewById(R.id.teach_dept);
        Sroll = findViewById(R.id.teach_sub);
        Semail = findViewById(R.id.teach_email);


        stdID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    StudentID = snapshot.getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



        RetrieveData();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Subject = SubjectList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        SSbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(studentProfile.this, showAttendance.class);
                MainIntent.putExtra("id", StudentID);
                MainIntent.putExtra("Sub", Subject);
                startActivity(MainIntent);
            }
        });



        imgbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                SendUserToLoginActivity();
            }

            private void SendUserToLoginActivity() {
                Intent LoginIntent = new Intent(studentProfile.this, Login_Activity.class);
                LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(LoginIntent);
                fileList();
            }
        });

        if(StudentID!=null){
            stdRef = database.getReference().child("Students").child(StudentID);
            stdRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String name = snapshot.child("name").getValue().toString();
                    String dept = snapshot.child("branch").getValue().toString();
                    String div = snapshot.child("div").getValue().toString();
                    String roll = snapshot.child("roll").getValue().toString();
                    String email = snapshot.child(StudentID).child("email").getValue().toString();
                    String sub = div+roll;
                    Sname.setText(name);
                    Sdept.setText(dept);
                    Sroll.setText(sub);
                    Semail.setText(email);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }

    private void RetrieveData() {
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    SubjectList.clear();
                    for(DataSnapshot subSnapshot: snapshot.getChildren()){
                        String subj = subSnapshot.getKey();
                        SubjectList.add(subj);
                    }
                    adapter = new ArrayAdapter<String>(studentProfile.this,android.R.layout.simple_spinner_dropdown_item, SubjectList);
                    spinner.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}