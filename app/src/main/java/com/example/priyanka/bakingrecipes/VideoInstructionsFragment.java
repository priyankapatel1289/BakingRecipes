package com.example.priyanka.bakingrecipes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.priyanka.bakingrecipes.models.StepsModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoInstructionsFragment extends Fragment {

    private ArrayList<StepsModel> videoUrlList = new ArrayList<>();


    public VideoInstructionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_instructions, container, false);
    }

    public void setVideoUrlList(ArrayList<StepsModel> list) {
        videoUrlList = list;
    }

}
