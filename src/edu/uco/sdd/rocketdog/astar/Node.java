package edu.uco.sdd.rocketdog.astar;

import javafx.geometry.Point2D;

public class Node implements Comparable {
    Point2D location;
    double weight;

    public Node(Point2D location, double weight) {
        this.location = location;
        this.weight = weight;
    }

    public Point2D getLocation() {
        return location;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(Object o) {
        Node n = (Node)o;
        return Double.compare(weight, n.weight);
    }


}
