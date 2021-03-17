package com.example.lanry.character;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.lanry.R;

import java.util.ArrayList;
import java.util.List;

public class Lanry extends Character {
    /*
     * @para bitmap: 图片
     * @para bullets: 存储子弹的list
     */
    private Context context;
    private float lanryTargetX, lanryTargetY;
    private List<Bullet> bullets = new ArrayList<Bullet>();
    private int booomCount;
    private int isLaser = 0;
    private int isShield = 0;
    private static int shootSpeed = 30;
    private static int shootLevel = 1;

    public Lanry(Context context) {
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.lanry);
        this.context = context;
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        speed = 10;
        x = context.getResources().getDisplayMetrics().widthPixels / 2;
        y = context.getResources().getDisplayMetrics().heightPixels - 300;
        lanryTargetX = x;
        lanryTargetY = y;
        HP = 3;
        booomCount = 3;
    }

    public void draw(Canvas meCanvas, Paint paint) {
        meCanvas.drawBitmap(bitmap, x - width / 2, y - height / 2, paint);
    }

    public void move() {
        float moveX = (lanryTargetX - x) / speed;
        float moveY = (lanryTargetY - y) / speed;
        x += moveX;
        y += moveY;
    }

    public void generateBullet(Bitmap bulletPic) {
        switch (shootLevel) {
            case 1:
                Bullet bullet = new Bullet(bulletPic, x, y - height / 2, x, 5000);
                bullet.setSpeed(30);
                bullets.add(bullet);
                break;
            case 2:
                Bullet bullet1 = new Bullet(bulletPic, x - 20, y - height / 2, x, 5000);
                bullet1.setSpeed(30);
                Bullet bullet2 = new Bullet(bulletPic, x + 20, y - height / 2, x, 5000);
                bullet2.setSpeed(30);
                bullets.add(bullet1);
                bullets.add(bullet2);
                break;
            case 3:
                Bullet bullet3 = new Bullet(bulletPic, x - 50, y - height / 2, x, 5000);
                bullet3.setSpeed(30);
                Bullet bullet4 = new Bullet(bulletPic, x, y - height / 2, x, 5000);
                bullet4.setSpeed(30);
                Bullet bullet5 = new Bullet(bulletPic, x + 50, y - height / 2, x, 5000);
                bullet5.setSpeed(30);
                bullets.add(bullet3);
                bullets.add(bullet4);
                bullets.add(bullet5);
                break;
        }
    }

    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public float getLanryTargetX() {
        return lanryTargetX;
    }

    public float getLanryTargetY() {
        return lanryTargetY;
    }

    public void setLanryTargetX(float lanryTargetX) {
        this.lanryTargetX = lanryTargetX;
    }

    public void setLanryTargetY(float lanryTargetY) {
        this.lanryTargetY = lanryTargetY;
    }

    public boolean isAlive() {
        return HP > 0;
    }

    public int getBooomCount() {
        return booomCount;
    }

    public void setBooomCount(int n) {
        booomCount = n;
    }

    public void setIsLaser(int n) {
        isLaser = n;
    }

    public int getIsLaser() {
        return isLaser;
    }

    public int getIsShield() {
        return isShield;
    }

    public void setIsShield(int isShield) {
        this.isShield = isShield;
    }

    public void countLaser() {
        isLaser--;
    }

    public int getShootSpeed() {
        return shootSpeed;
    }

    public void setShootSpeed(int shootSpeed) {
        if (shootSpeed >= 10) {
            this.shootSpeed = shootSpeed;
        }
    }

    public void countShield() {
        isShield--;
    }

    public int getShootLevel() {
        return shootLevel;
    }

    public void setShootLevel(int shootLevel) {
        if (shootLevel <= 3) {
            this.shootLevel = shootLevel;
        }
    }

    public static void initLanry() {
        shootLevel = 1;
        shootSpeed = 30;
    }
}
