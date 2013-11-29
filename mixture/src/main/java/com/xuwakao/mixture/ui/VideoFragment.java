package com.xuwakao.mixture.ui;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.xuwakao.mixture.R;

/**
 * Created by xujiexing on 13-11-11.
 */
public class VideoFragment extends Fragment {
    VideoView videoView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        videoView = (VideoView) getActivity().findViewById(R.id.video_view);
        videoView.setVideoPath(Environment.getExternalStorageDirectory() + "/Video/Download/test.mp4");
        videoView.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        videoView.suspend();
    }
}
