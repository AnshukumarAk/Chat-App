package com.anshu.chatapp.Adepter;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.anshu.chatapp.Activity.ChatDetailActivity;
import com.anshu.chatapp.Activity.ShowProfileImage;
import com.anshu.chatapp.Models.UserModel;
import com.anshu.chatapp.R;
import com.anshu.chatapp.Utills.CommonClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdepter extends RecyclerView.Adapter<UserAdepter.ViewHolder> {

    Context context;
    ArrayList<UserModel> list;
    int hour=0;
    int minute=0;


    public UserAdepter(Context context , ArrayList<UserModel> list){
        this.context =context;
        this.list=list;

    }
    @NonNull
    @Override
    public UserAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.custom_chat_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdepter.ViewHolder holder, int position) {


        UserModel userModel=list.get(position);

        Picasso.get().load(userModel.getProfilePic()).
                placeholder(R.drawable.image).into(holder.ivProfilePic);
        holder.tvName.setText(userModel.getUserName());


        FirebaseDatabase.getInstance().getReference()
                .child("chats")
                .child(FirebaseAuth.getInstance().getUid() + userModel.getUserId()) // Make sure this reference is correct
                .orderByChild("timestamp")
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                                String message = (String) messageSnapshot.child("message").getValue();
                                long timestamp = (long) messageSnapshot.child("timestamp").getValue();

                                if (message != null) {
                                    holder.tvLastMessage.setText(message);

                                    try{
                                        String formattedDate = CommonClass.formatDate(String.valueOf(timestamp));
                                        holder.tvLastMessageTime.setText(formattedDate);

                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                       ///// Other Option
//                                    // Assuming you want to format the timestamp for display
//                                    String formattedTimestamp = formatTimestamp(timestamp);
//                                    holder.tvLastMessageTime.setText(formattedTimestamp);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled
                    }
                });



        holder.ll_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, ChatDetailActivity.class);
                intent.putExtra("userId",userModel.getUserId());
                intent.putExtra("profilePic",userModel.getProfilePic());
                intent.putExtra("userName",userModel.getUserName());
                context.startActivity(intent);

            }
        });


        holder.ll_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowImageProfile(holder.tvName.getText().toString().trim(),
                        userModel.getProfilePic(),userModel);

            }
        });



    }

    private void ShowImageProfile(String name, String profilePic, UserModel userModel) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = layoutInflater.inflate(R.layout.show_image_profile, null);
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//            alertDialog.setTitle("Choose Image");


            alertDialog.setView(popupView);
            final AlertDialog dialog = alertDialog.show();
            alertDialog.setCancelable(true);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView tv_name = popupView.findViewById(R.id.tv_name);
        ImageView ivProfilePic = popupView.findViewById(R.id.ivProfilePic);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView ivChat = popupView.findViewById(R.id.ivChat);
        tv_name.setText(name);

        Picasso.get().load(profilePic).
                placeholder(R.drawable.image).into(ivProfilePic);

        ivProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ShowProfileImage.class);
                intent.putExtra("profileImage",profilePic);
                intent.putExtra("userName",name);
                dialog.dismiss();
                context.startActivity(intent);
            }
        });

        ivChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ChatDetailActivity.class);
                intent.putExtra("userId",userModel.getUserId());
                intent.putExtra("profilePic",userModel.getProfilePic());
                intent.putExtra("userName",userModel.getUserName());
                dialog.dismiss();
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName,tvLastMessage,tvLastMessageTime;
        LinearLayout ll_chat,ll_imageview;
        CircleImageView ivProfilePic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName =itemView.findViewById(R.id.tvName);
            tvLastMessage =itemView.findViewById(R.id.tvLastMessage);
            tvLastMessageTime =itemView.findViewById(R.id.tvLastMessageTime);
            ll_chat =itemView.findViewById(R.id.ll_chat);
            ll_imageview =itemView.findViewById(R.id.ll_imageview);
            ivProfilePic =itemView.findViewById(R.id.ivProfilePic);
        }
    }

    private String formatTimestamp(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }



}
