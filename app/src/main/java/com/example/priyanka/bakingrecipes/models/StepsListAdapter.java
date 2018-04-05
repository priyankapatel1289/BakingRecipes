package com.example.priyanka.bakingrecipes.models;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.priyanka.bakingrecipes.R;

import java.util.List;

public class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.StepsViewHolder> {
    private List<StepsModel> stepsList;

    public StepsListAdapter(List<StepsModel> stepsList) {
        this.stepsList = stepsList;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.steps_list_layout, parent, false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        StepsModel stepsModel = stepsList.get(position);
        holder.stepsTextView.setText(stepsModel.getDescription());
    }

    @Override
    public int getItemCount() {
        if (stepsList == null)
        return 0;
        return stepsList.size();
    }

    class StepsViewHolder extends RecyclerView.ViewHolder {
        private TextView stepsTextView;

        StepsViewHolder(View itemView) {
            super(itemView);
            stepsTextView = itemView.findViewById(R.id.tv_steps_description);
        }
    }
}
