package com.example.ternalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ternalogin.adapter.StudentMonthAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class showDefaulterStudent extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;
    Button send_msg;
    String Subject, Year, faculty;
    FirebaseDatabase database;
    DatabaseReference subRef, msgRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_defaulter_student);

        send_msg = findViewById(R.id.send_msg);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Defaulter Students");
        Subject = getIntent().getStringExtra("subject");
        Year = getIntent().getStringExtra("year");

        database = FirebaseDatabase.getInstance();
        subRef = database.getReference("subjects").child(Year).child(Subject);
        msgRef = database.getReference("Message");
        subRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                faculty = snapshot.child("teacher").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ShowDefaulterAdapter showDefaulterAdapter = new ShowDefaulterAdapter(StudMonthAttendance.defaulterList);
        recyclerView.setLayoutManager(new LinearLayoutManager(showDefaulterStudent.this));
        showDefaulterAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(showDefaulterAdapter);

        send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog=new AlertDialog.Builder(showDefaulterStudent.this).create();
                View view= LayoutInflater.from(showDefaulterStudent.this).inflate(R.layout.messagepopup,null);
                final EditText messageHere;
                Button cancelBtn, confirmBtn;
                messageHere = findViewById(R.id.messageHere);
                cancelBtn=view.findViewById(R.id.canclebtn);
                confirmBtn=view.findViewById(R.id.confirmbtn);
                dialog.setCancelable(true);
                dialog.setView(view);

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                confirmBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Message = messageHere.getText().toString();
                        if(TextUtils.isEmpty(Message)){
                            Toast.makeText(showDefaulterStudent.this, "No message entered...", Toast.LENGTH_SHORT).show();
                        }else{
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                            SimpleDateFormat yearFormat = new SimpleDateFormat("dd-MM-yyyy");
                            String dateFormat = simpleDateFormat.format(calendar.getTime());
                            String keyDate = yearFormat.format(calendar.getTime());

                            for(int i=0;i<StudMonthAttendance.defaulterList.size();i++){
                                String percent = String.valueOf(StudMonthAttendance.defaulterList.get(i).getPercentage());
                                msgRef.child(StudMonthAttendance.defaulterList.get(i).getId()).child(keyDate).push().setValue(Message);
                                msgRef.child(StudMonthAttendance.defaulterList.get(i).getId()).child(keyDate).push().setValue(Subject);
                                msgRef.child(StudMonthAttendance.defaulterList.get(i).getId()).child(keyDate).push().setValue(dateFormat);
                                msgRef.child(StudMonthAttendance.defaulterList.get(i).getId()).child(keyDate).push().setValue(percent);
                                msgRef.child(StudMonthAttendance.defaulterList.get(i).getId()).child(keyDate).push().setValue(faculty);
                            }
                        }
                    }
                });
                dialog.show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}