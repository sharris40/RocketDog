package edu.uco.sdd.rocketdog.controller;

import edu.uco.sdd.rocketdog.astar.Node;
import edu.uco.sdd.rocketdog.astar.OrderedNodeMap;
import edu.uco.sdd.rocketdog.astar.Point;
import edu.uco.sdd.rocketdog.model.Entity;
import edu.uco.sdd.rocketdog.model.Obstruction;
import edu.uco.sdd.rocketdog.model.TangibleEntity;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ObjectTraverseController extends AccelerationController {

    private MovementController next;
    private Point2D nextVelocity, nextAcceleration, destPoint, lastPosition;
    private List<Point2D> path;

    public ObjectTraverseController(TangibleEntity entity, MovementController nextController, Point2D nextVelocity, Point2D nextAcceleration) {
        super(entity);
        this.next = nextController;
        this.nextVelocity = nextVelocity;
        this.nextAcceleration = nextAcceleration;
        path = new LinkedList<>();
        findPathOut();
    }

    public MovementController getNext() {
        return next;
    }

    public void setNext(MovementController next) {
        this.next = next;
    }

    public Point2D getNextVelocity() {
        return nextVelocity;
    }

    public void setNextVelocity(Point2D nextVelocity) {
        this.nextVelocity = nextVelocity;
    }

    public Point2D getNextAcceleration() {
        return nextAcceleration;
    }

    public void setNextAcceleration(Point2D nextAcceleration) {
        this.nextAcceleration = nextAcceleration;
    }

    /*private boolean isBlockedVertical(Bounds self, Bounds obstacle, Point2D loc, Point2D dest) {
        Point2D toDest = dest.subtract(loc).normalize();
        Point2D nextLoc = loc.add(toDest);
        return !(nextLoc.getY() > obstacle.getMaxY() || nextLoc.getY() + self.getHeight() < obstacle.getMinY());
    }

    private boolean isBlockedHorizontal(Bounds self, Bounds obstacle, Point2D loc, Point2D dest) {
        Point2D toDest = dest.subtract(loc).normalize();
        Point2D nextLoc = loc.add(toDest);
        return !(nextLoc.getX() > obstacle.getMaxX() || nextLoc.getX() + self.getWidth() < obstacle.getMinX());
    }*/

    private boolean positionBlocked(Bounds bounds) {
        Bounds modifiedBounds = new BoundingBox(bounds.getMinX() + 4, bounds.getMinY() + 4, bounds.getWidth() - 8, bounds.getHeight() - 8);
        for (Obstruction obstruction : controlledObject.getLevel().getObstructions()) {
            if (modifiedBounds.intersects(obstruction.getHitbox().getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }

    private List<Point2D> getPath(Map<Point, Point> from, Point end) {
        LinkedList<Point2D> currentPath = new LinkedList<>();
        currentPath.add(new Point2D(end.getX(), end.getY()));
        Point current = end;
        while (from.containsKey(current)) {
            current = from.get(current);
            currentPath.addFirst(new Point2D(current.getX(), current.getY()));
        }
        return currentPath;
    }

    private List<Point2D> astar(Point start, double xSpacing, double ySpacing, Point beforeStart) {
        Point dest = new Point(destPoint, start.getXRange(), start.getYRange());
        Set<Point> closed = new HashSet<>();
        Set<Point> open = new HashSet<>();
        open.add(start);
        Map<Point, Point> from = new HashMap<>();
        OrderedNodeMap scoreFromStart = new OrderedNodeMap();
        scoreFromStart.put(start, 0., 0);
        OrderedNodeMap totalScore = new OrderedNodeMap();
        totalScore.put(start, dest.getDistance(start), 0);
        Point[] nextPoints = new Point[3];
        double[] nextSpacing = new double[3];
        while (!open.isEmpty()) {
            List<Node> nodes = totalScore.getNodeList();
            Node current = null;
            for (Node node : nodes) {
                if (open.contains(node.getLocation())) {
                    current = node;
                    break;
                }
            }
            if (current == null)
                return null;
            Point previous = from.get(current.getLocation());
            if (previous == null)
                previous = beforeStart;
            if (dest.closeTo(current.getLocation()))
                return getPath(from, current.getLocation());
            open.remove(current.getLocation());
            closed.add(current.getLocation());
            Point up = current.getLocation().above(ySpacing);
            Point down = current.getLocation().below(ySpacing);
            Point left = current.getLocation().left(xSpacing);
            Point right = current.getLocation().right(xSpacing);
            if (previous.closeTo(current.getLocation().above(ySpacing))) {
                nextPoints[0] = down;
                nextPoints[1] = left;
                nextPoints[2] = right;
                nextSpacing[0] = ySpacing;
                nextSpacing[1] = xSpacing;
                nextSpacing[2] = xSpacing;
            } else if (previous.closeTo(current.getLocation().below(ySpacing))) {
                nextPoints[0] = up;
                nextPoints[1] = left;
                nextPoints[2] = right;
                nextSpacing[0] = ySpacing;
                nextSpacing[1] = xSpacing;
                nextSpacing[2] = xSpacing;
            } else if (previous.closeTo(current.getLocation().left(xSpacing))) {
                nextPoints[0] = right;
                nextPoints[1] = up;
                nextPoints[2] = down;
                nextSpacing[0] = xSpacing;
                nextSpacing[1] = ySpacing;
                nextSpacing[2] = ySpacing;
            } else {
                nextPoints[0] = left;
                nextPoints[1] = up;
                nextPoints[2] = down;
                nextSpacing[0] = xSpacing;
                nextSpacing[1] = ySpacing;
                nextSpacing[2] = ySpacing;
            }
            for (int i = 0; i < 3; ++i) {
                ListIterator<Node> existingPoint = totalScore.findPosition(nextPoints[i]);
                if (existingPoint != null)
                    nextPoints[i] = existingPoint.previous().getLocation();
                if (!closed.contains(nextPoints[i])) {
                    if (positionBlocked(new BoundingBox(nextPoints[i].getX(), nextPoints[i].getY(),
                            controlledObject.getHitbox().getBoundsInParent().getWidth(),
                            controlledObject.getHitbox().getBoundsInParent().getHeight()))) {
                        open.remove(nextPoints[i]);
                        closed.add(nextPoints[i]);
                    } else {
                        double currentGuess = nextSpacing[i] + scoreFromStart.get(current.getLocation());
                        boolean add = true;
                        if (!open.contains(nextPoints[i])) {
                            open.add(nextPoints[i]);
                        } else if (currentGuess >= scoreFromStart.get(nextPoints[i])) {
                            add = false;
                        }
                        if (add) {
                            from.put(nextPoints[i], current.getLocation());
                            scoreFromStart.put(nextPoints[i], currentGuess, i);
                            double score = currentGuess + dest.getDistance(nextPoints[i]);
                            totalScore.put(nextPoints[i], currentGuess + dest.getDistance(nextPoints[i]), i);
                        }
                    }
                }
            }
        }
        return null;
    }

    private void findPathOut() {
        Bounds cbtemp = controlledObject.getHitbox().getBoundsInParent(),
                controlledBounds;
        double x = cbtemp.getMinX(), y = cbtemp.getMinY(), width = cbtemp.getWidth(), height = cbtemp.getHeight();
        Point2D velAdd = new Point2D(nextVelocity.getX(), nextVelocity.getY());
        do {
            x += velAdd.getX();
            y += velAdd.getY();
            controlledBounds = new BoundingBox(x, y, width, height);
            velAdd = velAdd.add(nextAcceleration);
        } while (positionBlocked(controlledBounds));
        destPoint = new Point2D(x, y);
        Point start = new Point(Math.round(cbtemp.getMinX()), Math.round(cbtemp.getMinY()));
        start.setXRange(cbtemp.getWidth() / 4.);
        start.setYRange(cbtemp.getHeight() / 4.);
        double xRange = cbtemp.getWidth() / 4. + 2;
        double yRange = cbtemp.getHeight() / 4. + 2;
        Point previous = null;
        if (Math.abs(nextVelocity.getY()) > Math.abs(nextVelocity.getX())) {
            if (nextVelocity.getY() > 0) {
                previous = start.above((yRange < 8) ? 8. : yRange);
            } else {
                previous = start.below((yRange < 8) ? 8. : yRange);
            }
        } else {
            if (nextVelocity.getX() > 0) {
                previous = start.left((xRange < 8) ? 8. : xRange);
            } else {
                previous = start.right((xRange < 8) ? 8. : xRange);
            }

        }
        path = astar(start, (xRange < 8) ? 8. : xRange, (yRange < 8) ? 8. : yRange, previous);
        lastPosition = controlledObject.getPosition();
    }

    @Override
    public boolean process(Map<Entity, Boolean> changedEntities) {
        Point2D newPosition = controlledObject.getPosition();
        boolean nextPoint = false;
        Point2D vNew = newPosition.subtract(path.get(0));
        Point2D vOld = lastPosition.subtract(path.get(0));
        if (vNew.magnitude() < 0.25) {
            nextPoint = true;
        }
        double cos = vNew.dotProduct(vOld) / vNew.magnitude() / vOld.magnitude();
        if (cos > 1)
            cos = 1;
        if (cos < -1)
            cos = -1;
        if (Math.acos(cos) > Math.PI / 2.) {
            nextPoint = true;
        }
        if (nextPoint)
            path.remove(0);
        if (path.isEmpty()) {
            double addWidth = controlledObject.getHitbox().getStrokeWidth() / 2.;
            controlledObject.setPosition(destPoint.add(addWidth, addWidth));
            controlledObject.setVelocity(nextVelocity);
            controlledObject.removeController(this);
            controlledObject.addController(next);
            return true;
        }
        lastPosition = newPosition;
        controlledObject.setVelocity(path.get(0).subtract(lastPosition).normalize().multiply(nextVelocity.magnitude()));
        return true;
    }

}
