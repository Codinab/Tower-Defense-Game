package com.example.towerdefense.Physics2d.rigidbody;

import com.example.towerdefense.gameObjects.GameObject;
import com.example.towerdefense.Physics2d.JMath;
import com.example.towerdefense.Physics2d.primitives.*;
import com.example.towerdefense.gameObjects.TowerArea;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector2f;


public class IntersectionDetector2D {
    //Point vs. Primitive Tests
    public static boolean intersection(Vector2f point, Line2D line) { //point on line
        float dy = line.getEnd().y - line.getStart().y;
        float dx = line.getEnd().x - line.getStart().x;
        if (dx == 0f) {
            return JMath.compare(point.x, line.getStart().x);
        }
        float m = dy / dx;

        float n = line.getEnd().y - (m * line.getEnd().x);

        return point.y == m * point.x + n;
    }


    public static boolean intersection(Line2D line2D, Vector2f point) { //point on line
        return intersection(point, line2D);
    }

    public static boolean intersection(Vector2f point, Circle circle) { //point on circle
        Vector2f cicleCenter = circle.getCenter();
        Vector2f centerToPoint = new Vector2f(point).sub(cicleCenter);

        return centerToPoint.lengthSquared() <= circle.getRadius() * circle.getRadius();
    }

    public static boolean intersection(Circle circle, Vector2f point) { //circle on point
        return intersection(point, circle);
    }

    public static boolean intersection(Vector2f point, AABB box) { //point on aabb
        Vector2f min = box.getMin();
        Vector2f max = box.getMax();

        return point.x <= max.x && point.x >= min.x &&
                point.y <= max.y && point.y >= min.y;
    }

    public static boolean intersection(AABB box, Vector2f point) { //aabb on point
        return intersection(point, box);
    }

    public static boolean intersection(Vector2f point, Box2D box) { //point on box2d
        Vector2f pointLocalBox = new Vector2f(point);
        JMath.rotate(pointLocalBox, box.body.getRotation(), box.body.getPosition());

        Vector2f min = box.getLocalMin();
        Vector2f max = box.getLocalMax();

        return pointLocalBox.x <= max.x && pointLocalBox.x >= min.x &&
                pointLocalBox.y <= max.y && pointLocalBox.y >= min.y;
    }

    public static boolean intersection(Box2D b1, Vector2f point) { //box2d on point
        return intersection(point, b1);
    }

    public static boolean intersection(Line2D line1, Line2D line2) { //line on line
        float dy1 = line1.getEnd().y - line1.getStart().y;
        float dy2 = line2.getEnd().y - line2.getStart().y;
        float dx1 = line1.getEnd().x - line1.getStart().x;
        float dx2 = line2.getEnd().x - line2.getStart().x;

        if (dx1 == 0f) {
            return intersection(line1.getStart(), line2);
        }
        if (dx2 == 0f) {
            return intersection(line2.getStart(), line1);
        }

        float m2 = dy2 / dx2;
        float m1 = dy1 / dx1;

        float n1 = line1.getEnd().y - (m1 * line1.getEnd().x);
        float n2 = line2.getEnd().y - (m2 * line2.getEnd().x);

        float mSub = (m1 - m2) == 0f ? 1f : (m1 - m2);

        float x = (n2 - n1) / mSub;
        float y = m1 * x + n1;
        Vector2f point = new Vector2f(x, y);

        return intersection(point, line1) || intersection(point, line2);
    }

    public static boolean intersection(Line2D line, Circle circle) {
        if (intersection(line.getStart(), circle) || intersection(line.getEnd(), circle)) {
            return true;
        }
        Vector2f closestPoint = getClosestPointOnLine(line, circle.getCenter());
        double distance = closestPoint.distance(circle.getCenter());
        return distance <= circle.getRadius();
    }

    public static Vector2f getClosestPointOnLine(Line2D line, Vector2f point) {
        double x1 = line.getStart().x;
        double y1 = line.getStart().y;
        double x2 = line.getEnd().x;
        double y2 = line.getEnd().y;
        double px = point.x;
        double py = point.y;

        double dx = x2 - x1;
        double dy = y2 - y1;

        if (dx == 0 && dy == 0) {
            // The line is a point.
            return new Vector2f((float) x1, (float) y1);
        }

        double t = ((px - x1) * dx + (py - y1) * dy) / (dx * dx + dy * dy);

        if (t < 0) {
            // The closest point is outside the line segment, on the side of x1,y1.
            return new Vector2f((float) x1, (float) y1);
        } else if (t > 1) {
            // The closest point is outside the line segment, on the side of x2,y2.
            return new Vector2f((float) x2, (float) y2);
        } else {
            // The closest point is inside the line segment.
            double closestX = x1 + t * dx;
            double closestY = y1 + t * dy;
            return new Vector2f((float) closestX, (float) closestY);
        }
    }


    public static boolean intersection(Circle circle, Line2D line) { //Intersection of a circle and a line
        return intersection(line, circle);
    }

    public static boolean intersection(Line2D line, AABB box) { //line on aabb
        if (intersection(line.getStart(), box) || intersection(line.getEnd(), box)) return true;

        AABB boxP = new AABB(box.getMin(), box.getMax());

        for (Line2D side : boxP.getSides()) {
            if (intersection(side, line)) return true;
        }

        return false;
    }

    public static boolean intersection(AABB box, Line2D line) { //line on aabb
        return intersection(line, box);
    }

    public static boolean intersection(Line2D line, Box2D box) { //line on box2d
        float rotation = -box.body.getRotation();
        Vector2f position = box.body.getPosition();
        Vector2f localStart = new Vector2f(line.getStart());
        Vector2f localEnd = new Vector2f(line.getEnd());
        JMath.rotate(localStart, rotation, position);
        JMath.rotate(localEnd, rotation, position);

        Line2D localLine = new Line2D(localStart, localEnd);
        AABB aabb = new AABB(box.getLocalMin(), box.getLocalMax());

        return intersection(localLine, aabb);
    }

    public static boolean intersection(Box2D box, Line2D line) { //line on box2d
        return intersection(line, box);
    }


    public static boolean intersection(Circle c1, Circle c2) { //Intersection of two circles
        Vector2f vecBetwenCenters = new Vector2f(c1.getCenter()).sub(c2.getCenter());
        float radiusSum = c1.getRadius() + c2.getRadius();
        return vecBetwenCenters.lengthSquared() < radiusSum * radiusSum;
    }

    public static boolean intersection(Circle c1, AABB box) { //Intersection of a circle and an AABB
        Line2D[] sides = box.getSides();
        for (Line2D side : sides) {
            if (intersection(side, c1)) {
                return true;
            }
        }
        return false;
    }

    public static boolean intersection(AABB box, Circle c1) { //Intersection of an AABB and a circle
        return intersection(c1, box);
    }

    public static boolean intersection(Circle c1, Box2D box) { //Intersection of a circle and a Box2D
        if (intersection(c1.getCenter(), box)) {
            return true;
        }
        Line2D[] sides = box.getSides();
        for (Line2D side : sides) {
            if (intersection(side, c1)) {
                return true;
            }
        }
        return false;
    }

    public static boolean intersection(Box2D box2D, Circle circle) { //Intersection of a Box2D and a circle
        return intersection(circle, box2D);
    }


    public static boolean intersection(AABB b1, AABB b2) { //Intersection of two AABB
        Vector2f[] b1vert = b1.getVertices();
        Vector2f[] b2vert = b2.getVertices();
        for (int i = 0; i < 4; i++) {
            if (intersection(b2vert[i], b1) || intersection(b1vert[i], b2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean intersection(AABB b1, Box2D b2) {
        Vector2f[] b1vert = b1.getVertices();
        Vector2f[] b2vert = b2.getVertices();
        for (int i = 0; i < 4; i++) {
            if (intersection(b2vert[i], b1) || intersection(b1vert[i], b2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean intersection(Box2D b2, AABB b) {
        return intersection(b, b2);
    }

    public static boolean intersection(Box2D b1, Box2D b2) {
        Vector2f[] b1vert = b1.getVertices();
        Vector2f[] b2vert = b2.getVertices();
        for (int i = 0; i < 4; i++) {
            if (intersection(b2vert[i], b1) || intersection(b1vert[i], b2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean intersection(Vector2f position, Collider2D collider2D) {
        if (collider2D instanceof Box2D) {
            return intersection(position, (Box2D) collider2D);
        } else if (collider2D instanceof Circle) {
            return intersection(position, (Circle) collider2D);
        }
        return false;
    }

    public static boolean intersection(@NotNull Circle circle, @NotNull Collider2D collider2D) {
        if (collider2D instanceof Box2D) {
            return intersection(circle, (Box2D) collider2D);
        } else if (collider2D instanceof Circle) {
            return intersection(circle, (Circle) collider2D);
        }
        return false;
    }

    public static boolean intersection(@NotNull Collider2D collider2D, @NotNull Collider2D collider2D1) {
        if (collider2D instanceof Box2D) {
            if (collider2D1 instanceof Box2D) {
                return intersection((Box2D) collider2D, (Box2D) collider2D1);
            } else if (collider2D1 instanceof Circle) {
                return intersection((Box2D) collider2D, (Circle) collider2D1);
            }
        } else if (collider2D instanceof Circle) {
            if (collider2D1 instanceof Box2D) {
                return intersection((Circle) collider2D, (Box2D) collider2D1);
            } else if (collider2D1 instanceof Circle) {
                return intersection((Circle) collider2D, (Circle) collider2D1);
            }
        }
        return false;
    }
}
