package com.example.ternalogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewholder> {

    List<post> postList = new ArrayList<>();
    Context context;
    public PostsAdapter() {
    }

    public PostsAdapter(List<post> postList, Context context) {
        this.postList = postList;
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.cdview,parent,false);
        return new PostsAdapter.PostViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewholder holder, int position) {
        String name = postList.get(position).getName();
        String branch = postList.get(position).getBranch();
        String email = postList.get(position).getEmail();
        String div = postList.get(position).getDiv();
        String roll = postList.get(position).getRoll();
        holder.setdata(name, branch, email, div, roll);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class PostViewholder extends RecyclerView.ViewHolder {
        TextView name, branch, email, div, roll;
      public PostViewholder(@NonNull View itemView) {
          super(itemView);
          name = (TextView) itemView.findViewById(R.id.stdName);
          branch = (TextView) itemView.findViewById(R.id.stdBranch);
          email = (TextView) itemView.findViewById(R.id.stdEmail);
          div = (TextView) itemView.findViewById(R.id.stdDiv);
          roll = (TextView) itemView.findViewById(R.id.stdRoll);
      }
      private void setdata(String Name, String Branch, String Email, String Div, String Roll){
          name.setText(Name);
          branch.setText(Branch);
          email.setText(Email);
          div.setText(Div);
          roll.setText(Roll);

      }
  }
}
