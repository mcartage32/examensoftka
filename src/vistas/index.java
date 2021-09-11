package vistas;

import controlador.Controlador;


public class index extends javax.swing.JFrame {
   
      private static ingresar_preguntas objin = new ingresar_preguntas();

    public index() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Concurso Preguntas y Respuestas");
    }

    
     public static void main(String args[]) {
    
        new index().setVisible(true);
        Controlador ctrl = new Controlador(objin);
       
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAgregarPreguntas = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(0, 0));

        btnAgregarPreguntas.setText("Ingresar Preguntas");
        btnAgregarPreguntas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarPreguntasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(299, 299, 299)
                .addComponent(btnAgregarPreguntas)
                .addContainerGap(264, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(btnAgregarPreguntas)
                .addContainerGap(350, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarPreguntasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarPreguntasActionPerformed
        objin.setVisible(true);
        objin.setLocationRelativeTo(null);
        objin.setTitle("Ingresar Preguntas");
        dispose();
    }//GEN-LAST:event_btnAgregarPreguntasActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAgregarPreguntas;
    // End of variables declaration//GEN-END:variables
}
