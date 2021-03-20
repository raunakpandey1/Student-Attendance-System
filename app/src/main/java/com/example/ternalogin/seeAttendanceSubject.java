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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class seeAttendanceSubject extends AppCompatActivity {

    Spinner spinner;
    Button button;
    FirebaseDatabase database;
    DatabaseReference databaseRef;
    List<String> yearList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    String year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_attendance_subject);

        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference("year");

        spinner = findViewById(R.id.spinnerSub);
        button = findViewById(R.id.subject_button_see);

        RetrieveData();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(seeAttendanceSubject.this, studentSubjectSelect.class);
                MainIntent.putExtra("id", "1234");
                MainIntent.putExtra("Year",year);
                startActivity(MainIntent);
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
                        String subj = subSnapshot.getKey();
                        yearList.add(subj);
                    }
                    adapter = new ArrayAdapter<String>(seeAttendanceSubject.this,android.R.layout.simple_spinner_dropdown_item, yearList);
                    spinner.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}