package com.anshu.chatapp.Adepter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anshu.chatapp.R;

import java.time.LocalTime;
import java.util.ArrayList;

public class UserAdepter extends RecyclerView.Adapter<UserAdepter.ViewHolder> {

    Context context;
    ArrayList<String> list;
    int hour=0;
    int minute=0;

    public UserAdepter(Context context , ArrayList<String> list){
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

        holder.tvName.setText(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName,tvOnlineCheckeUser;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName =itemView.findViewById(R.id.tvName);
            tvOnlineCheckeUser =itemView.findViewById(R.id.tvOnlineCheckeUser);
        }
    }
}
