package com.example.lanry.tool;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Explotion {
    private float x;
    private float y;
    private int time;
    private Bitmap bitmap;

    public Explotion(float x, float y, int time, Bitmap bitmap) {
        this.x = x;
        this.y = y;
        this.time = time;
        this.bitmap = bitmap;
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmap, x - bitmap.getWidth() / 2, y - bitmap.getHeight() / 2, paint);
        time--;
    }

    public int getTime() {
        return time;
    }
}
