package com.example.recycleview.classes;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleview.R;
import com.example.recycleview.Models.DataModel;

import java.util.ArrayList;

public class CustomeAdapter extends RecyclerView.Adapter<CustomeAdapter.MyViewHolder> {

    private ArrayList<DataModel> originalList;
    private ArrayList<DataModel> filteredList;

    public CustomeAdapter(ArrayList<DataModel> data) {
        filteredList = data;
        originalList = new ArrayList<>(data);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataModel item = filteredList.get(position);
        holder.name.setText(item.getName());
        holder.desc.setText(item.getDescription());
        holder.image.setImageResource(item.getImage());

        holder.itemView.setOnClickListener(v -> {
            ImageView copy = new ImageView(v.getContext());
            copy.setImageDrawable(holder.image.getDrawable());
            copy.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));

            AlertDialog alert = new AlertDialog.Builder(v.getContext())
                    .setTitle(holder.name.getText())
                    .setMessage(holder.desc.getText())
                    .setView(copy)
                    .setPositiveButton("Close", (d, w) -> d.dismiss())
                    .create();

            alert.show();
            if (alert.getWindow() != null) {
                alert.getWindow().setLayout(800, 800);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void filter(String text) {
        String query = text.toLowerCase();
        filteredList = new ArrayList<>();
        if (query.isEmpty()) {
            filteredList.addAll(originalList);
        } else {
            for (DataModel model : originalList) {
                if (model.getName().toLowerCase().contains(query)) {
                    filteredList.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, desc;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView);
            desc = itemView.findViewById(R.id.textView2);
            image = itemView.findViewById(R.id.imageView);
        }
    }
}
