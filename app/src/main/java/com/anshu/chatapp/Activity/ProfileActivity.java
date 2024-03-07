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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.anshu.chatapp.R;
import com.anshu.chatapp.Utills.SharedPrefHelper;
import com.anshu.chatapp.databinding.ActivityProfileBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    String UserName="",UserImage="",UserMobileNumber="";
    SharedPrefHelper sharedPrefHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable the back button

        AllIniClizeID();
        getValueSharePrefance();
        setUserData();



    }

    private void AllIniClizeID() {
        sharedPrefHelper=new SharedPrefHelper(this);
    }

    private void getValueSharePrefance() {
        UserName=sharedPrefHelper.getString("UserName","");
        UserImage=sharedPrefHelper.getString("UserPhoto","");
        UserMobileNumber=sharedPrefHelper.getString("FullNumber","");
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
    @SuppressLint("SetTextI18n")
    private void setUserData() {
     binding.TvUserName.setText(UserName);
     binding.TvMobileNumber.setText("+ "+ UserMobileNumber);

        if (UserImage != null && UserImage.length() > 200) {
            byte[] decodedString = Base64.decode(UserImage, Base64.NO_WRAP);
            InputStream inputStream = new ByteArrayInputStream(decodedString);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            binding.ProfileImage.setImageBitmap(bitmap);

        }else {
            try {
                Glide.with(ProfileActivity.this)
                        .load(UserImage)
                        .apply(new RequestOptions().centerCrop())
                        .into(binding.ProfileImage);
            } catch (Exception e) {
                Log.d("Exception", "" + e);
            }
        }

    }
}