package com.example.lanry.character;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.lanry.Control;
import com.example.lanry.R;
import com.example.lanry.tool.BossLaserItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Boss extends Character {
    private Context context;
    private List<Bullet> bullets = new ArrayList<Bullet>();
    private List<BossLaserItem> preLasers = new ArrayList<BossLaserItem>();
    private List<BossLaserItem> bossLasers = new ArrayList<BossLaserItem>();
    private Queue<Integer> types = new LinkedList<Integer>();
    private Queue<Integer> ranY = new LinkedList<Integer>();
    private Bitmap bossBullet1;
    private Bitmap bossBullet2;
    private Bitmap bossLaserPic;
    private Bitmap preLaserPic;
    private int type;
    private int laserHit = 0;

    public Boss(Bitmap bitmap, Context context) {
        this.bitmap = bitmap;
        this.context = context;
        bossBullet1 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.bossbullet1);
        bossBullet2 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.bossbullet2);
        bossLaserPic = BitmapFactory.decodeResource(context.getResources(), R.mipmap.bosslaser);
        preLaserPic = BitmapFactory.decodeResource(context.getResources(), R.mipmap.prelaser);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        x = 310;
        y = 350;
        HP = Control.level * 150 - 100;
        //Attack type generate
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            types.add(r.nextInt(3));
        }

        for (int i = 0; i < 200; i++) {
            ranY.add(r.nextInt(context.getResources().getDisplayMetrics().widthPixels));
        }

        type = types.poll();
    }

    public void draw(Canvas canvas, Paint paint, int second, int f) {
        f++;
        int dir = second % 4;
        switch (dir) {
            case 0:
                x = 310 + f;
                break;
            case 1:
                x = 410;
                y = 350 - f;
                break;
            case 2:
                x = 410 - f;
                y = 250;
                break;
            case 3:
                x = 310;
                y = 250 + f;
                break;
        }

        canvas.drawBitmap(bitmap, x - width / 2, y - height / 2, paint);
        drawBullets(canvas, paint);
        moveBullet();
    }

    public void generateBullet(int second, int f) {
        int r = 20;
        int time = ((int) (second % 3)) * 100 + f;

        //30 degree
        double angle = Math.PI / 6;

        //25 degree
        double angle1 = Math.PI * 25 / 180;

        if (((second - 0) * 100 + f) % 300 == 0) {
            type = types.poll();
        }
        switch (type) {
            case 0:
                if ((second * 100 + f) % 30 == 0) {
                    addBulletType1(angle);
                    addBulletType1(angle * 2);
                    addBulletType1(angle * 3);
                    addBulletType1(angle * 4);
                    addBulletType1(angle * 5);
                }
                break;
            case 1:
                if (time % 5 == 0) {
                    int t = (int) time / 5;
                    double angleT = (angle / 2 + angle1 * t) % (Math.PI * 2);

                    if (angleT <= Math.PI * 150 / 180) {
                        addBulletType2(angleT);
                    } else {
                        addBulletType2(-Math.PI / 6 - angleT);
                    }
                }
                break;
            case 2:
                if (time % 100 == 0) {
//                    preLasers.add(new BossLaserItem(ranY.poll(), 30));
                    preLasers.add(new BossLaserItem(ranY.poll(), 40));
                    preLasers.add(new BossLaserItem(ranY.poll(), 40));
                    preLasers.add(new BossLaserItem(ranY.poll(), 40));
                }
                break;
        }

    }

    private void moveBullet() {
        for (Bullet bullet : bullets) {
            bullet.move();
        }
    }


    private void drawBullets(Canvas canvas, Paint paint) {
        for (Bullet bullet : bullets) {
            bullet.draw(canvas, paint);
        }


        //Draw Pre Boss Laser
        for (BossLaserItem preLaser : preLasers) {
            if (preLaser.remainTime > 0) {
                canvas.drawBitmap(preLaserPic, preLaser.X - preLaserPic.getWidth() / 2,
                        y - preLaserPic.getHeight() / 2 + height / 3, paint);
                preLaser.remainTime--;
            } else {
                bossLasers.add(new BossLaserItem(preLaser.X, 60));
                preLasers.remove(preLaser);
            }
        }


        //Draw boss Laser
        for (BossLaserItem bossLaser : bossLasers) {
            if (bossLaser.remainTime > 0) {
                canvas.drawBitmap(bossLaserPic, bossLaser.X - bossLaserPic.getWidth() / 2,
                        y + height / 3, paint);
                bossLaser.remainTime--;
            } else {
                bossLasers.remove(bossLaser);
            }
        }
    }

    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public List<BossLaserItem> getBossLasers() {
        return bossLasers;
    }

    public List<BossLaserItem> getPreLasers() {
        return preLasers;
    }

    private void addBulletType1(double angle) {
        int r = 20;
        float y = this.y + 50;
        float x = this.x;
        Bullet b = new Bullet(bossBullet1, Math.cos(angle) * r + x, Math.sin(angle) * r + y,
                Math.cos(angle) * 1000 + x, Math.sin(angle) * 1000 + y);
        b.setSpeed(-5);
        bullets.add(b);
    }

    private void addBulletType2(double angle) {
        int r = 20;
        float y = this.y + 50;
        float x = this.x;
        Bullet b = new Bullet(bossBullet2, Math.cos(angle) * r + x, Math.sin(angle) * r + y,
                Math.cos(angle) * 1000 + x, Math.sin(angle) * 1000 + y);
        b.setSpeed(-5);
        bullets.add(b);
    }

    public int getLaserHit() {
        return laserHit;
    }

    public void setLaserHit(int laserHit) {
        this.laserHit = laserHit;
    }
}
