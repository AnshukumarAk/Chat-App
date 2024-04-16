package com.anshu.chatapp.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.anshu.chatapp.Activity.IncomingCallActivity;
import com.anshu.chatapp.Activity.ShowMyStatusActivity;
import com.anshu.chatapp.R;
import com.anshu.chatapp.databinding.FragmentCallsBinding;
import com.anshu.chatapp.databinding.FragmentChatsBinding;

public class fragment_calls extends Fragment{

    FragmentCallsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCallsBinding.inflate(getLayoutInflater());

        binding.ivAudioCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), IncomingCallActivity.class);
                startActivity(intent);
            }
        });

        return binding.getRoot();


    }


}