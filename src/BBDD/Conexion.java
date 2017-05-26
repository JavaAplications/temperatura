package BBDD;

import java.sql.*;


public class Conexion {
    private String _usuario="root";
    private String _pwd="";
    private static String _bd="bd_temperatura";
    static String url="jdbc:mysql://localhost/"+_bd;
	private Connection conn=null;

	  public Conexion() {
			
	     
		}

	
	public Connection Conexion(){
		
		String url="jdbc:mysql://localhost/"+_bd;
	
	try {
		Class.forName("com.mysql.jdbc.Driver");
		conn= DriverManager.getConnection(url,_usuario,_pwd);
		if(conn!=null){
	//		System.out.println("Conexion a base de datos "+ url +". . . ok");
		}
		if(conn== null){
			System.out.println("Conexion NULL...");
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
			rs=st.executeQuery("SELECT `nom_sen` FROM `sensores` WHERE `id_sen`='"+Id+"'");
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
			rs=st.executeQuery("SELECT COUNT(*) FROM `sensores`");
			  
			 while(rs.next()){
				 count = rs.getInt("count(*)");
			    }
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
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//count =0;
				 
		}
		
		return nombres;
	}
}