package com.anshu.chatapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.anshu.chatapp.Adepter.AddFriendAdapter;
import com.anshu.chatapp.Adepter.UserAdepter;
import com.anshu.chatapp.Models.UserModel;
import com.anshu.chatapp.R;
import com.anshu.chatapp.Utills.SharedPrefHelper;
import com.anshu.chatapp.databinding.ActivityAddFriendsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddFriendsActivity extends AppCompatActivity {

    ActivityAddFriendsBinding binding;
    ArrayList<UserModel> list;
    private MenuItem cameraItem;
    private SearchView searchView;

    FirebaseDatabase firebaseDatabase;
    SharedPrefHelper sharedPrefHelper;
    Dialog update_pic_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddFriendsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        update_pic_layout=new Dialog(this);
        ShowDialog();
        list = new ArrayList<>();
        firebaseDatabase=FirebaseDatabase.getInstance();
        // Initialize SharedPrefHelper
        sharedPrefHelper = new SharedPrefHelper(this);
        AddFriendAdapter userAdepter = new AddFriendAdapter(this,list);
        binding.rvUserList.setHasFixedSize(true);
        // Set the layout manager as GridLayoutManager with 2 columns
        binding.rvUserList.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvUserList.setAdapter(userAdepter);

        firebaseDatabase.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    UserModel users=dataSnapshot.getValue(UserModel.class);
//                    users.getUserId(dataSnapshot.getKey());
                    users.setUserId(dataSnapshot.getKey());
                    if (!users.getUserId().equals(FirebaseAuth.getInstance().getUid())){
                        list.add(users);
                        update_pic_layout.dismiss();
                    }

                }
                userAdepter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void ShowDialog() {

        update_pic_layout.setContentView(R.layout.progress_dailoge);
        update_pic_layout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = update_pic_layout.getWindow().getAttributes();
        params.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;

        android.widget.ProgressBar progressBar = (ProgressBar) update_pic_layout.findViewById(R.id.spin_kit);

        update_pic_layout.show();
        update_pic_layout.setCanceledOnTouchOutside(false);
        update_pic_layout.setCancelable(false);
    }

}