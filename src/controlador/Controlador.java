package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Auxiliar;
import modelo.Modelo;
import modelo.Opcion;
import modelo.Pregunta;
import modelo.Historico;
import vistas.historico;
import vistas.ingresar_preguntas;
import vistas.juego;
import vistas.index;

public class Controlador implements ActionListener {
    
    private ingresar_preguntas objIngresarPreguntas;
    private juego objJuego;
    private index objIndex;
    private Modelo objModelo = new Modelo();
    private String opcionCorrecta;
    private int numeroDeRonda = 1;
    private String nombreJugador = "";
    private int puntosAcumulados=500;
    private int puntosxRonda;
    private Historico objHistorico;
    private historico objVistaHistorico;


    public Controlador(ingresar_preguntas objIngresarPreguntas, index objIndex, juego objJuego, historico objVistaHistorico) {
        
        this.objIngresarPreguntas = objIngresarPreguntas;
        this.objIngresarPreguntas.btnIngresar.addActionListener(this);
        this.objIngresarPreguntas.bntIngPregVolver.addActionListener(this);

        this.objIndex = objIndex;
        this.objIndex.btnJugar.addActionListener(this);
        this.objIndex.btnHistorico.addActionListener(this);

        this.objJuego = objJuego;
        this.objJuego.btnVerificar.addActionListener(this);
        this.objJuego.btnSalirDelJuego.addActionListener(this);
        
        this.objVistaHistorico = objVistaHistorico;
        this.objVistaHistorico.bntVolverHistorico.addActionListener(this);

    }

  
    @Override
    public void actionPerformed(ActionEvent e) 
    {

        //PROCESO PARA AGREGAR PREGUNTA A LA BD
        if (e.getSource() == objIngresarPreguntas.btnIngresar) 
        {
            Pregunta pregunta = new Pregunta();
            pregunta.setDescripcion(objIngresarPreguntas.txtPreguntaDescripcion.getText());
            pregunta.setOpcionCorrecta(obtenerOpcionSeleccionadaEnIngresarPreguntas());
            pregunta.setCategoria(Integer.parseInt(objIngresarPreguntas.comboxCategoria.getSelectedItem().toString()));
            objModelo.ingresarPregunta(pregunta);
            int idDePreguntaIngresada = objModelo.obtenerIdDePregunta(objIngresarPreguntas.txtPreguntaDescripcion.getText());

            Opcion opcion = new Opcion();
            opcion.setOpcA(objIngresarPreguntas.txtOpcionA.getText());
            opcion.setOpcB(objIngresarPreguntas.txtOpcionB.getText());
            opcion.setOpcC(objIngresarPreguntas.txtOpcionC.getText());
            opcion.setOpcD(objIngresarPreguntas.txtOpcionD.getText());

            if (objModelo.ingresarOpciones(opcion, idDePreguntaIngresada)) {
                JOptionPane.showMessageDialog(null, "Exito al ingresar la pregunta");
                limpiarFormularioIngresarPreguntas();
            } 
            else {
                JOptionPane.showMessageDialog(null, "Fallo al ingresar la pregunta");
            }

        }

        // CUANDO SE OPRIME EL BOTON DE JUGAR EN EL INDEX
        if (e.getSource() == objIndex.btnJugar) {
            desplegar(numeroDeRonda);    
        }

        // SE VERIFICA LA RESPUESTA EN EL JUEGO
        if (e.getSource() == objJuego.btnVerificar) {
            verificarRespuesta();
        }
        
        // CERRAR VENTANA DE INGRESAR PREGUNTA Y VOLVER AL INDEX
        if(e.getSource()== objIngresarPreguntas.bntIngPregVolver){
            objIngresarPreguntas.dispose();
            objIndex.setVisible(true);
        }
        
        // CERRAR VENTANA DE HISTORICO Y VOLVER AL INDEX
        if(e.getSource()== objVistaHistorico.bntVolverHistorico){
            objVistaHistorico.dispose();
            objIndex.setVisible(true);
        }
        
        
        // SALIR DEL JUEGO CON EL ACUMULADO
        if(e.getSource()== objJuego.btnSalirDelJuego){
        int respuesta = JOptionPane.showConfirmDialog(null,"¿Esta seguro de retirarse con un acumulado de "+puntosAcumulados+" puntos?","Abandonar Partida",JOptionPane.YES_NO_OPTION);
            
            if(respuesta==0)
            {  objHistorico = new Historico(nombreJugador,"Si","No",numeroDeRonda,puntosAcumulados);
            
                if(objModelo.ingresarHistorico(objHistorico))
                {JOptionPane.showMessageDialog(null,"Se inserto la partida al historico correctamente.");}
                else
                {JOptionPane.showMessageDialog(null,"Fallo al insertar la partida al historico.");} 
                
               reiniciarJuego();
            }

        }
        
        // MIRAR EL HISTORICO DEL JUEGO      
        if(e.getSource()== objIndex.btnHistorico){

             limpiarTabla();
             listarTablaHistorico();
        }
        
        
    }
    
    
     public void limpiarTabla()
     {
        DefaultTableModel tablaAuxiliar = (DefaultTableModel)objVistaHistorico.tablaHistorico.getModel();
        int a = objVistaHistorico.tablaHistorico.getRowCount()-1;
        for (int i = a; i >= 0; i--) 
        {tablaAuxiliar.removeRow(tablaAuxiliar.getRowCount()-1);}
     
     }
     
    
    public void listarTablaHistorico()
    { DefaultTableModel tablaAuxiliar = new DefaultTableModel();
      tablaAuxiliar = (DefaultTableModel)objVistaHistorico.tablaHistorico.getModel();
      ArrayList<Historico> lista = objModelo.obtenerDatosDelHistorico();
      Object[] object = new Object[6];
              
              for(int i=0; i<lista.size();i++)
              {
                object[0]=lista.get(i).getNombreJugador();
                object[1]=lista.get(i).getFecha();
                object[2]=lista.get(i).getRetiro();
                object[3]=lista.get(i).getGano();
                object[4]=lista.get(i).getUltimaRonda();
                object[5]=lista.get(i).getPuntosAcumulados();
                tablaAuxiliar.addRow(object);
              }
              
              objVistaHistorico.tablaHistorico.setModel(tablaAuxiliar); 
    }

    
     public void desplegar(int rondaActualRecibida)
    {
        if ("".equals(nombreJugador)) {
            String auxNombreJugador = JOptionPane.showInputDialog(null, "Ingrese el nombre del jugador:", "Nombre del Jugador", JOptionPane.INFORMATION_MESSAGE);
            nombreJugador = auxNombreJugador;
            objJuego.lblNomJugador.setText(auxNombreJugador);
        }

        if (numeroDeRonda > 5) {
            JOptionPane.showMessageDialog(null, "HAS GANADO!! :D, te esperamos para proxima una proxima partida!!","FELICITACIONES HAS GANADO EL JUEGO!!",JOptionPane.INFORMATION_MESSAGE);
            
            objHistorico = new Historico(nombreJugador,"No","Si",numeroDeRonda,puntosAcumulados);
            if(objModelo.ingresarHistorico(objHistorico))
            {JOptionPane.showMessageDialog(null,"Se inserto la partida al historico correctamente.");}
            else
            {JOptionPane.showMessageDialog(null,"Fallo al insertar la partida al historico.");} 
            
            reiniciarJuego();
        }

        int rondaActual = rondaActualRecibida;
        puntosxRonda=objModelo.premioAzar(rondaActual);
        objJuego.lblRonda.setText(String.valueOf(rondaActual));
        objJuego.lblPuntosAcumulados.setText(String.valueOf(puntosAcumulados));
        objJuego.lblPuntosxRonda.setText(String.valueOf(puntosxRonda)+" puntos");
        
        ArrayList<Auxiliar> lista = objModelo.obtenerPreguntaConOpciones(objModelo.idPreguntaAzar(rondaActual));
        for (Auxiliar aux : lista) 
        {
            objJuego.lblPregunta.setText(aux.getDescripcionPregunta());

            if (aux.getOpcion().equals("A")) {
                objJuego.opcionA.setText(aux.getOpcion() + ") " + aux.getDescripcionOpcion());
            }
            if (aux.getOpcion().equals("B")) {
                objJuego.opcionB.setText(aux.getOpcion() + ") " + aux.getDescripcionOpcion());
            }
            if (aux.getOpcion().equals("C")) {
                objJuego.opcionC.setText(aux.getOpcion() + ") " + aux.getDescripcionOpcion());
            }
            if (aux.getOpcion().equals("D")) {
                objJuego.opcionD.setText(aux.getOpcion() + ") " + aux.getDescripcionOpcion());
            }

            opcionCorrecta = aux.getOpcionCorrecta();

        }
        
    }

    public void verificarRespuesta() 
    {
        String opcionSeleccionada = obtenerOpcionSeleccionadaEnElJuego();

        if (opcionSeleccionada.equals(opcionCorrecta)) {
            JOptionPane.showMessageDialog(null, "Respuesta correcta, avanzas a la siguiente ronda", "Felicitaciones",JOptionPane.INFORMATION_MESSAGE);
            numeroDeRonda++;
            puntosAcumulados=puntosAcumulados+puntosxRonda;
            desplegar(numeroDeRonda);   
        } 
        
        else {
            JOptionPane.showMessageDialog(null, "Has perdido, no puedes seguir jugando. Pierdes el premio acumulado de "+puntosAcumulados+" puntos", "Respuesta Incorrecta",JOptionPane.INFORMATION_MESSAGE);
            
            objHistorico = new Historico(nombreJugador,"No","No",numeroDeRonda,puntosAcumulados);
            if(objModelo.ingresarHistorico(objHistorico))
            {JOptionPane.showMessageDialog(null,"Se inserto la partida al historico correctamente.");}
            else
            {JOptionPane.showMessageDialog(null,"Fallo al insertar la partida al historico.");}
            
            reiniciarJuego();
        }
    }
    
    public void reiniciarJuego()
    { objJuego.dispose();
      nombreJugador="";
      numeroDeRonda=1;
      puntosAcumulados=500;
      objIndex.setVisible(true);
    }        
    
    
    public char obtenerOpcionSeleccionadaEnIngresarPreguntas()
    { char opcionSeleccionada;
    
             if (objIngresarPreguntas.rdOpcionA.isSelected()) 
                opcionSeleccionada = 'A';
             else if (objIngresarPreguntas.rdOpcionB.isSelected()) 
                opcionSeleccionada = 'B';
             else if (objIngresarPreguntas.rdOpcionC.isSelected()) 
                opcionSeleccionada = 'C';
             else 
                opcionSeleccionada = 'D';
             
    return opcionSeleccionada;
    }
    
    
    public String obtenerOpcionSeleccionadaEnElJuego()
    { String opcionSeleccionada="";
    
        if (objJuego.opcionA.isSelected()) 
            opcionSeleccionada = "A";
        else if (objJuego.opcionB.isSelected()) 
            opcionSeleccionada = "B";
         else if (objJuego.opcionC.isSelected()) 
            opcionSeleccionada = "C";
         else if (objJuego.opcionD.isSelected()) 
            opcionSeleccionada = "D";

      return opcionSeleccionada;
    }        
    
    public void limpiarFormularioIngresarPreguntas()
    {  objIngresarPreguntas.txtPreguntaDescripcion.setText("");
       objIngresarPreguntas.txtOpcionA.setText("");
       objIngresarPreguntas.txtOpcionB.setText("");
       objIngresarPreguntas.txtOpcionC.setText("");
       objIngresarPreguntas.txtOpcionD.setText("");
       objIngresarPreguntas.rdOpcionA.setSelected(false);
       objIngresarPreguntas.rdOpcionB.setSelected(false);
       objIngresarPreguntas.rdOpcionC.setSelected(false);
       objIngresarPreguntas.rdOpcionD.setSelected(false);
       objIngresarPreguntas.comboxCategoria.setSelectedIndex(1);
    }
    
    
  
    
    
    
    
}
