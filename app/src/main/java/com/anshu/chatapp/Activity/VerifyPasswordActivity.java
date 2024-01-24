package com.anshu.chatapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.anshu.chatapp.R;
import com.anshu.chatapp.databinding.ActivityLoginBinding;
import com.anshu.chatapp.databinding.ActivityVerifyPasswordBinding;

public class VerifyPasswordActivity extends AppCompatActivity {

    ActivityVerifyPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}