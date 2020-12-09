package com.example.chatengine;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context context;
    private ArrayList<FriendlyMessage> arrayList;

    public MessageAdapter(Context context, ArrayList<FriendlyMessage> arraylist) {
        this.context = context;
        this.arrayList = arraylist;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_message,
                        parent,
                        false);

        // return itemView
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FriendlyMessage friendlyMessage = arrayList.get(position);

        String nametxt = friendlyMessage.getName();
        String mestxt = friendlyMessage.getText();

        holder.nametxt.setText(nametxt);
        holder.msgtext.setText(mestxt);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView msgtext, nametxt;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            msgtext = itemView.findViewById(R.id.messageTextView);
            nametxt = itemView.findViewById(R.id.nameTextView);

        }
    }
}
