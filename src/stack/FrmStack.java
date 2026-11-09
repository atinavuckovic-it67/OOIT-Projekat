package stack;

import dialogs.DlgDonut;
import model.Donut;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmStack extends JFrame {
    private DefaultListModel<Donut> dlm = new DefaultListModel<>();
    private JList<Donut> lstDonuts;

    public FrmStack() {
        setTitle("Atina Vuckovic IT67/2024 - Stack");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initGUI();
    }

    private void initGUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnAdd = new JButton("Push (Add Donut)");
        JButton btnRemove = new JButton("Pop (Remove Donut)");

        pnlButtons.add(btnAdd);
        pnlButtons.add(btnRemove);
        add(pnlButtons, BorderLayout.NORTH);

        lstDonuts = new JList<>(dlm);
        JScrollPane scrollPane = new JScrollPane(lstDonuts);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Donut Stack (LIFO - Top item first)"));
        add(scrollPane, BorderLayout.CENTER);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DlgDonut dlg = new DlgDonut(FrmStack.this, "Add Donut to Stack", true);
                dlg.setVisible(true);

                if (dlg.isConfirmed() && dlg.getDonut() != null) {
                    dlm.add(0, dlg.getDonut());
                }
            }
        });

        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dlm.isEmpty()) {
                    JOptionPane.showMessageDialog(FrmStack.this, "Stack is empty!", "Stack Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Donut topDonut = dlm.getElementAt(0);
                DlgDonut dlg = new DlgDonut(FrmStack.this, "Remove Donut from Stack", true);
                dlg.setDonut(topDonut);
                dlg.setReadOnly(true);
                dlg.setVisible(true);

                if (dlg.isConfirmed()) {
                    dlm.remove(0);
                    JOptionPane.showMessageDialog(FrmStack.this, "Donut successfully removed from stack.", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrmStack().setVisible(true);
            }
        });
    }
}
