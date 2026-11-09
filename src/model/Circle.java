package model;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends SurfaceShape implements Comparable<Shape> {
    private Point center;
    private int radius;

    public Circle() {
    }

    public Circle(Point center, int radius) {
        this.center = center;
        this.radius = radius;
    }

    public Circle(Point center, int radius, Color color, Color innerColor) {
        super(color, innerColor);
        this.center = center;
        this.radius = radius;
    }

    @Override
    public void fill(Graphics g) {
        g.setColor(getInnerColor());
        g.fillOval(center.getX() - radius, center.getY() - radius, 2 * radius, 2 * radius);
    }

    @Override
    public void draw(Graphics g) {
        fill(g);
        g.setColor(getColor());
        g.drawOval(center.getX() - radius, center.getY() - radius, 2 * radius, 2 * radius);

        if (isSelected()) {
            g.setColor(Color.BLUE);
            g.drawRect(center.getX() - 3, center.getY() - 3, 6, 6);
            g.drawRect(center.getX() - radius - 3, center.getY() - 3, 6, 6);
            g.drawRect(center.getX() + radius - 3, center.getY() - 3, 6, 6);
            g.drawRect(center.getX() - 3, center.getY() - radius - 3, 6, 6);
            g.drawRect(center.getX() - 3, center.getY() + radius - 3, 6, 6);
        }
    }

    @Override
    public boolean contains(int x, int y) {
        return center.distance(x, y) <= radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public int compareTo(Shape o) {
        if (o instanceof Circle) {
            return Double.compare(this.area(), ((Circle) o).area());
        }
        return 0;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Circle: Center=(" + center.getX() + ", " + center.getY() + "), Radius=" + radius;
    }
}
