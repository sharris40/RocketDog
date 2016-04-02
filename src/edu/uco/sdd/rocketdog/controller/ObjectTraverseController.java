package edu.uco.sdd.rocketdog.controller;

import edu.uco.sdd.rocketdog.model.Entity;
import edu.uco.sdd.rocketdog.model.Obstruction;
import edu.uco.sdd.rocketdog.model.TangibleEntity;
import java.util.LinkedList;
import java.util.Map;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;

public class ObjectTraverseController extends AccelerationController {

    private Obstruction target;
    private MovementController next;
    private Point2D nextVelocity, nextAcceleration, destPoint, lastPosition;
    private final LinkedList<Point2D> path;

    public ObjectTraverseController(TangibleEntity entity, Obstruction target, MovementController nextController, Point2D nextVelocity, Point2D nextAcceleration) {
        super(entity);
        this.target = target;
        this.next = nextController;
        this.nextVelocity = nextVelocity;
        this.nextAcceleration = nextAcceleration;
        path = new LinkedList<>();
        findPathOut();
    }

    public Obstruction getTarget() {
        return target;
    }

    public void setTarget(Obstruction target) {
        this.target = target;
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

    private void findPathOut() {
        Bounds cbtemp = controlledObject.getHitbox().getBoundsInParent(),
                targetBounds = target.getHitbox().getBoundsInParent(),
                controlledBounds;
        double x = cbtemp.getMinX(), y = cbtemp.getMinY(), width = cbtemp.getWidth(), height = cbtemp.getHeight(), vel = nextVelocity.magnitude();
        Point2D velAdd = new Point2D(nextVelocity.getX(), nextVelocity.getY());
        do {
            x += velAdd.getX();
            y += velAdd.getY();
            controlledBounds = new BoundingBox(x, y, width, height);
            velAdd = velAdd.add(nextAcceleration);
        } while (controlledBounds.intersects(target.getHitbox().getBoundsInParent()));
        destPoint = new Point2D(x, y);
        boolean blockedUp = true, blockedDown = true, blockedLeft = true, blockedRight = true;
        if (cbtemp.getMinX() + nextVelocity.getX() > targetBounds.getMaxX() || cbtemp.getMaxX() + nextVelocity.getX() < targetBounds.getMinX()) {
            blockedRight = false;
            blockedLeft = false;
        } else if (nextVelocity.getX() > 0) { // moving right
            blockedLeft = false;
        } else {
            blockedRight = false;
        }
        if (cbtemp.getMinY() + nextVelocity.getY() > targetBounds.getMaxY() || cbtemp.getMaxY() + nextVelocity.getY() < targetBounds.getMinY()) {
            blockedUp = false;
            blockedDown = false;
        } else if (nextVelocity.getY() > 0) { // moving down
            blockedUp = false;
        } else {
            blockedDown = false;
        }
        if (blockedUp || blockedDown) {
            // check left first
            Point2D pointLeft1 = new Point2D(targetBounds.getMinX() - cbtemp.getWidth() - 1, cbtemp.getMinY());
            Point2D pointLeft2 = new Point2D(pointLeft1.getX(), destPoint.getY());
            // check right first
            Point2D pointRight1 = new Point2D(targetBounds.getMaxX() + 1, cbtemp.getMinY());
            Point2D pointRight2 = new Point2D(pointRight1.getX(), destPoint.getY());
            double distLeft = destPoint.getX() - pointLeft2.getX() + Math.abs(pointLeft2.getY() - pointLeft1.getY()) + cbtemp.getMinX() - pointLeft1.getX();
            double distRight = pointRight2.getX() - destPoint.getX() + Math.abs(pointRight2.getY() - pointRight1.getY()) + pointRight1.getX() - cbtemp.getMinX();
            if (distLeft < distRight) {
                path.add(pointLeft1);
                path.add(pointLeft2);
                path.add(destPoint);
            } else {
                path.add(pointRight1);
                path.add(pointRight2);
            }
        } else if (blockedLeft || blockedRight) {
            // check up first
            Point2D pointUp1 = new Point2D(cbtemp.getMinX(), targetBounds.getMinY() - cbtemp.getHeight() - 1);
            Point2D pointUp2 = new Point2D(destPoint.getX(), pointUp1.getY());
            // check down first
            Point2D pointDown1 = new Point2D(cbtemp.getMinX(), targetBounds.getMaxY() + 1);
            Point2D pointDown2 = new Point2D(destPoint.getX(), pointDown1.getY());
            double distUp = destPoint.getY() - pointUp2.getY() + Math.abs(pointUp2.getX() - pointUp1.getX()) + cbtemp.getMinY() - pointUp1.getY();
            double distDown = pointDown2.getY() - destPoint.getY() + Math.abs(pointDown2.getX() - pointDown1.getX()) + pointDown1.getY() - cbtemp.getMinY();
            if (distUp < distDown) {
                path.add(pointUp1);
                path.add(pointUp2);
                path.add(destPoint);
            } else {
                path.add(pointDown1);
                path.add(pointDown2);
            }
        }
        path.add(destPoint);
        lastPosition = controlledObject.getPosition();
    }

    @Override
    public boolean process(Map<Entity, Boolean> changedEntities) {
        Point2D newPosition = controlledObject.getPosition();
        boolean nextPoint = false;
        if (newPosition.getX() - lastPosition.getX() > 0) {
            if (newPosition.getX() - path.get(0).getX() >= 0)
                nextPoint = true;
        } else {
            if (newPosition.getX() - path.get(0).getX() <= 0)
                nextPoint = true;
        }
        if (newPosition.getY() - lastPosition.getY() > 0) {
            if (newPosition.getY() - path.get(0).getY() >= 0)
                nextPoint = true;
        } else {
            if (newPosition.getY() - path.get(0).getY() <= 0)
                nextPoint = true;
        }
        if (nextPoint)
            path.remove();
        if (path.isEmpty()) {
            controlledObject.removeController(this);
            controlledObject.addController(next);
            return next.process(changedEntities);
        }
        lastPosition = newPosition;
        controlledObject.setVelocity(path.get(0).subtract(lastPosition).normalize().multiply(nextVelocity.magnitude()));
        return true;
    }

}
