package com.example.towerdefense.Physics2d.primitives;

import com.example.towerdefense.Physics2d.JMath;
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D;
import org.joml.Vector2f;

public class Box2D extends Collider2D {
    private Vector2f size = new Vector2f();
    private Vector2f halfSize = new Vector2f();
    private Rigidbody2D rigidbody = null;

    public Box2D() {
        this.halfSize =  new Vector2f(size).mul(0.5f);
    }

    public Box2D(Vector2f min, Vector2f max) {
        this.size = new Vector2f(max).sub(min);
        this.halfSize =  new Vector2f(size).mul(0.5f);
        rigidbody = new Rigidbody2D();
    }
    public Box2D(Vector2f size, Vector2f halfSize, Rigidbody2D rigidbody) {
        this.size = size;
        this.halfSize = halfSize;
        this.rigidbody = rigidbody;
    }

    public Box2D(Vector2f size, Rigidbody2D rigidbody) {
        this.size = size;
        this.halfSize =  new Vector2f(size).mul(0.5f);
        this.rigidbody = rigidbody;
    }

    public Vector2f getCenter() {
        return rigidbody.getPosition();
    }

    public Vector2f getLocalMin(){
        return new Vector2f(this.rigidbody.getPosition()).sub(this.halfSize);
    }

    public Vector2f getLocalMax() {
        return new Vector2f(this.rigidbody.getPosition()).add(this.halfSize);
    }

    public Vector2f[] getVertices() {
        Vector2f min = getLocalMin();
        Vector2f max = getLocalMax();

        Vector2f[] vertices = {
                new Vector2f(min.x, min.y), new Vector2f(max.x, min.y),
                new Vector2f(min.x, max.y), new Vector2f(max.x, max.y)
        };
        if (rigidbody.getRotation() != 0) {
            for(Vector2f vert : vertices) {
                JMath.rotate(vert, rigidbody.getRotation(), this.rigidbody.getPosition());
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

    public Rigidbody2D getRigidbody() {
        return this.rigidbody;
    }

    public Vector2f getHalfSize() {
        return this.halfSize;
    }

    public void setRigidbody(Rigidbody2D rigidbody) {
        this.rigidbody = rigidbody;
    }

    public void setSize(Vector2f size) {
        this.size = size;
        this.halfSize.set(size).mul(0.5f);
    }

}