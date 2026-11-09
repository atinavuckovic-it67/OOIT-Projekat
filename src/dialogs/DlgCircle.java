package dialogs;

import model.Circle;
import model.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DlgCircle extends JDialog {
    private JTextField txtX;
    private JTextField txtY;
    private JTextField txtRadius;
    private JButton btnEdgeColor;
    private JButton btnInnerColor;

    private Color edgeColor = Color.BLACK;
    private Color innerColor = Color.WHITE;

    private boolean isConfirmed = false;
    private Circle circle;

    public DlgCircle(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        initGUI();
    }

    private void initGUI() {
        setSize(360, 280);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        JPanel pnlForm = new JPanel(new GridLayout(5, 2, 8, 8));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        pnlForm.add(new JLabel("Center X:"));
        txtX = new JTextField();
        pnlForm.add(txtX);

        pnlForm.add(new JLabel("Center Y:"));
        txtY = new JTextField();
        pnlForm.add(txtY);

        pnlForm.add(new JLabel("Radius:"));
        txtRadius = new JTextField();
        pnlForm.add(txtRadius);

        pnlForm.add(new JLabel("Edge Color:"));
        btnEdgeColor = new JButton("Select Edge Color");
        btnEdgeColor.setBackground(edgeColor);
        btnEdgeColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selected = JColorChooser.showDialog(DlgCircle.this, "Choose Edge Color", edgeColor);
                if (selected != null) {
                    edgeColor = selected;
                    btnEdgeColor.setBackground(edgeColor);
                }
            }
        });
        pnlForm.add(btnEdgeColor);

        pnlForm.add(new JLabel("Inner Color:"));
        btnInnerColor = new JButton("Select Inner Color");
        btnInnerColor.setBackground(innerColor);
        btnInnerColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selected = JColorChooser.showDialog(DlgCircle.this, "Choose Inner Color", innerColor);
                if (selected != null) {
                    innerColor = selected;
                    btnInnerColor.setBackground(innerColor);
                }
            }
        });
        pnlForm.add(btnInnerColor);

        add(pnlForm, BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateAndSave()) {
                    isConfirmed = true;
                    dispose();
                }
            }
        });

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isConfirmed = false;
                dispose();
            }
        });

        pnlButtons.add(btnOk);
        pnlButtons.add(btnCancel);
        add(pnlButtons, BorderLayout.SOUTH);
    }

    private boolean validateAndSave() {
        String strX = txtX.getText().trim();
        String strY = txtY.getText().trim();
        String strR = txtRadius.getText().trim();

        if (strX.isEmpty() || strY.isEmpty() || strR.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int x, y, r;
        try {
            x = Integer.parseInt(strX);
            y = Integer.parseInt(strY);
            r = Integer.parseInt(strR);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Coordinates and radius must be valid integers!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (x < 0 || y < 0) {
            JOptionPane.showMessageDialog(this, "Coordinates X and Y must be non-negative!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (r <= 0) {
            JOptionPane.showMessageDialog(this, "Radius must be greater than zero!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (circle == null) {
            circle = new Circle(new Point(x, y), r, edgeColor, innerColor);
        } else {
            circle.getCenter().setX(x);
            circle.getCenter().setY(y);
            circle.setRadius(r);
            circle.setColor(edgeColor);
            circle.setInnerColor(innerColor);
        }

        return true;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
        if (circle != null) {
            txtX.setText(String.valueOf(circle.getCenter().getX()));
            txtY.setText(String.valueOf(circle.getCenter().getY()));
            txtRadius.setText(String.valueOf(circle.getRadius()));
            edgeColor = circle.getColor();
            innerColor = circle.getInnerColor();
            btnEdgeColor.setBackground(edgeColor);
            btnInnerColor.setBackground(innerColor);
        }
    }

    public Circle getCircle() {
        return circle;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }
}
