package com.example.recipe_scanner_app;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.MenuItemHolder> {
    private ArrayList<Bitmap> recipePreview;
    private ArrayList<String> recipeName;

    public MenuRecyclerAdapter(ArrayList<Bitmap> recipePreviews, ArrayList<String> recipeNames) {
        this.recipePreview = recipePreviews;
        this.recipeName = recipeNames;
    }
    @NonNull

    @Override
    public MenuItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate( R.layout.recipe_recyclerview, parent, false);
        return new MenuItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemHolder holder, int position) {

        // We set the texts and the image of our MenuItemHolder object
        holder.previewView.setImageBitmap(recipePreview.get(position));
        holder.nameView.setText(recipeName.get(position));
        holder.linearLayout.setBackgroundResource(R.drawable.ic_launcher_background);


    }

    @Override
    public int getItemCount() {
        return recipeName.size();
    }

    class MenuItemHolder extends RecyclerView.ViewHolder {
        ImageView previewView;
        TextView nameView;
        LinearLayout linearLayout;

        public MenuItemHolder(@NonNull View itemView) {
            super(itemView);

            // links the attributes with the recycler_row items
            previewView = itemView.findViewById(R.id.recipe_preview_view);
            nameView = itemView.findViewById(R.id.recipe_name_view);

            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
