package com.example.ternalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;

import com.example.ternalogin.adapter.PostsAdapter;
import com.example.ternalogin.model.post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Posts extends AppCompatActivity implements RecyclerViewClickInterface{


    RecyclerView recyclerView;
   // FirebaseRecyclerAdapter<post, PostViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference dataRef;
    private FirebaseAuth mAuth;
    Toolbar toolbar;
    ImageButton imgbutton;

    List<post> PostList = new ArrayList<>();
    String Subjec, Year, month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imgbutton = findViewById(R.id.toolbar_logout);
        toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Students");

        Subjec = getIntent().getStringExtra("Subject");
        Year = getIntent().getStringExtra("Years");

        //database
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dataRef = database.getReference("Students");


        showList();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
        month = monthFormat.format(calendar.getTime());

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
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    for(DataSnapshot StuSnapshot: snapshot.getChildren()){
                        post Post = StuSnapshot.getValue(post.class);
                        PostList.add(Post);
                    }
                }
                PostsAdapter postsAdapter = new PostsAdapter(PostList, Posts.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(Posts.this));
                postsAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(postsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onItemClick(int position) {

         Intent MainIntent = new Intent(Posts.this, showAttendance.class);
         MainIntent.putExtra("id",PostList.get(position).getId());
         MainIntent.putExtra("subject",Subjec);
         MainIntent.putExtra("years",Year);
         MainIntent.putExtra("month", month);
         startActivity(MainIntent);
    }

    @Override
    public void onLongItemClick(int position) {

    }

}