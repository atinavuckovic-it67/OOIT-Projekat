package model;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends SurfaceShape {
    private Point upperLeft;
    private int width;
    private int height;

    public Rectangle() {
    }

    public Rectangle(Point upperLeft, int width, int height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    public Rectangle(Point upperLeft, int width, int height, Color color, Color innerColor) {
        super(color, innerColor);
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    @Override
    public void fill(Graphics g) {
        g.setColor(getInnerColor());
        g.fillRect(upperLeft.getX(), upperLeft.getY(), width, height);
    }

    @Override
    public void draw(Graphics g) {
        fill(g);
        g.setColor(getColor());
        g.drawRect(upperLeft.getX(), upperLeft.getY(), width, height);

        if (isSelected()) {
            g.setColor(Color.BLUE);
            g.drawRect(upperLeft.getX() - 3, upperLeft.getY() - 3, 6, 6);
            g.drawRect(upperLeft.getX() + width - 3, upperLeft.getY() - 3, 6, 6);
            g.drawRect(upperLeft.getX() - 3, upperLeft.getY() + height - 3, 6, 6);
            g.drawRect(upperLeft.getX() + width - 3, upperLeft.getY() + height - 3, 6, 6);
        }
    }

    @Override
    public boolean contains(int x, int y) {
        return x >= upperLeft.getX() && x <= upperLeft.getX() + width &&
               y >= upperLeft.getY() && y <= upperLeft.getY() + height;
    }

    @Override
    public double area() {
        return width * height;
    }

    public Point getUpperLeft() {
        return upperLeft;
    }

    public void setUpperLeft(Point upperLeft) {
        this.upperLeft = upperLeft;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Rectangle: UpperLeft=(" + upperLeft.getX() + ", " + upperLeft.getY() + "), Width=" + width + ", Height=" + height;
    }
}
