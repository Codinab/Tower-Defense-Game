package com.example.towerdefense.Physics2d.primitives;

import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D;
import com.example.towerdefense.utility.Drawing;

import org.joml.Vector2f;

public class Circle extends Collider2D {
    private float radius;

    public Circle(float radius, Rigidbody2D center) {
        this.radius = radius;
        this.body = center;
    }

    public Circle(float radius, Vector2f center) {
        this.radius = radius;
        this.body = new Rigidbody2D(center);
    }

    public float getRadius() {
        return this.radius;
    }


    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Vector2f getCenter() {
        return this.body.getPosition();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawCircle(this.getCenter().x, this.getCenter().y, this.getRadius(), new Paint());
    }

    @Override
    public Vector2f layoutSize() {
        return new Vector2f(this.getRadius() * 2, this.getRadius() * 2);
    }

    @Override
    public Collider2D clone() {
        return new Circle(this.radius, this.body.clone());
    }
}
