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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipesListFragment extends Fragment {

    public ArrayList<RecipeModel> recipeList = new ArrayList<>();
    private RecipesListAdapter adapter;
    @BindView(R.id.rv_fragment_layout)
    RecyclerView mRecyclerView;
    Unbinder unbinder;

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

        View view = inflater.inflate(R.layout.fragment_recipes_list, container, false);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public class Data implements RecipeUtils.AsyncTaskCompleteListener {

        @Override
        public void onTaskComplete(ArrayList<RecipeModel> result) {
            adapter.setRecipesList(result);
//            Log.v("TAG", "THE VALUE OF RECIPElIST ===============??????????????????????? " + result);
        }
    }





}
