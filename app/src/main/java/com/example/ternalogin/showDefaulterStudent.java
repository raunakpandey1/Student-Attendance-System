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

public class showDefaulterStudent extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;
    Button button_def;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_defaulter_student);

        button_def = findViewById(R.id.button_def);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Defaulter Students");


        ShowDefaulterAdapter showDefaulterAdapter = new ShowDefaulterAdapter(StudMonthAttendance.defaulterList);
        recyclerView.setLayoutManager(new LinearLayoutManager(showDefaulterStudent.this));
        showDefaulterAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(showDefaulterAdapter);

        button_def.setOnClickListener(new View.OnClickListener() {
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
                            //
                        }
                    }
                });
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