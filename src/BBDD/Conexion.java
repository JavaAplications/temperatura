package BBDD;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.*;

import static java.nio.charset.StandardCharsets.*;

public class Conexion {
	
	
	
	 private static String _bd="bd_temperatura";
//	static String url="jdbc:mysql://192.168.205.222/"+_bd;
	 private String _usuario="diego",_pwd="diego";
   // private String _usuario="root",_pwd="";
    static String url="jdbc:mysql://localhost/"+_bd;

 	private Connection conn=null;

	  public Conexion() {
				     
		}

	
	public Connection Conexion(){
	 
	
	try {
		
		Class.forName("com.mysql.jdbc.Driver");
		conn= DriverManager.getConnection(url,_usuario,_pwd);
		if(conn!=null){
		//	System.out.println("Conexion a base de datos "+ url +". . . ok");
		}
		if(conn== null){
			System.out.println("Conexion NULL...");
		}
		
	} catch (SQLException e) {
		System.out.print("SQLException: ");System.out.println(e);
	}
	catch(ClassNotFoundException e){
		System.out.print("ClassNotFoundException: ");System.out.println(e);
		
	}
	return conn;
}

	public String ConsultarNombre(int Id)

	{
		conn=Conexion();
		Statement st;
		ResultSet rs=null;
		String Nombre = null;
		try {
			st=conn.createStatement();
			rs=st.executeQuery("SELECT `nom_sen` FROM `sensores` WHERE `id_sen`='"+Id+"'");
			while(rs.next()){
				Nombre=  rs.getString("nom_sen");				
			}	
			conn.close();
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Nombre ="Desconocido";
				 System.out.println("error ConsultarNombre: id"+Id);
		}
		
		return Nombre;
	}

    public void InsertarDato(int id,float temp,float hum,boolean valido){
	
	    conn=Conexion();
	   
		PreparedStatement pst;
		try {
			pst = conn.prepareStatement("INSERT INTO dataloguer (id_sen,temp_dat,hum_dat,val_dat) VALUES (?,?,?,?)");
		
			pst.setInt(1,id);
			pst.setFloat(2,temp);
			pst.setFloat(3,hum);
			pst.setBoolean(4,valido);
			pst.execute();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

}

    public int ConsultarCantidadSensores()

	{
		conn=Conexion();
		Statement st;
		ResultSet rs=null;
		int count = 0;
		try {
			st=conn.createStatement();
			rs = st.executeQuery("SELECT 1+1 as a");
			//rs=st.executeQuery("SELECT COUNT(*) FROM `sensores`");
			  
			 while(rs.next()){
				count = rs.getInt("a");
				 //count = rs.getInt("count(*)");
			    }
			 conn.close();
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//count =0;
				 
		}
		
		return count;
	}
    
    public String[] ConsultarListadoSensores()

	{
		conn=Conexion();
		Statement st;
		ResultSet rs=null;
		int largo=ConsultarCantidadSensores();
		String[] nombres = new String[largo];
		int count = 0;
		try {
			st=conn.createStatement();
			rs=st.executeQuery("SELECT `nom_sen` FROM `sensores`");
			  
			 while(rs.next()){
				 
				 nombres[count] = rs.getString("nom_sen");
				 count++;
			    }
			 rs=st.executeQuery("SELECT `nom_sen` FROM `sensores`");
			 conn.close();
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//count =0;
				 
		}
		
		return nombres;
	}
}