package com.example.ternalogin;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

    class PostViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, branch, email, div, roll;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.stdName);
            branch = (TextView) itemView.findViewById(R.id.stdBranch);
            email = (TextView) itemView.findViewById(R.id.stdEmail);
            div = (TextView) itemView.findViewById(R.id.stdDiv);
            roll = (TextView) itemView.findViewById(R.id.stdRoll);
        }
    }

