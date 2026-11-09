package model;

import java.awt.Color;
import java.awt.Graphics;

public class Point extends Shape {
    private int x;
    private int y;

    public Point() {
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, Color color) {
        super(color);
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(getColor());
        g.drawLine(x - 2, y, x + 2, y);
        g.drawLine(x, y - 2, x, y + 2);

        if (isSelected()) {
            g.setColor(Color.BLUE);
            g.drawRect(x - 3, y - 3, 6, 6);
        }
    }

    @Override
    public boolean contains(int x, int y) {
        return this.distance(x, y) <= 4;
    }

    public double distance(int pointX, int pointY) {
        int dX = this.x - pointX;
        int dY = this.y - pointY;
        return Math.sqrt(dX * dX + dY * dY);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point: (" + x + ", " + y + ")";
    }
}
