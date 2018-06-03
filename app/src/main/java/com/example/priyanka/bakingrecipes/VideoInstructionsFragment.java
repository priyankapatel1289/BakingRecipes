package com.example.priyanka.bakingrecipes;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.priyanka.bakingrecipes.models.StepsModel;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class VideoInstructionsFragment extends Fragment {

    private ArrayList<StepsModel> videoUrlList = new ArrayList<>();
    private SimpleExoPlayer player;
    @BindView(R.id.video_view)
    SimpleExoPlayerView playerView;
    @BindView(R.id.image_thumbnail)
    ImageView imageThumbnail;
    Unbinder unbinder;
    private String individualVideoLink;
    private String PLAYBACK_POSITION = "playback position";
    private String CURRENT_WINDOW = "current window";

    private boolean playWhenReady = true;
    private int currentWindow;
    private long playbackPosition;

    public VideoInstructionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_video_instructions, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = this.getArguments();
        if (getArguments() != null) {
            individualVideoLink = bundle.getString("videoURL");
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null && videoUrlList != null) {
            if (playerView == null) {
                ButterKnife.bind(imageThumbnail);
                for (int i=0; i<videoUrlList.size(); i++) {
                    if (videoUrlList.get(i).getThumbnailURL().isEmpty()) {
                        imageThumbnail.setImageResource(android.R.drawable.stat_notify_error);
                    } else {
                        Picasso.with(view.getContext()).load(videoUrlList.get(i).getThumbnailURL()).into(imageThumbnail);
                    }
                }
            } else {
                ButterKnife.bind(playerView);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAYBACK_POSITION, playbackPosition);
        outState.putInt(CURRENT_WINDOW, currentWindow);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(PLAYBACK_POSITION)) {
                playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION, C.TIME_UNSET);
            } if (savedInstanceState.containsKey(CURRENT_WINDOW)) {
                currentWindow = savedInstanceState.getInt(CURRENT_WINDOW);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        initializePlayer();
    }

    @SuppressLint("InlineApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
        | View.SYSTEM_UI_FLAG_FULLSCREEN
        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.stop();
            player.release();
            player = null;
        }
    }

    private void initializePlayer() {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());
            playerView.setPlayer(player);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
        }

        List<MediaSource> list = new ArrayList<>();
        if (individualVideoLink != null) {
             Uri videoUri = Uri.parse(individualVideoLink);

            if (!videoUri.equals(Uri.EMPTY)) {
                MediaSource mediaSource = buildMediaSource(videoUri);
                list.add(mediaSource);
            }
        } else {
            for (int i = 0; i<videoUrlList.size(); i++) {
                Uri videoListUri = Uri.parse(videoUrlList.get(i).getVideoURL());

                if (!videoListUri.equals(Uri.EMPTY)) {
                    MediaSource mediaSource = buildMediaSource(videoListUri);
                    list.add(mediaSource);
                }
            }
        }

        MediaSource[] mediaSourcesToLoad = list.toArray(new MediaSource[list.size()]);

        MediaSource mediaSources = mediaSourcesToLoad.length == 1 ? mediaSourcesToLoad[0]
                : new ConcatenatingMediaSource(mediaSourcesToLoad);

        if (player != null) {
            if (playbackPosition != C.TIME_UNSET) {
                player.seekTo(playbackPosition);
            }
            player.prepare(mediaSources, false, false);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {

        ExtractorMediaSource videoSource =
                new ExtractorMediaSource.Factory(
                        new DefaultHttpDataSourceFactory("exoplayer")).
                        createMediaSource(uri);

        return new ConcatenatingMediaSource(videoSource);
    }

    public void setVideoUrlList(ArrayList<StepsModel> list) {
        videoUrlList = list;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
