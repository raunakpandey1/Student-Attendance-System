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

public class studentSubjectSelect extends AppCompatActivity {

    List<String> subjectList = new ArrayList<>();
    List<String> monthList = new ArrayList<>();
    String Year, Subject, month, id;
    FirebaseDatabase database;
    DatabaseReference yearRef;
    ArrayAdapter<String> subAdapter,MonthAdapter;
    Spinner sub_spinner, Month_spinner;
    Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_subject_select);
        Year = getIntent().getStringExtra("Year");
        id = getIntent().getStringExtra("id");

        database = FirebaseDatabase.getInstance();
        yearRef = database.getReference("year");
        sub_spinner = findViewById(R.id.sub_spinner);
        btn_next = findViewById(R.id.btn_next);
        Month_spinner = findViewById(R.id.Month_spinner);

        monthList.add(0,"Select Month");
        monthList.add(1,"January");
        monthList.add(2,"February");
        monthList.add(3,"March");
        monthList.add(4,"April");
        monthList.add(5,"May");
        monthList.add(6,"June");
        monthList.add(7,"July");
        monthList.add(8,"August");
        monthList.add(9,"September");
        monthList.add(10,"October");
        monthList.add(11,"November");
        monthList.add(12,"December");

        MonthAdapter = new ArrayAdapter<>(studentSubjectSelect.this,android.R.layout.simple_spinner_dropdown_item, monthList);
        Month_spinner.setAdapter(MonthAdapter);
        Month_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = parent.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        yearRef.child(Year).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    subjectList.add(dataSnapshot.getKey());
                }
                subAdapter = new ArrayAdapter<>(studentSubjectSelect.this,android.R.layout.simple_spinner_dropdown_item, subjectList);
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

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( Subject==null){
                    Toast.makeText(studentSubjectSelect.this, "Please select a valid value...", Toast.LENGTH_SHORT).show();
                }else {
                    Intent MainIntent = new Intent(studentSubjectSelect.this, showAttendance.class);
                    MainIntent.putExtra("subject", Subject);
                    MainIntent.putExtra("years", Year);
                    MainIntent.putExtra("month", month);
                    MainIntent.putExtra("id", id);
                    startActivity(MainIntent);
                }
            }
        });

    }
}