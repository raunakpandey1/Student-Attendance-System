package com.example.ternalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

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
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}