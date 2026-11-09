package dialogs;

import model.Line;
import model.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DlgLine extends JDialog {
    private JTextField txtStartX;
    private JTextField txtStartY;
    private JTextField txtEndX;
    private JTextField txtEndY;
    private JButton btnColor;
    private Color color = Color.BLACK; 

    private boolean isConfirmed = false;
    private Line line;

    public DlgLine(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        initGUI();
    }

    private void initGUI() {
        setSize(360, 280);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));

        JPanel pnlForm = new JPanel(new GridLayout(5, 2, 8, 8));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        pnlForm.add(new JLabel("Start X:"));
        txtStartX = new JTextField();
        pnlForm.add(txtStartX);

        pnlForm.add(new JLabel("Start Y:"));
        txtStartY = new JTextField();
        pnlForm.add(txtStartY);

        pnlForm.add(new JLabel("End X:"));
        txtEndX = new JTextField();
        pnlForm.add(txtEndX);

        pnlForm.add(new JLabel("End Y:"));
        txtEndY = new JTextField();
        pnlForm.add(txtEndY);

        pnlForm.add(new JLabel("Line Color:"));
        btnColor = new JButton("Select Color");
        btnColor.setBackground(color);
        btnColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selected = JColorChooser.showDialog(DlgLine.this, "Choose Line Color", color);
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
        String strStartX = txtStartX.getText().trim();
        String strStartY = txtStartY.getText().trim();
        String strEndX = txtEndX.getText().trim();
        String strEndY = txtEndY.getText().trim();

        if (strStartX.isEmpty() || strStartY.isEmpty() || strEndX.isEmpty() || strEndY.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int sx, sy, ex, ey;
        try {
            sx = Integer.parseInt(strStartX);
            sy = Integer.parseInt(strStartY);
            ex = Integer.parseInt(strEndX);
            ey = Integer.parseInt(strEndY);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Coordinates must be valid integers!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (sx < 0 || sy < 0 || ex < 0 || ey < 0) {
            JOptionPane.showMessageDialog(this, "Coordinates must be non-negative!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (line == null) {
            line = new Line(new Point(sx, sy), new Point(ex, ey), color);
        } else {
            line.getStartPoint().setX(sx);
            line.getStartPoint().setY(sy);
            line.getEndPoint().setX(ex);
            line.getEndPoint().setY(ey);
            line.setColor(color);
        }

        return true;
    }

    public void setLine(Line line) {
        this.line = line;
        if (line != null) {
            txtStartX.setText(String.valueOf(line.getStartPoint().getX()));
            txtStartY.setText(String.valueOf(line.getStartPoint().getY()));
            txtEndX.setText(String.valueOf(line.getEndPoint().getX()));
            txtEndY.setText(String.valueOf(line.getEndPoint().getY()));
            color = line.getColor();
            btnColor.setBackground(color);
        }
    }

    public Line getLine() {
        return line;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }
}
