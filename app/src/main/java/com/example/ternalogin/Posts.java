package com.example.ternalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Posts extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<post, PostViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference dataRef;
    private FirebaseAuth mAuth;
    ImageButton imgbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imgbutton = findViewById(R.id.toolbar_logout);

        //database
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dataRef = database.getReference("Students");


       showList();

       imgbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mAuth.signOut();
               SendUserToLoginActivity();

           }

           private void SendUserToLoginActivity() {
               Intent LoginIntent = new Intent(Posts.this, Login_Activity.class);
               LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
               startActivity(LoginIntent);
               fileList();
           }
       });
    }

    private void showList(){

        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<post>()
                .setQuery(dataRef, post.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<post, PostViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i, @NonNull post post) {
                postViewHolder.name.setText(post.getName());
                postViewHolder.branch.setText(post.getBranch());
                postViewHolder.email.setText(post.getEmail());
                postViewHolder.div.setText(post.getDiv());
                postViewHolder.roll.setText(post.getRoll());
                postViewHolder.attendance.setText(String.valueOf(post.getAttendance()));
                postViewHolder.tattendance.setText(String.valueOf(post.getTattendance()));
            }

            @NonNull
            @Override
            public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cdview, parent, false);
                return new PostViewHolder(view);
            }
        };

        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}