package com.example.towerdefense.Physics2d.primitives;

import android.graphics.Canvas;

import androidx.annotation.NonNull;

import com.example.towerdefense.Physics2d.JMath;
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D;

import org.joml.Vector2f;

public class Box2D extends Collider2D {
    private Vector2f size;
    private Vector2f halfSize;

    public Box2D() {
        size = new Vector2f();
        this.halfSize = new Vector2f(size).mul(0.5f);
        body = new Rigidbody2D();
    }

    public Box2D(Vector2f min, Vector2f max) {
        this.size = new Vector2f(max).sub(min);
        this.halfSize = new Vector2f(size).mul(0.5f);
        body = new Rigidbody2D(new Vector2f(min).add(this.halfSize));
    }

    public Box2D(Vector2f size, Vector2f halfSize, Rigidbody2D body) {
        this.size = size;
        this.halfSize = halfSize;
        this.body = body;
    }

    public Box2D(Vector2f size, Rigidbody2D body) {
        this.size = size;
        this.halfSize = new Vector2f(size).mul(0.5f);
        this.body = body;
    }

    public Vector2f getLocalMin() {
        return new Vector2f(this.body.getPosition()).sub(this.halfSize);
    }

    public Vector2f getLocalMax() {
        return new Vector2f(this.body.getPosition()).add(this.halfSize);
    }

    public Vector2f[] getVertices() {
        Vector2f min = getLocalMin();
        Vector2f max = getLocalMax();

        Vector2f[] vertices = {
                new Vector2f(min.x, min.y), new Vector2f(max.x, min.y),
                new Vector2f(min.x, max.y), new Vector2f(max.x, max.y)
        };
        if (body.getRotation() != 0) {
            for (Vector2f vert : vertices) {
                JMath.rotate(vert, body.getRotation(), this.body.getPosition());
            }
        }

        return vertices;
    }

    public Line2D[] getSides() {
        Vector2f[] vertices = getVertices();
        Line2D[] lines = {
                new Line2D(vertices[0], vertices[1]),
                new Line2D(vertices[3], vertices[1]),
                new Line2D(vertices[2], vertices[3]),
                new Line2D(vertices[2], vertices[0])
        };
        return lines;
    }

    public Vector2f getHalfSize() {
        return this.halfSize;
    }

    public void setSize(Vector2f size) {
        this.size = size;
        this.halfSize.set(size).mul(0.5f);
    }

    public Vector2f getSize() {
        return this.size;
    }

    public void addSize(Vector2f size) {
        this.size.add(size);
        this.halfSize.set(this.size).mul(0.5f);
    }

    @Override
    public String toString() {
        return "Box2D{" +
                "size=" + size +
                ", body=" + body.getPosition() +
                '}';
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawRect(
                body.getPosition().x - halfSize.x,
                body.getPosition().y - halfSize.y,
                body.getPosition().x + halfSize.x,
                body.getPosition().y + halfSize.y,
                new android.graphics.Paint()
        );
    }

    @Override
    public Vector2f layoutSize() {
        return new Vector2f(this.size);
    }

    @Override
    public Collider2D clone() {
        return new Box2D(new Vector2f(this.size), new Vector2f(this.halfSize), this.body.clone());
    }
}
