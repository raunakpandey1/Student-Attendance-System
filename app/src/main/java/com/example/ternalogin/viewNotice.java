package com.example.ternalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ternalogin.adapter.DocumentAdapter;
import com.example.ternalogin.model.DocumentDetail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class viewNotice extends AppCompatActivity implements RecyclerViewClickInterface {

    List<DocumentDetail> docList = new ArrayList<>();
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notice);
        toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Notices");

        recyclerView = findViewById(R.id.recycler_view);

        databaseReference = FirebaseDatabase.getInstance().getReference("DocumentPDF");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DocumentDetail documentDetail = postSnapshot.getValue(DocumentDetail.class);
                    docList.add(documentDetail);
                }
                DocumentAdapter docAdapter = new DocumentAdapter(docList, viewNotice.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(viewNotice.this);
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                recyclerView.setLayoutManager(linearLayoutManager);
                docAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(docAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(docList.get(position).getUrl()));
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(int position) {

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}