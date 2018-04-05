package com.example.priyanka.bakingrecipes;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.priyanka.bakingrecipes.models.RecipeModel;
import com.example.priyanka.bakingrecipes.models.RecipesListAdapter;

import java.util.ArrayList;

public class RecipesListFragment extends Fragment {

    public ArrayList<RecipeModel> recipeList = new ArrayList<>();
    private RecipesListAdapter adapter;

    interface RecipeListListener {
        void itemClicked(RecipeModel model);
    }
    private RecipeListListener listener;

    public RecipesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_recipes_list, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            RecyclerView mRecyclerView = view.findViewById(R.id.rv_fragment_layout);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new RecipesListAdapter(recipeList);
            mRecyclerView.setAdapter(adapter);
            adapter.setListener(new RecipesListAdapter.Listener() {
                @Override
                public void onClick(RecipeModel model)  {
                    if (listener != null) {
                        listener.itemClicked(model);
                    }
                }
            });
            new RecipeUtils(view.getContext(), new Data()).execute();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (RecipeListListener) context;
    }


    public class Data implements RecipeUtils.AsyncTaskCompleteListener {

        @Override
        public void onTaskComplete(ArrayList<RecipeModel> result) {
            adapter.setRecipesList(result);
//            Log.v("TAG", "THE VALUE OF RECIPElIST ===============??????????????????????? " + result);
        }
    }



}
