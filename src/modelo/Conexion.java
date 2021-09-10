package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {
   
    static Connection con;
    
    public static void Conexiondos()
    { String usuario="root";
      String clave="";
      String url="jdbc:mysql://localhost:3306/examensoftka";
      
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        try {
            con= DriverManager.getConnection(url,usuario,clave);
           
            System.out.println("conexion buena");
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("conexion mala");
        }
        
    }

    public Connection getCon() {
        return con;
    }
    
    
    public static void main(String[] args) {
       Conexiondos();
    }
}
