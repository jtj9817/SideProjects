package com.example.josh.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VideoView vidView = (VideoView)findViewById(R.id.videoView);

        vidView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.potg);

        MediaController vid_media_ctrl = new MediaController(this);

        vid_media_ctrl.setAnchorView(vidView);

        vidView.setMediaController(vid_media_ctrl);

        vidView.start();
    }
}
