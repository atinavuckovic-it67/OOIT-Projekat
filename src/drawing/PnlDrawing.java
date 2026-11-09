package drawing;

import dialogs.*;
import model.Shape;
import model.Point;
import model.Line;
import model.Rectangle;
import model.Circle;
import model.Donut;

import javax.swing.*;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PnlDrawing extends JPanel {
    public enum Mode {
        POINT, LINE, RECTANGLE, CIRCLE, DONUT, SELECT, MODIFY, DELETE
    }

    private Mode activeMode = Mode.POINT;
    private List<Shape> shapes = new ArrayList<>();
    private Shape selectedShape = null;

    private Point lineStartPoint = null;

    private Color activeEdgeColor = Color.BLACK;
    private Color activeInnerColor = Color.WHITE;

    public PnlDrawing() {
        setBackground(Color.WHITE);
        initMouseListener();
    }

    private void initMouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });
    }

    private void handleMouseClick(int x, int y) {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);

        switch (activeMode) {
            case POINT:
                Point point = new Point(x, y, activeEdgeColor);
                shapes.add(point);
                lineStartPoint = null;
                repaint();
                break;

            case LINE:
                if (lineStartPoint == null) {
                    lineStartPoint = new Point(x, y);
                } else {
                    Line line = new Line(lineStartPoint, new Point(x, y), activeEdgeColor);
                    shapes.add(line);
                    lineStartPoint = null;
                    repaint();
                }
                break;

            case RECTANGLE:
                lineStartPoint = null;
                DlgRectangle dlgRect = new DlgRectangle(parentFrame, "Add Rectangle", true);
                Rectangle rect = new Rectangle(new Point(x, y), 0, 0, activeEdgeColor, activeInnerColor);
                dlgRect.setRectangle(rect);
                dlgRect.setVisible(true);
                if (dlgRect.isConfirmed() && dlgRect.getRectangle() != null) {
                    shapes.add(dlgRect.getRectangle());
                    repaint();
                }
                break;

            case CIRCLE:
                lineStartPoint = null;
                DlgCircle dlgCircle = new DlgCircle(parentFrame, "Add Circle", true);
                Circle circle = new Circle(new Point(x, y), 0, activeEdgeColor, activeInnerColor);
                dlgCircle.setCircle(circle);
                dlgCircle.setVisible(true);
                if (dlgCircle.isConfirmed() && dlgCircle.getCircle() != null) {
                    shapes.add(dlgCircle.getCircle());
                    repaint();
                }
                break;

            case DONUT:
                lineStartPoint = null;
                DlgDonut dlgDonut = new DlgDonut(parentFrame, "Add Donut", true);
                Donut donut = new Donut(new Point(x, y), 0, 0, activeEdgeColor, activeInnerColor);
                dlgDonut.setDonut(donut);
                dlgDonut.setVisible(true);
                if (dlgDonut.isConfirmed() && dlgDonut.getDonut() != null) {
                    shapes.add(dlgDonut.getDonut());
                    repaint();
                }
                break;

            case SELECT:
                lineStartPoint = null;
                selectShapeAt(x, y);
                repaint();
                break;

            default:
                break;
        }
    }

    private void selectShapeAt(int x, int y) {
        Shape newlySelected = null;

        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape s = shapes.get(i);
            if (s.contains(x, y)) {
                newlySelected = s;
                break;
            }
        }

        if (newlySelected != null) {
            if (newlySelected.isSelected()) {
                newlySelected.setSelected(false);
                selectedShape = null;
            } else {
                if (selectedShape != null) {
                    selectedShape.setSelected(false);
                }
                newlySelected.setSelected(true);
                selectedShape = newlySelected;
            }
        } else {
            if (selectedShape != null) {
                selectedShape.setSelected(false);
                selectedShape = null;
            }
        }
    }

    public void modifySelectedShape() {
        if (selectedShape == null) {
            JOptionPane.showMessageDialog(this, "Please select a shape to modify!", "Modification Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);

        if (selectedShape instanceof Donut) {
            DlgDonut dlg = new DlgDonut(parentFrame, "Modify Donut", true);
            dlg.setDonut((Donut) selectedShape);
            dlg.setVisible(true);
            if (!dlg.isConfirmed()) {
                selectedShape.setSelected(false);
                selectedShape = null;
            }
        } else if (selectedShape instanceof Circle) {
            DlgCircle dlg = new DlgCircle(parentFrame, "Modify Circle", true);
            dlg.setCircle((Circle) selectedShape);
            dlg.setVisible(true);
            if (!dlg.isConfirmed()) {
                selectedShape.setSelected(false);
                selectedShape = null;
            }
        } else if (selectedShape instanceof Rectangle) {
            DlgRectangle dlg = new DlgRectangle(parentFrame, "Modify Rectangle", true);
            dlg.setRectangle((Rectangle) selectedShape);
            dlg.setVisible(true);
            if (!dlg.isConfirmed()) {
                selectedShape.setSelected(false);
                selectedShape = null;
            }
        } else if (selectedShape instanceof Line) {
            DlgLine dlg = new DlgLine(parentFrame, "Modify Line", true);
            dlg.setLine((Line) selectedShape);
            dlg.setVisible(true);
            if (!dlg.isConfirmed()) {
                selectedShape.setSelected(false);
                selectedShape = null;
            }
        } else if (selectedShape instanceof Point) {
            DlgPoint dlg = new DlgPoint(parentFrame, "Modify Point", true);
            dlg.setPoint((Point) selectedShape);
            dlg.setVisible(true);
            if (!dlg.isConfirmed()) {
                selectedShape.setSelected(false);
                selectedShape = null;
            }
        }

        repaint();
    }

    public void deleteSelectedShape() {
        if (selectedShape == null) {
            JOptionPane.showMessageDialog(this, "Please select a shape to delete!", "Deletion Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete the selected shape?", 
                "Confirm Deletion", 
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            shapes.remove(selectedShape);
            selectedShape = null;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Shape shape : shapes) {
            shape.draw(g);
        }
    }

    public Mode getActiveMode() {
        return activeMode;
    }

    public void setActiveMode(Mode activeMode) {
        this.activeMode = activeMode;
        lineStartPoint = null;
    }

    public Shape getSelectedShape() {
        return selectedShape;
    }

    public Color getActiveEdgeColor() {
        return activeEdgeColor;
    }

    public void setActiveEdgeColor(Color activeEdgeColor) {
        this.activeEdgeColor = activeEdgeColor;
    }

    public Color getActiveInnerColor() {
        return activeInnerColor;
    }

    public void setActiveInnerColor(Color activeInnerColor) {
        this.activeInnerColor = activeInnerColor;
    }

    public List<Shape> getShapes() {
        return shapes;
    }
}
