package com.example.towerdefense.utility;

import java.util.*;


public enum Directions2D {

    UNDEFINED(EnumSet.of(Direction2D.UNDEFINED)),
    UP(EnumSet.of(Direction2D.UP)), DOWN(EnumSet.of(Direction2D.DOWN)),
    LEFT(EnumSet.of(Direction2D.LEFT)), RIGHT(EnumSet.of(Direction2D.RIGHT)),
    UP_DOWN(EnumSet.of(Direction2D.UP, Direction2D.DOWN)), LEFT_RIGHT(EnumSet.of(Direction2D.LEFT, Direction2D.RIGHT)),
    UP_LEFT(EnumSet.of(Direction2D.UP, Direction2D.LEFT)), UP_RIGHT(EnumSet.of(Direction2D.UP, Direction2D.RIGHT)),
    DOWN_LEFT(EnumSet.of(Direction2D.DOWN, Direction2D.LEFT)), DOWN_RIGHT(EnumSet.of(Direction2D.DOWN, Direction2D.RIGHT)),
    UP_DOWN_LEFT(EnumSet.of(Direction2D.UP, Direction2D.DOWN, Direction2D.LEFT)), UP_DOWN_RIGHT(EnumSet.of(Direction2D.UP, Direction2D.DOWN, Direction2D.RIGHT)),
    UP_LEFT_RIGHT(EnumSet.of(Direction2D.UP, Direction2D.LEFT, Direction2D.RIGHT)), DOWN_LEFT_RIGHT(EnumSet.of(Direction2D.DOWN, Direction2D.LEFT, Direction2D.RIGHT)),
    UP_DOWN_LEFT_RIGHT(EnumSet.of(Direction2D.UP, Direction2D.DOWN, Direction2D.LEFT, Direction2D.RIGHT));

    

    Directions2D(EnumSet <Direction2D> directions) {
        this.directions = directions;
    }

    //For directions in an array
    public static Directions2D getMultiDirection(Direction2D[] directions) {
        if(directions.length == 0) return Directions2D.UNDEFINED;
        for (Directions2D multiDirection : Directions2D.values()) {
            if (multiDirection.getDirections().containsAll(EnumSet.of(directions[0], directions[1]))) {
                return multiDirection;
            }
        }
        return Directions2D.UNDEFINED;
    }

    public int getDirectionCount() {
        return getDirections().size();
    }

    public static Directions2D getMultiDirection(Collection <Direction2D> directions) {
        if(directions.size() == 0) return Directions2D.UNDEFINED;
        for (Directions2D multiDirection : Directions2D.values()) {
            if (multiDirection.getDirections().containsAll(directions)) {
                return multiDirection;
            }
        }
        return Directions2D.UNDEFINED;
    }

    public static Directions2D getMultiDirection(Direction2D direction) {
        for (Directions2D directions2D : Directions2D.values()) {
            if (directions2D.getDirections().contains(direction)) {
                return directions2D;
            }
        }
        return Directions2D.UNDEFINED;
    }

    public Directions2D opposite() {
        if(this.equals(UNDEFINED)) return UP_DOWN_LEFT_RIGHT;
        if(this.equals(UP_DOWN_LEFT_RIGHT)) return UNDEFINED;
        ArrayList<Direction2D> directions = UP_DOWN_LEFT_RIGHT.getDirections();
        directions.removeAll(this.getDirections());
        return getMultiDirection(directions);
    }

    public ArrayList<Direction2D> getDirections() {
        return new ArrayList<>(directions);
    }

    public static boolean connected(Directions2D directions2D1, Directions2D directions2D2) {
        for (Direction2D direction : directions2D1.getDirections()) {
            if (directions2D2.getDirections().contains(direction.getOpposite())) {
                return true;
            }
        }
        return false;
    }

    public boolean connected(Directions2D directions2D1) {
        for (Direction2D direction : directions2D1.getDirections()) {
            if (getDirections().contains(direction.getOpposite())) {
                return true;
            }
        }
        return false;
    }

    private EnumSet<Direction2D> directions;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < directions.size(); i++) {
            stringBuilder.append(directions.toArray()[i]);
            if (i != directions.size() - 1) {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }
}
