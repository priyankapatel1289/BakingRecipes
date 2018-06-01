package com.example.priyanka.bakingrecipes.models;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.priyanka.bakingrecipes.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.StepsViewHolder> {
    private List<StepsModel> stepsList;
    private Listener listener;
    private VideoClickListener videoClickListener;
    private String videoURL;
//    public StepsModel stepsModel = new StepsModel();

    public interface Listener {
        void onClick(StepsModel stepsModel);
//        void videoClick(View v, int position);
    }

    public interface VideoClickListener {
        void videoClickListener(StepsModel stepsModel);
    }

    public void setVideoClickListener(VideoClickListener videoClickListener) {
        this.videoClickListener = videoClickListener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }



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
    public void onBindViewHolder(final StepsViewHolder holder, final int position) {
        final StepsModel stepsModel = stepsList.get(position);
        videoURL = stepsModel.getVideoURL();
        if (videoURL != null && !videoURL.isEmpty()) {
            holder.videoIcon.setVisibility(View.VISIBLE);
        }

        holder.stepsDetails.setText(stepsModel.getDescription());
        holder.stepsShortDescription.setText(String.format("%s. %s", String.valueOf(position), stepsModel.getShortDescription()));
        final StepsModel model = stepsList.get(holder.getAdapterPosition());
        holder.stepsShortDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(model);
                    if (!stepsModel.getDescription().isEmpty()) {
                        holder.stepsDetails.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        holder.videoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoClickListener != null) {
                    videoClickListener.videoClickListener(model);
                }
            }
        });

//        holder.videoIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if ( != null) {
//                    listener.videoClick(holder.videoIcon, position);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (stepsList == null)
        return 0;
        return stepsList.size();
    }

    class StepsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_steps_details)
        TextView stepsDetails;
        @BindView(R.id.tv_steps_short_description)
        TextView stepsShortDescription;
        @BindView(R.id.image_video)
        android.widget.ImageView videoIcon;

        StepsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
