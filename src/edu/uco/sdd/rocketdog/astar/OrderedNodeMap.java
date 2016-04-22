package edu.uco.sdd.rocketdog.astar;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javafx.geometry.Point2D;

public class OrderedNodeMap {
    private final LinkedList<Node> nodes;

    public OrderedNodeMap() {
        nodes = new LinkedList<>();
    }

    public int size() {
        return nodes.size();
    }

    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    public List<Node> getNodeList() {
        return nodes;
    }

    public Double get(Object key) {
        if (isEmpty() || key == null || !(key instanceof Point2D))
            return null;
        for (Node n : nodes) {
            if (n.getLocation().equals(key))
                return n.getWeight();
        }
        return null;
    }

    public void put(Point2D key, Double value) {
        if (isEmpty() || key == null || value == null)
            return;
        if (value < 0 || (value.isNaN() && !value.isInfinite()))
            return;
        ListIterator<Node> pos = findPosition(key);
        if (pos != null) {
            pos.remove();
        }
        pos = findPosition(value);
        pos.add(new Node(key, value));
    }

    private ListIterator<Node> findPosition(Double d) {
        if (d == null)
            return null;
        if (d.isNaN() && !d.isInfinite() || d < 0)
            return null;
        if (d == 0) {
            return nodes.listIterator();
        }
        if (d.isInfinite()) {
            return nodes.listIterator(nodes.size());
        }
        ListIterator<Node> iterator = nodes.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().getWeight() >= d) {
                return iterator;
            }
        }
        return iterator;
    }

    public ListIterator<Node> findPosition(Point2D key) {
        if (key == null)
            return null;
        ListIterator<Node> iterator = nodes.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().getLocation().equals(key)) {
                return iterator;
            }
        }
        return null;

    }
}
