package com.example.priyanka.bakingrecipes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.priyanka.bakingrecipes.models.IngredientsListAdapter;
import com.example.priyanka.bakingrecipes.models.IngredientsModel;

import java.util.ArrayList;

public class IngredientsFragment extends Fragment {

    public ArrayList<IngredientsModel> ingredientsList = new ArrayList<>();

    public IngredientsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredients, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            RecyclerView recyclerView = view.findViewById(R.id.rv_ingredients_fragment);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            IngredientsListAdapter adapter = new IngredientsListAdapter(ingredientsList);
            recyclerView.setAdapter(adapter);
        }
    }

    public void setIngredientsList(ArrayList<IngredientsModel> list) {
        ingredientsList = list;
    }

}
