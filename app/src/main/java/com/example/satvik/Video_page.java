package com.example.satvik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class Video_page extends AppCompatActivity {
    VideoView vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_page);

        Button get_started = findViewById(R.id.get_started);
        get_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Video_page.this, login.class);
                startActivity(intent);

            }
        });

        vv=findViewById(R.id.vv);

        vv.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.fsd);

        // Create a MediaController
        MediaController med=new MediaController(this);

//        // Set the MediaController to the VideoView
//        vv.setMediaController(med);
//
//        // Set anchor view for the MediaController
//        med.setAnchorView(vv);

        // Start the video
        vv.start();

        // Listen for when the video is prepared
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                // Once prepared, set looping to true
                mediaPlayer.setLooping(true);
            }
        });
    }
}
