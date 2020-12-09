package com.example.chatengine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonToPersonAdapter extends RecyclerView.Adapter<PersonToPersonAdapter.PersonViewHolder> {

    private Context context;
    private ArrayList<PersonModel> persontopersonarray;

    public PersonToPersonAdapter(Context context, ArrayList<PersonModel> persontopersonarray) {
        this.context = context;
        this.persontopersonarray = persontopersonarray;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        PersonModel personModel = persontopersonarray.get(position);

        holder.nametxt.setText(personModel.getName());
        holder.msgtxt.setText(personModel.getMsg());
    }

    @Override
    public int getItemCount() {
        return persontopersonarray.size();
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {

        TextView msgtxt, nametxt;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);

            msgtxt = itemView.findViewById(R.id.messageTextView);
            nametxt = itemView.findViewById(R.id.nameTextView);
        }
    }
}
