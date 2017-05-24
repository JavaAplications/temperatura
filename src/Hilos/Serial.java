package Hilos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import BBDD.Conexion;

import com.fazecast.jSerialComm.SerialPort;

public class Serial extends Thread {
	Conexion conectar;
	SerialPort comPort;
	
	String dato;
	boolean control=true;
	
	
	StringBuffer txt = new StringBuffer(); 

	public Serial(){
				conectar=new Conexion();
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		char char_dat = 0;
		StringBuffer txt = new StringBuffer(); 
		SerialPort comPort = SerialPort.getCommPorts()[0];
		comPort.openPort();
		comPort.setComPortTimeouts(SerialPort.LISTENING_EVENT_DATA_AVAILABLE, 100, 0);
		InputStream in = comPort.getInputStream();
		OutputStream out = comPort.getOutputStream();
		String content = "t\r";
		byte[] bytes = content.getBytes();

		while(true){
			
			 try {
				sleep(5000);
				 out.write(bytes);
				
				 System.out.println("Comando Temperatura");
				
 
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			while(true){
				
		      try {
		    	 
		    	  char_dat=(char)in.read();
		    	  txt.append(char_dat);
				} catch (IOException e) {
					e.printStackTrace();}
		      
		      if(char_dat=='\n'){
		    	  dato=txt.toString();
		    	  ProcesarMensaje();
		    	  txt.delete(0, txt.length());
		    	  break;
		      }
			}
		
	   }
	}

	public void ProcesarMensaje(){//expresion regular para controlar caracteres ingresados.
		 String[] parts ;
		 int numParts=8;
		 String id ; 
		 String temp ; 
		 String hum ; 
		 String t="0",h="0";
		
		Pattern pat = Pattern.compile("[a-z1-9]\\s");
		Matcher mat = pat.matcher(dato);
		
		 boolean valido=mat.find();
	 if (valido) {//comprueba caracteres validos
		 parts = dato.split(" ");// separa por espacio debe ser ;
		 if(parts.length==numParts){// comprueba numero de datos separados
			 id = parts[0]; 
			 temp = parts[1]; 
			 hum = parts[2]; 
			 t=temp.substring(5,10);
		   	 h=hum.substring(4,9);
		 }
		
	   	 }
	 else {
	   	  t="0";
	   	  h="0";
	     }
	  System.out.println("\t Id:"+1+"\t T:"+t+"\t H:"+h+"\t valido:"+valido);
	  conectar.InsertarDato(1, t, h,valido);
   	  
   	  
   	  
	}
	
	
	public int[] ConsultarSensores(){
		 
		
		int[] listSens; 
		return null;
		
		
		
		
	}
		
}
