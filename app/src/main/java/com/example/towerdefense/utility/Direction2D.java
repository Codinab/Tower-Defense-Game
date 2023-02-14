package com.example.towerdefense.utility;


import java.util.ArrayList;
import java.util.Collection;

public enum Direction2D {
    DOWN((byte)1, 360.0F),
    LEFT((byte)2, 270.0F),
    RIGHT((byte)4, 90.0F),
    UNDEFINED((byte)8, 0.0F),
    UP((byte)16, 180.0F);

    private final byte flagValue;
    private final float angle;


    Direction2D(byte flagValue, float angle) {
        this.flagValue = flagValue;
        this.angle = angle;
    }

    public static Collection<Direction2D> directions() {
        //Return a collection of all directions except UNDEFINED
        return new ArrayList<Direction2D>() {{
            add(UP);
            add(DOWN);
            add(LEFT);
            add(RIGHT);
        }};
    }

    public static Direction2D fromFlagValue(byte flagValue) {
        Direction2D[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Direction2D dir = var1[var3];
            if (dir.toFlagValue() == flagValue) {
                return dir;
            }
        }

        return UNDEFINED;
    }

    public static Direction2D getDirection(Vector2i direction) {
        if (direction.getX() == 0.0 && direction.getY() == 1.0) {
            return DOWN;
        } else if (direction.getX() == 0.0 && direction.getY() == -1.0) {
            return UP;
        } else if (direction.getX() == 1.0 && direction.getY() == 0.0) {
            return RIGHT;
        } else {
            return direction.getX() == -1.0 && direction.getY() == 0.0 ? LEFT : UNDEFINED;
        }
    }


    public Direction2D getOpposite() {
        switch (this) {
            case RIGHT:
                return LEFT;
            case UP:
                return DOWN;
            case LEFT:
                return RIGHT;
            case DOWN:
                return UP;
            default:
                return this;
        }
    }

    public Direction2D fromVector(Vector2i vector) {
        double x = vector.getX();
        double y = vector.getY();
        if(x == 0.0f && y == 1.0f) {
            return DOWN;
        } else if(x == 1.0f && y == 0.0f) {
            return RIGHT;
        } else if(x == 0.0f && y == -1.0f) {
            return UP;
        } else if(x == -1.0f && y == 0.0f) {
            return LEFT;
        } else {
            return UNDEFINED;
        }
    }

    public float toAngle() {
        return this.angle;
    }

    public byte toFlagValue() {
        return this.flagValue;
    }

    public Vector2i getVector() {
        switch (this) {
            case RIGHT:
                return new Vector2i(1, 0);
            case UP:
                return new Vector2i(0, -1);
            case LEFT:
                return new Vector2i(-1, 0);
            case DOWN:
                return new Vector2i(0, 1);
            default:
                return new Vector2i(0, 0);
        }
    }

    public static Collection<Direction2D> directions(Vector2i from, Vector2i to){
        Collection<Direction2D> directions = new ArrayList<>();
        Vector2i direct = to.sub(from).unitDirection();
        if(direct.getX() > 0) directions.add(RIGHT);
        else if (direct.getX() < 0) directions.add(LEFT);
        if(direct.getY() > 0) directions.add(DOWN);
        else if(direct.getY() < 0) directions.add(UP);
        if(directions.isEmpty()) directions.add(UNDEFINED);
        return directions;
    }

    public static Direction2D connected(Vector2i position1, Vector2i position2) {
        for (Direction2D direction : Direction2D.directions()) {
            Vector2i result = position1.add(direction.getVector());
            if(result.getX() == position2.getX() && result.getY() == position2.getY()) {
                return direction;
            }
        }

        return UNDEFINED;
    }

    @Override
    public String toString() {
        if(this.equals(UP)) return "UP";
        if(this.equals(DOWN)) return "DOWN";
        if(this.equals(LEFT)) return "LEFT";
        if(this.equals(RIGHT)) return "RIGHT";
        return "UNDEFINED";
    }

    public int HashCode() {
        return this.toString().hashCode();
    }
}
