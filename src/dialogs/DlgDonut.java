package dialogs;

import model.Donut;
import model.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DlgDonut extends JDialog {
    private JTextField txtX;
    private JTextField txtY;
    private JTextField txtRadius;
    private JTextField txtInnerRadius;
    private JButton btnEdgeColor;
    private JButton btnInnerColor;

    private Color edgeColor = Color.BLACK;
    private Color innerColor = Color.WHITE;

    private boolean isConfirmed = false;
    private Donut donut;

    public DlgDonut(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        initGUI();
    }

    private void initGUI() {
        setSize(360, 320);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        JPanel pnlForm = new JPanel(new GridLayout(6, 2, 8, 8));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        pnlForm.add(new JLabel("Center X:"));
        txtX = new JTextField();
        pnlForm.add(txtX);

        pnlForm.add(new JLabel("Center Y:"));
        txtY = new JTextField();
        pnlForm.add(txtY);

        pnlForm.add(new JLabel("Outer Radius:"));
        txtRadius = new JTextField();
        pnlForm.add(txtRadius);

        pnlForm.add(new JLabel("Inner Radius:"));
        txtInnerRadius = new JTextField();
        pnlForm.add(txtInnerRadius);

        pnlForm.add(new JLabel("Edge Color:"));
        btnEdgeColor = new JButton("Select Edge Color");
        btnEdgeColor.setBackground(edgeColor);
        btnEdgeColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selected = JColorChooser.showDialog(DlgDonut.this, "Choose Edge Color", edgeColor);
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
                Color selected = JColorChooser.showDialog(DlgDonut.this, "Choose Inner Color", innerColor);
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
        if (!txtX.isEnabled()) {
            return true;
        }

        String strX = txtX.getText().trim();
        String strY = txtY.getText().trim();
        String strR = txtRadius.getText().trim();
        String strIR = txtInnerRadius.getText().trim();

        if (strX.isEmpty() || strY.isEmpty() || strR.isEmpty() || strIR.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int x, y, r, ir;
        try {
            x = Integer.parseInt(strX);
            y = Integer.parseInt(strY);
            r = Integer.parseInt(strR);
            ir = Integer.parseInt(strIR);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Coordinates and radii must be valid integers!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (x < 0 || y < 0) {
            JOptionPane.showMessageDialog(this, "Coordinates X and Y must be non-negative!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (r <= 0 || ir <= 0) {
            JOptionPane.showMessageDialog(this, "Radii must be greater than zero!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (ir >= r) {
            JOptionPane.showMessageDialog(this, "Inner radius must be smaller than outer radius!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (donut == null) {
            donut = new Donut(new Point(x, y), r, ir, edgeColor, innerColor);
        } else {
            donut.setCenter(new Point(x, y));
            donut.setRadius(r);
            donut.setInnerRadius(ir);
            donut.setColor(edgeColor);
            donut.setInnerColor(innerColor);
        }

        return true;
    }

    public void setDonut(Donut donut) {
        this.donut = donut;
        if (donut != null) {
            txtX.setText(String.valueOf(donut.getCenter().getX()));
            txtY.setText(String.valueOf(donut.getCenter().getY()));
            txtRadius.setText(String.valueOf(donut.getRadius()));
            txtInnerRadius.setText(String.valueOf(donut.getInnerRadius()));
            edgeColor = donut.getColor();
            innerColor = donut.getInnerColor();
            btnEdgeColor.setBackground(edgeColor);
            btnInnerColor.setBackground(innerColor);
        }
    }

    public void setReadOnly(boolean readOnly) {
        txtX.setEnabled(!readOnly);
        txtY.setEnabled(!readOnly);
        txtRadius.setEnabled(!readOnly);
        txtInnerRadius.setEnabled(!readOnly);
        btnEdgeColor.setEnabled(!readOnly);
        btnInnerColor.setEnabled(!readOnly);
    }

    public Donut getDonut() {
        return donut;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }
}
