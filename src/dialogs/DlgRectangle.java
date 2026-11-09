package dialogs;

import model.Point;
import model.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DlgRectangle extends JDialog {
    private JTextField txtUpperLeftX;
    private JTextField txtUpperLeftY;
    private JTextField txtWidth;
    private JTextField txtHeight;
    private JButton btnEdgeColor;
    private JButton btnInnerColor;

    private Color edgeColor = Color.BLACK;
    private Color innerColor = Color.WHITE;

    private boolean isConfirmed = false;
    private Rectangle rectangle;

    public DlgRectangle(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        initGUI();
    }

    private void initGUI() {
        setSize(360, 320);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        JPanel pnlForm = new JPanel(new GridLayout(6, 2, 8, 8));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        pnlForm.add(new JLabel("Upper Left X:"));
        txtUpperLeftX = new JTextField();
        pnlForm.add(txtUpperLeftX);

        pnlForm.add(new JLabel("Upper Left Y:"));
        txtUpperLeftY = new JTextField();
        pnlForm.add(txtUpperLeftY);

        pnlForm.add(new JLabel("Width:"));
        txtWidth = new JTextField();
        pnlForm.add(txtWidth);

        pnlForm.add(new JLabel("Height:"));
        txtHeight = new JTextField();
        pnlForm.add(txtHeight);

        pnlForm.add(new JLabel("Edge Color:"));
        btnEdgeColor = new JButton("Select Edge Color");
        btnEdgeColor.setBackground(edgeColor);
        btnEdgeColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selected = JColorChooser.showDialog(DlgRectangle.this, "Choose Edge Color", edgeColor);
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
                Color selected = JColorChooser.showDialog(DlgRectangle.this, "Choose Inner Color", innerColor);
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
        String strX = txtUpperLeftX.getText().trim();
        String strY = txtUpperLeftY.getText().trim();
        String strW = txtWidth.getText().trim();
        String strH = txtHeight.getText().trim();

        if (strX.isEmpty() || strY.isEmpty() || strW.isEmpty() || strH.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int x, y, w, h;
        try {
            x = Integer.parseInt(strX);
            y = Integer.parseInt(strY);
            w = Integer.parseInt(strW);
            h = Integer.parseInt(strH);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Coordinates, width, and height must be valid integers!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (x < 0 || y < 0) {
            JOptionPane.showMessageDialog(this, "Coordinates X and Y must be non-negative!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (w <= 0 || h <= 0) {
            JOptionPane.showMessageDialog(this, "Width and height must be greater than zero!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (rectangle == null) {
            rectangle = new Rectangle(new Point(x, y), w, h, edgeColor, innerColor);
        } else {
            rectangle.getUpperLeft().setX(x);
            rectangle.getUpperLeft().setY(y);
            rectangle.setWidth(w);
            rectangle.setHeight(h);
            rectangle.setColor(edgeColor);
            rectangle.setInnerColor(innerColor);
        }

        return true;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
        if (rectangle != null) {
            txtUpperLeftX.setText(String.valueOf(rectangle.getUpperLeft().getX()));
            txtUpperLeftY.setText(String.valueOf(rectangle.getUpperLeft().getY()));
            txtWidth.setText(String.valueOf(rectangle.getWidth()));
            txtHeight.setText(String.valueOf(rectangle.getHeight()));
            edgeColor = rectangle.getColor();
            innerColor = rectangle.getInnerColor();
            btnEdgeColor.setBackground(edgeColor);
            btnInnerColor.setBackground(innerColor);
        }
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }
}
