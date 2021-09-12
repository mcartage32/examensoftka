package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Auxiliar;
import modelo.Modelo;
import modelo.Opcion;
import modelo.Pregunta;
import vistas.ingresar_preguntas;
import vistas.juego;
import vistas.index;

public class Controlador implements ActionListener {
    
    private ingresar_preguntas objin;
    private juego objjue;
    private index objind;
    private Modelo objmod = new Modelo();
    private String opccorrecta;
    private int rondanum = 1;
    private String nombreJugador = "";
    private int premio=10000;

    public Controlador(ingresar_preguntas objin, index objind, juego objjue) {
        this.objin = objin;
        this.objin.btnIngresar.addActionListener(this);
        this.objin.bntIngPregVolver.addActionListener(this);

        this.objind = objind;
        this.objind.btnJugar.addActionListener(this);

        this.objjue = objjue;
        this.objjue.btnVerificar.addActionListener(this);

    }

    public void desplegar(int i)
    {
        if ("".equals(nombreJugador)) {
            String nomJugador = JOptionPane.showInputDialog(null, "Ingrese el nombre del jugador:", "Nombre del Jugador", JOptionPane.INFORMATION_MESSAGE);
            nombreJugador = nomJugador;
            objjue.lblNomJugador.setText(nomJugador);
        }

        if (rondanum > 5) {
            JOptionPane.showMessageDialog(null, "HAS GANADO!! :D, te esperamos para proxima una proxima partida!!","FELICITACIONES HAS GANADO EL JUEGO!!",JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }

        int ronda = i;
        objjue.lblPremio.setText(String.valueOf(premio)+" pesos colombianos");
        objjue.lblRonda.setText(String.valueOf(ronda));
        String opccorre = "";
        String opcselec = "";
        ArrayList<Auxiliar> lista = objmod.preguntaConOpciones(objmod.idPreguntaAzar(ronda));

        for (Auxiliar aux : lista) {
            objjue.lblPregunta.setText(aux.getDescripcionPregunta());

            if (aux.getOpcion().equals("A")) {
                objjue.opcionA.setText(aux.getOpcion() + ") " + aux.getDescripcionOpcion());
            }
            if (aux.getOpcion().equals("B")) {
                objjue.opcionB.setText(aux.getOpcion() + ") " + aux.getDescripcionOpcion());
            }
            if (aux.getOpcion().equals("C")) {
                objjue.opcionC.setText(aux.getOpcion() + ") " + aux.getDescripcionOpcion());
            }
            if (aux.getOpcion().equals("D")) {
                objjue.opcionD.setText(aux.getOpcion() + ") " + aux.getDescripcionOpcion());
            }

            opccorrecta = aux.getOpcionCorrecta();

        }
        
        

    }

    public void verificarRespuesta() 
    {
        String opcselec = "";

        if (objjue.opcionA.isSelected()) 
            opcselec = "A";
        else if (objjue.opcionB.isSelected()) 
            opcselec = "B";
         else if (objjue.opcionC.isSelected()) 
            opcselec = "C";
         else if (objjue.opcionD.isSelected()) 
            opcselec = "D";
        

        if (opcselec.equals(opccorrecta)) {
            JOptionPane.showMessageDialog(null, "Respuesta correcta, avanzas a la siguiente ronda", "Felicitaciones",JOptionPane.INFORMATION_MESSAGE);
            rondanum++;
            premio=premio+15500;
            desplegar(rondanum);
        } else {
            JOptionPane.showMessageDialog(null, "Has perdido, no puedes seguir jugando. Pierdes el premio acumulado de "+premio+" pesos colombianos", "Respuesta Incorrecta",JOptionPane.INFORMATION_MESSAGE);
            objjue.dispose();
            nombreJugador="";
            rondanum=1;
            premio=10000;
            objind.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {

        //PROCESO PARA AGREGAR PREGUNTA A LA BD
        if (e.getSource() == objin.btnIngresar) 
        {
            char opccorr;
            if (objin.A.isSelected()) 
                opccorr = 'A';
             else if (objin.B.isSelected()) 
                opccorr = 'B';
             else if (objin.C.isSelected()) 
                opccorr = 'C';
             else 
                opccorr = 'D';
            

            Pregunta pre = new Pregunta();
            pre.setDescripcion(objin.txtPreguntaDescripcion.getText());
            pre.setOpcionCorrecta(opccorr);
            pre.setCategoria(Integer.parseInt(objin.comboxCategoria.getSelectedItem().toString()));
            objmod.ingresarPregunta(pre);
            int idpre = objmod.idDePregunta(objin.txtPreguntaDescripcion.getText());

            Opcion opc = new Opcion();
            opc.setOpcA(objin.txtOpcionA.getText());
            opc.setOpcB(objin.txtOpcionB.getText());
            opc.setOpcC(objin.txtOpcionC.getText());
            opc.setOpcD(objin.txtOpcionD.getText());

            if (objmod.ingresarOpciones(opc, idpre)) {
                JOptionPane.showMessageDialog(null, "Exito al ingresar la pregunta");
                objin.txtPreguntaDescripcion.setText("");
                objin.txtOpcionA.setText("");
                objin.txtOpcionB.setText("");
                objin.txtOpcionC.setText("");
                objin.txtOpcionD.setText("");
                objin.A.setSelected(false);
                objin.B.setSelected(false);
                objin.C.setSelected(false);
                objin.D.setSelected(false);
                objin.comboxCategoria.setSelectedItem("1");

            } else {
                JOptionPane.showMessageDialog(null, "Fallo al ingresar la pregunta");
            }

        }

        // CUANDO SE OPRIME EL BOTON DE JUGAR EN EL INDEX, SE DESPLIEGA
        if (e.getSource() == objind.btnJugar) {
            desplegar(rondanum);
            
        }

        // SE VERIFICA LA RESPUESTA Y SE VUELVE A DESPLEGAR LA VENTANA PERO CON EL SIGUIENTE NIVEL
        if (e.getSource() == objjue.btnVerificar) {
            verificarRespuesta();
            

        }

        if(e.getSource()== objin.bntIngPregVolver){
            objin.dispose();
            objind.setVisible(true);
            
            
        }
        
        
    }
    
    
    
    
}
