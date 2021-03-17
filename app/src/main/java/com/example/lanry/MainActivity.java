package com.example.lanry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.lanry.character.Lanry;
import com.example.lanry.music.BGMusic;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);

        BGMusic.play(this, R.raw.china_rain);

        //设置SurfaceView
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Lanry.initLanry();
                Control.level = 1;

                MySurfaceView mySurfaceView = new MySurfaceView(MainActivity.this, true);
                setContentView(R.layout.game);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams
                        (FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                addContentView(mySurfaceView, params);

                //play bgm
                BGMusic.stop(getApplicationContext());
                BGMusic.play(getApplicationContext(), R.raw.thegirlfallinlove);
            }
        });

//        findViewById(R.id.randomStart).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Lanry.initLanry();
//
//                MySurfaceView mySurfaceView = new MySurfaceView(MainActivity.this, true);
//                setContentView(R.layout.game);
//                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams
//                        (FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//                addContentView(mySurfaceView, params);
//
//                //play bgm
//                BGMusic.stop(getApplicationContext());
//                BGMusic.play(getApplicationContext(), R.raw.thegirlfallinlove);
//            }
//        });

        findViewById(R.id.alpha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        BGMusic.stop(this);
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        BGMusic.play(this, R.raw.thegirlfallinlove);
    }
}