package model;

import java.awt.Color;
import java.awt.Graphics;

public abstract class SurfaceShape extends Shape {
    private Color innerColor = Color.WHITE;

    public SurfaceShape() {
    }

    public SurfaceShape(Color color, Color innerColor) {
        super(color);
        this.innerColor = innerColor;
    }

    public abstract void fill(Graphics g);
    public abstract double area();

    public Color getInnerColor() {
        return innerColor;
    }

    public void setInnerColor(Color innerColor) {
        this.innerColor = innerColor;
    }
}
