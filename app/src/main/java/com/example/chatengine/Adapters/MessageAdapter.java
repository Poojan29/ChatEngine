package com.example.chatengine.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatengine.Models.FriendlyMessage;
import com.example.chatengine.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_LEFT = 0;
    public static final int MSG_RIGHT = 1;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    private Context context;
    private ArrayList<FriendlyMessage> arrayList;

    public MessageAdapter(Context context, ArrayList<FriendlyMessage> arraylist) {
        this.context = context;
        this.arrayList = arraylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_RIGHT) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right, parent, false);
            // return itemView
            return new ViewHolder(itemView);
        }else{
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, parent, false);
            // return itemView
            return new ViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FriendlyMessage friendlyMessage = arrayList.get(position);

        String nametxt = friendlyMessage.getSender();
        String mestxt = friendlyMessage.getText();
        String datetext = friendlyMessage.getDate();


        holder.nametxt.setText(nametxt);
        holder.msgtext.setText(mestxt);
        holder.date.setText(datetext);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView msgtext, nametxt, date;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            msgtext = itemView.findViewById(R.id.messageTextView);
            nametxt = itemView.findViewById(R.id.nameTextView);
            date = itemView.findViewById(R.id.date);

        }
    }

    @Override
    public int getItemViewType(int position) {
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (arrayList.get(position).getUid().equals(firebaseUser.getUid())){
            return MSG_RIGHT;
        }else{
            return MSG_LEFT;
        }
    }

}
