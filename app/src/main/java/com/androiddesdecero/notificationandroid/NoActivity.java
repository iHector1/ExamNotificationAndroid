package com.androiddesdecero.notificationandroid;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class NoActivity extends AppCompatActivity {
    protected VideoView videoView;
    protected MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no);
        videoView = findViewById(R.id.videoView);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
//Reproducir un video en especifico
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/"
                + R.raw.video);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
        Toast.makeText(this, "Comienza video", Toast.LENGTH_LONG).show();
        videoView.setMediaController(mediaController);
    }
}
