package com.example.lanry.character;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.lanry.Control;

public class Witch extends Character {
    public static final int STRAIGHT = 1;
    private float stopX;
    private float stopY;
    private int trackType;
    private Context context;
    private int bornFarme;
    private int hited = -1;

    public Witch(Bitmap bitmap, Context context, float x, float y, float stopX, float stopY, int trackType, int bornFarme) {
        this.context = context;
        this.bitmap = bitmap;
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        this.x = x;
        this.y = y;
        this.stopX = stopX;
        this.stopY = stopY;
        speed = 100;
        HP = Control.level / 2 + 1;
        this.trackType = trackType;
        this.bornFarme = bornFarme;
    }

    public void draw(Canvas meCanvas, Paint paint) {
        meCanvas.drawBitmap(bitmap, x - width / 2, y - height / 2, paint);
    }

    public void move(int trackType) {
        switch (trackType) {
            case STRAIGHT:
                float speedX = (stopX - x) / speed;
                float speedY = (stopY - y) / speed;
                if (speedX > speed) {
                    speedX = speed;
                }
                if (speedY > speed) {
                    speedY = speed;
                }
                x += speedX;
                y += speedY;
                break;
        }
    }

    public Bullet generateBullet(float lanryX, float lanryY, Bitmap bitmap) {
        Bullet bullet = new Bullet(bitmap, x, y + height / 2, lanryX, lanryY);
        bullet.setSpeed(7);
        bullet.speed = -bullet.getSpeed();
        return bullet;
    }

    public int getBornFarme() {
        return bornFarme;
    }

    public int getHited() {
        return hited;
    }

    public void setHited(int hited) {
        this.hited = hited;
    }

    public void setBornFarme(int bornFarme) {
        this.bornFarme = bornFarme;
    }
}
