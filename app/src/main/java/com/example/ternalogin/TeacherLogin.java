package com.example.ternalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherLogin extends AppCompatActivity {

    private EditText enter_email, enter_password;
    private Button login_button;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;
    private DatabaseReference facRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        loadingbar = new ProgressDialog(this);
        enter_email = findViewById(R.id.enter_email);
        enter_password = findViewById(R.id.enter_password);
        login_button = findViewById(R.id.login_button);

        mAuth = FirebaseAuth.getInstance();
        facRef = FirebaseDatabase.getInstance().getReference("Faculty");

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowUserToLogin();
            }


            private void AllowUserToLogin() {
                String email = enter_email.getText().toString();
                String password = enter_password.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(TeacherLogin.this, "Please write your email id", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(password)){
                    Toast.makeText(TeacherLogin.this, "Please enter the password", Toast.LENGTH_SHORT).show();
                }else{
                    loadingbar.setMessage("Logging in...");
                    loadingbar.show();
                    loadingbar.setCanceledOnTouchOutside(true);
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){
                                        final String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        facRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if(snapshot.hasChild(user)){
                                                    Intent MainIntent = new Intent(TeacherLogin.this, MainActivity.class);
                                                    loadingbar.dismiss();
                                                    MainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(MainIntent);
                                                    Toast.makeText(TeacherLogin.this, "Login Successfully..", Toast.LENGTH_SHORT).show();
                                                    finish();

                                                }
                                                else{
                                                    loadingbar.dismiss();
                                                    Toast.makeText(TeacherLogin.this, "Only Faculty can Login..", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    }else{
                                        loadingbar.dismiss();
                                        String message = task.getException().getMessage();
                                        Toast.makeText(TeacherLogin.this, message, Toast.LENGTH_SHORT).show();
                                    }

                                }


                            });
                }
            }
        });

    }
}