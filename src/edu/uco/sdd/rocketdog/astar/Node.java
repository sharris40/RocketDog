package edu.uco.sdd.rocketdog.astar;

import javafx.geometry.Point2D;

public class Node implements Comparable {
    Point location;
    double weight;
    int preference;

    public Node(Point location, double weight, int preference) {
        this.location = location;
        this.weight = weight;
        this.preference = preference;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getPreference() {
        return preference;
    }

    public void setPreference(int preference) {
        this.preference = preference;
    }

    @Override
    public int compareTo(Object o) {
        Node n = (Node)o;
        int compare = Double.compare(weight, n.weight);
        if (compare == 0)
            compare = Integer.compare(preference, n.preference);
        return compare;
    }


}
