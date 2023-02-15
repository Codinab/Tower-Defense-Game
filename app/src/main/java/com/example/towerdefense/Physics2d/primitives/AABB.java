package com.example.towerdefense.Physics2d.primitives;

import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D;
import org.joml.Vector2f;

//Axis Aligned Bounding Box
public class AABB {
    private Vector2f size = new Vector2f();
    private Vector2f halfSize = new Vector2f();
    private Rigidbody2D rigidbody = null;

    public AABB() {
        this.halfSize =  new Vector2f(size).mul(0.5f);
    }

    public AABB(Vector2f min, Vector2f max) {
        this.size = new Vector2f(max).sub(min);
        this.halfSize =  new Vector2f(size).mul(0.5f);
        rigidbody = new Rigidbody2D();
    }
    public AABB(Vector2f size, Vector2f halfSize, Rigidbody2D rigidbody) {
        this.size = size;
        this.halfSize = halfSize;
        this.rigidbody = rigidbody;
    }

    public Vector2f getMin(){
        return new Vector2f(this.rigidbody.getPosition()).sub(this.halfSize);
    }

    public Vector2f getMax() {
        return new Vector2f(this.rigidbody.getPosition()).add(this.halfSize);
    }

    public Vector2f[] getVertices() {
        Vector2f min = getMin();
        Vector2f max = getMax();

        Vector2f[] vertices = {
                new Vector2f(min.x, min.y), new Vector2f(max.x, min.y),
                new Vector2f(min.x, max.y), new Vector2f(max.x, max.y)
        };
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

    public void setRigidbody(Rigidbody2D rigidbody) {
        this.rigidbody = rigidbody;
    }

    public void setSize(Vector2f size) {
        this.size = size;
        this.halfSize.set(size).mul(0.5f);
    }
}
