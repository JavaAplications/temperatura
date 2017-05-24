package temperatura;

import BBDD.Conexion;
import Hilos.Hilo;
import Hilos.Serial;

public class server {
	static Conexion conectar;
	static Hilo hilito;
    static Serial com;
	public static void main(String[] args) {
 
		com=new Serial();
		com.run();
	

}

	
	}


