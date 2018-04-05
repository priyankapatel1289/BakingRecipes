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


/**
 * A simple {@link Fragment} subclass.
 */
public class StepsFragment extends Fragment {

    private ArrayList<StepsModel> stepsList = new ArrayList<>();

    public StepsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_steps, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            RecyclerView recyclerView = view.findViewById(R.id.rv_steps_fragment);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            StepsListAdapter adapter = new StepsListAdapter(stepsList);
            recyclerView.setAdapter(adapter);
        }
    }

    public void setStepsList(ArrayList<StepsModel> list) {
        stepsList = list;
//        Log.v("TAG", "INGREDIENTS LIST ===================== " + stepsList);
    }
}
