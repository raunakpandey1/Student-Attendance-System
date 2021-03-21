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

public class TASelectYear extends AppCompatActivity {

    Spinner spinner;
    Button button;
    FirebaseDatabase database;
    DatabaseReference databaseRef;
    List<String> SubjectList = new ArrayList<>();;
    ArrayAdapter<String> adapter;
    String year = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_a_select_year);

        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference("subjects");

        spinner = findViewById(R.id.spinner);
        button = findViewById(R.id.subject_button);

        RetrieveData();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = parent.getSelectedItem().toString();
                Toast.makeText(TASelectYear.this, year, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent MainIntent = new Intent(TASelectYear.this, AttendanceSubject.class);
                MainIntent.putExtra("year",year);
                startActivity(MainIntent);

            }
        });
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
                    adapter = new ArrayAdapter<String>(TASelectYear.this,android.R.layout.simple_spinner_dropdown_item, SubjectList);
                    spinner.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}