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

public class LoginActivity extends AppCompatActivity {
    EditText txtE,txtP;
    Button bt,btrs,btDK;
    FirebaseAuth firebaseAuth;
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
                        Toast.makeText(LoginActivity.this,"Xin chào!",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
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