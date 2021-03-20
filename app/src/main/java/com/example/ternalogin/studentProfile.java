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
import android.widget.Toast;

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
    List<String> yearList = new ArrayList<>();;
    ArrayAdapter<String> adapter;
    private TextView Sname,Sdept,Sroll,Semail;
    String Year = "2020";
    String StudentID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        mAuth = FirebaseAuth.getInstance();
        String CurrentUserId = mAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        stdID = database.getReference("studentID").child(CurrentUserId);
        databaseRef = database.getReference("year");
        stdRef = database.getReference().child("Students");

        imgbutton1 = findViewById(R.id.fac_toolbar_logout);
        SSbutton = findViewById(R.id.SSButton);
        spinner = findViewById(R.id.spinnerSub);
        Sname = findViewById(R.id.teach_name);
        Sdept = findViewById(R.id.teach_dept);
        Sroll = findViewById(R.id.teach_sub);
        Semail = findViewById(R.id.teach_email);

        RetrieveData();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Year = yearList.get(position);
                Toast.makeText(studentProfile.this, Year, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getStudentId();

        SSbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(studentProfile.this, studentSubjectSelect.class);
                MainIntent.putExtra("id", StudentID);
                MainIntent.putExtra("Year", Year);
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
                finish();
            }
        });





    }

    public void getStudentId(){
        stdID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StudentID = snapshot.getValue().toString();
                stdRef.child(StudentID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.child("name").getValue().toString();
                        String dept = snapshot.child("branch").getValue().toString();
                        String div = snapshot.child("div").getValue().toString();
                        String roll = snapshot.child("roll").getValue().toString();
                        String email = snapshot.child("email").getValue().toString();
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void RetrieveData() {
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    yearList.clear();
                    for(DataSnapshot subSnapshot: snapshot.getChildren()){
                        String years = subSnapshot.getKey();
                        yearList.add(years);
                    }
                    adapter = new ArrayAdapter<String>(studentProfile.this,android.R.layout.simple_spinner_dropdown_item, yearList);
                    spinner.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}