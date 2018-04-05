package com.example.priyanka.bakingrecipes.models;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.priyanka.bakingrecipes.R;

import java.util.ArrayList;
import java.util.List;

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.IngredientsViewHolder> {
    private List<IngredientsModel> ingredientsList;

    public IngredientsListAdapter(ArrayList<IngredientsModel> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredients_list_layout, parent, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position) {
        IngredientsModel ingredientsModel = ingredientsList.get(position);
        holder.quantityTextView.setText(String.valueOf(ingredientsModel.getQuantity()));
        holder.measurementTextView.setText(ingredientsModel.getMeasure());
        holder.ingredientTextView.setText(ingredientsModel.getIngredient());
    }

    @Override
    public int getItemCount() {
        if (ingredientsList == null)
        return 0;
        return ingredientsList.size();
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder {
        private TextView quantityTextView;
        private TextView measurementTextView;
        private TextView ingredientTextView;

        private IngredientsViewHolder(View itemView) {
            super(itemView);
            quantityTextView = itemView.findViewById(R.id.tv_ingredients_quantity);
            measurementTextView = itemView.findViewById(R.id.tv_ingredients_measurement);
            ingredientTextView = itemView.findViewById(R.id.tv_ingredients_ingredient);
        }
    }
}
