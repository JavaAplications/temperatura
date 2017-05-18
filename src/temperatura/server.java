package temperatura;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import BBDD.Conexion;


public class server {
	static Conexion conectar;
	
	public static void main(String[] args) {
	
	Connection con=null;
	
	conectar=new Conexion();
	System.out.println("Sensor : "+conectar.ConsultarNombre(1));
	System.out.println("Sensor : "+conectar.ConsultarNombre(2));
	conectar.InsertarDato(1,"23.00","78.00" );
	
}
		
		
			
	}


