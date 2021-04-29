package com.example.ternalogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class View_Uploaded_PDF extends AppCompatActivity {

    ListView myPDFListView;
    DatabaseReference databaseReference;
    List<DocumentDetail> uploadPDFS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__uploaded__p_d_f);

        myPDFListView= (ListView)findViewById(R.id.myListView);
        uploadPDFS=new ArrayList<>();
        viewAllFiles();

        myPDFListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                DocumentDetail documentDetail = uploadPDFS.get(position);

                Intent intent = new Intent();
                intent.setType(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(documentDetail.getUrl()));
                startActivity(intent);



            }
        });
    }

    private void viewAllFiles() {
        databaseReference = FirebaseDatabase.getInstance().getReference("DocumentPDF");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    DocumentDetail documentDetail = postSnapshot.getValue(DocumentDetail.class);
                    uploadPDFS.add(documentDetail);

                }
                String[] uploads = new String[uploadPDFS.size()];
                for(int i=0;i<uploads.length;i++)
                {
                    uploads[i] = uploadPDFS.get(i).getName();
                }
                ArrayAdapter<String> adapter =new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,uploads){


                    @Override
                    public View getView(int position, @Nullable View convertView, ViewGroup parent) {

                        View view = super.getView(position, convertView, parent);

                        TextView myText =(TextView)view.findViewById(android.R.id.text1);
                        myText.setTextColor(Color.BLACK);

                        return view;
                    }
                };

                myPDFListView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}