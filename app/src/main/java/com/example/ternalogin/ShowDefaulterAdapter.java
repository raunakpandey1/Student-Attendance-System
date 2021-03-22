package com.example.ternalogin;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ternalogin.adapter.StudentMonthAdapter;
import com.example.ternalogin.model.monModel;

import java.util.ArrayList;
import java.util.List;

public class ShowDefaulterAdapter extends RecyclerView.Adapter<ShowDefaulterAdapter.ShowDefaulterViewholder> {

    List<monModel> defaulterList = new ArrayList<>();

    public ShowDefaulterAdapter() {
    }

    public ShowDefaulterAdapter(List<monModel> defaulterList) {
        this.defaulterList = defaulterList;
    }

    @NonNull
    @Override
    public ShowDefaulterViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.defaulterview,parent,false);
        return new ShowDefaulterAdapter.ShowDefaulterViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowDefaulterViewholder holder, int position) {
      //  String id = defaulterList.get(position).getId();
        String name = defaulterList.get(position).getName();
        String roll = defaulterList.get(position).getRoll();
        int present = (int)defaulterList.get(position).getPresent();
        int total = (int)defaulterList.get(position).getTotal();
        float f = defaulterList.get(position).getPercentage();
        holder.setdata(name, roll, present, total, f);
    }

    @Override
    public int getItemCount() {
        return defaulterList.size();
    }

    class ShowDefaulterViewholder extends RecyclerView.ViewHolder{

        TextView Name, Roll, Present, Absent, Total, Total1, Percentages;
        public ShowDefaulterViewholder(@NonNull View itemView) {
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
            Percentages.setTextColor(Color.parseColor("#FF0000"));

        }
    }
}
