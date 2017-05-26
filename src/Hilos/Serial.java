package Hilos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import BBDD.Conexion;

import com.fazecast.jSerialComm.SerialPort;

public class Serial extends Thread {
	Conexion conectar;
	SerialPort comPort;
	Hilo hilito;
	runables hilo_run;
	String dato;
	static boolean control=true;
	
	
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
		int CantidadSensores=conectar.ConsultarCantidadSensores();
		String content;
		byte[] bytes ; 

		while(true){
			control=true;
			for(int i=1;i<=CantidadSensores;i++){
				 
				hilito=new Hilo(conectar.ConsultarNombre(i));
				hilito.start();
				System.out.println("SENSOR DE: "+conectar.ConsultarNombre(i));
				content=i+"\r";
				bytes=content.getBytes();
				 try {
					out.write(bytes);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
					
				while(control){
					 try {
					   	  char_dat=(char)in.read();
					   	  txt.append(char_dat);
						} catch (IOException e) {
								e.printStackTrace();}
					      
					      if(char_dat=='\n'){
					    	  dato=txt.toString();
					    	  ProcesarMensaje();
					    	  txt.delete(0, txt.length());
					    //	  Hilo.controlHilo=true;
					    	  break;
					      }
				   }
					control=true;
					
					// tiempo de epera entre mediciones
				try {
							sleep(4000);
						
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 
					    
				
					
					
			}
		}
	}

	public void ProcesarMensaje(){//expresion regular para controlar caracteres ingresados.
		 String[] parts ;
		 int numParts=3;
		 String id = null ; 
		 int id_f = 0;
		 String temp ; 
		 String hum ; 
		 
		float t_f = 0,h_f = 0;
		Pattern pat = Pattern.compile("[1-9]:;");
		Matcher mat = pat.matcher(dato);
		
		 boolean valido=mat.find();
	 if (true) {//comprueba caracteres validos
		 parts = dato.split(";");// separa por espacio debe ser ;
	//	 System.out.println("Numero de partes"+parts.length);
		 if(parts.length==numParts){// comprueba numero de datos separados
			 id = parts[0]; 
			 temp = parts[1]; 
			 hum = parts[2]; 
			// System.out.println("id:"+id+"  temp:"+temp+"  hum:"+hum);
			 id_f=Integer.valueOf(id);
			 t_f=Float.parseFloat(temp);
		   	 h_f=Float.parseFloat(hum);
		   	 //System.out.println("id_f:"+id_f+"  t_f:"+t_f+"  h_f:"+h_f);
		   	 valido=true;
		 }
		
	   	 }
	 else {
	   	  id_f=0;
	   	  t_f=0;
	   	  h_f=0;
	   	 valido=false;
	     }
	  System.out.println("\t Id:"+id+"\t T:"+t_f+"\t H:"+h_f+"\t valido:"+valido);
	  
	  conectar.InsertarDato(id_f,t_f,h_f,valido);
	 
  	}
	
	
		
}
