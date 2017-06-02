package Hilos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.regex.*;


import BBDD.Conexion;

import com.fazecast.jSerialComm.SerialPort;

public class Serial extends Thread {
	
	Conexion conectar;
	SerialPort comPort;
	Hilo hilito;
	int tiempoPooling=60;//seg
	String dato;
	static boolean control=true;
	
	
	StringBuffer txt = new StringBuffer(); 

	public Serial(int Seg){
				conectar=new Conexion();
				this.tiempoPooling=Seg;
	}


	@Override
	public void run() {
		super.run();
		
		char char_dat = 0;
		StringBuffer txt = new StringBuffer(); 
	  //SerialPort comPort = SerialPort.getCommPorts()[0];//windows
		SerialPort comPort = SerialPort.getCommPort("/dev/ttyS0");//linux
		
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
				System.out.println(conectar.ConsultarNombre(i));
				content=i+"\r";
				bytes=content.getBytes();
				 try {
					out.write(bytes);
				} catch (IOException e2) {
				
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
					    	  break;
					      }
				   }
					control=true;
					
					// tiempo de epera entre mediciones
				try {
							sleep(tiempoPooling*1000);
						
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
		 Calendar calendario = Calendar.getInstance();
			int hora = 0, minutos = 0, segundos = 0,dia = 0,mes = 0,ano = 0;
		float t_f = 0,h_f = 0;
		Pattern pat = Pattern.compile("[1-9].;");
		Matcher mat = pat.matcher(dato);
		
		 boolean valido=mat.find();
		/* if(mat.find()){
			 System.out.println("true");
			 
		 }else{
			 System.out.println("false");
		 }*/
		 
	 if (true) {//comprueba caracteres validos
		 parts = dato.split(";");// separa por espacio debe ser ;
	
		 if(parts.length==numParts){// comprueba numero de datos separados
			 id = parts[0]; 
			 temp = parts[1]; 
			 hum = parts[2]; 
			
			 id_f=Integer.valueOf(id);
			 t_f=Float.parseFloat(temp);
		   	 h_f=Float.parseFloat(hum);
		   	 dia =calendario.get(Calendar.DAY_OF_MONTH);
			 mes =calendario.get(Calendar.MONTH);
			 ano =calendario.get(Calendar.YEAR);
			 hora =calendario.get(Calendar.HOUR_OF_DAY);
			 minutos = calendario.get(Calendar.MINUTE);
			 segundos = calendario.get(Calendar.SECOND);
		
		   	 valido=true;
		 }
		
	   	 }

	
	 System.out.print(dia+"-"+mes+"-"+ano+" "+hora + ":" + minutos + ":" + segundos);
	 System.out.println(" Id:"+id+"\t T:"+t_f+"\t H:"+h_f+"\t lectura:"+valido);
	 conectar.InsertarDato(id_f,t_f,h_f,valido);
	 
  	}
	
	
		
}
