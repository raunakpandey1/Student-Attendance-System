package com.example.ternalogin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ternalogin.model.DocumentDetail;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DocumentActivity extends AppCompatActivity {

    TextView textView;
    EditText title, description;
    Button uploadBtn, selectBtn;

    StorageReference storageReference;
    DatabaseReference databaseReference;
    String Title, Description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        textView = findViewById(R.id.textView);
        title =findViewById(R.id.title);
        description =findViewById(R.id.description);
        selectBtn =findViewById(R.id.selectBtn);
        uploadBtn =findViewById(R.id.uploadBtn);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("DocumentPDF");

        uploadBtn.setEnabled(false);
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               selectPDF();

            }
        });

    }

    private void selectPDF() {
        Intent intent =new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"PDF FILE SELECT"),12);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==12 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            uploadBtn.setEnabled(true);
            textView.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/")+1));

            uploadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Title = title.getText().toString();
                    Description = description.getText().toString();

                    if(TextUtils.isEmpty(Title) || TextUtils.isEmpty(Description)){
                        Toast.makeText(DocumentActivity.this, "Please enter title and description", Toast.LENGTH_SHORT).show();
                    }else{
                        uploadPDFFileFirebase(data.getData());
                    }

                }
            });


        }
    }

    private void uploadPDFFileFirebase(Uri data) {

        final ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("File is loading...");
        progressDialog.show();

        StorageReference reference =storageReference.child("upload" + System.currentTimeMillis() + ".pdf");

        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> uriTask =taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete());
                        Uri uri =uriTask.getResult();

                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                        SimpleDateFormat keyFormat = new SimpleDateFormat("yyyy-MM-dd-kk-mm-ss");
                        String dateFormat = simpleDateFormat.format(calendar.getTime());
                        String keyDate = keyFormat.format(calendar.getTime());

                        DocumentDetail documentDetail = new DocumentDetail(uri.toString(), Title, Description, dateFormat);
                        databaseReference.child(keyDate).setValue(documentDetail);
                        Toast.makeText(DocumentActivity.this,"File upload", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress =(100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("File uploaded..." +(int)progress + "%");
            }
        });
    }
}
