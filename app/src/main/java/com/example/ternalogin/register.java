package com.example.ternalogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class register extends AppCompatActivity {

    private EditText name, branch, email, div, roll, pass, cpass;
    private Button signup;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;
    private FirebaseDatabase mdatabase;
    private DatabaseReference dataRef, mRef, gRef,subjects, mecRef, facRef;
    String CurrentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loadingbar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance();
        facRef = mdatabase.getReference("Faculty");
        dataRef = mdatabase.getReference().child("Students");
        subjects = mdatabase.getReference().child("subjects");
        mRef = subjects.child("Math");
        gRef = subjects.child("Graphics");
        mecRef = subjects.child("Mechanics");


        name = findViewById(R.id.enter_name);
        branch = findViewById(R.id.enter_branch);
        email = findViewById(R.id.enter_email);
        div = findViewById(R.id.enter_div);
        roll = findViewById(R.id.enter_roll);
        pass = findViewById(R.id.enter_password);
        cpass = findViewById(R.id.enter_cpassword);
        signup = findViewById(R.id.signup_button);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }

            private void CreateNewAccount() {
                final String Name = name.getText().toString();
                final String Branch = branch.getText().toString();
                final String Email = email.getText().toString();
                final String Div = div.getText().toString();
                final String Roll = roll.getText().toString();
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
                } else if (TextUtils.isEmpty(Roll)) {
                    Toast.makeText(register.this, "Please write your Roll number....", Toast.LENGTH_SHORT).show();
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
                    mAuth.createUserWithEmailAndPassword(Email, Pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        CurrentUserId = mAuth.getCurrentUser().getUid();
                                        Detail detail = new Detail(Name, Branch, Email, Div, Roll, 0, 0);
                                        dataRef.child(Roll + Div + CurrentUserId).setValue(detail);
                                        PAList palist = new PAList(0,"", Name);
                                        mRef.child(Roll + Div + CurrentUserId).setValue(palist);
                                        gRef.child(Roll + Div + CurrentUserId).setValue(palist);
                                        mecRef.child(Roll + Div + CurrentUserId).setValue(palist);
                                        Toast.makeText(register.this, "you are authenticated successfully ", Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();
                                        Intent MainIntent = new Intent(register.this, Posts.class);
                                        MainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(MainIntent);
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