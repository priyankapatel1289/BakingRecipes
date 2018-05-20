package com.example.priyanka.bakingrecipes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class StepsFragment extends Fragment {

    private ArrayList<StepsModel> stepsList = new ArrayList<>();
    Unbinder unbinder;
    @BindView(R.id.rv_steps_fragment)
    RecyclerView recyclerView;

    public StepsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_steps, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            StepsListAdapter adapter = new StepsListAdapter(stepsList);
            recyclerView.setAdapter(adapter);
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
