package com.example.priyanka.bakingrecipes;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.priyanka.bakingrecipes.models.StepsModel;
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
import com.google.android.exoplayer2.util.Util;
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

    private boolean playWhenReady = true;
    private int currentWindow;
    private long playbackPosition;
//    private Uri videoUri;

    public VideoInstructionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_video_instructions, container, false);
        unbinder = ButterKnife.bind(this, view);

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
                if (Util.SDK_INT >23) {
                    initializePlayer();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
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
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
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
        for (int i = 0; i<videoUrlList.size(); i++) {
            Uri videoUri = Uri.parse(videoUrlList.get(i).getVideoURL());

            if (!videoUri.equals(Uri.EMPTY)) {
                MediaSource mediaSource = buildMediaSource(videoUri);
                list.add(mediaSource);
            }
        }

        MediaSource[] mediaSourcesToLoad = list.toArray(new MediaSource[list.size()]);

        MediaSource mediaSources = mediaSourcesToLoad.length == 1 ? mediaSourcesToLoad[0]
                : new ConcatenatingMediaSource(mediaSourcesToLoad);
//
        if (player != null) {
            player.prepare(mediaSources);
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
