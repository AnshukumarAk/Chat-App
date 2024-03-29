package com.anshu.chatapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.anshu.chatapp.R;
import com.anshu.chatapp.Utills.SharedPrefHelper;
import com.anshu.chatapp.databinding.ActivityFirstTimeOpenBinding;
import com.anshu.chatapp.databinding.ActivityLoginBinding;

public class FirstTimeOpenActivity extends AppCompatActivity {

    ActivityFirstTimeOpenBinding binding;
    Context context=this;
    private SharedPrefHelper sharedPrefHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFirstTimeOpenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPrefHelper=new SharedPrefHelper(this);
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(FirstTimeOpenActivity.this,SignInActivity.class);
                startActivity(intent);
                sharedPrefHelper.setString("agree_and_continue","yes");

            }
        });

    }

}