package com.example.towerdefense.Utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class MultiVector implements Serializable {
    private HashMap<Vector2Di, MultiDirection> vectorHashMap;

    public MultiVector(Vector2Di position) {this(position, MultiDirection.UNDEFINED);}
    public MultiVector(Vector2Di position, MultiDirection multiDirection) {
        vectorHashMap = new HashMap<>();
        vectorHashMap.put(position, multiDirection);
    }
    public MultiVector(Vector2Di[] positions) {
        vectorHashMap = new HashMap<>();
        add(positions);
    }


    public boolean addNotSafe(Vector2Di position, MultiDirection multiDirection) {
        vectorHashMap.put(position, multiDirection);
        return true;
    }
    public boolean addNotSafe(Vector2Di position) {
        vectorHashMap.put(position, MultiDirection.UNDEFINED);
        return true;
    }
    protected boolean add(Vector2Di position, MultiDirection multiDirection) {
        if(!nexTo(position)) return false;
        vectorHashMap.put(position, multiDirection);
        update();
        return true;
    }
    public Collection<Vector2Di> add(Vector2Di[] positions) {
        Collection<Vector2Di> notAdded = new ArrayList<>();
        for(Vector2Di position : positions) {
            if(!add(position)) notAdded.add(position);
        }
        return notAdded;
    }
    public boolean add(Vector2Di position) {
        if(!nexTo(position)) return false;
        vectorHashMap.put(position, MultiDirection.UNDEFINED);
        update();
        return true;
    }
    public boolean add(MultiVector road) {
        if(!nexTo(road)) return false;
        vectorHashMap.putAll(road.vectorHashMap);
        update();
        return true;
    }

    public Collection<Vector2Di> getPositions() {
        return vectorHashMap.keySet();
    }

    public Collection<Vector2Di> getExternalPositions() {
        Collection<Vector2Di> externalPositions = new ArrayList<>();
        for(Vector2Di position : vectorHashMap.keySet()) {
            if(!vectorHashMap.get(position).equals(MultiDirection.UP_DOWN_LEFT_RIGHT)) externalPositions.add(position);
        }
        return externalPositions;
    }

    public HashMap<Vector2Di, MultiDirection> getConnectableDirections() {
        HashMap<Vector2Di, MultiDirection> connectableDirections = new HashMap<>();
        Collection<Vector2Di> externalPositions = getExternalPositions();
        for(Vector2Di position: externalPositions) {
            connectableDirections.put(position, vectorHashMap.get(position).opposite());
        }
        return connectableDirections;
    }

    public MultiDirection getRoadType(Vector2Di position) {
        return vectorHashMap.get(position);
    }
    public void update(){
        for (Vector2Di position : vectorHashMap.keySet()) {
            MultiDirection multiDirection = vectorHashMap.get(position);

            ArrayList<Direction2D> newDirections = new ArrayList<>();
            for (Direction2D direction : Direction2D.directions()) {
                if (vectorHashMap.containsKey(position.add(direction.getVector()))) {
                    newDirections.add(direction);
                }
            }
            MultiDirection newMultiDirection = MultiDirection.getMultiDirection(newDirections);
            if (newMultiDirection.equals(multiDirection)) continue;
            vectorHashMap.replace(position, newMultiDirection);
        }
    }

    public Collection<Vector2Di> getConnectablePositions() {
        Collection<Vector2Di> positions = getPositions();
        Collection<Vector2Di> connectablePositions = new ArrayList<>();
        for (Vector2Di position : positions) {
            MultiDirection multiDirection = getRoadType(position).opposite();
            if (multiDirection.equals(MultiDirection.UNDEFINED)) continue;
            for(Direction2D direction : multiDirection.getDirections()) {
                if (vectorHashMap.containsKey(position.add(direction.getVector()))) continue;
                connectablePositions.add(position.add(direction.getVector()));
            }
        }
        return connectablePositions;
    }

    public boolean nexTo(MultiVector road2) {
        Collection<Vector2Di> positions1 = getPositions();
        Collection<Vector2Di> positions2 = road2.getConnectablePositions();
        if(positions1.size() == 0 || positions2.size() == 0) return false;
        return positions1.stream().anyMatch(positions2::contains);
    }
    private boolean nexTo(Vector2Di position) {
        return getConnectablePositions().contains(position);
    }

    @Override
    public String toString() {
        //sort roadMap
        Vector2Di[] positions = vectorHashMap.keySet().toArray(new Vector2Di[0]);
        Arrays.sort(positions);
        //create string
        StringBuilder stringBuilder = new StringBuilder();
        for (Vector2Di position : positions) {
            stringBuilder.append(position.toString());
            stringBuilder.append("=");
            stringBuilder.append(vectorHashMap.get(position).toString());
            stringBuilder.append(" ; ");
        }
        return stringBuilder.toString();
    }

    public int size() {
        return vectorHashMap.size();
    }

    public Vector2Di get(int i) {
        if (i >= size() || i < 0) return null;
        return (Vector2Di) vectorHashMap.keySet().toArray()[i];
    }

    public void printVectors() {
        int[][] matrix = new int[size() * 2 + 2][size() * 2 + 2];
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                matrix[i][j] = 0;
            }
        }
        //fill matrix
        for (int i = 0; i < size(); i++) {
            matrix[get(i).getX() + size() + 1][get(i).getY() + size() + 1] = 1;
        }

        //print matrix
        for (int i = 0; i < size() * 2 + 2; i++) {
            for (int j = 0; j < size() * 2 + 2; j++) {
                if(matrix[i][j] == 1) System.out.print("$");
                else System.out.print(" ");
            }
            System.out.println();
        }
    }
}