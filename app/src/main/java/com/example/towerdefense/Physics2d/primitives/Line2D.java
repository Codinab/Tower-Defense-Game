package com.example.towerdefense.Physics2d.primitives;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Line2D {
    private Vector2f from;
    private Vector2f to;
    private Vector3f color;
    private int lifeTime;

    public Line2D(Vector2f from, Vector2f to) {
        init(from, to, null, 1);
    }

    public Line2D(Vector2f from, Vector2f to, Vector3f color, int lifeTime) {
        init(from, to, color, lifeTime);
    }

    private void init(Vector2f from, Vector2f to, Vector3f color, int lifeTime) {
        this.from = from;
        this.to = to;
        this.color = color;
        this.lifeTime = lifeTime;
    }

    public int beginFrame() {
        this.lifeTime--;
        return lifeTime;
    }

    public Vector2f getVector() {
        return new Vector2f(to).sub(from);
    }

    public Vector3f getColor() {
        return color;
    }

    public Vector2f getStart() {
        return from;
    }

    public void setStart(Vector2f from) {
        this.from = from;
    }

    public Vector2f getEnd() {
        return to;
    }

    public void setEnd(Vector2f to) {
        this.to = to;
    }
}
