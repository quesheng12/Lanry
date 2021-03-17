package com.example.lanry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_game_over);
        TextView textView = findViewById(R.id.textView);

        //设定最好成绩
        Control.bestLevel = Math.max(Control.bestLevel, Control.level);
        ((TextView) findViewById(R.id.best)).setText("Best Grade: Level" + Control.bestLevel);

        if (getIntent().getBooleanExtra("isWin", true)) {
            textView.setText("YOU WIN");
        } else {
            textView.setText("GAME OVER!");
        }

        findViewById(R.id.restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}