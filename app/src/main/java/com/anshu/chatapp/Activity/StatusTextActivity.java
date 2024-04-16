package com.anshu.chatapp.Activity;

import static com.anshu.chatapp.Fragments.fragment_chats.UserName;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.anshu.chatapp.Models.MessageModel;
import com.anshu.chatapp.Models.StatusModel;
import com.anshu.chatapp.R;
import com.anshu.chatapp.databinding.ActivityStatusTextBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.Random;

public class StatusTextActivity extends AppCompatActivity {

    ActivityStatusTextBinding binding;
    private int p = 0;
    private int t = 0;
    private Context context=this;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityStatusTextBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AllIniClizeID();


       CheckEditValue();
        binding.etTextStatus.requestFocus();
       ChangeBackGroundColor();
       ChangeTextStyle();
       binding.iconEmojiStatus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               openEmojiPicker();
           }
       });

      String userId=FirebaseAuth.getInstance().getCurrentUser().getUid();

       binding.fabStatusText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String message = binding.etTextStatus.getText().toString().trim();
               if (!message.equals("")) {
                   final StatusModel model = new StatusModel(userId, UserName,message);
                   model.setTimestamp(new Date().getTime());
                   binding.etTextStatus.setText("");
                   firebaseDatabase.getReference().child("userStatus")
                           .push()
                           .child(userId)
                           .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void unused) {
                                   Toast.makeText(context, "Status Uploaded", Toast.LENGTH_SHORT).show();

                               }
                           });

               }else {
                   Toast.makeText(context, "Type Something...", Toast.LENGTH_SHORT).show();

               }

           }
       });

    }

    private void AllIniClizeID() {
        firebaseDatabase=FirebaseDatabase.getInstance();
        auth= FirebaseAuth.getInstance();
    }

    private void ChangeTextStyle() {

        binding.iconTextStyleStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               t++;
                if (t==1){
                    setEditTextStyle(Typeface.BOLD);
                    t++;
                } else if (t==2) {
                    setEditTextStyle(Typeface.ITALIC);
                    t++;
                } else if (t==3) {
                    t++;
                    setEditTextStyle(Typeface.NORMAL);
                }
            }
        });
    }

    private void ChangeBackGroundColor() {
        final int[] colorBackgrounds = StatusTextActivity.this.getResources().getIntArray(R.array.statusBackground);
        int randomColor = colorBackgrounds[new Random().nextInt(colorBackgrounds.length)];
        binding.layoutTextStatus.setBackgroundColor(randomColor);
        binding.iconColorStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p++;
                if (p < colorBackgrounds.length) {
                    binding.layoutTextStatus.setBackgroundColor(colorBackgrounds[p]);
                } else {
                    p = 0;
                    binding.layoutTextStatus.setBackgroundColor(colorBackgrounds[p]);
                }

            }
        });
    }

    private void CheckEditValue() {
        binding.etTextStatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    binding.fabStatusText.show();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    binding.fabStatusText.hide();
                }
            }
        });
    }
    private void setEditTextStyle(int style) {
        binding.etTextStatus.setTypeface(null, style);
    }

    private void openEmojiPicker() {
        // Show the system keyboard, which may include the emoji picker
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(binding.etTextStatus, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}