package com.anshu.chatapp.Adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anshu.chatapp.Models.UserModel;
import com.anshu.chatapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddFriendAdapter extends RecyclerView.Adapter<AddFriendAdapter.ViewHolder> {

    Context context;
    ArrayList<UserModel> list;

    public AddFriendAdapter(Context context, ArrayList<UserModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public AddFriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custome_add_friends, parent, false);
        return new AddFriendAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddFriendAdapter.ViewHolder holder, int position) {

        UserModel userModel = list.get(position);

        Picasso.get().load(userModel.getProfilePic()).
                placeholder(R.drawable.image).into(holder.ivProfilePic);
        holder.tvName.setText(userModel.getUserName());


        // Assuming you have a reference to the Firebase database
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");

       // Get the current user's ID or any unique identifier
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();


      // Sender (Anshu)
        String senderId = FirebaseAuth.getInstance().getCurrentUser().getUid();// Anshu's user ID
        String recipientId = userModel.getUserId(); // Deepak's user ID
        String requestStatus = "pending"; // Pending status for the request

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("requestStatus", requestStatus);


//        holder.AddFriend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                usersRef.child(recipientId).child("friendRequests").child(senderId).
//                        setValue(requestData)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(context, "Request Send Successfully..", Toast.LENGTH_SHORT).show();
//
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                // Handle any errors
//                                Toast.makeText(context, "Failed to update request status", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        LinearLayout ll_chat,ll_imageview;
        CircleImageView ivProfilePic;
        Button AddFriend;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName =itemView.findViewById(R.id.tvUserName);
            ivProfilePic =itemView.findViewById(R.id.ivProfilePic);
            AddFriend =itemView.findViewById(R.id.btnAddFriend);

        }
    }
}
