package com.anshu.chatapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.anshu.chatapp.R;
import com.anshu.chatapp.databinding.ActivityChooseLanguageBinding;

public class ChooseLanguage extends AppCompatActivity {

    ActivityChooseLanguageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseLanguageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(ChooseLanguage.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}