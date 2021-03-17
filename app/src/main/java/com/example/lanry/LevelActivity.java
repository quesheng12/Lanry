package com.example.lanry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.lanry.character.Lanry;
import com.example.lanry.music.BGMusic;

import java.util.List;
import java.util.logging.Level;

public class LevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_level);

        //play bgm
        BGMusic.play(this, R.raw.china_rain);

        //设定最好成绩
        Control.bestLevel = Math.max(Control.bestLevel, Control.level);

        ((TextView) findViewById(R.id.best)).setText("Best Grade: Level" + Control.bestLevel);
        ((Button) findViewById(R.id.nextLevel)).setText("Level " + (Control.level + 1));


        //nextLevel button onclick
        findViewById(R.id.nextLevel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Control.level++;
                MySurfaceView mySurfaceView = new MySurfaceView(LevelActivity.this, true);
                setContentView(R.layout.game);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams
                        (FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                addContentView(mySurfaceView, params);

                //play bgm
                BGMusic.stop(getApplicationContext());
                BGMusic.play(getApplicationContext(), R.raw.thegirlfallinlove);
            }
        });

        //back button set onclick
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lanry.initLanry();
                Intent intent = new Intent(LevelActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
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