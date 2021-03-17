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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MonthAttendance extends AppCompatActivity {

    List<String> yearList = new ArrayList<>();
    List<String> fMonthList = new ArrayList<>();
    List<String> tMonthList = new ArrayList<>();

    FirebaseDatabase database;
    DatabaseReference yearRef;

    Button btn_next;
    Spinner year_spinner, fMonth_spinner, tMonth_spinner;
    ArrayAdapter<String> yearAdapter, fMonthAdapter, tMonthAdapter;
    String Year, fMonth, tMonth;
    int f,t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_attendance);

        year_spinner = findViewById(R.id.year_spinner);
        fMonth_spinner = findViewById(R.id.fMonth_spinner);
        tMonth_spinner = findViewById(R.id.tMonth_spinner);
        btn_next = findViewById(R.id.btn_next);

        yearList.clear();
        fMonthList.clear();
        tMonthList.clear();

        yearList.add("Select Year");

        database = FirebaseDatabase.getInstance();
        yearRef = database.getReference("year");

        yearRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    yearList.add(dataSnapshot.getKey());
                }
                yearAdapter = new ArrayAdapter<>(MonthAttendance.this,android.R.layout.simple_spinner_dropdown_item, yearList);
                year_spinner.setAdapter(yearAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fMonthList.add(0, "Month From");
        fMonthList.add(1,"January");
        fMonthList.add(2, "February");
        fMonthList.add(3, "March");
        fMonthList.add(4,"April");
        fMonthList.add(5,"May");
        fMonthList.add(6,"June");
        fMonthList.add(7,"July");
        fMonthList.add(8,"August");
        fMonthList.add(9,"September");
        fMonthList.add(10,"October");
        fMonthList.add(11,"November");
        fMonthList.add(12,"December");

        tMonthList.add(0, "Month To");
        tMonthList.add(1,"January");
        tMonthList.add(2, "February");
        tMonthList.add(3, "March");
        tMonthList.add(4,"April");
        tMonthList.add(5,"May");
        tMonthList.add(6,"June");
        tMonthList.add(7,"July");
        tMonthList.add(8,"August");
        tMonthList.add(9,"September");
        tMonthList.add(10,"October");
        tMonthList.add(11,"November");
        tMonthList.add(12,"December");


        year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Year = parent.getSelectedItem().toString();
                Toast.makeText(MonthAttendance.this, Year, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        fMonthAdapter = new ArrayAdapter<>(MonthAttendance.this,android.R.layout.simple_spinner_dropdown_item, fMonthList);
        fMonth_spinner.setAdapter(fMonthAdapter);
        fMonth_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fMonth = parent.getSelectedItem().toString();
                f = position;
                Toast.makeText(MonthAttendance.this, fMonth, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        tMonthAdapter = new ArrayAdapter<>(MonthAttendance.this,android.R.layout.simple_spinner_dropdown_item, tMonthList);
        tMonth_spinner.setAdapter(tMonthAdapter);
        tMonth_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tMonth = parent.getSelectedItem().toString();
                t = position;
                Toast.makeText(MonthAttendance.this, tMonth, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( Year==null || fMonth==null || tMonth==null || Year=="Select Year" || fMonth=="Select Month" || tMonth=="Select Month" || f>t){
                    Toast.makeText(MonthAttendance.this, "Please select a valid value...", Toast.LENGTH_SHORT).show();
                }else {
                    Intent MainIntent = new Intent(MonthAttendance.this, SubjectYear.class);
                    MainIntent.putExtra("years", Year);
                    MainIntent.putExtra("fMonth", String.valueOf(f));
                    MainIntent.putExtra("tMonth", String.valueOf(t));
                    startActivity(MainIntent);
                }
            }
        });
    }
}