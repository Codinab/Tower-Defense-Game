package com.example.towerdefense.Physics2d.primitives;


import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector2f;

public class Collider2D {

    public Rigidbody2D body;

    public void update() {
        body.update();
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    public void draw(@NotNull Canvas canvas) {
        if (this instanceof Circle) {
            Circle circle = (Circle) this;
            canvas.drawCircle(circle.getCenter().x, circle.getCenter().y, circle.getRadius(), new Paint());
        } else if (this instanceof Box2D) {
            Box2D box = (Box2D) this;
            canvas.drawRect(
                    body.getPosition().x - box.getHalfSize().x,
                    body.getPosition().y - box.getHalfSize().y,
                    body.getPosition().x + box.getHalfSize().x,
                    body.getPosition().y + box.getHalfSize().y,
                    new Paint()
            );
        }
    }
}
