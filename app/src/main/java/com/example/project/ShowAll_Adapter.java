package com.example.project;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShowAll_Adapter extends RecyclerView.Adapter<ShowAll_Adapter.ShowAll_ViewHolder> {

    private List<ShowAllModel> showAllModels;
    private ShowAll_Interface listener;

    public ShowAll_Adapter(ShowAll_Interface listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShowAll_Adapter.ShowAll_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_showall, parent, false);
        return new ShowAll_ViewHolder(view);
    }

    public ShowAll_Adapter(List<ShowAllModel> showAllModels) {
        this.showAllModels = showAllModels;
    }


    @Override
    public void onBindViewHolder(@NonNull ShowAll_Adapter.ShowAll_ViewHolder holder, int position) {
        ShowAllModel showAllModel = showAllModels.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClicked(showAllModel);
                }
            }
        });

        holder.name.setText(showAllModel.getName());
        holder.ppo_number.setText(String.valueOf(showAllModel.getPpo_number()));
        holder.profile_photo.setImageResource(showAllModel.getProfile_image());
    }

    @Override
    public int getItemCount() {
        if (showAllModels != null) {
            return showAllModels.size();
        } else {
            return 0; // or any other appropriate value
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setShowAllModels(List<ShowAllModel> showAllModels) {
        this.showAllModels = showAllModels;
        notifyDataSetChanged();
    }

    static class ShowAll_ViewHolder extends RecyclerView.ViewHolder {
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
}
