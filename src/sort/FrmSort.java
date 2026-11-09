package sort;

import dialogs.DlgDonut;
import model.Donut;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FrmSort extends JFrame {
    private DefaultListModel<Donut> dlm = new DefaultListModel<>();
    private JList<Donut> lstDonuts;

    public FrmSort() {
        setTitle("Atina Vuckovic IT67/2024 - Sort");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initGUI();
    }

    private void initGUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnAdd = new JButton("Add Donut");
        JButton btnSort = new JButton("Sort (Descending Area)");

        pnlButtons.add(btnAdd);
        pnlButtons.add(btnSort);
        add(pnlButtons, BorderLayout.NORTH);

        lstDonuts = new JList<>(dlm);
        JScrollPane scrollPane = new JScrollPane(lstDonuts);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Donut List"));
        add(scrollPane, BorderLayout.CENTER);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DlgDonut dlg = new DlgDonut(FrmSort.this, "Add Donut to List", true);
                dlg.setVisible(true);

                if (dlg.isConfirmed() && dlg.getDonut() != null) {
                    dlm.addElement(dlg.getDonut());
                }
            }
        });

        btnSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dlm.isEmpty()) {
                    JOptionPane.showMessageDialog(FrmSort.this, "List is empty! Nothing to sort.", "Sort Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                List<Donut> donutsList = new ArrayList<>();
                for (int i = 0; i < dlm.size(); i++) {
                    donutsList.add(dlm.getElementAt(i));
                }

                Collections.sort(donutsList, new Comparator<Donut>() {
                    @Override
                    public int compare(Donut d1, Donut d2) {
                        return Double.compare(d2.area(), d1.area());
                    }
                });

                dlm.clear();
                for (Donut donut : donutsList) {
                    dlm.addElement(donut);
                }

                JOptionPane.showMessageDialog(FrmSort.this, "Donuts successfully sorted by descending area!", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrmSort().setVisible(true);
            }
        });
    }
}
