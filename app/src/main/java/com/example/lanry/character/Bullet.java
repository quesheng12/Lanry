package com.example.lanry.character;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bullet extends Character {
    private float dx;
    private float dy;

    public Bullet(Bitmap bitmap, float x, float y, float targetX, float targetY) {
        this.bitmap = bitmap;
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        this.x = x;
        this.y = y;
        dx = targetX - x;
        dy = targetY - y;
        speed = 20;
    }

    public Bullet(Bitmap bitmap, double x, double y, double targetX, double targetY) {
        this.bitmap = bitmap;
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        this.x = (float) x;
        this.y = (float) y;
        dx = (float) (targetX - x);
        dy = (float) (targetY - y);
        speed = 20;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void draw(Canvas meCanvas, Paint paint) {
        meCanvas.drawBitmap(bitmap, x - width / 2, y - height / 2, paint);
    }

    public void move() {
        int direct = (int) Math.sqrt(dx * dx + dy * dy);
        y -= speed * dy / direct;
        x -= speed * dx / direct;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
