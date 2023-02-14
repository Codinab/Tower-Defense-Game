package com.example.towerdefense.Physics2d.primitives;

import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D;
import org.joml.Vector2f;

public class Circle extends Collider2D {
    protected float radius;
    protected Rigidbody2D body;

    public Circle(float radius, Rigidbody2D center) {
        this.radius = radius;
        this.body = center;
    }

    public float getRadius() {
        return this.radius;
    }

    public Vector2f getCenter() {
        return body.getPosition();
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setRigidbody(Rigidbody2D body) {
        this.body = body;
    }
}
