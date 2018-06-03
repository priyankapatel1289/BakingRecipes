package com.example.priyanka.bakingrecipes;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
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

public class RecipesListFragment extends android.support.v4.app.Fragment {

    public ArrayList<RecipeModel> recipeList = new ArrayList<>();
    private RecipesListAdapter adapter;
    @BindView(R.id.rv_fragment_layout)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    Parcelable mListState;
    private String SCROLL_POSITION = "scroll_postion";
    private int mPosition = RecyclerView.NO_POSITION;
    private String networkURL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    interface RecipeListListener {
        void itemClicked(RecipeModel model);
    }
    private RecipeListListener listener;

    public RecipesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        if (savedInstanceState != null) {
            mListState = savedInstanceState.getParcelable(SCROLL_POSITION);
        }
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
            if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
            mRecyclerView.smoothScrollToPosition(mPosition);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRecyclerView.getLayoutManager().onRestoreInstanceState(mListState);
                }
            }, 300);
            mRecyclerView.setAdapter(adapter);
            adapter.setListener(new RecipesListAdapter.Listener() {
                @Override
                public void onClick(RecipeModel model)  {
                    if (listener != null) {
                        listener.itemClicked(model);
                    }
                }
            });
            new RecipeUtils(view.getContext(), new Data()).execute(networkURL);
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
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mListState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(SCROLL_POSITION, mListState);
    }
}
