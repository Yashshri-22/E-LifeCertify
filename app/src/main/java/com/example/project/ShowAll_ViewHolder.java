package com.example.project;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ShowAll_ViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView ppo_number;
    public ImageView profile_photo;


    public ShowAll_ViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.Name);
        ppo_number = itemView.findViewById(R.id.PpoNumber);
        profile_photo = itemView.findViewById(R.id.profile_photo);
    }
}
