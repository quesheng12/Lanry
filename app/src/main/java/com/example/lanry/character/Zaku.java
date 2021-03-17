package com.example.lanry.character;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.lanry.Control;
import com.example.lanry.R;

public class Zaku extends Character {

    public static final int STRAIGHT = 1;
    private float stopX;
    private float stopY;
    private int trackType;
    private Context context;
    private int bornFarme;
    private int hited = -1;

    public Zaku(Bitmap bitmap, Context context, float x, float y, float stopX, float stopY, int trackType, int bornFarme) {
        this.context = context;
        this.bitmap = bitmap;
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        this.x = x;
        this.y = y;
        this.stopX = stopX;
        this.stopY = stopY;
        HP = (Control.level + 1) / 2;
        speed = 3;
        this.trackType = trackType;
        this.bornFarme = bornFarme;
    }

    public void draw(Canvas meCanvas, Paint paint) {
        meCanvas.drawBitmap(bitmap, x - width / 2, y - height / 2, paint);
    }

    public void move(int trackType) {
        switch (trackType) {
            case STRAIGHT:
//                float speedX = (stopX - x) / speed;
//                float speedY = (stopY - y) / speed;
//                if (speedX > speed) {
//                    speedX = speed;
//                }
//                if (speedY > speed) {
//                    speedY = speed;
//                }
                float dx = stopX - x;
                float dy = stopY - y;
                double dl = Math.sqrt(dx * dx + dy * dy);
                double speedX = speed * dx / dl;
                double speedY = speed * dy / dl;

                if (Math.abs(speedX) < Math.abs(dx))
                    x += speedX;
                if (Math.abs(speedY) < Math.abs(dy))
                    y += speedY;
                break;
        }
    }

    public Bullet generateBullet(float lanryX, float lanryY, Bitmap bitmap) {
//        Bullet bullet = new Bullet(bitmap, x, y + height / 2, x, 3000);
//        bullet.setSpeed(6);
//        bullet.speed = -bullet.getSpeed();
//        return bullet;

        Bullet bullet = new Bullet(bitmap, x, y + height / 2, lanryX + 50, lanryY + 50);
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
