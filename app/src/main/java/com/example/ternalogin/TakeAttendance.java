package com.example.ternalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class TakeAttendance extends AppCompatActivity {

    List<student> StudentList = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference dataRef, stdRef;
    //TextView Subview;
    Button submitButton;
    RecyclerView recyclerView;
    String datetime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
       // LinearLayoutManager LayoutManager = new LinearLayoutManager(TakeAttendance.this);
       // LayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //Subview = findViewById(R.id.sub_tv);

        submitButton = findViewById(R.id.submit_button);
        recyclerView = findViewById(R.id.at_recycler_view);
        StudentList = new ArrayList<>();
        String Sub = getIntent().getStringExtra("sub");
        //Subview.setText(Sub);

        database = FirebaseDatabase.getInstance();
        stdRef = FirebaseDatabase.getInstance().getReference("Students");
        dataRef = database.getReference("subjects").child(Sub);

        stdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StudentList.clear();
                if(snapshot.exists()){
                    for(DataSnapshot stdSnapshot : snapshot.getChildren()){

                        String name = (String) stdSnapshot.child("name").getValue();
                        String roll = (String) stdSnapshot.child("roll").getValue();
                        String id = (String) stdSnapshot.getKey();
                        StudentList.add(new student(name,roll,id));
                    }
                }

                takeattenAdapter attendAdapter = new takeattenAdapter(StudentList, TakeAttendance.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(TakeAttendance.this));
                attendAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(attendAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
                datetime = simpleDateFormat.format(calendar.getTime());

                String presentstudentID = "";
                for (int i = 0; i < takeattenAdapter.presentList.size(); i++) {
                    presentstudentID = takeattenAdapter.presentList.get(i);
                    dataRef.child(presentstudentID).child("present").push().setValue(datetime);
                    dataRef.child(presentstudentID).child("total").push().setValue(datetime);
                    stdRef.child(presentstudentID).child("attendance").push().setValue(datetime);
                    stdRef.child(presentstudentID).child("tattendance").push().setValue(datetime);
                }

                String absentstudentID = "";
                for (int i = 0; i < takeattenAdapter.absentList.size(); i++) {
                    absentstudentID = takeattenAdapter.absentList.get(i);
                    dataRef.child(absentstudentID).child("absent").push().setValue(datetime);
                    dataRef.child(absentstudentID).child("total").push().setValue(datetime);
                    stdRef.child(absentstudentID).child("tattendance").push().setValue(datetime);
                }

                takeattenAdapter.presentList.clear();
                takeattenAdapter.absentList.clear();

                finish();
            }
        });


    }
}