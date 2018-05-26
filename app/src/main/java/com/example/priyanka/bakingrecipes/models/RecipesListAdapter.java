package com.example.priyanka.bakingrecipes.models;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.priyanka.bakingrecipes.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.RecipesViewHolder> {
    private List<RecipeModel> recipesList;
    private Listener listener;
    private ArrayList<Integer> images = new ArrayList<>();

    public interface Listener {
        void onClick(RecipeModel model);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public RecipesListAdapter(ArrayList<RecipeModel> recipesList) {
        this.recipesList = recipesList;
    }

    @Override
    public RecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipes_list_layout, parent, false);
        return new RecipesViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecipesViewHolder holder, int position) {

        images.add(R.drawable.nutella_pie);
        images.add(R.drawable.brownies);
        images.add(R.drawable.yellow_cake);
        images.add(R.drawable.cheesecake);

        RecipeModel recipeModel = recipesList.get(position);
        holder.recipeTextView.setText(recipeModel.getName());
        holder.recipeImageView.setImageResource(images.get(position));
        final RecipeModel model = recipesList.get(holder.getAdapterPosition());
        holder.recipeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!= null) listener.onClick(model);
            }
        });
        holder.recipeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onClick(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (recipesList == null)
            return 0;
            return recipesList.size();
    }

    public void setRecipesList(List<RecipeModel> mRecipesList) {
        recipesList = mRecipesList;
        notifyDataSetChanged();
    }

    public class RecipesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_recipe__list_rv)
        TextView recipeTextView;
        @BindView(R.id.image_recipe)
        ImageView recipeImageView;

        private RecipesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
