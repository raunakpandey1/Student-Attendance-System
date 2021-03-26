package com.example.ternalogin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ternalogin.adapter.takeattenAdapter;
import com.example.ternalogin.model.msgModel;

import java.util.ArrayList;
import java.util.List;

public class ShowMessageAdapter extends RecyclerView.Adapter<ShowMessageAdapter.ShowMessageViewholder> {

    List<msgModel> msgList = new ArrayList<>();
    public ShowMessageAdapter() {
    }

    public ShowMessageAdapter(List<msgModel> msgList) {
        this.msgList = msgList;
    }

    @NonNull
    @Override
    public ShowMessageViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.message_view,parent,false);
        return new ShowMessageAdapter.ShowMessageViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowMessageViewholder holder, int position) {
        String desc = msgList.get(position).getDescription();
        String subject = msgList.get(position).getSubject();
        String faculty = msgList.get(position).getFaculty();
        String percentage = msgList.get(position).getPercent();
        String monthRange = msgList.get(position).getDateRange();
        String date = msgList.get(position).getDate();

        holder.setdata(desc, subject, faculty, percentage, monthRange, date);
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    class ShowMessageViewholder extends RecyclerView.ViewHolder{

        TextView description, subject, monthRange, faculty, percentage, date;
        public ShowMessageViewholder(@NonNull View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.description);
            subject = (TextView) itemView.findViewById(R.id.subjectName);
            monthRange = (TextView) itemView.findViewById(R.id.monthRange);
            faculty = (TextView) itemView.findViewById(R.id.facultyName);
            percentage = (TextView) itemView.findViewById(R.id.msgPercent);
            date = (TextView) itemView.findViewById(R.id.date);
        }
        private void setdata(String desc, String subjectName, String Faculty, String Percentage, String mRange, String Date){
            description.setText(desc);
            subject.setText(subjectName);
            monthRange.setText(mRange);
            faculty.setText(Faculty);
            percentage.setText(Percentage+"%");
            date.setText(Date);

        }
    }
}
