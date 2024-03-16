package com.anshu.chatapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.anshu.chatapp.Models.UserModel;
import com.anshu.chatapp.R;
import com.anshu.chatapp.Utills.SharedPrefHelper;
import com.anshu.chatapp.databinding.ActivityProfileBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    String UserName="",UserImage="",UserMobileNumber="",UserAbout="";
    SharedPrefHelper sharedPrefHelper;

    FirebaseStorage firebaseStorage;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable the back button

        AllIniClizeID();
        getValus();




        binding.profilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this,CreateProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getValus() {

        firebaseDatabase.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel userModel=snapshot.getValue(UserModel.class);
                        Picasso.get().load(userModel.getProfilePic()).
                                placeholder(R.drawable.image).into(binding.ProfileImage);

                        binding.tvAbout.setText(userModel.getStatus());
                        binding.TvUserName.setText(userModel.getUserName());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void AllIniClizeID() {
        sharedPrefHelper=new SharedPrefHelper(this);
        auth=FirebaseAuth.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
    }



    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}