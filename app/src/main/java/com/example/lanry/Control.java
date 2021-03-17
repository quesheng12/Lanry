package com.example.lanry;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.example.lanry.character.Boss;
import com.example.lanry.character.Bullet;
import com.example.lanry.character.Lanry;
import com.example.lanry.character.LaserItem;
import com.example.lanry.character.ShieldItem;
import com.example.lanry.character.Witch;
import com.example.lanry.character.Zaku;
import com.example.lanry.tool.BossLaserItem;
import com.example.lanry.tool.Explotion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Control {
    private Context context;
    private float[] zakuBX = {645f, 75f, 550f, 170f, 455f, 265f, 360f, 265f, 455f, 170f, 550f, 75f, 645f, 0f, 720f, 0f, 720f, 0f, 0f, 0f, 720f, 720f};
    private float[] zakuBY = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 200f, 200f, 400f, 400f, 200f, 200f, 200f, 200f, 200f};
    private float[] zakuEX = {75f, 645f, 170f, 550f, 265f, 455f, 360f, 455f, 265f, 550f, 170f, 645f, 75f, 250f, 470f, 150f, 570f, 30f, 150f, 360f, 580f, 700f};
    private float[] zakuEY = {410f, 410f, 345f, 345f, 280f, 280f, 215f, 150f, 150f, 85f, 85f, 20f, 20f, 200f, 200f, 400f, 400f, 500f, 500f, 500f, 500f, 500f};
    private int[] zakuTimeView = {100, 101, 130, 131, 160, 161, 190, 210, 211, 240, 241, 270, 271, 300, 310, 320, 330, 400, 401, 402, 403, 404};
    private float[] witchBX = {0f, 720f, 0f, 580f, 360f, 120f, 0f, 720f, 360f};
    private float[] witchBY = {150f, 300f, 450f, 0f, 0f, 0f, 600f, 600f, 400f};
    private float[] witchEX = {500f, 220f, 500f, 120f, 360f, 580f, 100f, 620f, 360f};
    private float[] witchEY = {150f, 300f, 450f, 600f, 300f, 600f, 300f, 300f, 500f};
    private int[] witchTimeView = {300, 301, 302, 450, 451, 452, 501, 502, 503};
    private int[] laserItemBX = {360, 520, 430, 140};
    private int[] laserItemView = {100, 500, 900, 1200};
    private int[] shieldItemBX = {500, 630, 320, 550};
    private int[] shieldItemView = {40, 300, 600, 820};
    private Queue<Float> zakuBXList = new LinkedList<Float>();
    private Queue<Float> zakuBYList = new LinkedList<Float>();
    private Queue<Float> zakuEXList = new LinkedList<Float>();
    private Queue<Float> zakuEYList = new LinkedList<Float>();
    private Queue<Float> witchBXList = new LinkedList<Float>();
    private Queue<Float> witchBYList = new LinkedList<Float>();
    private Queue<Float> witchEXList = new LinkedList<Float>();
    private Queue<Float> witchEYList = new LinkedList<Float>();
    private Queue<LaserItem> initLaserItems = new LinkedList<LaserItem>();
    private List<LaserItem> laserItems = new LinkedList<LaserItem>();
    private Queue<ShieldItem> initShieldItems = new LinkedList<ShieldItem>();
    private List<ShieldItem> shieldItems = new LinkedList<ShieldItem>();
    private Queue<Integer> laserItemBXList = new LinkedList<Integer>();
    private Queue<Integer> shieldItemBXList = new LinkedList<Integer>();
    int scWidth;
    int scHeight;
    int lanryHited = 0;
    int lanryKill = 0;
    int lanryLevelUp = 0;
    int bossTime = 100000;
    private float boooomX = 0.0f;
    private float boooomY = 0.0f;
    private Lanry lanry;
    private Queue<Zaku> initZakus = new LinkedList<Zaku>();
    private List<Zaku> zakus = new ArrayList<Zaku>();
    private Queue<Witch> initWitches = new LinkedList<Witch>();
    private List<Witch> witches = new ArrayList<Witch>();
    private List<Bullet> ebullets = new ArrayList<Bullet>();
    private Paint paint;
    public boolean isWin = true;
    private Bitmap zakuPic, witchPic;
    private Bitmap heart;
    private Bitmap energy;
    private Bitmap explotionPic;
    private Bitmap bulletPic;
    private Bitmap ebulletPic, witchbulletPic;
    private Bitmap laserPic1, laserPic2, laserItemPic, shieldItemPic;
    private Bitmap boooom1, boooom2, boooom3, boooom4, boooom5, boooom6, boooom7, boooom8, boooom9, boooom10,
            boooom11, boooom12, boooom13, boooom14, boooom15, boooom16, boooom17, boooom18, boooom19, boooom20;
    private Bitmap shootE0, shootE1, shootE2, shootE3, shootE4, shootE5;
    private Bitmap levelup1, levelup2;
    private Bitmap bossPic;
    private ArrayList<Explotion> explotions = new ArrayList<Explotion>();

    private Boss boss;

    public static int level = 1;
    public static int bestLevel = 1;

    public Control(Context context, Paint paint, Boolean isRandom) {
        this.context = context;
        lanry = new Lanry(context);
        this.paint = paint;
        scWidth = context.getResources().getDisplayMetrics().widthPixels;
        scHeight = context.getResources().getDisplayMetrics().heightPixels;

        //加载图片
        loadImg();


        if (isRandom) {
            //随机关卡
            randomInit();
        } else {
            //加载人物资源
            characterInit();
        }

    }


    public boolean gameControl(int second, int f, Canvas canvas) {
        try {
            if (zakus.isEmpty() && initZakus.isEmpty()
                    && witches.isEmpty() && initWitches.isEmpty()) {
                if (level >= 3) {
                    if (boss.getHP() <= 0) {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                //items初始化
                if (initZakus.peek() != null) {
                    if (second * 100 + f >= initZakus.peek().getBornFarme()) {
                        zakus.add(initZakus.poll());
                    }
                }
                if (initWitches.peek() != null) {
                    if (second * 100 + f >= initWitches.peek().getBornFarme()) {
                        witches.add(initWitches.poll());
                    }
                }
                if (initLaserItems.peek() != null) {
                    if (second * 100 + f >= initLaserItems.peek().getBornFrame()) {
                        laserItems.add(initLaserItems.poll());
                    }
                }
                if (initShieldItems.peek() != null) {
                    if (second * 100 + f >= initShieldItems.peek().getBornFrame()) {
                        shieldItems.add(initShieldItems.poll());
                    }
                }
            }
        } catch (Exception ignored) {
        }

        //Boss logic
        if (second >= bossTime && level >= 3) {
            bossLogic(canvas, second, f);
            if (!zakus.isEmpty() || !witches.isEmpty()) {
                for (Zaku zaku : zakus) {
                    if (zaku.getHP() > 0) {
                        zaku.setHP(0);
                        zaku.setHited(10);
                    }
                }

                for (Witch witch : witches) {
                    if (witch.getHP() > 0) {
                        witch.setHP(0);
                        witch.setHited(10);
                    }
                }
            }
        }


        //lanry子弹升级
        if (lanryKill > 5) {
            lanryKill = 0;
            lanry.setShootSpeed(lanry.getShootSpeed() - 5);

            //Set level up time
            lanryLevelUp = 60;

            if (lanry.getShootSpeed() <= 10) {
                if (lanry.getShootLevel() < 3) {
                    lanry.setShootLevel(lanry.getShootLevel() + 1);
                    lanry.setShootSpeed(30);
                } else {
                    if (lanry.getHP() < 3) {
                        lanry.setHP(lanry.getHP() + 1);
                    }
                }
            }
        }

        //Explotion draw
        for (Explotion e : explotions) {
            if (e.getTime() > 0) {
                e.draw(canvas, paint);
            }
        }


        //Items deal 处理item
        itemsDeal(canvas);

        //zaku逻辑处理
        zakuCalculate(canvas, second, f);

        //Witch逻辑处理
        witchCalculate(canvas, second, f);

        List<Bullet> ebulletsRemoved = new ArrayList<Bullet>();
        //Lanry击中检测
        for (int i = 0; i < ebullets.size(); i++) {
            Bullet bullet = ebullets.get(i);
            float[] hitbox = lanry.getHitbox();
            //被击中后短暂无敌
            if (lanryHited == 0) {
                if (hitCheck(bullet.getX(), bullet.getY(), hitbox)) {
                    // Lanry被击中后的判定和动画
                    if (lanry.getIsShield() <= 0) {
                        lanry.setHP(lanry.getHP() - 1);
                        lanryHited = 50;
                    }
                    ebulletsRemoved.add(bullet);
                }
            }

            if (ebullets.get(i) != null) {
                bullet.draw(canvas, paint);
                bullet.move();
            }

            //移除超出屏幕的bullet
            if (bullet.getY() > context.getResources().getDisplayMetrics().heightPixels) {
                ebulletsRemoved.add(bullet);
            }
        }

        for (int i = 0; i < ebulletsRemoved.size(); i++) {
            ebullets.remove(ebulletsRemoved.get(i));
        }

        if (!lanry.isAlive()) {
            isWin = false;
            return false;
        } else {
            drawHP(canvas);
            drawShoote(canvas);
            drawEnergy(canvas);
            return true;
        }
    }

    private void drawHP(Canvas canvas) {
        canvas.drawBitmap(heart, 20, scHeight - 200, paint);
        switch (lanry.getHP()) {
            case 2:
                canvas.drawBitmap(heart, 100, scHeight - 200, paint);
                break;
            case 3:
                canvas.drawBitmap(heart, 100, scHeight - 200, paint);
                canvas.drawBitmap(heart, 180, scHeight - 200, paint);
                break;
        }
    }

    //击杀能量条
    private void drawShoote(Canvas canvas) {
        switch (lanryKill) {
            case 0:
                canvas.drawBitmap(shootE0, 20, scHeight - 270, paint);
                break;
            case 1:
                canvas.drawBitmap(shootE1, 20, scHeight - 270, paint);
                break;
            case 2:
                canvas.drawBitmap(shootE2, 20, scHeight - 270, paint);
                break;
            case 3:
                canvas.drawBitmap(shootE3, 20, scHeight - 270, paint);
                break;
            case 4:
                canvas.drawBitmap(shootE4, 20, scHeight - 270, paint);
                break;
            case 5:
                canvas.drawBitmap(shootE5, 20, scHeight - 270, paint);
                break;
        }
    }

    private void drawEnergy(Canvas canvas) {
        switch (lanry.getBooomCount()) {
            case 1:
                canvas.drawBitmap(energy, 20, scHeight - 130, paint);
                break;
            case 2:
                canvas.drawBitmap(energy, 20, scHeight - 130, paint);
                canvas.drawBitmap(energy, 100, scHeight - 130, paint);
                break;
            case 3:
                canvas.drawBitmap(energy, 20, scHeight - 130, paint);
                canvas.drawBitmap(energy, 100, scHeight - 130, paint);
                canvas.drawBitmap(energy, 180, scHeight - 130, paint);
                break;
        }
    }


    //处理点击事件
    public boolean touchDeal(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            lanry.setLanryTargetX((lanry.getLanryTargetX() + lanry.getX()) / 2);
            lanry.setLanryTargetY((lanry.getLanryTargetY() + lanry.getY()) / 2);
        } else if (event.getAction() != MotionEvent.ACTION_DOWN) {
            lanry.setLanryTargetX(event.getX());
            lanry.setLanryTargetY(event.getY() - lanry.getHeight() / 2);
        }
        return true;
    }

    public void drawLanry(Canvas mCanvas, Paint paint, int f, int sencond) {
        lanry.move();
        lanry.draw(mCanvas, paint);

        //有护盾时画护盾
        if (lanry.getIsShield() > 0) {
            mCanvas.drawBitmap(boooom1, lanry.getX() - boooom1.getWidth() / 2, lanry.getY() - boooom1.getWidth() / 2, paint);
            lanry.countShield();
        }

        //被击中后的爆炸画面
        if (lanryHited > 0) {
            mCanvas.drawBitmap(explotionPic, lanry.getX() - explotionPic.getWidth() / 2, lanry.getY() - explotionPic.getHeight() / 2, paint);
            lanryHited--;
        }

        //level up animation draw
        if (lanryLevelUp > 30) {
            mCanvas.drawBitmap(levelup1, lanry.getX() - levelup1.getWidth() / 2, lanry.getY() - levelup1.getWidth() / 2, paint);
            lanryLevelUp--;
        } else if (lanryLevelUp > 0) {
            mCanvas.drawBitmap(levelup2, lanry.getX() - levelup2.getWidth() / 2, lanry.getY() - levelup2.getWidth() / 2, paint);
            lanryLevelUp--;
        }

        List<Bullet> bullets = lanry.getBullets();
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            bullet.draw(mCanvas, paint);
            bullet.move();
            if (bullet.getY() < 0) {
                lanry.removeBullet(bullet);
            }
        }

        //Lanry子弹生成 Lanry激光生成
        if (lanry.getIsLaser() > 0) {
            if (f % 4 == 0) {
                mCanvas.drawBitmap(laserPic1, lanry.getX() - laserPic1.getWidth() / 2, lanry.getY() - laserPic1.getHeight() - lanry.getHeight() / 2, paint);
            } else {
                mCanvas.drawBitmap(laserPic2, lanry.getX() - laserPic2.getWidth() / 2, lanry.getY() - laserPic1.getHeight() - lanry.getHeight() / 2, paint);
            }
            lanry.countLaser();
        } else {
            if ((sencond * 100 + f) % lanry.getShootSpeed() == 0) {
                lanry.generateBullet(bulletPic);
            }
        }
    }


    public boolean hitCheck(float x, float y, float[] hitbox) {
        return x > hitbox[0] && x < hitbox[1] && y > hitbox[2] && y < hitbox[3];
    }


    //check 两个hitbox是否重叠
    public boolean hitCheck(float[] hitbox1, float[] hitbox2) {
        return !(hitbox1[1] < hitbox2[0] || hitbox1[0] > hitbox2[0] || hitbox1[3] < hitbox2[2] || hitbox1[2] > hitbox2[3]);
    }

    public void boooom() {
//        zakus.clear();
        for (Zaku zaku : zakus) {
            if (zaku.getHP() > 0) {
                zaku.setHited(10);
            }
            zaku.setHP(0);
        }
        for (Witch witch : witches) {
            if (witch.getHP() > 0) {
                witch.setHited(10);
            }
            witch.setHP(0);
        }

        if (boss != null) {
            boss.getBossLasers().clear();
            boss.getPreLasers().clear();
            boss.getBullets().clear();
            boss.setHP(boss.getHP() - 50);
        }

        ebullets.clear();
        boooomX = lanry.getX();
        boooomY = lanry.getY();
        lanry.setBooomCount(lanry.getBooomCount() - 1);
    }

    public void drawBoooom(Canvas canvas, Paint paint, int isBoom) {
        Bitmap boooomPic;
        switch ((int) (20 - isBoom)) {
            case 0:
                boooomPic = boooom1;
                break;
            case 1:
                boooomPic = boooom2;
                break;
            case 2:
                boooomPic = boooom3;
                break;
            case 3:
                boooomPic = boooom4;
                break;
            case 4:
                boooomPic = boooom5;
                break;
            case 5:
                boooomPic = boooom6;
                break;
            case 6:
                boooomPic = boooom7;
                break;
            case 7:
                boooomPic = boooom8;
                break;
            case 8:
                boooomPic = boooom9;
                break;
            case 9:
                boooomPic = boooom10;
                break;
            case 10:
                boooomPic = boooom11;
                break;
            case 11:
                boooomPic = boooom12;
                break;
            case 12:
                boooomPic = boooom13;
                break;
            case 13:
                boooomPic = boooom14;
                break;
            case 14:
                boooomPic = boooom15;
                break;
            case 15:
                boooomPic = boooom16;
                break;
            case 16:
                boooomPic = boooom17;
                break;
            case 17:
                boooomPic = boooom18;
                break;
            case 18:
                boooomPic = boooom19;
                break;
            case 19:
                boooomPic = boooom20;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + isBoom / 5);
        }
        canvas.drawBitmap(boooomPic, boooomX - boooomPic.getWidth() / 2, boooomY - boooomPic.getHeight() / 2, paint);
    }

    private void characterInit() {
        //Zaku Initial
        for (int i = 0; i < zakuBX.length; i++) {
            zakuBXList.add(zakuBX[i]);
            zakuBYList.add(zakuBY[i]);
            zakuEXList.add(zakuEX[i]);
            zakuEYList.add(zakuEY[i]);
        }

        for (int value : zakuTimeView) {
            initZakus.add(new Zaku(zakuPic, context,
                    zakuBXList.poll(), zakuBYList.poll(), zakuEXList.poll(), zakuEYList.poll(), Zaku.STRAIGHT, value));
        }

        //Witch Initial
        for (int i = 0; i < witchBX.length; i++) {
            witchBXList.add(witchBX[i]);
            witchBYList.add(witchBY[i]);
            witchEXList.add(witchEX[i]);
            witchEYList.add(witchEY[i]);
        }

        for (int value : witchTimeView) {
//            zakuTime.add(value);
            initWitches.add(new Witch(witchPic, context,
                    witchBXList.poll(), witchBYList.poll(), witchEXList.poll(), witchEYList.poll(), Witch.STRAIGHT, value));
        }

        //LaserItem init
        for (int bx : laserItemBX) {
            laserItemBXList.add(bx);
        }

        for (int value : laserItemView) {
            int x = laserItemBXList.poll();
            initLaserItems.add(new LaserItem(laserItemPic, x, 0, x, scHeight, value));
        }

        //ShieldItem init
        for (int itemBX : shieldItemBX) {
            shieldItemBXList.add(itemBX);
        }

        for (int value : shieldItemView) {
            int x = shieldItemBXList.poll();
            initShieldItems.add(new ShieldItem(shieldItemPic, x, 0, x, scHeight, value));
        }

        boss = new Boss(bossPic, context);
        bossTime = 8;
    }

    private void randomInit() {
        //随机test
        Random random = new Random();
        int zakuNumber = level * 25;
        int witchNumber = level * 20;
        int time = 3000;
//        int time = 100;

        switch (level) {
            case 1:
                zakuNumber = 15;
                witchNumber = 5;
                time = 1000;
                break;
            case 2:
                zakuNumber = 35;
                witchNumber = 15;
                time = 2000;
                break;
        }

        ArrayList<Integer> zakuTimeView = new ArrayList<>();
        ArrayList<Integer> witchTimeView = new ArrayList<>();
        ArrayList<Integer> laserItemView = new ArrayList<>();
        ArrayList<Integer> shieldItemView = new ArrayList<>();

        for (int i = 0; i < zakuNumber; i++) {
            zakuTimeView.add(random.nextInt(time));
        }

        for (int i = 0; i < witchNumber; i++) {
            witchTimeView.add(random.nextInt(time));
        }

        for (int i = 0; i < 5; i++) {
            laserItemView.add(random.nextInt(time));
        }

        for (int i = 0; i < 6; i++) {
            shieldItemView.add(random.nextInt(time));
        }

        Collections.sort(zakuTimeView, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        Collections.sort(witchTimeView, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        Collections.sort(laserItemView, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        Collections.sort(shieldItemView, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });


        //Zaku init
        for (int i = 0; i < zakuTimeView.size(); i++) {
            zakuBXList.add((float) random.nextInt(scWidth));
            zakuBYList.add((float) random.nextInt(500));
            zakuEXList.add((float) random.nextInt(scWidth));
            zakuEYList.add((float) random.nextInt(500));
        }
        for (int value : zakuTimeView) {
            initZakus.add(new Zaku(zakuPic, context,
                    zakuBXList.poll(), zakuBYList.poll(), zakuEXList.poll(), zakuEYList.poll(), Zaku.STRAIGHT, value));
        }

        //Witch init
        for (int i = 0; i < witchTimeView.size(); i++) {
            witchBXList.add((float) random.nextInt(scWidth));
            witchBYList.add((float) random.nextInt(500));
            witchEXList.add((float) random.nextInt(scWidth));
            witchEYList.add((float) random.nextInt(500));
        }
        for (int value : witchTimeView) {
            initWitches.add(new Witch(witchPic, context,
                    witchBXList.poll(), witchBYList.poll(), witchEXList.poll(), witchEYList.poll(), Witch.STRAIGHT, value));
        }

        //LaserItem init
        for (int i = 0; i < laserItemView.size(); i++) {
            laserItemBXList.add(random.nextInt(scWidth));
        }

        for (int value : laserItemView) {
            int x = laserItemBXList.poll();
            initLaserItems.add(new LaserItem(laserItemPic, x, 0, x, scHeight, value));
        }

        //ShieldItem init
        for (int i = 0; i < shieldItemView.size(); i++) {
            shieldItemBXList.add(random.nextInt(scWidth));
        }

        for (int value : shieldItemView) {
            int x = shieldItemBXList.poll();
            initShieldItems.add(new ShieldItem(shieldItemPic, x, 0, x, scHeight, value));
        }

        if (level >= 3) {
            boss = new Boss(bossPic, context);
            bossTime = time / 100;
//            bossTime = 1;
        }
    }

    private void zakuCalculate(Canvas canvas, int second, int f) {
        //Zaku逻辑处理
        for (int i = 0; i < zakus.size(); i++) {
            Zaku zaku = zakus.get(i);
            float[] hitbox = zaku.getHitbox();

            //zaku子弹生成
            if ((second * 100 + f + zaku.getBornFarme()) % 70 == 0 && zaku.getHited() < 0) {
                ebullets.add(zaku.generateBullet(lanry.getX(), lanry.getY(), ebulletPic));
            }

            for (int j = 0; j < lanry.getBullets().size(); j++) {
                //Lanry的子弹碰撞zaku检测
                Bullet bullet = lanry.getBullets().get(j);
                if (hitCheck(bullet.getX(), bullet.getY(), hitbox)) {
                    lanry.removeBullet(bullet);
                    if (zaku.getHP() > 0) {
                        zaku.setHited(10);
                    }
                    zaku.setHP(zaku.getHP() - 1);
                    if (zaku.getHP() == 0) {
                        lanryKill++;
                    }
                }
            }

            //Laser攻击逻辑处理处理
            if (lanry.getIsLaser() > 0) {
                for (int x = (int) (lanry.getX() - 10); x < lanry.getX() + 10; x++) {
                    for (int y = (int) lanry.getY(); y >= 0; y--) {
                        if (hitCheck(x, y, hitbox)) {
                            if (zaku.getHP() > 0) {
                                zaku.setHited(10);
                            }
                            zaku.setHP(zaku.getHP() - 1);
                        }
                    }
                }
            }

            if (zakus.get(i) != null) {
                zaku.draw(canvas, paint);
                if (zaku.getHited() >= 0) {
                    //绘制zaku爆炸
                    canvas.drawBitmap(explotionPic, zaku.getX() - explotionPic.getWidth() / 2, zaku.getY() - explotionPic.getHeight() / 2, paint);
                    zaku.setHited(zaku.getHited() - 1);
                }
            }

        }

        //移除死去的zaku
        List<Zaku> deadzakus = new ArrayList<Zaku>();
        for (Zaku zaku : zakus) {
            if (zaku.getHited() == 0 && zaku.getHP() <= 0) {
                deadzakus.add(zaku);
            } else if (zaku.getHP() > 0) {
                zaku.move(Zaku.STRAIGHT);
            }
        }

        for (Zaku zaku : deadzakus) {
            zakus.remove(zaku);
        }
    }

    private void bossLogic(Canvas canvas, int second, int f) {
        //Boss logic
        if (boss.getHP() > 0) {
            boss.draw(canvas, paint, second, f);
            boss.generateBullet(second, f);
            for (int j = 0; j < lanry.getBullets().size(); j++) {
                Bullet bullet = lanry.getBullets().get(j);
                if (hitCheck(bullet.getX(), bullet.getY(), boss.getHitbox())) {
                    boss.setHP(boss.getHP() - 1);
                    lanry.removeBullet(bullet);
                    explotions.add(new Explotion(bullet.getX(), bullet.getY(), 10, explotionPic));
                }
            }

            //boss子弹撞击lanry检测
            for (Bullet bullet : boss.getBullets()) {
                if (hitCheck(bullet.getX(), bullet.getY(), lanry.getHitbox()) && lanryHited <= 0) {
                    lanry.setHP(lanry.getHP() - 1);
                    boss.removeBullet(bullet);
                    lanryHited = 30;
                }
            }

            //boss laser check
            for (BossLaserItem item : boss.getBossLasers()) {
                if (lanry.getX() >= item.X - 22 && lanry.getX() <= item.X + 22 && lanryHited <= 0) {
                    lanry.setHP(lanry.getHP() - 1);
                    lanryHited = 30;
                }
            }

//            lanry laser check
            if (boss.getLaserHit() <= 0) {
                if (lanry.getIsLaser() > 0) {
                    for (int x = (int) (lanry.getX() - 10); x < lanry.getX() + 10; x++) {
                        for (int y = (int) lanry.getY(); y >= 0; y--) {
                            if (hitCheck(x, y, boss.getHitbox())) {
                                if (boss.getLaserHit() <= 0) {
                                    explotions.add(new Explotion(lanry.getX(), boss.getY() + bossPic.getHeight() / 2, 10, explotionPic));
                                    boss.setLaserHit(10);
                                    boss.setHP(boss.getHP() - 5);
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                }
            } else {
                boss.setLaserHit(boss.getLaserHit() - 1);
            }
        }
    }

    private void itemsDeal(Canvas canvas) {
        //LaserItem 处理
        List<LaserItem> deadLaserItems = new ArrayList<LaserItem>();
        for (int i = 0; i < laserItems.size(); i++) {
            LaserItem laserItem = laserItems.get(i);
            laserItem.draw(canvas, paint);
            if (hitCheck(lanry.getHitbox(), laserItem.getHitbox())) {
                lanry.setIsLaser(100);
                deadLaserItems.add(laserItem);
            }
            laserItem.move();
        }

        for (int i = 0; i < deadLaserItems.size(); i++) {
            laserItems.remove(deadLaserItems.get(i));
        }

        //ShieldItem 处理
        List<ShieldItem> deadShieldItems = new ArrayList<ShieldItem>();
        for (int i = 0; i < shieldItems.size(); i++) {
            ShieldItem shieldItem = shieldItems.get(i);
            shieldItem.draw(canvas, paint);
            if (hitCheck(lanry.getHitbox(), shieldItem.getHitbox())) {
                lanry.setIsShield(150);
                deadShieldItems.add(shieldItem);
            }
            shieldItem.move();
        }

        for (int i = 0; i < deadShieldItems.size(); i++) {
            shieldItems.remove(deadShieldItems.get(i));
        }
    }

    //witch逻辑处理
    private void witchCalculate(Canvas canvas, int second, int f) {
        for (int i = 0; i < witches.size(); i++) {
            Witch witch = witches.get(i);
            float[] hitbox = witch.getHitbox();

            //witch子弹生成
            if ((second * 100 + f + witch.getBornFarme()) % 100 == 0 && witch.getHited() < 0) {
                ebullets.add(witch.generateBullet(lanry.getX(), lanry.getY(), witchbulletPic));
            }

            for (int j = 0; j < lanry.getBullets().size(); j++) {
                //Lanry的子弹碰撞zaku检测
                Bullet bullet = lanry.getBullets().get(j);
                if (hitCheck(bullet.getX(), bullet.getY(), hitbox)) {
                    lanry.removeBullet(bullet);
                    if (witch.getHP() > 0) {
                        witch.setHited(15);
                    }
                    witch.setHP(witch.getHP() - 1);
                    if (witch.getHP() == 0) {
                        lanryKill++;
                    }
                }
            }

            //Laser攻击逻辑处理处理
            if (lanry.getIsLaser() > 0) {
                for (int x = (int) (lanry.getX() - 10); x < lanry.getX() + 10; x++) {
                    for (int y = (int) lanry.getY(); y >= 0; y--) {
                        if (hitCheck(x, y, hitbox)) {
                            if (witch.getHP() > 0) {
                                witch.setHited(10);
                                witch.setHP(witch.getHP() - 1);
                            }
                        }
                    }
                }
            }

            if (witches.get(i) != null) {
                witch.draw(canvas, paint);
                if (witch.getHited() >= 0) {
                    //绘制witch爆炸
                    canvas.drawBitmap(explotionPic, witch.getX() - explotionPic.getWidth() / 2, witch.getY() - explotionPic.getHeight() / 2, paint);
                    witch.setHited(witch.getHited() - 1);
                }
            }
        }

        //移除死去的zaku
        List<Witch> deadWitches = new ArrayList<Witch>();
        for (Witch witch : witches) {
            if (witch.getHited() == 0 && witch.getHP() <= 0) {
                deadWitches.add(witch);
            } else if (witch.getHP() > 0) {
                witch.move(Witch.STRAIGHT);
            }
        }

        for (Witch witch : deadWitches) {
            witches.remove(witch);
        }
    }

    public Lanry getLanry() {
        return lanry;
    }

    //加载图片
    private void loadImg() {
        zakuPic = BitmapFactory.decodeResource(context.getResources(), R.mipmap.zaku);
        witchPic = BitmapFactory.decodeResource(context.getResources(), R.mipmap.witch);
        heart = BitmapFactory.decodeResource(context.getResources(), R.mipmap.heart);
        energy = BitmapFactory.decodeResource(context.getResources(), R.mipmap.energy);
        explotionPic = BitmapFactory.decodeResource(context.getResources(), R.mipmap.explotion);
        bulletPic = BitmapFactory.decodeResource(context.getResources(), R.mipmap.bullet);
        ebulletPic = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ebullet);
        witchbulletPic = BitmapFactory.decodeResource(context.getResources(), R.mipmap.witchbullet);
        boooom1 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boooom1);
        boooom2 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boooom2);
        boooom3 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boooom3);
        boooom4 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boooom4);
        boooom5 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boooom5);
        boooom6 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boooom6);
        boooom7 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boooom7);
        boooom8 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boooom8);
        boooom9 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boooom9);
        boooom10 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boooom10);
        boooom11 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boooom11);
        boooom12 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boooom12);
        boooom13 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boooom13);
        boooom14 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boooom14);
        boooom15 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boooom15);
        boooom16 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boooom16);
        boooom17 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boooom17);
        boooom18 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boooom18);
        boooom19 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boooom19);
        boooom20 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boooom20);
        laserPic1 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.laser1);
        laserPic2 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.laser2);
        laserItemPic = BitmapFactory.decodeResource(context.getResources(), R.mipmap.laseritem);
        shieldItemPic = BitmapFactory.decodeResource(context.getResources(), R.mipmap.shielditem);
        shootE0 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.shoote0);
        shootE1 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.shoote1);
        shootE2 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.shoote2);
        shootE3 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.shoote3);
        shootE4 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.shoote4);
        shootE5 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.shoote5);
        levelup1 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.levelup1);
        levelup2 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.levelup2);
        bossPic = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boss);
    }
}
