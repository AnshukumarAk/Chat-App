package com.anshu.chatapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.anshu.chatapp.R;
import com.anshu.chatapp.databinding.ActivityChatDetailBinding;
import com.anshu.chatapp.databinding.ActivityLoginBinding;

public class ChatDetailActivity extends AppCompatActivity {

    ActivityChatDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

      binding.backArrow.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              onBackPressed();
          }
      });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}