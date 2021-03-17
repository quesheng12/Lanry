package com.example.lanry.background;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;


public class MainBackground {
    private int x;
    private int y;
    private Bitmap bitmap;

    public MainBackground(Bitmap bitmap) {
        this.bitmap = bitmap;
        x = 0;
        y = x - bitmap.getHeight();
    }


    public void draw(Canvas canvas) {
        logic();
        Paint paint = new Paint();
        canvas.drawBitmap(bitmap, 0, x, paint);
        canvas.drawBitmap(bitmap, 0, y, paint);
    }

    public void logic() { //逻辑方法
        x += 10;
        y += 10;
        if (x >= bitmap.getHeight()) {
            x = y - bitmap.getHeight(); //移动在第二张上面

        }
        if (y >= bitmap.getHeight()) {
            y = x - bitmap.getHeight();
        }
    }
}
