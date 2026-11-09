package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Donut extends Circle {
    private int innerRadius;

    public Donut() {
    }

    public Donut(Point center, int radius, int innerRadius) {
        super(center, radius);
        this.innerRadius = innerRadius;
    }

    public Donut(Point center, int radius, int innerRadius, Color color, Color innerColor) {
        super(center, radius, color, innerColor);
        this.innerRadius = innerRadius;
    }

    @Override
    public void fill(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        double x = getCenter().getX();
        double y = getCenter().getY();
        double r = getRadius();
        double ir = innerRadius;

        Ellipse2D outer = new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r);
        Ellipse2D inner = new Ellipse2D.Double(x - ir, y - ir, 2 * ir, 2 * ir);

        Area donutArea = new Area(outer);
        donutArea.subtract(new Area(inner));

        if (getInnerColor() != null) {
            g2d.setColor(getInnerColor()); 
            g2d.fill(donutArea);
        }
    }

    @Override
    public void draw(Graphics g) {
        fill(g);
        g.setColor(getColor());
        int x = getCenter().getX();
        int y = getCenter().getY();
        int r = getRadius();
        int ir = innerRadius;

        g.drawOval(x - r, y - r, 2 * r, 2 * r);
        g.drawOval(x - ir, y - ir, 2 * ir, 2 * ir);

        if (isSelected()) {
            g.setColor(Color.BLUE);
            g.drawRect(x - 3, y - 3, 6, 6);
            g.drawRect(x - r - 3, y - 3, 6, 6);
            g.drawRect(x + r - 3, y - 3, 6, 6);
            g.drawRect(x - 3, y - r - 3, 6, 6);
            g.drawRect(x - 3, y + r - 3, 6, 6);
            g.drawRect(x - ir - 3, y - 3, 6, 6);
            g.drawRect(x + ir - 3, y - 3, 6, 6);
            g.drawRect(x - 3, y - ir - 3, 6, 6);
            g.drawRect(x - 3, y + ir - 3, 6, 6);
        }
    }

    @Override
    public boolean contains(int x, int y) {
        double d = getCenter().distance(x, y);
        return d <= getRadius() && d >= innerRadius;
    }

    @Override
    public double area() {
        return Math.PI * (getRadius() * getRadius() - innerRadius * innerRadius);
    }

    @Override
    public int compareTo(Shape o) {
        if (o instanceof Circle) {
            return Double.compare(this.area(), ((Circle) o).area());
        }
        return 0;
    }

    public int getInnerRadius() {
        return innerRadius;
    }

    public void setInnerRadius(int innerRadius) {
        this.innerRadius = innerRadius;
    }

    @Override
    public String toString() {
        return "Donut: Center=(" + getCenter().getX() + ", " + getCenter().getY() + 
               "), Outer Radius=" + getRadius() + ", Inner Radius=" + innerRadius + 
               ", Area=" + String.format("%.2f", area());
    }
}
