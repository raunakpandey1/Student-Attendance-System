package com.example.ternalogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ternalogin.model.Detail;
import com.example.ternalogin.model.PAList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class register extends AppCompatActivity {

    private EditText name, branch, email, div, pass, cpass;
    private Button signup;
    Spinner SpinRoll;
    List<String> rollList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    String Roll = "";

    private FirebaseAuth mAuth1;
    private ProgressDialog loadingbar;
    private FirebaseDatabase mdatabase;
    private DatabaseReference dataRef, mRef, gRef,subjects, mecRef, facRef, stdid;
    String CurrentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loadingbar = new ProgressDialog(this);
        mAuth1 = FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance();
        facRef = mdatabase.getReference("Faculty");
        dataRef = mdatabase.getReference().child("Students");
        subjects = mdatabase.getReference().child("subjects");
        stdid = mdatabase.getReference().child("studentID");
        mRef = subjects.child("Math");
        gRef = subjects.child("Graphics");
        mecRef = subjects.child("Mechanics");


        name = findViewById(R.id.enter_name);
        branch = findViewById(R.id.enter_branch);
        email = findViewById(R.id.enter_email);
        SpinRoll = findViewById(R.id.enter_roll);
        div = findViewById(R.id.enter_div);
        pass = findViewById(R.id.enter_password);
        cpass = findViewById(R.id.enter_cpassword);
        signup = findViewById(R.id.signup_button);

        rollList.add("Select Roll No.");
        rollList.add("01");
        rollList.add("02");
        rollList.add("03");
        rollList.add("04");
        rollList.add("05");
        rollList.add("06");
        rollList.add("07");
        rollList.add("08");
        rollList.add("09");

        for(int i=10;i<=80;i++){
            rollList.add(String.valueOf(i));
        }
        adapter = new ArrayAdapter<String>(register.this,android.R.layout.simple_spinner_dropdown_item, rollList);
        SpinRoll.setAdapter(adapter);
        SpinRoll.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Roll = rollList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }

            private void CreateNewAccount() {
                final String Name = name.getText().toString().toUpperCase(); 
                final String Branch = branch.getText().toString();
                final String Email = email.getText().toString();
                final String Div = div.getText().toString().toUpperCase();
                String Pass = pass.getText().toString();
                String Cpass = cpass.getText().toString();

                if (TextUtils.isEmpty(Name)) {
                    Toast.makeText(register.this, "Please Write your Name....", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(Branch)) {
                    Toast.makeText(register.this, "Please write your Branch....", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(register.this, "Please write your Email....", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(Div)) {
                    Toast.makeText(register.this, "Please write your Division....", Toast.LENGTH_SHORT).show();
                } else if (Roll.equals("Select Roll No.")) {
                    Toast.makeText(register.this, "Please Select your Roll number....", Toast.LENGTH_SHORT).show();
                }else if (Roll.equals("")) {
                    Toast.makeText(register.this, "Please Select your Roll number....", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(Pass)) {
                    Toast.makeText(register.this, "Please write your Password....", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(Cpass)) {
                    Toast.makeText(register.this, "Please write your Confirm Password....", Toast.LENGTH_SHORT).show();
                } else if (!Pass.equals(Cpass)) {
                    Toast.makeText(register.this, "your password do not match with your confirm password", Toast.LENGTH_SHORT).show();
                } else {
                    loadingbar.setMessage("Setting up your profile");
                    loadingbar.show();
                    loadingbar.setCanceledOnTouchOutside(true);
                    mAuth1.createUserWithEmailAndPassword(Email, Pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        CurrentUserId = mAuth1.getCurrentUser().getUid();
                                        Detail detail = new Detail(Name, Branch, Email, Div, Roll, "", "", Roll + Div + CurrentUserId);
                                        dataRef.child(Roll + Div + CurrentUserId).setValue(detail);
                                        stdid.child(CurrentUserId).setValue(Roll + Div + CurrentUserId);
                                        PAList palist = new PAList("","", "",Name, Roll + Div + CurrentUserId);
                                        mRef.child(Roll + Div + CurrentUserId).setValue(palist);
                                        gRef.child(Roll + Div + CurrentUserId).setValue(palist);
                                        mecRef.child(Roll + Div + CurrentUserId).setValue(palist);
                                        mAuth1.signOut();
                                        Toast.makeText(register.this, "Student Registered Successfully...", Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();
                                        //Intent MainIntent = new Intent(register.this, register.class);
                                       // MainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        //startActivity(MainIntent);
                                        finish();
                                    } else {
                                        String message = task.getException().getMessage();
                                        Toast.makeText(register.this, message, Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();
                                    }
                                }
                            });

                }

            }
        });
    }
}