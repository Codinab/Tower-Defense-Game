package com.example.towerdefense.Physics2d.rigidbody;



import com.example.towerdefense.Physics2d.JMath;
import com.example.towerdefense.Physics2d.jade.Transform;
import com.example.towerdefense.Physics2d.primitives.Collider2D;

import org.joml.Vector2f;
import org.joml.Vector2fc;

public class Rigidbody2D  {

    public Rigidbody2D(float rotation, float rotationVelocity, Vector2f position, Vector2f velocity) {
        rawTransform = new Transform(position);
        collider = new Collider2D();
        this.rotation = rotation;
        this.velocity = velocity;
        this.angularVelocity = rotationVelocity;
    }

    public Rigidbody2D(float rotation, Vector2f position, Vector2f velocity) {
        rawTransform = new Transform(position);
        collider = new Collider2D();
        this.rotation = rotation;
        this.velocity = velocity;
    }

    public Rigidbody2D(float rotation, float rotationVelocity, Vector2f position) {
        rawTransform = new Transform(position);
        collider = new Collider2D();
        this.rotation = rotation;
        this.angularVelocity = rotationVelocity;
    }

    public Rigidbody2D(float rotation, Vector2f position) {
        rawTransform = new Transform(position);
        collider = new Collider2D();
        this.rotation = rotation;
        velocity = new Vector2f();
    }

    public Rigidbody2D(Vector2f position) {
        rawTransform = new Transform(position);
        collider = new Collider2D();
        velocity = new Vector2f();
    }

    public Rigidbody2D() {
        rawTransform = new Transform();
        collider = new Collider2D();
        velocity = new Vector2f();
    }
    private Transform rawTransform;
    private Collider2D collider;
    private float rotation = 0.0f;

    private Vector2f velocity;
    private float angularVelocity;

    private float cor = 1.0f; //Correction

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
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
    public void addRotation(float rotation) {
        this.rotation += rotation;
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

    public float getCor() {
        return cor;
    }

    public void setCor(float cor) {
        this.cor = cor;
    }

    public Vector2f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    public void addVelocity(Vector2f velocity) {
        this.velocity.add(velocity);
    }

    public void addAngularVelocity(float angularVelocity) {
        this.angularVelocity += angularVelocity;
    }
    public float getAngularVelocity() {
        return angularVelocity;
    }
    public void setAngularVelocity(float angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    public void update() {
        positionUpdate();
        rotationUpdate();
    }

    private void rotationUpdate() {
        rotation += angularVelocity;
    }

    private void positionUpdate() {
        Vector2f velocityRotated = new Vector2f(velocity);
        JMath.rotate(velocityRotated, rotation, new Vector2f());
        rawTransform.position.add(velocityRotated);
    }
}
