package com.example.towerdefense.Physics2d;

import org.joml.Vector2f;

public class JMath {

    public static void rotate(Vector2f vec, float angleDeg, Vector2f origin) {
        float x = vec.x - origin.x;
        float y = vec.y - origin.y;

        float cos = (float) Math.cos(Math.toRadians(angleDeg));
        float sin = (float) Math.sin((Math.toRadians(angleDeg)));

        float xPrime = (x * cos) - (y * sin);
        float yPrime = (x * sin) + (y* cos);
        xPrime += origin.x;
        yPrime += origin.y;

        vec.x = xPrime;
        vec.y = yPrime;
    }


    public static boolean compare(float a, float b, float epsilon) {
        return Math.abs(a - b) <= epsilon * Math.max(Math.abs(a), Math.abs(b));
    }
    public static boolean compare(Vector2f vec1, Vector2f vec2, float epsilon) {
        return compare(vec1.x, vec2.x, epsilon) && compare(vec1.y, vec2.y, epsilon);
    }

    public static boolean compare(float a, float b) {
        return Math.abs(a - b) <= Float.MIN_VALUE * Math.max(Math.abs(a), Math.abs(b));
    }
    public static boolean compare(Vector2f vec1, Vector2f vec2) {
        return compare(vec1.x, vec2.x) && compare(vec1.y, vec2.y);
    }
}
