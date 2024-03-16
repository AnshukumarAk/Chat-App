package com.anshu.chatapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.anshu.chatapp.Adepter.ChatAdapter;
import com.anshu.chatapp.Models.MessageModel;
import com.anshu.chatapp.R;
import com.anshu.chatapp.databinding.ActivityChatDetailBinding;
import com.anshu.chatapp.databinding.ActivityGroupChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {

    private ActivityGroupChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final ArrayList<MessageModel> messageModels = new ArrayList<>();

        final String senderId = FirebaseAuth.getInstance().getUid();
        binding.userName.setText("Friends Group");


        final ChatAdapter chatAdapter = new ChatAdapter(messageModels, this);
        binding.chatRecyclearView.setAdapter(chatAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        binding.chatRecyclearView.setLayoutManager(linearLayoutManager);

        firebaseDatabase.getReference().child("Group Chat")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageModels.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                            MessageModel model=snapshot1.getValue(MessageModel.class);
                            messageModels.add(model);
                        }

                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.etMessageChat.getText().toString().trim();
                if (!message.equals("")) {
                    final MessageModel model = new MessageModel(senderId, message);
                    model.setTimestamp(new Date().getTime());
                    binding.etMessageChat.setText("");

                    firebaseDatabase.getReference().child("Group Chat")
                            .push()
                            .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            });

                }else {

                    Toast.makeText(GroupChatActivity.this, "Please Enter Message", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.etMessageChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    showSendButton();
                    binding.imgAttachPicture.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    showVoiceButton();
                    binding.imgAttachPicture.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void showSendButton() {
        binding.btnSend.setImageDrawable(ContextCompat.getDrawable(GroupChatActivity.this, R.drawable.ic_send_black_24dp));
        binding.btnSend.setTag("send_image");
    }

    private void showVoiceButton() {
        binding.btnSend.setImageDrawable(ContextCompat.getDrawable(GroupChatActivity.this, R.drawable.ic_keyboard_voice_black_24dp));
        binding.btnSend.setTag("mic_image");
    }

}