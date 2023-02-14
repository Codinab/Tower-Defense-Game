package com.example.towerdefense.Physics2d.rigidbody;



import com.example.towerdefense.Physics2d.jade.Transform;
import com.example.towerdefense.Physics2d.primitives.Collider2D;

import org.joml.Vector2f;
import org.joml.Vector2fc;

public class Rigidbody2D  {

    public Rigidbody2D(float rotation, Vector2f position) {
        rawTransform = new Transform(position);
        collider = new Collider2D();
        this.rotation = rotation;
    }

    public Rigidbody2D(Vector2f position) {
        rawTransform = new Transform(position);
        collider = new Collider2D();
    }

    public Rigidbody2D() {
        rawTransform = new Transform();
        collider = new Collider2D();
    }
    private Transform rawTransform;
    private Collider2D collider;
    private float rotation = 0.0f;
    private float inverseMass = 0.0f;


    private float cor = 1.0f; //Correction

    private boolean fixedRotation = false;

    public Vector2f getPosition() {
        return rawTransform.position;
    }



    public void setTransform(Vector2f position, float rotation) {
        this.rotation = rotation;
        rawTransform.position = position;
    }
    public void setTransform(Vector2f position) {
        rawTransform.position = position;
    }

    public float getRotation() {
        return rotation;
    }


    public void setRawTransform(Transform rawTransform) {
        this.rawTransform = rawTransform;
    }



    public Collider2D getCollider() {
        return collider;
    }

    public void setCollider(Collider2D collider) {
        this.collider = collider;
    }

    public float getInverseMass() {
        return this.inverseMass;
    }

    public float getCor() {
        return cor;
    }

    public void setCor(float cor) {
        this.cor = cor;
    }
}
