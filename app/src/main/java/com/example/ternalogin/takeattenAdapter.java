package com.example.ternalogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ternalogin.model.student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class takeattenAdapter extends RecyclerView.Adapter<takeattenAdapter.takeattenViewholder> {

    private List<student> StudentList = new ArrayList<>();
    private Context context;
    int i;
    public static List<String> presentList = new ArrayList<>();
    public static List<String> absentList = new ArrayList<>();
  //  private final boolean[] mcheckedStateA;
 //   private final boolean[] mcheckedStateB;

    public takeattenAdapter(List<student> studentList, Context context) {
        StudentList = studentList;
        this.context = context;
        for(i=0; i<studentList.size();i++){
            absentList.add(studentList.get(i).getId());
        }
       // mcheckedStateA = new boolean[studentList.size()];
       // mcheckedStateB = new boolean[studentList.size()];
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
      //  holder.abButton.setChecked(false);

       /* if(mcheckedStateA[position]){
            holder.PreCheckBox.setChecked(true);
        }else{
            holder.PreCheckBox.setChecked(false);
        }
        if(mcheckedStateB[position]){
            holder.abButton.setChecked(true);
        }else{
            holder.abButton.setChecked(false);
        }  */

        holder.PreCheckBox.setOnCheckedChangeListener(null);
        holder.PreCheckBox.setChecked(StudentList.get(position).isState());
        holder.PreCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                StudentList.get(position).setState(isChecked);
                if(StudentList.get(position).isState()){
                    presentList.add(StudentList.get(holder.getAdapterPosition()).getId());
                    absentList.remove(StudentList.get(holder.getAdapterPosition()).getId());
                }
                if(!StudentList.get(position).isState()) {
                    absentList.add(StudentList.get(holder.getAdapterPosition()).getId());
                    presentList.remove(StudentList.get(holder.getAdapterPosition()).getId());
                }
            }
        });

        /*holder.PreCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mcheckedStateA[position] = false;
                    StudentList.get(position).setState(true);
                    if(StudentList.get(position).isState()){
                        presentList.add(StudentList.get(holder.getAdapterPosition()).getId());
                        absentList.remove(StudentList.get(holder.getAdapterPosition()).getId());
                    }
                }else{
                    mcheckedStateA[position] = true;
                    StudentList.get(position).setState(false);
                    if(!StudentList.get(position).isState()) {
                        absentList.add(StudentList.get(holder.getAdapterPosition()).getId());
                        presentList.remove(StudentList.get(holder.getAdapterPosition()).getId());
                    }
                }
            }
        }); */

       /* holder.abButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        });  */
    }

    @Override
    public int getItemCount() {
        return StudentList.size();
    }

    static class takeattenViewholder extends RecyclerView.ViewHolder {
        TextView Name, Roll;
        CheckBox PreCheckBox;
        public takeattenViewholder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.stdName);
            Roll = itemView.findViewById(R.id.stdRoll);
            PreCheckBox = itemView.findViewById(R.id.present);

        }

        private void setdata(String name, String roll){
            Name.setText(name);
            Roll.setText(roll);
        }
    }
}
