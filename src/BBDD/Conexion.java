package BBDD;

import java.sql.*;


public class Conexion {
    private String _usuario="root";
    private String _pwd="";
    private static String _bd="bd_temperatura";
    static String url="jdbc:mysql://localhost/"+_bd;
	private Connection conn;

public Conexion() {
		
	}

	
	public Connection Conexion( ){
	

	try {
		Class.forName("com.mysql.jdbc.Driver");
		conn= DriverManager.getConnection(url,_usuario,_pwd);
		if(conn!=null){
			System.out.println("Conexion a base de datos "+ url +". . . ok");
		}
		
	} catch (SQLException e) {
		System.out.println("No se pudo conectar");
	}
	catch(ClassNotFoundException e){
		System.out.println(e);
		
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
			rs=st.executeQuery("SELECT `nom_sen` FROM `sensores` WHERE `Id_sen`='"+Id+"'");
			while(rs.next()){
				Nombre=  rs.getString("nom_sen");				
			}
				
		
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Nombre ="Desconocido";
				 System.out.println("error ConsultarNombre: id"+Id);
		}
		
		return Nombre;
	}
}