package com.anshu.chatapp.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.anshu.chatapp.Adepter.MyStatusAdapter;
import com.anshu.chatapp.R;
import com.anshu.chatapp.databinding.ActivityShowMyStatusBinding;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowMyStatusActivity extends AppCompatActivity {

    private static final String TAG = "ShowMyStatusActivity";

    private DatabaseReference statusRef;
    private ValueEventListener statusListener;

    private TextView usernameTextView;
    private TextView dateTextView;
    private TextView statusTextView;
    private TextView viewCountTextView;
    private ImageView avatarImageView;
    ActivityShowMyStatusBinding binding;
    private List<String> statusList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityShowMyStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize views
        usernameTextView = findViewById(R.id.tv_status_username);
        dateTextView = findViewById(R.id.tv_status_date);
//        statusTextView = findViewById(R.id.tv_status_message);
        viewCountTextView = findViewById(R.id.tv_seen_count);
        avatarImageView = findViewById(R.id.avatar_status_user);

        // Firebase initialization
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // User is not signed in
            Toast.makeText(this, "User not signed in", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            String userId = currentUser.getUid();
            statusRef = FirebaseDatabase.getInstance().getReference().child("userStatus").child(userId);
            fetchStatus();
        }
    }

    private void fetchStatus() {
        if (statusRef != null) {
            statusListener = statusRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Retrieve status data from Firebase
                        String username = snapshot.child("userName").getValue(String.class);
                        String date = snapshot.child("date").getValue(String.class);
//                        long viewCount = snapshot.child("viewCount").getValue(Long.class);
                        String avatarUrl = snapshot.child("avatarUrl").getValue(String.class);

                        // Update UI with status data
                        usernameTextView.setText(username);
                        Log.d("","UserName"+username);
                        dateTextView.setText(date);
//                        viewCountTextView.setText(String.valueOf(viewCount));
                        Glide.with(ShowMyStatusActivity.this).load(avatarUrl).into(avatarImageView);
                        String status = snapshot.child("message").getValue(String.class);
                        statusList.add(status);
                        MyStatusAdapter adapter = new MyStatusAdapter(ShowMyStatusActivity.this, statusList);
                        binding.statusViewFlipper.setAdapter(adapter);

                    } else {
                        Log.d(TAG, "No status data found");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(TAG, "Failed to fetch status data: " + error.getMessage());
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (statusRef != null && statusListener != null) {
            statusRef.removeEventListener(statusListener);
        }
    }
}
