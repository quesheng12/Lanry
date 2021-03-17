package com.example.lanry.character;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class ShieldItem extends Bullet {
    private int bornFrame;

    public ShieldItem(Bitmap bitmap, float x, float y, float targetX, float targetY, int bornFrame) {
        super(bitmap, x, y, targetX, targetY);
        this.bornFrame = bornFrame;
        speed = -2.5f;
    }

    public int getBornFrame() {
        return bornFrame;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmap, x - width, y - height / 2, paint);
    }
}
