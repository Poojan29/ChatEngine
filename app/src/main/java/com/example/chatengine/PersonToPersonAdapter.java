package com.example.chatengine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class PersonToPersonAdapter extends RecyclerView.Adapter<PersonToPersonAdapter.PersonViewHolder> {

    private Context context;
    private ArrayList<PersonModel> persontopersonarray;

    private static final int LEFT_MSG = 0;
    private static final int RIGHT_MSG = 1;
    FirebaseUser firebaseUser;

    public PersonToPersonAdapter(Context context, ArrayList<PersonModel> persontopersonarray) {
        this.context = context;
        this.persontopersonarray = persontopersonarray;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == RIGHT_MSG){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right, parent, false);
            return new PersonViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, parent, false);
            return new PersonViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        PersonModel personModel = persontopersonarray.get(position);

        holder.nametxt.setText(personModel.getSender());
        holder.msgtxt.setText(personModel.getMsg());
        holder.date.setText(personModel.getTime());

    }

    @Override
    public int getItemCount() {
        return persontopersonarray.size();
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {

        TextView msgtxt, nametxt, date;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);

            msgtxt = itemView.findViewById(R.id.messageTextView);
            nametxt = itemView.findViewById(R.id.nameTextView);
            date = itemView.findViewById(R.id.date);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (persontopersonarray.get(position).getSenderuid().equals(firebaseUser.getUid())){
            return RIGHT_MSG;
        }else{
            return LEFT_MSG;
        }
    }
}
