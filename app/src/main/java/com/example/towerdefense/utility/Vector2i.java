package com.example.towerdefense.utility;



import java.io.Serializable;
import java.util.Objects;

public class Vector2i implements Comparable<Vector2i>, Serializable {
    protected int dX;
    protected int dY;

    public Vector2i() {
        this.dX = this.dY = 0;
    }

    public Vector2i(int dX, int dY) {
        this.dX = dX;
        this.dY = dY;
    }

    public Vector2i add(Vector2i v1) {
        return new Vector2i(this.dX + v1.dX, this.dY + v1.dY);
    }

    public double dotProduct(Vector2i v1) {
        return this.dX * v1.dX + this.dY * v1.dY;
    }

    public int getX() {
        return this.dX;
    }

    public int getY() {
        return this.dY;
    }

    public double length() {
        return Math.sqrt(this.dX * this.dX + this.dY * this.dY);
    }

    public Vector2i sub(Vector2i v1) {
        return new Vector2i(this.dX - v1.dX, this.dY - v1.dY);
    }

    public String toString() {
        return "Vector2Di(" + this.dX + ", " + this.dY + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if(super.equals(obj)) return true;
        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        Vector2i other = (Vector2i) obj;
        return (this.getY() == other.getY() && this.getX() == other.getX());
    }

    @Override
    public int hashCode() {
        return Objects.hash(dX, dY);
    }

    public Vector2i multiply(int i) {
        return new Vector2i(this.dX * i, this.dY * i);
    }

    public Vector2i unitDirection() {
        Vector2i unit = new Vector2i();
        unit.dX = Integer.compare(this.dX, 0);
        unit.dY = Integer.compare(this.dY, 0);
        return unit;
    }

    public Direction2D direction(Vector2i v2) {
        v2 = v2.sub(this).unitDirection();
        if(v2.dX == 0 && v2.dY == 0) return Direction2D.UNDEFINED;
        if(v2.dX == 0) return (v2.dY == 1) ? Direction2D.DOWN : Direction2D.UP;
        if(v2.dY == 0) return (v2.dX == 1) ? Direction2D.RIGHT : Direction2D.LEFT;
        return Direction2D.UNDEFINED;
    }

    public boolean nextTo(Vector2i v2) {
        return this.sub(v2).length() == 1;
    }

    @Override
    public int compareTo(Vector2i o) {
        int x = Integer.compare(this.dX, o.dX);
        int y = Integer.compare(this.dY, o.dY);
        if(x == 0) return y;
        return x;
    }
}