package com.example.truyenhay;

import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {
    EditText txtE,txtP;
    Button btDK,btCancel;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtE=findViewById(R.id.txtEmail);
        txtP=findViewById(R.id.txtPass);
        firebaseAuth=FirebaseAuth.getInstance();
        btCancel=findViewById(R.id.btCancel);
        btDK=findViewById(R.id.btDK);
        firebaseAuth=FirebaseAuth.getInstance();
        btDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.createUserWithEmailAndPassword(txtE.getText().toString(),txtP.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(RegisterActivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}