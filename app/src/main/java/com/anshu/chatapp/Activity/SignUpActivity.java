package com.anshu.chatapp.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.anshu.chatapp.Models.UserModel;
import com.anshu.chatapp.R;
import com.anshu.chatapp.databinding.ActivitySignUpBinding;
import com.anshu.chatapp.databinding.ActivitySplashBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    ProgressDialog  progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ...
         // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating Account...");
        progressDialog.setMessage("We're Creating Your Account ");



        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String UserName = binding.etUserName.getText().toString().trim();
                String UserEmail = binding.etEmail.getText().toString().trim();
                String UserPassword = binding.etPassword.getText().toString().trim();
                auth.createUserWithEmailAndPassword(UserEmail, UserPassword)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "User Created Successfully", Toast.LENGTH_LONG).show();

                                    UserModel userModel=new UserModel(UserName,UserEmail,UserPassword);
                                       String id=task.getResult().getUser().getUid();
                                    firebaseDatabase.getReference().child("Users").child(id).setValue(userModel);
                                     progressDialog.dismiss();
                                     Intent intent=new Intent(SignUpActivity.this,SignInActivity.class);
                                     startActivity(intent);
                                     finish();

                                } else {
                                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
                            }
                  });

           binding.tvClickSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });

    }
}