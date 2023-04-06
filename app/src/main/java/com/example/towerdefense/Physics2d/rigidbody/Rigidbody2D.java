package com.example.towerdefense.Physics2d.rigidbody;



import com.example.towerdefense.Physics2d.JMath;

import org.joml.Vector2f;

public class Rigidbody2D  {

    public Rigidbody2D(float rotation, float rotationVelocity, Vector2f position, float velocity) {
        init(rotation, rotationVelocity, position, velocity);
    }
    public Rigidbody2D(float rotation, Vector2f position, float velocity) {
        init(rotation, 0f, position, velocity);
    }
    public Rigidbody2D(float rotation, float rotationVelocity, Vector2f position) {
        init(rotation, rotationVelocity, position, 0f);
    }
    public Rigidbody2D(float rotation, Vector2f position) {
        init(rotation, 0f, position, 0f);
    }
    public Rigidbody2D(Vector2f position) {
        init(0f, 0f, position, 0f);
    }
    public Rigidbody2D() {
        init(0f, 0f, new Vector2f(), 0f);
    }

    private void init(float rotation, float rotationVelocity, Vector2f position, float velocity) {
        this.position = position;
        this.rotation = rotation;
        this.velocity = velocity;
        this.angularVelocity = rotationVelocity;
    }
    private Vector2f position;
    private float rotation = 0.0f;

    private Float velocity;
    private float angularVelocity;

    private float cor = 1.0f; //Correction

    public Vector2f getPosition() {
        return position;
    }



    public void setPosition(Vector2f position, float rotation) {
        this.rotation = rotation;
        this.position = position;
    }
    public void addPosition(Vector2f position) {
        this.position.add(position);
    }
    public void setPosition(Vector2f position) {
        this.position = position;
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

    public float getCor() {
        return cor;
    }

    public void setCor(float cor) {
        this.cor = cor;
    }

    public Float getVelocity() {
        return velocity;
    }

    public void setVelocity(Float velocity) {
        this.velocity = velocity;
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
        angleUpdated = true;
        rotation += angularVelocity;
    }
    private void positionUpdate() {
        this.position.add(getAngleVector().mul(velocity));
    }
    private Vector2f lastAngle = null;
    public Boolean angleUpdated = false;
    public Vector2f getAngleVector() {
        if(lastAngle == null || angleUpdated) {
            lastAngle = JMath.angleToVector(rotation);
            angleUpdated = false;
        }
        return lastAngle;
    }

    public Rigidbody2D clone() {
        return new Rigidbody2D(rotation, angularVelocity, new Vector2f(position), velocity);
    }


    @Override
    public String toString() {
        return "Rigid-body2D{" +
                "position=" + position +
                ", rotation=" + rotation +
                ", velocity=" + velocity +
                ", angularVelocity=" + angularVelocity +
                ", cor=" + cor +
                '}';
    }
}
