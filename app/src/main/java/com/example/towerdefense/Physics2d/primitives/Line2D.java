package com.example.towerdefense.Physics2d.primitives;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Line2D {
    private Vector2f from;
    private Vector2f to;


    public Line2D(Vector2f from, Vector2f to) {
        this.from = from;
        this.to = to;
    }

    public Vector2f getVector() {
        return new Vector2f(to).sub(from);
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
