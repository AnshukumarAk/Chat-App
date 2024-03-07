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
import com.anshu.chatapp.Models.UserModel;
import com.anshu.chatapp.R;
import com.anshu.chatapp.databinding.ActivityMainBinding;
import com.anshu.chatapp.databinding.FragmentChatsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fragment_chats extends Fragment{

    FragmentChatsBinding binding;
    ArrayList<UserModel> list;
    private MenuItem cameraItem;
    private SearchView searchView;

    FirebaseDatabase firebaseDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentChatsBinding.inflate(getLayoutInflater());
        list = new ArrayList<>();
        firebaseDatabase=FirebaseDatabase.getInstance();

        UserAdepter userAdepter = new UserAdepter(requireContext(),list);
        binding.rvChatsProfile.setHasFixedSize(true);
        binding.rvChatsProfile.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvChatsProfile.setAdapter(userAdepter);


        firebaseDatabase.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    UserModel users=dataSnapshot.getValue(UserModel.class);
//                    users.getUserId(dataSnapshot.getKey());
                    users.setUserId(dataSnapshot.getKey());
                    list.add(users);

                }
                userAdepter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




//        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for
//                (DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    Users users dataSnapshot.getValue(Users.class);
//                    users.getUserId(dataSnapshot.getKey());
//                    list.add(users);
//                }
//                adapter.notifyDataSetChanged();
//            }
//            @Override
//            public void onCancelled(@NonNull Database Error error) {
//            }
//        });









        return binding.getRoot();
    }


}