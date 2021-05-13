package com.example.ternalogin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ternalogin.model.DocumentDetail;
import com.example.ternalogin.R;
import com.example.ternalogin.RecyclerViewClickInterface;

import java.util.ArrayList;
import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ShowDocViewholder>{

    List<DocumentDetail> docList = new ArrayList<>();
    RecyclerViewClickInterface recyclerViewClickInterface;

    public DocumentAdapter() {
    }

    public DocumentAdapter(List<DocumentDetail> docList, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.docList = docList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public ShowDocViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.document_view,parent,false);
        return new DocumentAdapter.ShowDocViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowDocViewholder holder, int position) {
        String desc = docList.get(position).getDescription();
        String title = docList.get(position).getTitle();
        String date = docList.get(position).getDate();
        String url = docList.get(position).getUrl();

        holder.setdata(title,desc,url,date);

    }

    @Override
    public int getItemCount() {
        return docList.size();
    }

    class ShowDocViewholder extends RecyclerView.ViewHolder{

        TextView Title, Description, Date;
        ImageView downDoc;
        String urls;
        public ShowDocViewholder(@NonNull View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.doc_title);
            Description = (TextView) itemView.findViewById(R.id.doc_description);
            Date = (TextView) itemView.findViewById(R.id.doc_date);
            downDoc = (ImageView) itemView.findViewById(R.id.downDoc);

            downDoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });
        }
        private void setdata(String title, String description, String url, String date){
            Title.setText(title);
            Description.setText(description);
            Date.setText(date);
            urls = url;
        }

    }
}
