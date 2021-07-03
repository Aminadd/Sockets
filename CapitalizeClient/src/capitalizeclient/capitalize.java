/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capitalizeclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Amina
 */
class CapitalizeClient extends Thread {

    private String lowerCase;
    private JLabel label;

    CapitalizeClient(JLabel label) {
        this.label = label;
    }

    public synchronized void setLowerCase(String l) {
        lowerCase = l;
        notifyAll();
    }

    public void run() {
        try {
            Socket socket = new Socket("localhost", 8888);
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                try {
                    if (lowerCase != null) {
                        writer.println(lowerCase);
                        String upperCase = scanner.nextLine();
                        label.setText(upperCase);
                        setLowerCase(null);
                    } else {
                        synchronized (this) {
                            wait();
                        }
                    }
                } catch (InterruptedException e) {}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class capitalize extends JFrame {

    JLabel label = new JLabel("Capitalized");
    JTextField textField = new JTextField(32);
    JButton button = new JButton("Capitalize");
    /**
     * Creates new form capitalize
     */
    public capitalize() {
        super("Capitalize Client");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel pane = new JPanel();
        pane.add(label);
        pane.add(textField);
        pane.add(button);
        add(pane);

        CapitalizeClient capitalizeThread = new CapitalizeClient(label);
        capitalizeThread.start();

        button.addActionListener(action -> {
            capitalizeThread.setLowerCase(textField.getText());
            textField.setText("");
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(capitalize.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(capitalize.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(capitalize.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(capitalize.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new capitalize().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}