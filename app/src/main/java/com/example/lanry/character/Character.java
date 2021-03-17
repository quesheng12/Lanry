package com.example.lanry.character;

import android.graphics.Bitmap;

public abstract class Character {
    protected Bitmap bitmap;
    protected float x, y;
    protected int width, height;
    protected float speed;
    protected int HP;

    public float getX() {
        return x;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float[] getHitbox() {
        return new float[]{x - width / 2, x + width / 2, y - height / 2, y + height / 2};
    }
}
