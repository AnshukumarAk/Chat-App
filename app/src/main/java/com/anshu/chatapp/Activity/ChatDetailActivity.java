package com.anshu.chatapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.anshu.chatapp.Adepter.ChatAdapter;
import com.anshu.chatapp.Models.MessageModel;
import com.anshu.chatapp.R;
import com.anshu.chatapp.databinding.ActivityChatDetailBinding;
import com.anshu.chatapp.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ChatDetailActivity extends AppCompatActivity {

    ActivityChatDetailBinding binding;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseDatabase=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

     final  String senderId= auth.getUid();
       String receiveId= getIntent().getStringExtra("userId");
       String userName= getIntent().getStringExtra("userName");
       String profilePic= getIntent().getStringExtra("profilePic");


       binding.userName.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.image).
                into(binding.profileImage);





        binding.backArrow.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              onBackPressed();
          }
      });


      final ArrayList<MessageModel>messageModels=new ArrayList<>();

      final ChatAdapter chatAdapter=new ChatAdapter(messageModels,this,receiveId);
      binding.chatRecyclearView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.chatRecyclearView.setLayoutManager(layoutManager);


      final String senderRoom=senderId+receiveId;
      final String receiverRoom=receiveId+senderId;

      firebaseDatabase.getReference().child("chats")
                      .child(senderRoom)
                              .addValueEventListener(new ValueEventListener() {
                                  @Override
                                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                                      messageModels.clear();
                                      for (DataSnapshot snapshot1 : snapshot.getChildren()){
                                          MessageModel model=snapshot1.getValue(MessageModel.class);
                                           model.setMessageId(snapshot1.getKey());

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

             firebaseDatabase.getReference().child("chats").
                     child(senderRoom)
                     .push()
                     .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                         @Override
                         public void onSuccess(Void unused) {
                             firebaseDatabase.getReference().child("chats").
                                     child(receiverRoom)
                                     .push()
                                     .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                         @Override
                                         public void onSuccess(Void unused) {

                                         }
                                     });
                         }
                     });

                     }else {

                 Toast.makeText(ChatDetailActivity.this, "Please Enter Message", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(ChatDetailActivity.this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private void showSendButton() {
        binding.btnSend.setImageDrawable(ContextCompat.getDrawable(ChatDetailActivity.this, R.drawable.ic_send_black_24dp));
        binding.btnSend.setTag("send_image");
    }

    private void showVoiceButton() {
        binding.btnSend.setImageDrawable(ContextCompat.getDrawable(ChatDetailActivity.this, R.drawable.ic_keyboard_voice_black_24dp));
        binding.btnSend.setTag("mic_image");
    }
}