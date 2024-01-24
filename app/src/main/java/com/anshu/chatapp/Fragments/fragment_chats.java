package com.anshu.chatapp.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.anshu.chatapp.Adepter.UserAdepter;
import com.anshu.chatapp.R;
import com.anshu.chatapp.databinding.ActivityMainBinding;
import com.anshu.chatapp.databinding.FragmentChatsBinding;

import java.util.ArrayList;

public class fragment_chats extends Fragment{

    FragmentChatsBinding binding;
    ArrayList<String> list;
    private MenuItem cameraItem;
    private SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentChatsBinding.inflate(getLayoutInflater());
        list = new ArrayList<>();
//        setHasOptionsMenu(true);
        list.add("Anush Kumar");
        list.add("Deepak Kumar");
        list.add("Ram Kumar");
        list.add("Shyam Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        list.add("Mohan Kumar");
        UserAdepter userAdepter = new UserAdepter(requireContext(),list);
        binding.rvChatsProfile.setHasFixedSize(true);
        binding.rvChatsProfile.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvChatsProfile.setAdapter(userAdepter);
        userAdepter.notifyDataSetChanged();

        return binding.getRoot();
    }


}