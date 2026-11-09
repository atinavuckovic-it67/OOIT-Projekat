package dialogs;

import model.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DlgPoint extends JDialog {
    private JTextField txtX;
    private JTextField txtY;
    private JButton btnColor;
    private Color color = Color.BLACK;

    private boolean isConfirmed = false;
    private Point point;

    public DlgPoint(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        initGUI();
    }

    private void initGUI() {
        setSize(320, 220);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        JPanel pnlForm = new JPanel(new GridLayout(3, 2, 8, 8));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        pnlForm.add(new JLabel("X Coordinate:"));
        txtX = new JTextField();
        pnlForm.add(txtX);

        pnlForm.add(new JLabel("Y Coordinate:"));
        txtY = new JTextField();
        pnlForm.add(txtY);

        pnlForm.add(new JLabel("Point Color:"));
        btnColor = new JButton("Select Color");
        btnColor.setBackground(color);
        btnColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selected = JColorChooser.showDialog(DlgPoint.this, "Choose Point Color", color);
                if (selected != null) {
                    color = selected;
                    btnColor.setBackground(color);
                }
            }
        });
        pnlForm.add(btnColor);

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

        if (strX.isEmpty() || strY.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int x, y;
        try {
            x = Integer.parseInt(strX);
            y = Integer.parseInt(strY);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Coordinates must be valid integers!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (x < 0 || y < 0) {
            JOptionPane.showMessageDialog(this, "Coordinates must be non-negative!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (point == null) {
            point = new Point(x, y, color);
        } else {
            point.setX(x);
            point.setY(y);
            point.setColor(color);
        }

        return true;
    }

    public void setPoint(Point point) {
        this.point = point;
        if (point != null) {
            txtX.setText(String.valueOf(point.getX()));
            txtY.setText(String.valueOf(point.getY()));
            color = point.getColor();
            btnColor.setBackground(color);
        }
    }

    public Point getPoint() {
        return point;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }
}
