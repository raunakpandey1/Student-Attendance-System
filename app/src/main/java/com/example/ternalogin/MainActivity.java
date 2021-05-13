package com.example.ternalogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ternalogin.views.viewNotice;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView tname,tdept,tsub,temail,TAbutton,SSbutton,addStudent,exitApp;
    Toolbar toolbar;
   // private ImageButton imgbutton1;
    private FirebaseAuth mAuth;
    private DatabaseReference dataRef;
    CardView cdTA,cdVA,cdAS,cdEA;
    String CurrentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        CurrentUserId = mAuth.getCurrentUser().getUid();
        dataRef = FirebaseDatabase.getInstance().getReference("Faculty").child(CurrentUserId);


        toolbar = findViewById(R.id.mainToolbar);
        TAbutton = findViewById(R.id.TAButton);
        SSbutton = findViewById(R.id.SSButton);
        addStudent = findViewById(R.id.add_student);
        exitApp = findViewById(R.id.exit);
       // imgbutton1 = findViewById(R.id.fac_toolbar_logout);
        tname = findViewById(R.id.teach_name);
        tdept = findViewById(R.id.teach_dept);
        tsub = findViewById(R.id.teach_sub);
        temail = findViewById(R.id.teach_email);

        cdTA = findViewById(R.id.cdTA);
        cdVA = findViewById(R.id.cdVA);
        cdAS = findViewById(R.id.cdAS);
        cdEA = findViewById(R.id.cdEA);

        setSupportActionBar(toolbar);

        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString().toUpperCase();
                    String dept = snapshot.child("department").getValue().toString();
                    String sub = snapshot.child("subject").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();

                        tname.setText(name);
                        tdept.setText(dept);
                        tsub.setText(sub);
                        temail.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       /* imgbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                SendUserToLoginActivity();
            }

            private void SendUserToLoginActivity() {
                Intent LoginIntent = new Intent(MainActivity.this, Login_Activity.class);
                LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(LoginIntent);
                overridePendingTransition(0,0);
                finish();
            }
        });  */


        cdTA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(MainActivity.this, TASelectYear.class);
                startActivity(MainIntent);
            }
        });

        cdAS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(MainActivity.this, register.class);
                startActivity(MainIntent);
            }
        });

        cdVA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(MainActivity.this, seeAttendanceSubject.class);
                startActivity(MainIntent);
            }
        });

        cdEA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(MainActivity.this, MonthAttendance.class);
                startActivity(MainIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.notice:
                Intent newIntent = new Intent(MainActivity.this, viewNotice.class);
                startActivity(newIntent);
                return true;
            case R.id.upDoc:
                Intent intent = new Intent(MainActivity.this, DocumentActivity.class);
                startActivity(intent);
                Toast.makeText(this, "document", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.logout:
                mAuth.signOut();
                Intent LoginIntent = new Intent(MainActivity.this, Login_Activity.class);
                LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                LoginIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(LoginIntent);
                overridePendingTransition(0,0);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}