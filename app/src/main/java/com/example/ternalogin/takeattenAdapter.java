package com.example.ternalogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ternalogin.model.student;

import java.util.ArrayList;
import java.util.List;

public class takeattenAdapter extends RecyclerView.Adapter<takeattenAdapter.takeattenViewholder> {

    private List<student> StudentList = new ArrayList<>();
    private Context context;
    public static List<String> presentList = new ArrayList<>();
    public static List<String> absentList = new ArrayList<>();
    private final boolean[] mcheckedStateA;
    private final boolean[] mcheckedStateB;

    public takeattenAdapter(List<student> studentList, Context context) {
        StudentList = studentList;
        this.context = context;
        mcheckedStateA = new boolean[studentList.size()];
        mcheckedStateB = new boolean[studentList.size()];
    }

    @NonNull
    @Override
    public takeattenViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.take_attendancev,parent,false);
        return new takeattenViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final takeattenViewholder holder, final int position) {
        String name = StudentList.get(position).getName();
        String roll = StudentList.get(position).getRoll();
        holder.setdata(name,roll);
        holder.preButton.setChecked(false);
        holder.abButton.setChecked(false);

        if(mcheckedStateA[position]){
            holder.preButton.setChecked(true);
        }else{
            holder.preButton.setChecked(false);
        }
        if(mcheckedStateB[position]){
            holder.abButton.setChecked(true);
        }else{
            holder.abButton.setChecked(false);
        }

        holder.preButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mcheckedStateA[position] = false;
                    if(!presentList.contains(StudentList.get(holder.getAdapterPosition()).getId())){
                        presentList.add(StudentList.get(holder.getAdapterPosition()).getId());
                    }
                }else{
                    mcheckedStateA[position] = true;
                    presentList.remove(StudentList.get(holder.getAdapterPosition()).getId());
                }
            }
        });

        holder.abButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mcheckedStateB[position] = false;
                    if(!absentList.contains(StudentList.get(holder.getAdapterPosition()).getId())) {
                        absentList.add(StudentList.get(holder.getAdapterPosition()).getId());
                    }
                }else{
                    mcheckedStateB[position] = true;
                    absentList.remove(StudentList.get(holder.getAdapterPosition()).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return StudentList.size();
    }

    static class takeattenViewholder extends RecyclerView.ViewHolder {
        TextView Name, Roll;
        RadioButton preButton, abButton;
        public takeattenViewholder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.stdName);
            Roll = itemView.findViewById(R.id.stdRoll);
            preButton = itemView.findViewById(R.id.present);
            abButton = itemView.findViewById(R.id.absent);

        }

        private void setdata(String name, String roll){
            Name.setText(name);
            Roll.setText(roll);
        }
    }
}
