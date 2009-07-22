/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ControlPanel.java
 *
 * Created on 16/07/2009, 09:51:11
 */
package tempo2;

import javax.swing.SwingUtilities;

/**
 *
 * @author kei041
 */
public class ControlPanel extends javax.swing.JPanel {

    final private Tempo2Applet applet;

    /** Creates new form ControlPanel */
    public ControlPanel(Tempo2Applet applet) {
        initComponents();

        this.applet = applet;
    }

    public void setTempo2Pane(String output) {
        this.jTextArea_tempo2.setText(output);
    }

    public void setInTim(String txt) {
        this.jTextArea_intim.setText(txt);
    }

    public void setInPar(String txt) {
        this.jTextArea_inpar.setText(txt);
    }

    public String getInPar() {
        return this.jTextArea_inpar.getText();
    }

    public boolean positiveWrap() {
        return this.jRadioButton1.isSelected();
    }

    public boolean negativeWrap() {
        return this.jRadioButton2.isSelected();
    }

    public boolean clickStart() {
        return this.jRadioButton3.isSelected();
    }

    public boolean clickFinish() {
        return this.jRadioButton4.isSelected();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea_inpar = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea_outpar = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea_intim = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea_tempo2 = new javax.swing.JTextArea();

        setMaximumSize(new java.awt.Dimension(300, 400));
        setMinimumSize(new java.awt.Dimension(300, 400));
        setPreferredSize(new java.awt.Dimension(300, 400));
        setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Tempo2Applet");
        add(jLabel1, java.awt.BorderLayout.NORTH);

        jPanel1.setMaximumSize(new java.awt.Dimension(300, 120));
        jPanel1.setMinimumSize(new java.awt.Dimension(300, 120));
        jPanel1.setPreferredSize(new java.awt.Dimension(300, 120));

        jButton1.setText("Run Fit");
        jButton1.setMinimumSize(new java.awt.Dimension(150, 25));
        jButton1.setPreferredSize(new java.awt.Dimension(150, 25));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        jButton2.setText("Copy out->in");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("+Wrap");
        jPanel1.add(jRadioButton1);

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("-Wrap");
        jPanel1.add(jRadioButton2);

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Start");
        jPanel1.add(jRadioButton3);

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setText("Finish");
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jRadioButton4);

        jButton3.setText("Clear Wraps");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);

        jButton4.setText("Clear Zoom");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);

        jToggleButton1.setText("Post-Fit");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jToggleButton1);

        add(jPanel1, java.awt.BorderLayout.SOUTH);

        jTabbedPane1.setFont(new java.awt.Font("Dialog", 1, 10));

        jTextArea_inpar.setColumns(20);
        jTextArea_inpar.setFont(new java.awt.Font("Courier New", 0, 12));
        jTextArea_inpar.setRows(5);
        jScrollPane1.setViewportView(jTextArea_inpar);

        jTabbedPane1.addTab("input.par", jScrollPane1);

        jTextArea_outpar.setColumns(20);
        jTextArea_outpar.setEditable(false);
        jTextArea_outpar.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        jTextArea_outpar.setRows(5);
        jScrollPane2.setViewportView(jTextArea_outpar);

        jTabbedPane1.addTab("output.par", jScrollPane2);

        jTextArea_intim.setColumns(20);
        jTextArea_intim.setFont(new java.awt.Font("Courier New", 0, 12));
        jTextArea_intim.setRows(5);
        jScrollPane3.setViewportView(jTextArea_intim);

        jTabbedPane1.addTab("input.tim", jScrollPane3);

        jTextArea_tempo2.setColumns(20);
        jTextArea_tempo2.setEditable(false);
        jTextArea_tempo2.setFont(new java.awt.Font("Courier New", 0, 12));
        jTextArea_tempo2.setRows(5);
        jScrollPane4.setViewportView(jTextArea_tempo2);

        jTabbedPane1.addTab("Tempo2", jScrollPane4);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        final String par = this.jTextArea_inpar.getText();
        final String tim = this.jTextArea_intim.getText();
        Thread thread = new Thread() {

            @Override
            public void run() {
                super.run();
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        jButton1.setEnabled(false);
                        jButton1.setText("Fitting...");
                    }
                });
                final String result = applet.reFit(par, tim);
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        jTextArea_outpar.setText(result);
                        jButton1.setEnabled(true);
                        jButton1.setText("Run Fit");
                        jTabbedPane1.setSelectedIndex(1);
                    }
                });

            }
        };
        thread.start();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jTextArea_inpar.setText(jTextArea_outpar.getText());
        jTextArea_outpar.setText("");
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.applet.clearPhaseWraps();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.applet.clearZoom();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton4ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        if(this.jToggleButton1.isSelected()){
            this.jToggleButton1.setText("Pre-Fit");
            this.applet.showPreFit();
        } else {
            this.jToggleButton1.setText("Post-Fit");
            this.applet.showPostFit();
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea_inpar;
    private javax.swing.JTextArea jTextArea_intim;
    private javax.swing.JTextArea jTextArea_outpar;
    private javax.swing.JTextArea jTextArea_tempo2;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables
}