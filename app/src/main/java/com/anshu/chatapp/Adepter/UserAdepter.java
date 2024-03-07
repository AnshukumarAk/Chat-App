package com.anshu.chatapp.Adepter;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.anshu.chatapp.Activity.ChatDetailActivity;
import com.anshu.chatapp.Models.UserModel;
import com.anshu.chatapp.R;
import com.anshu.chatapp.Utills.SharedPrefHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
//
//
//        holder.ll_imageview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ShowImageProfile(holder.tvName.getText().toString().trim());
//
//            }
//        });



    }

//    private void ShowImageProfile(String name) {
//
//            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
//            View popupView = layoutInflater.inflate(R.layout.show_image_profile, null);
//            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
////            alertDialog.setTitle("Choose Image");
//
//
//            alertDialog.setView(popupView);
//            final AlertDialog dialog = alertDialog.show();
//            alertDialog.setCancelable(true);
//        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
//        TextView tv_name = popupView.findViewById(R.id.tv_name); // Replace R.id.textView with the actual ID of your TextView
//        tv_name.setText(name);
//
//
//    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName,tvOnlineCheckeUser;
        LinearLayout ll_chat,ll_imageview;
        CircleImageView ivProfilePic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName =itemView.findViewById(R.id.tvName);
            tvOnlineCheckeUser =itemView.findViewById(R.id.tvOnlineCheckeUser);
            ll_chat =itemView.findViewById(R.id.ll_chat);
            ll_imageview =itemView.findViewById(R.id.ll_imageview);
            ivProfilePic =itemView.findViewById(R.id.ivProfilePic);
        }
    }
}
