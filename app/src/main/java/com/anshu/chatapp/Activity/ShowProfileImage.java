package com.anshu.chatapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.anshu.chatapp.R;
import com.anshu.chatapp.databinding.ActivityProfileBinding;
import com.anshu.chatapp.databinding.ActivityShowProfileImageBinding;
import com.squareup.picasso.Picasso;

public class ShowProfileImage extends AppCompatActivity {

      ActivityShowProfileImageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityShowProfileImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String UserImage= getIntent().getStringExtra("profileImage");
        String UserName= getIntent().getStringExtra("userName");

        binding.tvUserName.setText(UserName);


        Picasso.get().load(UserImage).
                placeholder(R.drawable.image).into(binding.ivProfilePic);


        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}