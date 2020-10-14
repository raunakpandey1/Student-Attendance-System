package com.example.ternalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.ternalogin.model.post;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
    String Subjec;

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

        Subjec = getIntent().getStringExtra("sub");

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
         MainIntent.putExtra("Sub",Subjec);
         startActivity(MainIntent);
    }

    @Override
    public void onLongItemClick(int position) {

    }




       /*
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
    }   */
}