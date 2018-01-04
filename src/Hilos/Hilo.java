package Hilos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import BBDD.Conexion;

public class Hilo extends Thread{
	
	int c=0;
	boolean control=true;
	static boolean controlHilo=false;
	private String nombre;
	
	public Hilo(String nombre) {
		this.nombre=nombre;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
//	while(true){
		try {
			System.out.println(nombre+" "+c+"Seg");
			sleep(3000);
			Serial.control=false;
			c++;
			
			//System.out.println(nombre+": "+c+" seg.");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		
/*		if(c>5){
	
			Serial.control=false;
		
			break;
			}
	*/	
//	}
		
	}

	
	
	
	
}
