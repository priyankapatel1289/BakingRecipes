package com.example.priyanka.bakingrecipes;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.priyanka.bakingrecipes.models.StepsListAdapter;
import com.example.priyanka.bakingrecipes.models.StepsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StepsFragment extends Fragment {

    private ArrayList<StepsModel> stepsList = new ArrayList<>();
    private String SCROLL_POSITION = "scroll_postion";
    Parcelable mListState;
    private int mPosition = RecyclerView.NO_POSITION;
    Unbinder unbinder;
    @BindView(R.id.rv_steps_fragment)
    RecyclerView recyclerView;

    interface StepsListListener {
        void itemClicked(StepsModel model);
    }
    private StepsListListener listener;

    interface VideoClickListener {
        void videoClicked(StepsModel model);
    }
    public VideoClickListener videoClickListener;

    public StepsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_steps, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mListState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(SCROLL_POSITION, mListState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(SCROLL_POSITION)) {
                mListState = savedInstanceState.getParcelable(SCROLL_POSITION);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        if (view != null) {
            recyclerView.setLayoutManager(linearLayoutManager);
            if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
            recyclerView.smoothScrollToPosition(mPosition);

            if (mListState != null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.getLayoutManager().onRestoreInstanceState(mListState);
                    }
                }, 300);
            }

            final StepsListAdapter adapter = new StepsListAdapter(stepsList);
            recyclerView.setAdapter(adapter);
            adapter.setListener(new StepsListAdapter.Listener() {
                @Override
                public void onClick(StepsModel stepsModel) {
                    if (listener != null) {
                        listener.itemClicked(stepsModel);
                    }
                }
            });
            adapter.setVideoClickListener(new StepsListAdapter.VideoClickListener() {
                @Override
                public void videoClickListener(StepsModel stepsModel) {
                    if (videoClickListener != null) {
                        videoClickListener.videoClicked(stepsModel);
                    }
                }
            });
        }

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof VideoClickListener) {
            videoClickListener = (VideoClickListener) context;
            }
    }

    public void setStepsList(ArrayList<StepsModel> list) {
        stepsList = list;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
