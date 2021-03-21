package com.example.ternalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ternalogin.adapter.takeattenAdapter;
import com.example.ternalogin.model.student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TakeAttendance extends AppCompatActivity {

    List<student> StudentList = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference dataRef, stdRef;
    //TextView Subview;
    Button submitButton;
    RecyclerView recyclerView;
    Toolbar toolbar;
    String datetime = "";
    String year = "", Year;
    String month = "";
    String Sub;

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
        Sub = getIntent().getStringExtra("sub");
        Year = getIntent().getStringExtra("year");
        toolbar = findViewById(R.id.at_mainToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Attendance");

        database = FirebaseDatabase.getInstance();
        stdRef = FirebaseDatabase.getInstance().getReference("Students");


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

                final AlertDialog dialog=new AlertDialog.Builder(TakeAttendance.this).create();
                View view= LayoutInflater.from(TakeAttendance.this).inflate(R.layout.attendancepopup,null);
                TextView total,present,absent;
                Button cancleBtn, confirmBtn;
                total=view.findViewById(R.id.TotalStudentTV);
                present=view.findViewById(R.id.presentStudentTV);
                absent=view.findViewById(R.id.absentStudentTV);
                cancleBtn=view.findViewById(R.id.canclebtn);
                confirmBtn=view.findViewById(R.id.confirmbtn);
                total.setText(Integer.toString(StudentList.size()));
                present.setText(Integer.toString(takeattenAdapter.presentList.size()));
                absent.setText(Integer.toString(takeattenAdapter.absentList.size()));
                dialog.setCancelable(true);

                dialog.setView(view);


                cancleBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                confirmBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //last
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a, EEEE");
                        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
                        datetime = simpleDateFormat.format(calendar.getTime());
                        year = yearFormat.format(calendar.getTime());
                        month = monthFormat.format(calendar.getTime());
                        dataRef = database.getReference("year").child(Year).child(Sub).child(month);

                        String presentstudentID = "";
                        for (int i = 0; i < takeattenAdapter.presentList.size(); i++) {
                            presentstudentID = takeattenAdapter.presentList.get(i);
                            final String finalPresentstudentID = presentstudentID;
                            dataRef.child(presentstudentID).child("present").push().setValue(datetime);
                            dataRef.child(presentstudentID).child("total").push().setValue(datetime);
                            stdRef.child(presentstudentID).child("attendance").push().setValue(datetime);
                            stdRef.child(presentstudentID).child("tattendance").push().setValue(datetime);
                            dataRef.child(presentstudentID).child("id").setValue(presentstudentID);

                            stdRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    dataRef.child(finalPresentstudentID).child("name").setValue(snapshot.child(finalPresentstudentID).child("name").getValue());
                                    dataRef.child(finalPresentstudentID).child("roll").setValue(snapshot.child(finalPresentstudentID).child("roll").getValue());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                        String absentstudentID = "";
                        for (int i = 0; i < takeattenAdapter.absentList.size(); i++) {
                            absentstudentID = takeattenAdapter.absentList.get(i);
                            final String finalAbsentstudentID = absentstudentID;
                            dataRef.child(absentstudentID).child("absent").push().setValue(datetime);
                            dataRef.child(absentstudentID).child("total").push().setValue(datetime);
                            stdRef.child(absentstudentID).child("tattendance").push().setValue(datetime);
                            dataRef.child(presentstudentID).child("id").setValue(absentstudentID);

                            stdRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    dataRef.child(finalAbsentstudentID).child("name").setValue(snapshot.child(finalAbsentstudentID).child("name").getValue());
                                    dataRef.child(finalAbsentstudentID).child("roll").setValue(snapshot.child(finalAbsentstudentID).child("roll").getValue());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }


                        takeattenAdapter.presentList.clear();
                        takeattenAdapter.absentList.clear();

                        finish();

                    }
                });

                dialog.show();

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        takeattenAdapter.presentList.clear();
        takeattenAdapter.absentList.clear();
    }
}