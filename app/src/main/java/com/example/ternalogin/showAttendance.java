package com.example.ternalogin;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ternalogin.model.post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class showAttendance extends AppCompatActivity {

    TextView current_month,name, branch, email, div, roll, tpresent, ttotal, tpercentage, subject, present , total, percent;
    Spinner presentspinner, absentspinner;
    List<String> PresentList = new ArrayList<>();
    List<String> AbsentList = new ArrayList<>();
    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2;

    FirebaseDatabase database;
    DatabaseReference stdRef, subRef;

    String presen, tota, percen;
    String sPresent, sTotal;
    float fo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendance);
        name = findViewById(R.id.pname);
        branch = findViewById(R.id.pbranch);
        email = findViewById(R.id.pemail);
        div = findViewById(R.id.pdiv);
        roll = findViewById(R.id.proll);
        tpresent = findViewById(R.id.tpresent);
        ttotal = findViewById(R.id.ttotal);
        tpercentage = findViewById(R.id.percent);
        subject = findViewById(R.id.subject);
        present = findViewById(R.id.pre);
        total = findViewById(R.id.tot);
        percent = findViewById(R.id.percentage);
        presentspinner = findViewById(R.id.spinner1);
        absentspinner = findViewById(R.id.spinner2);
        current_month = findViewById(R.id.current_month);

       // id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String Subj = getIntent().getStringExtra("subject");
        final String Year = getIntent().getStringExtra("years");
        final String month = getIntent().getStringExtra("month");
        final String id = getIntent().getStringExtra("id");

        database = FirebaseDatabase.getInstance();
        stdRef = database.getReference("Students").child(id);
        subRef = database.getReference("year").child(Year).child(Subj).child(month).child(id);

        subject.setText(Subj.toUpperCase());
        current_month.setText(month + " (Current Month)");
        subRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sPresent = String.valueOf(snapshot.child("present").getChildrenCount());
                sTotal = String.valueOf(snapshot.child("total").getChildrenCount());
                fo = ((Float.parseFloat(sPresent)/Float.parseFloat(sTotal))*100);
                fo = (float) (Math.round(fo*100.0)/100.0);
                String sPercent = String.valueOf(fo);

                present.setText(sPresent);
                total.setText(sTotal);
                percent.setText(sPercent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        stdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    post Post = snapshot.getValue(post.class);
                    name.setText(Post.getName());
                    branch.setText(Post.getBranch());
                    email.setText(Post.getEmail());
                    div.setText(Post.getDiv());
                    roll.setText(Post.getRoll());

                    presen = String.valueOf(snapshot.child("attendance").getChildrenCount());
                    tota = String.valueOf(snapshot.child("tattendance").getChildrenCount());
                    float f = ((Float.parseFloat(presen)/Float.parseFloat(tota))*100);
                    f = (float) (Math.round(f*100.0)/100.0);
                    percen = String.valueOf(f);

                    tpresent.setText(presen);
                    ttotal.setText(tota);
                    tpercentage.setText(percen);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




       subRef.child("present").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    PresentList.clear();
                    PresentList.add("See Present List");
                    for (DataSnapshot preSnapshot : snapshot.getChildren()){
                        PresentList.add(preSnapshot.getValue().toString());
                    }
                    adapter1 = new ArrayAdapter<String>(showAttendance.this,android.R.layout.simple_spinner_dropdown_item, PresentList);
                    presentspinner.setAdapter(adapter1);
                }else{
                    PresentList.clear();
                    PresentList.add("See Present List");
                    adapter1 = new ArrayAdapter<String>(showAttendance.this,android.R.layout.simple_spinner_dropdown_item, PresentList);
                    presentspinner.setAdapter(adapter1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        subRef.child("absent").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    AbsentList.clear();
                    AbsentList.add("See Absent List");
                    for (DataSnapshot preSnapshot : snapshot.getChildren()){
                        AbsentList.add(preSnapshot.getValue().toString());
                    }
                    adapter2 = new ArrayAdapter<String>(showAttendance.this,android.R.layout.simple_spinner_dropdown_item, AbsentList);
                    absentspinner.setAdapter(adapter2);
                }else{
                    AbsentList.clear();
                    AbsentList.add("See Absent List");
                    adapter2 = new ArrayAdapter<String>(showAttendance.this,android.R.layout.simple_spinner_dropdown_item, AbsentList);
                    absentspinner.setAdapter(adapter2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}