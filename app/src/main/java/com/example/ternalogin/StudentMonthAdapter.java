package com.example.ternalogin;

import android.graphics.Color;
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
    public static List<monModel> defaulterList = new ArrayList<>();
    int[] preArr = new int[100];
    int[] totArr = new int[100];
    float f;

    public StudentMonthAdapter() {
    }
   /* public StudentMonthAdapter(List<monModel> studList) {
        this.studList = studList;
    } */
    public StudentMonthAdapter(List<monModel> studList, int[] preArr, int[] totArr) {
        this.studList = studList;
        this.preArr = preArr;
        this.totArr = totArr;
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

        String id = studList.get(position).getId();
        String name = studList.get(position).getName();
        String roll = studList.get(position).getRoll();
        int present = preArr[position];
        int total = totArr[position];
        f = (((float)present/(float)total)*100);
        f = (float) (Math.round(f*100.0)/100.0);
        monModel monmodel = new monModel(id ,name ,roll ,present ,total, f);
        defaulterList.add(monmodel);
        //String present = String.valueOf(studList.get(position).getPresent());
        //String total = String.valueOf(studList.get(position).getTotal());
        holder.setdata(name, roll, present, total, f);
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
        private void setdata(String name, String roll, int present, int total, float f){
            Name.setText(name);
            Roll.setText(roll);
            Present.setText(String.valueOf(present));
            Absent.setText(String.valueOf(total-present));
            Total.setText(String.valueOf(total));
            Total1.setText(String.valueOf(total));
            Percentages.setText(String.valueOf(f)+"%");
            if(f<75){
                Percentages.setTextColor(Color.parseColor("#FF0000"));
            }else{
                Percentages.setTextColor(Color.parseColor("#000000"));
            }

        }
    }
}
