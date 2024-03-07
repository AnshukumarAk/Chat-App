package com.anshu.chatapp.Adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anshu.chatapp.Models.MessageModel;
import com.anshu.chatapp.R;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter{

    ArrayList<MessageModel> messageModels;
   Context context;

   int SENDER_VIEW_TYPE=1;
   int RECEIVER_VIEW_TYPE=2;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_sender, parent, false);
            return new SenderviewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_reciver, parent, false);
            return new ReciverviewHolder(view);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid()))
        {
              return SENDER_VIEW_TYPE;

        }else {
              return RECEIVER_VIEW_TYPE;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageModel messageModel=messageModels.get(position);

        if (holder.getClass()==SenderviewHolder.class){
            ((SenderviewHolder)holder).senderMessage.setText(messageModel.getMessage());
        }else {
            ((ReciverviewHolder)holder).receiverMessage.setText(messageModel.getMessage());
        }


    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class ReciverviewHolder extends RecyclerView.ViewHolder {
        TextView receiverMessage,receiverTime;

        public ReciverviewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMessage=itemView.findViewById(R.id.recieverText);
            receiverTime=itemView.findViewById(R.id.recieverTime);
        }

    }

    public class SenderviewHolder extends RecyclerView.ViewHolder {
        TextView senderMessage,senderTime;

        public SenderviewHolder(@NonNull View itemView) {
            super(itemView);
            senderMessage=itemView.findViewById(R.id.senderText);
            senderTime=itemView.findViewById(R.id.senderTime);
        }

    }
}
