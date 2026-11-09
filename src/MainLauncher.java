import drawing.FrmDrawing;
import sort.FrmSort;
import stack.FrmStack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainLauncher extends JFrame {

    public MainLauncher() {
        setTitle("OOIT 2025/2026 - Atina Vuckovic IT67/2024");
        setSize(420, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initGUI();
    }

    private void initGUI() {
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitle = new JLabel("Object-Oriented IT Exam 2025/2026", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
        add(lblTitle, BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel(new GridLayout(3, 1, 10, 10));
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));

        JButton btnStack = new JButton("Launch Stack Application");
        JButton btnSort = new JButton("Launch Sort Application");
        JButton btnDrawing = new JButton("Launch Drawing Application");

        btnStack.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnSort.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnDrawing.setFont(new Font("SansSerif", Font.PLAIN, 14));

        pnlCenter.add(btnStack);
        pnlCenter.add(btnSort);
        pnlCenter.add(btnDrawing);

        add(pnlCenter, BorderLayout.CENTER);

        btnStack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FrmStack().setVisible(true);
            }
        });

        btnSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FrmSort().setVisible(true);
            }
        });

        btnDrawing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FrmDrawing().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainLauncher().setVisible(true);
            }
        });
    }
}
