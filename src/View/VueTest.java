package View;

import javax.swing.*;
import java.awt.EventQueue;

public class VueTest {

    private JFrame frame;

    private JCheckBox checkBoxLampe1;
    private JCheckBox checkBoxLampe2;
    private JCheckBox checkBoxLampe3;
    private JLabel labelCommutateur;
    private JButton bouttonInterrupteur;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VueTest window = new VueTest();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Create the application.
     */
    public VueTest() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        checkBoxLampe1 = new JCheckBox("Lampe1");
        checkBoxLampe1.setBounds(26, 39, 93, 21);
        frame.getContentPane().add(checkBoxLampe1);

        checkBoxLampe2 = new JCheckBox("Lampe2");
        checkBoxLampe2.setBounds(26, 106, 93, 21);
        frame.getContentPane().add(checkBoxLampe2);

        checkBoxLampe3 = new JCheckBox("Lampe3");
        checkBoxLampe3.setBounds(26, 164, 93, 21);
        frame.getContentPane().add(checkBoxLampe3);

        labelCommutateur = new JLabel("Commutateur");
        labelCommutateur.setBounds(190, 97, 159, 13);
        frame.getContentPane().add(labelCommutateur);

        bouttonInterrupteur = new JButton("Interrupteur");
        bouttonInterrupteur.setBounds(260, 177, 128, 21);
        frame.getContentPane().add(bouttonInterrupteur);

        new Tempo(checkBoxLampe1);
    }


}
