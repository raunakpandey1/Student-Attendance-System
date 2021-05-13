package com.example.ternalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SubjectYear extends AppCompatActivity {

    List<String> subjectList = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference yearRef;
    Spinner sub_spinner;
    Button btn_next1;
    ArrayAdapter<String> subAdapter;
    String Subject,Year, fMonth, tMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_year);
        Year = getIntent().getStringExtra("years");
        fMonth = getIntent().getStringExtra("fMonth");
        tMonth = getIntent().getStringExtra("tMonth");

        sub_spinner = findViewById(R.id.sub_spinner);
        btn_next1 = findViewById(R.id.btn_next1);
        subjectList.clear();
        subjectList.add("Select Subject");

        database = FirebaseDatabase.getInstance();
        yearRef = database.getReference("year");

        yearRef.child(Year).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    subjectList.add(dataSnapshot.getKey());
                }
                subAdapter = new ArrayAdapter<>(SubjectYear.this,android.R.layout.simple_spinner_dropdown_item, subjectList);
                sub_spinner.setAdapter(subAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sub_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Subject = parent.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btn_next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( Subject==null || Subject=="Select Subject"){
                    Toast.makeText(SubjectYear.this, "Please select a valid value...", Toast.LENGTH_SHORT).show();
                }else {
                    Intent MainIntent = new Intent(SubjectYear.this, StudMonthAttendance.class);
                    MainIntent.putExtra("subject", Subject);
                    MainIntent.putExtra("years", Year);
                    MainIntent.putExtra("fMonth", fMonth);
                    MainIntent.putExtra("tMonth", tMonth);
                    startActivity(MainIntent);
                }
            }
        });
    }
}