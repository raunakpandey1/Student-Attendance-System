package com.example.ternalogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TakeAttendance extends AppCompatActivity {

    List<String> StudentList;
    FirebaseDatabase database;
    DatabaseReference dataRef;
    TextView Subview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        Subview = findViewById(R.id.sub_tv);


        StudentList = new ArrayList<>();
        String Sub = getIntent().getStringExtra("sub");
        Subview.setText(Sub);

        database = FirebaseDatabase.getInstance();
        dataRef = database.getReference("Students").child(Sub);


    }
}