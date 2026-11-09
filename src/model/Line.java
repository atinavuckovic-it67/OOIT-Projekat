package model;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape {
    private Point startPoint;
    private Point endPoint;

    public Line() {
    }

    public Line(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Line(Point startPoint, Point endPoint, Color color) {
        super(color);
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.drawLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());

        if (isSelected()) {
            g.setColor(Color.BLUE);
            g.drawRect(startPoint.getX() - 3, startPoint.getY() - 3, 6, 6);
            g.drawRect(endPoint.getX() - 3, endPoint.getY() - 3, 6, 6);
            int midX = (startPoint.getX() + endPoint.getX()) / 2;
            int midY = (startPoint.getY() + endPoint.getY()) / 2;
            g.drawRect(midX - 3, midY - 3, 6, 6);
        }
    }

    @Override
    public boolean contains(int x, int y) {
        double d1 = startPoint.distance(x, y);
        double d2 = endPoint.distance(x, y);
        double lineLen = startPoint.distance(endPoint.getX(), endPoint.getY());
        return (d1 + d2) >= lineLen - 0.5 && (d1 + d2) <= lineLen + 0.5;
    }

    public double length() {
        return startPoint.distance(endPoint.getX(), endPoint.getY());
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public String toString() {
        return "Line: (" + startPoint.getX() + ", " + startPoint.getY() + ") -> (" + endPoint.getX() + ", " + endPoint.getY() + ")";
    }
}
