package com.example.truyenhay;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText txtE,txtP;
    Button bt,btrs,btDK;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(getApplicationContext());
        setContentView(R.layout.activity_login);
        txtE=findViewById(R.id.txtEmail);
        txtP=findViewById(R.id.txtPass);
        bt=findViewById(R.id.btNhap);
        firebaseAuth=FirebaseAuth.getInstance();
        btrs=findViewById(R.id.btReset);
        btDK=findViewById(R.id.btDangky);
        db = FirebaseFirestore.getInstance();
        CollectionReference admin = db.collection("admin");
        admin.whereEqualTo("username","hue@gmail.com").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().getDocuments().isEmpty()){
                        String uid = "adhsadhsad";
                        Map<String, Object> rootAdmin = new HashMap<>();
                        rootAdmin.put("username","hue@gmail.com");
                        rootAdmin.put("uid", uid);
                        admin.document(uid).set(rootAdmin);
                    }
                    //Toast.makeText(LoginActivity.this, task.getResult().getDocuments().get(0).get("username").toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        btDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        btrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.sendPasswordResetEmail(txtE.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(LoginActivity.this, "Kiểm tra mail của bạn", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signInWithEmailAndPassword(txtE.getText().toString(),
                        txtP.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LoginActivity.this,authResult.getUser().getEmail().toString(),Toast.LENGTH_SHORT).show();
                        db.collection("admin").whereEqualTo("username", authResult.getUser().getEmail().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.getResult().getDocuments().isEmpty()){
                                    // Cho nay là activity cua nguoi dung thuong
                                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    // Cho nay la activity cua admin
                                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this,"Bạn đã nhập sai email/password",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}