package com.example.ternalogin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ternalogin.model.monModel;
import com.example.ternalogin.model.post;

import java.util.ArrayList;
import java.util.List;

public class StudentMonthAdapter extends RecyclerView.Adapter<StudentMonthAdapter.StudentMonthViewholder> {

    List<monModel> studList = new ArrayList<>();

    public StudentMonthAdapter() {
    }
    public StudentMonthAdapter(List<monModel> studList) {
        this.studList = studList;
    }


    @NonNull
    @Override
    public StudentMonthViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.defaulterview,parent,false);
        return new StudentMonthAdapter.StudentMonthViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentMonthViewholder holder, int position) {

        String name = studList.get(position).getName();
        String roll = studList.get(position).getRoll();
        long present = studList.get(position).getPresent();
        long total = studList.get(position).getTotal();
        holder.setdata(name, roll, present, total);
    }

    @Override
    public int getItemCount() {
        return studList.size();
    }

    class StudentMonthViewholder extends RecyclerView.ViewHolder{

        TextView Name, Roll, Present, Absent, Total, Total1, Percentages;
        public StudentMonthViewholder(@NonNull View itemView) {
            super(itemView);
            Name = (TextView) itemView.findViewById(R.id.studentName);
            Roll = (TextView) itemView.findViewById(R.id.rollNo);
            Present = (TextView) itemView.findViewById(R.id.present);
            Absent = (TextView) itemView.findViewById(R.id.absent);
            Total = (TextView) itemView.findViewById(R.id.total);
            Total1 = (TextView) itemView.findViewById(R.id.total1);
            Percentages = (TextView) itemView.findViewById(R.id.percentages);
        }
        private void setdata(String name, String roll, long present, long total){
            Name.setText(name);
            Roll.setText(roll);
            Present.setText(String.valueOf(present));
            Absent.setText(String.valueOf(total-present));
            Total.setText(String.valueOf(total));
            Total1.setText(String.valueOf(total));
            Percentages.setText("");

        }
    }
}
