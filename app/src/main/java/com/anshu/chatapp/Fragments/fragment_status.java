package com.anshu.chatapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anshu.chatapp.Activity.CameraActivity;
import com.anshu.chatapp.R;
import com.anshu.chatapp.databinding.FragmentChatsBinding;
import com.anshu.chatapp.databinding.FragmentStatusBinding;

public class fragment_status extends Fragment {

      FragmentStatusBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStatusBinding.inflate(getLayoutInflater());

        binding.layoutSelfStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(), CameraActivity.class);
                startActivity(intent);

            }
        });


        return binding.getRoot();
    }
}