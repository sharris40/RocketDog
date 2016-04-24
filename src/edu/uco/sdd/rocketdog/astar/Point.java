package edu.uco.sdd.rocketdog.astar;

import javafx.geometry.Point2D;

public class Point {
    private long x;
    private long y;
    private double xRange = 0;
    private double yRange = 0;

    public Point(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point2D point) {
        this.x = Math.round(point.getX());
        this.y = Math.round(point.getY());
    }

    public Point(long x, long y, double xRange, double yRange) {
        this.x = x;
        this.y = y;
        this.xRange = xRange;
        this.yRange = yRange;
    }

    public Point(Point2D point, double xRange, double yRange) {
        this.x = Math.round(point.getX());
        this.y = Math.round(point.getY());
        this.xRange = xRange;
        this.yRange = yRange;
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public double getXRange() {
        return xRange;
    }

    public void setXRange(double xRange) {
        if (Double.isNaN(xRange) && !Double.isInfinite(xRange))
            this.xRange = 0.;
        else if (xRange < 0)
            this.xRange = -xRange;
        else
            this.xRange = xRange;
    }

    public double getYRange() {
        return yRange;
    }

    public void setYRange(double yRange) {
        if (Double.isNaN(yRange) && !Double.isInfinite(yRange))
            this.yRange = 0.;
        else if (yRange < 0)
            this.yRange = -yRange;
        else
            this.yRange = yRange;
    }

    public boolean closeTo(Point compare) {
        double diffX = Math.abs((double)x - (double)compare.x);
        double diffY = Math.abs((double)y - (double)compare.y);
        return diffX < xRange && diffY < yRange;
    }

    public boolean closeTo(Point2D point) {
        if (point == null)
            return false;
        double diffX = Math.abs((double)x - point.getX());
        double diffY = Math.abs((double)y - point.getY());
        return diffX < xRange && diffY < yRange;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Point))
            return false;
        Point point = (Point) o;
        return (x == point.x && y == point.y);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (int) (this.x ^ (this.x >>> 32));
        hash = 47 * hash + (int) (this.y ^ (this.y >>> 32));
        return hash;
    }

    public double getDistance(Point other) {
        return Math.sqrt(Math.pow((double)x - (double)other.x, 2) + Math.pow((double)y - (double)other.y, 2));
    }

    public Point above(double difference) {
        return new Point(x, Math.round((double)y - difference), xRange, yRange);
    }

    public Point below(double difference) {
        return new Point(x, Math.round((double)y + difference), xRange, yRange);
    }

    public Point left(double difference) {
        return new Point(Math.round((double)x - difference), y, xRange, yRange);
    }

    public Point right(double difference) {
        return new Point(Math.round((double)x + difference), y, xRange, yRange);
    }
}
