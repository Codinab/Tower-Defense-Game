package com.example.towerdefense.Utility;

import java.util.*;


public enum MultiDirection {

    UNDEFINED(EnumSet.of(Direction2D.UNDEFINED)),
    UP(EnumSet.of(Direction2D.UP)), DOWN(EnumSet.of(Direction2D.DOWN)),
    LEFT(EnumSet.of(Direction2D.LEFT)), RIGHT(EnumSet.of(Direction2D.RIGHT)),
    UP_DOWN(EnumSet.of(Direction2D.UP, Direction2D.DOWN)), LEFT_RIGHT(EnumSet.of(Direction2D.LEFT, Direction2D.RIGHT)),
    UP_LEFT(EnumSet.of(Direction2D.UP, Direction2D.LEFT)), UP_RIGHT(EnumSet.of(Direction2D.UP, Direction2D.RIGHT)),
    DOWN_LEFT(EnumSet.of(Direction2D.DOWN, Direction2D.LEFT)), DOWN_RIGHT(EnumSet.of(Direction2D.DOWN, Direction2D.RIGHT)),
    UP_DOWN_LEFT(EnumSet.of(Direction2D.UP, Direction2D.DOWN, Direction2D.LEFT)), UP_DOWN_RIGHT(EnumSet.of(Direction2D.UP, Direction2D.DOWN, Direction2D.RIGHT)),
    UP_LEFT_RIGHT(EnumSet.of(Direction2D.UP, Direction2D.LEFT, Direction2D.RIGHT)), DOWN_LEFT_RIGHT(EnumSet.of(Direction2D.DOWN, Direction2D.LEFT, Direction2D.RIGHT)),
    UP_DOWN_LEFT_RIGHT(EnumSet.of(Direction2D.UP, Direction2D.DOWN, Direction2D.LEFT, Direction2D.RIGHT));

    

    MultiDirection(EnumSet <Direction2D> directions) {
        this.directions = directions;
    }

    //For directions in an array
    public static MultiDirection getMultiDirection(Direction2D[] directions) {
        if(directions.length == 0) return MultiDirection.UNDEFINED;
        for (MultiDirection multiDirection : MultiDirection.values()) {
            if (multiDirection.getDirections().containsAll(EnumSet.of(directions[0], directions[1]))) {
                return multiDirection;
            }
        }
        return MultiDirection.UNDEFINED;
    }
    

    public static MultiDirection getMultiDirection(Collection <Direction2D> directions) {
        if(directions.size() == 0) return MultiDirection.UNDEFINED;
        for (MultiDirection multiDirection : MultiDirection.values()) {
            if (multiDirection.getDirections().containsAll(directions)) {
                return multiDirection;
            }
        }
        return MultiDirection.UNDEFINED;
    }

    public static MultiDirection getMultiDirection(Direction2D direction) {
        for (MultiDirection multiDirection : MultiDirection.values()) {
            if (multiDirection.getDirections().contains(direction)) {
                return multiDirection;
            }
        }
        return MultiDirection.UNDEFINED;
    }

    public MultiDirection opposite() {
        if(this.equals(UNDEFINED)) return UP_DOWN_LEFT_RIGHT;
        if(this.equals(UP_DOWN_LEFT_RIGHT)) return UNDEFINED;
        ArrayList<Direction2D> directions = UP_DOWN_LEFT_RIGHT.getDirections();
        directions.removeAll(this.getDirections());
        return getMultiDirection(directions);
    }

    public ArrayList<Direction2D> getDirections() {
        return new ArrayList<>(directions);
    }

    public static boolean connected(MultiDirection multiDirection1, MultiDirection multiDirection2) {
        for (Direction2D direction : multiDirection1.getDirections()) {
            if (multiDirection2.getDirections().contains(direction.getOpposite())) {
                return true;
            }
        }
        return false;
    }

    public boolean connected(MultiDirection multiDirection1) {
        for (Direction2D direction : multiDirection1.getDirections()) {
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
