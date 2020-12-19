package com.example.chatengine.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatengine.Models.AllUserModel;
import com.example.chatengine.Activities.PersonToPersonChat;
import com.example.chatengine.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AllUserAdapter extends RecyclerView.Adapter<AllUserAdapter.UserViewHolder> {

    private Context context;
    private ArrayList<AllUserModel> allUserModelArrayList;
    private FirebaseAuth firebaseAuth;

    public AllUserAdapter(Context context, ArrayList<AllUserModel> arraylist) {
        this.context = context;
        this.allUserModelArrayList = arraylist;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alluser_singleraw, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserViewHolder holder, final int position) {
        final AllUserModel users = allUserModelArrayList.get(position);

        holder.username.setText(users.getUsername());
        final String userid = users.getUid();

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth = FirebaseAuth.getInstance();

                if (userid.equals(firebaseAuth.getCurrentUser().getUid())){
                    Toast.makeText(context, "You can not message yourself.", Toast.LENGTH_SHORT).show();

                }else {

                    Intent intent = new Intent(view.getContext(), PersonToPersonChat.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("uid", userid);
                    bundle.putString("name", users.getUsername());
                    intent.putExtras(bundle);
                    view.getContext().startActivity(intent);

//                Toast.makeText(context, "You want sent message to " +
//                            holder.username.getText().toString() +"? this feature will be available soon..!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return allUserModelArrayList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        TextView username,mesgcount;
        CardView cardView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            cardView = itemView.findViewById(R.id.cardview);
            mesgcount = itemView.findViewById(R.id.mesgcount);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    String firebaseAuth = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
//
//                    Toast.makeText(view.getContext(), firebaseAuth, Toast.LENGTH_LONG).show();

//                    Toast.makeText(view.getContext(), "You want sent message to " +
//                            username.getText().toString() +"? this feature will be available soon..!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
