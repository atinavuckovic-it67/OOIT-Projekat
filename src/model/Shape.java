package model;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Shape implements Comparable<Shape> {
    private boolean selected;
    private Color color = Color.BLACK;

    public Shape() {
    }

    public Shape(Color color) {
        this.color = color;
    }

    public abstract void draw(Graphics g);
    public abstract boolean contains(int x, int y);

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public int compareTo(Shape o) {
        return 0;
    }
}
