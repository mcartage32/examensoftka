package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
     private int rondanum=1;
     private String nombreJugador="";
   
     public Controlador(ingresar_preguntas objin,index objind, juego objjue )
     {
      this.objin = objin;
      this.objin.btnIngresar.addActionListener(this);
     
      this.objind = objind;
      this.objind.btnJugar.addActionListener(this);
      
      this.objjue= objjue;
      this.objjue.btnVerificar.addActionListener(this);
      
     }
    
     
     public void desplegar(int i)
     {  
         if(nombreJugador=="")
         {String nomJugador= JOptionPane.showInputDialog(null,"Ingrese el nombre del jugador:", "Nombre del Jugador", JOptionPane.INFORMATION_MESSAGE);
          nombreJugador=nomJugador;
          objjue.lblNomJugador.setText(nomJugador);
         }
         
        if(rondanum>5)
        { JOptionPane.showMessageDialog(null,"HAS GANADO!! :D, te esperamos para proxima una proxima partida!!");   
          System.exit(0);
        
        }

                int ronda=i;
                String opccorre="";
                String opcselec="";
                ArrayList<Auxiliar> lista = objmod.preguntaConOpciones(objmod.idPreguntaAzar(ronda));

                for (Auxiliar aux:lista)
                {
                    objjue.lblPregunta.setText(aux.getDescripcionPregunta());

                    if(aux.getOpcion().equals("A"))
                    objjue.opcionA.setText(aux.getOpcion()+") "+aux.getDescripcionOpcion());
                    if(aux.getOpcion().equals("B"))
                    objjue.opcionB.setText(aux.getOpcion()+") "+aux.getDescripcionOpcion());
                    if(aux.getOpcion().equals("C"))
                    objjue.opcionC.setText(aux.getOpcion()+") "+aux.getDescripcionOpcion());
                    if(aux.getOpcion().equals("D"))
                    objjue.opcionD.setText(aux.getOpcion()+") "+aux.getDescripcionOpcion());
                    
                    opccorrecta=aux.getOpcionCorrecta();
                    
                }
           
                
     }
     
     
     public void verificarRespuesta()
     {   String opcselec="";
     
     
                    if(objjue.opcionA.isSelected())
                    opcselec="A";
                    else if(objjue.opcionB.isSelected())
                    opcselec="B";
                    else if(objjue.opcionC.isSelected())
                    opcselec="C";
                    else if(objjue.opcionD.isSelected())
                    opcselec="D";
                   
                    
                    if(opcselec.equals(opccorrecta))
                    {JOptionPane.showMessageDialog(null,"Respuesta Correcta");
                     rondanum++;
                    }
                    else
                    {JOptionPane.showMessageDialog(null,"Has perdido, no puedes seguir jugando");   
                    System.exit(0);  
                    }
     }
     
     
     

    @Override
    public void actionPerformed(ActionEvent e) {

        //PROCESO PARA AGREGAR PREGUNTA A LA BD
        if(e.getSource()==objin.btnIngresar)
        {
           char opccorr;
           if(objin.A.isSelected())
           opccorr='A';
           else if(objin.B.isSelected())
           opccorr='B';
           else if(objin.C.isSelected())
           opccorr='C';
           else
           opccorr='D';
           
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
           

           if(objmod.ingresarOpciones(opc, idpre))
           {JOptionPane.showMessageDialog(null,"Exito al ingresar la pregunta");
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
            
           }
           else
           JOptionPane.showMessageDialog(null,"Fallo al ingresar la pregunta");   
 
        }
        
        
        // PROCESO DE JUGAR
        
        if(e.getSource()==objind.btnJugar)
         desplegar(rondanum);       

       
        
        if(e.getSource()==objjue.btnVerificar)
        {verificarRespuesta();
         desplegar(rondanum);
        
        }
        
    }
    
    
    
    
}
