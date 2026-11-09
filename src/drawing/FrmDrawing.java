package drawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmDrawing extends JFrame {
    private PnlDrawing pnlDrawing;

    private JToggleButton btnPoint;
    private JToggleButton btnLine;
    private JToggleButton btnRectangle;
    private JToggleButton btnCircle;
    private JToggleButton btnDonut;
    private JToggleButton btnSelect;

    private JButton btnModify;
    private JButton btnDelete;

    private JButton btnEdgeColor;
    private JButton btnInnerColor;

    public FrmDrawing() {
        setTitle("Atina Vuckovic IT67/2024 - Drawing");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initGUI();
    }

    private void initGUI() {
        setLayout(new BorderLayout(5, 5));

        pnlDrawing = new PnlDrawing();
        add(pnlDrawing, BorderLayout.CENTER);

        JToolBar toolBarTop = new JToolBar();
        toolBarTop.setFloatable(false);
        toolBarTop.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 5));

        ButtonGroup groupTools = new ButtonGroup();

        btnPoint = new JToggleButton("Point", true);
        btnLine = new JToggleButton("Line");
        btnRectangle = new JToggleButton("Rectangle");
        btnCircle = new JToggleButton("Circle");
        btnDonut = new JToggleButton("Donut");
        btnSelect = new JToggleButton("Select");

        groupTools.add(btnPoint);
        groupTools.add(btnLine);
        groupTools.add(btnRectangle);
        groupTools.add(btnCircle);
        groupTools.add(btnDonut);
        groupTools.add(btnSelect);

        toolBarTop.add(btnPoint);
        toolBarTop.add(btnLine);
        toolBarTop.add(btnRectangle);
        toolBarTop.add(btnCircle);
        toolBarTop.add(btnDonut);
        toolBarTop.addSeparator();
        toolBarTop.add(btnSelect);

        btnModify = new JButton("Modify");
        btnDelete = new JButton("Delete");

        toolBarTop.addSeparator();
        toolBarTop.add(btnModify);
        toolBarTop.add(btnDelete);

        add(toolBarTop, BorderLayout.NORTH);

        JToolBar toolBarBottom = new JToolBar();
        toolBarBottom.setFloatable(false);
        toolBarBottom.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));

        btnEdgeColor = new JButton("Edge Color");
        btnEdgeColor.setBackground(pnlDrawing.getActiveEdgeColor());
        btnEdgeColor.setOpaque(true);

        btnInnerColor = new JButton("Inner Color");
        btnInnerColor.setBackground(pnlDrawing.getActiveInnerColor());
        btnInnerColor.setOpaque(true);

        toolBarBottom.add(new JLabel("Active Colors: "));
        toolBarBottom.add(btnEdgeColor);
        toolBarBottom.add(btnInnerColor);

        add(toolBarBottom, BorderLayout.SOUTH);

        ActionListener modeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btnPoint.isSelected()) {
                    pnlDrawing.setActiveMode(PnlDrawing.Mode.POINT);
                } else if (btnLine.isSelected()) {
                    pnlDrawing.setActiveMode(PnlDrawing.Mode.LINE);
                } else if (btnRectangle.isSelected()) {
                    pnlDrawing.setActiveMode(PnlDrawing.Mode.RECTANGLE);
                } else if (btnCircle.isSelected()) {
                    pnlDrawing.setActiveMode(PnlDrawing.Mode.CIRCLE);
                } else if (btnDonut.isSelected()) {
                    pnlDrawing.setActiveMode(PnlDrawing.Mode.DONUT);
                } else if (btnSelect.isSelected()) {
                    pnlDrawing.setActiveMode(PnlDrawing.Mode.SELECT);
                }
            }
        };

        btnPoint.addActionListener(modeListener);
        btnLine.addActionListener(modeListener);
        btnRectangle.addActionListener(modeListener);
        btnCircle.addActionListener(modeListener);
        btnDonut.addActionListener(modeListener);
        btnSelect.addActionListener(modeListener);

        btnModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnlDrawing.modifySelectedShape();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnlDrawing.deleteSelectedShape();
            }
        });

        btnEdgeColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selected = JColorChooser.showDialog(FrmDrawing.this, "Choose Edge Color", pnlDrawing.getActiveEdgeColor());
                if (selected != null) {
                    pnlDrawing.setActiveEdgeColor(selected);
                    btnEdgeColor.setBackground(selected);
                }
            }
        });

        btnInnerColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selected = JColorChooser.showDialog(FrmDrawing.this, "Choose Inner Color", pnlDrawing.getActiveInnerColor());
                if (selected != null) {
                    pnlDrawing.setActiveInnerColor(selected);
                    btnInnerColor.setBackground(selected);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrmDrawing().setVisible(true);
            }
        });
    }
}
