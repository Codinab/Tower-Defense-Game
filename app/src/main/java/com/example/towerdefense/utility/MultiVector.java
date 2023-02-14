package com.example.towerdefense.utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;


public class MultiVector implements Serializable {
    protected HashMap<Vector2i, Directions2D> vectorConnections;

    //Has to be initialized by addNotSafe()
    public MultiVector() {
        vectorConnections = new HashMap<>();
    }
    public MultiVector(Vector2i position) {
        this(position, Directions2D.UNDEFINED);
    }
    private MultiVector(Vector2i position, Directions2D directions2D) {
        vectorConnections = new HashMap<>();
        vectorConnections.put(position, directions2D);
    }

    protected boolean addNotSafe(Vector2i position, Directions2D directions2D) {
        vectorConnections.put(position, directions2D);
        return true;
    }
    protected boolean addNotSafe(Vector2i position) {
        vectorConnections.put(position, Directions2D.UNDEFINED);
        return true;
    }
    protected boolean add(Vector2i position, Directions2D directions2D) {
        if(!nexTo(position)) return false;
        vectorConnections.put(position, directions2D);
        update();
        return true;
    }
    public Collection<Vector2i> add(Vector2i[] positions) {
        Collection<Vector2i> notAdded = new ArrayList<>();
        for(Vector2i position : positions) {
            if(!add(position)) notAdded.add(position);
        }
        return notAdded;
    }
    public boolean add(Vector2i position) {
        if(!nexTo(position)) return false;
        vectorConnections.put(position, Directions2D.UNDEFINED);
        update();
        return true;
    }
    public boolean add(MultiVector multiVector) {
        if(!nexTo(multiVector)) return false;
        vectorConnections.putAll(multiVector.vectorConnections);
        update();
        return true;
    }

    public Collection<Vector2i> getPositions() {
        return vectorConnections.keySet();
    }

    public int size() {
        return vectorConnections.size();
    }

    public boolean contains(Vector2i vector2Di) {
        return vectorConnections.containsKey(vector2Di);
    }



    public Collection<Vector2i> getExternalPositions() {
        Collection<Vector2i> externalPositions = new ArrayList<>();
        for(Vector2i position : vectorConnections.keySet()) {
            if(!vectorConnections.get(position).equals(Directions2D.UP_DOWN_LEFT_RIGHT)) externalPositions.add(position);
        }
        return externalPositions;
    }

    public HashMap<Vector2i, Directions2D> getConnectableDirections() {
        HashMap<Vector2i, Directions2D> connectableDirections = new HashMap<>();
        Collection<Vector2i> externalPositions = getExternalPositions();
        for(Vector2i position: externalPositions) {
            connectableDirections.put(position, vectorConnections.get(position).opposite());
        }
        return connectableDirections;
    }

    public Directions2D getDirections(Vector2i position) {
        return vectorConnections.get(position);
    }

    public Directions2D[] getAllDirections() {
        return vectorConnections.values().toArray(new Directions2D[0]);
    }
    public void update(){
        for (Vector2i position : vectorConnections.keySet()) {
            Directions2D directions2D = vectorConnections.get(position);

            ArrayList<Direction2D> newDirections = new ArrayList<>();
            for (Direction2D direction : Direction2D.directions()) {
                if (vectorConnections.containsKey(position.add(direction.getVector()))) {
                    newDirections.add(direction);
                }
            }
            Directions2D newMultiDirection = Directions2D.getMultiDirection(newDirections);
            if (newMultiDirection.equals(directions2D)) continue;
            vectorConnections.replace(position, newMultiDirection);
        }
    }

    public Collection<Vector2i> getConnectablePositions() {
        Collection<Vector2i> positions = getPositions();
        Collection<Vector2i> connectablePositions = new ArrayList<>();
        for (Vector2i position : positions) {
            Directions2D directions2D = getDirections(position).opposite();
            if (directions2D.equals(Directions2D.UNDEFINED)) continue;
            for(Direction2D direction : directions2D.getDirections()) {
                if (vectorConnections.containsKey(position.add(direction.getVector()))) continue;
                connectablePositions.add(position.add(direction.getVector()));
            }
        }
        return connectablePositions;
    }

    public boolean nexTo(MultiVector multiVector) {
        Collection<Vector2i> positions1 = getPositions();
        Collection<Vector2i> positions2 = multiVector.getConnectablePositions();
        if(positions1.size() == 0 || positions2.size() == 0) return false;
        return positions1.stream().anyMatch(positions2::contains);
    }
    private boolean nexTo(Vector2i position) {
        return getConnectablePositions().contains(position);
    }

    @Override
    public String toString() {
        Vector2i[] positions = vectorConnections.keySet().toArray(new Vector2i[0]);
        Arrays.sort(positions);
        //create string
        StringBuilder stringBuilder = new StringBuilder();
        for (Vector2i position : positions) {
            stringBuilder.append(position.toString());
            stringBuilder.append("=");
            stringBuilder.append(vectorConnections.get(position).toString());
            stringBuilder.append(" ; ");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiVector that = (MultiVector) o;
        return Objects.equals(vectorConnections, that.vectorConnections);
    }

    public Vector2i get(int i) {
        if (i >= size() || i < 0) return null;
        return (Vector2i) vectorConnections.keySet().toArray()[i];
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