package Hilos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import BBDD.Conexion;

public class Hilo extends Thread{
	private Conexion conectar;
	boolean go=true;
	int c=0;
	public Hilo() {
		// TODO Auto-generated constructor stub
		System.out.println("Comienza Proceso");
		c=0;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	while(go){
		
		try {
			sleep(1000);
			c++;
			System.out.println("Pasos: "+c);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(c>4){
			
			destroy();
			
		}
		
	}
		
	}

	@Override
	public  void start() {
		// TODO Auto-generated method stub
		go=true;
		System.out.println("Comienza Proceso");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		go=false;
		System.out.println("Proceso Terminado");
	}
	
	
	
}