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
	private String os;
	InputStream in;
	OutputStream out;
	StringBuffer txt = new StringBuffer(); 

	public Serial(int pooling, String sistemaOP) {
		
		conectar=new Conexion();
		this.tiempoPooling=pooling;
		this.os=sistemaOP;
		if(os.equals("w")){
			comPort = SerialPort.getCommPorts()[0];//windows
			System.out.println("Accediendo al puerto serial com[0] ...");
		}else{
			comPort = SerialPort.getCommPort("/dev/ttyS0");//linux
			System.out.println("Accediendo al puerto serial /dev/ttyS0 ...");
			
		}
	}

	@Override
	public void run() {
		super.run();
		
		char char_dat = 0;
		StringBuffer txt = new StringBuffer(); 
	try {
			comPort.openPort();
	    	comPort.setComPortTimeouts(SerialPort.LISTENING_EVENT_DATA_RECEIVED, 100, 0);
	    	in = comPort.getInputStream();
			out = comPort.getOutputStream();
		} catch (Exception e) {
			System.out.println("Error serial: "+e);
		}
	
	
	int CantidadSensores=conectar.ConsultarCantidadSensores();
	System.out.println("Cantidad Sensores:"+CantidadSensores);
	String content ;
	byte[] bytes ; 

	while(true){
			//control=true;
			for(int i=1;i<=CantidadSensores;i++){
				System.out.println("i:"+i);
			
				System.out.println(conectar.ConsultarNombre(i));
				content=i+"\r";
				bytes=content.getBytes();
				
				try {				
					out.write(bytes);
					bytes=null;
					System.out.println("enviar:"+content);
					hilito=new Hilo(conectar.ConsultarNombre(i));
					hilito.start();
					
					}
				catch (IOException e2) {
					e2.printStackTrace();
					}
				 System.out.println("inicio while control");	
				control=true;
				while(control){
					 try {
					   	  char_dat=(char)in.read();
					   	  txt.append(char_dat);
					   
							}
					 catch (IOException e) {
								e.printStackTrace();
							}
					      
					 if((char_dat=='\n')){
						 dato=txt.toString();
					     System.out.println("Recibido:'"+dato+"'");
					  //   ProcesarMensaje();
					     
					     ProcesoCorto();
					     txt.delete(0, txt.length());
					      break;
					 //    control=false;
					  }
				
					
				   }
				
					// tiempo de espera entre mediciones
				try {sleep(tiempoPooling*1000);
					}
				catch (InterruptedException e1) {
					e1.printStackTrace();
			 		} 
			  }//for
		}//while
	}

	public void ProcesarMensaje(){//expresion regular para controlar caracteres ingresados.
		
		boolean valido=false;
		String[] parts ;
		int numParts=3;
		String id = null ; 
		int id_f = 0;
		String temp ; 
		String hum ; 
		Calendar calendario = Calendar.getInstance();
		int hora = 0, minutos = 0, segundos = 0,dia = 0,mes = 0,ano = 0;
		float t_f = 0,h_f = 0;
		System.out.println("Procesando el dato:"+dato);
		Pattern pat = Pattern.compile("\\d\\;\\d{2}\\.\\d{2}\\;\\d{2}\\.\\d{2}\\s");
	   
		Matcher mat = pat.matcher(dato);
		//boolean valido=mat.find();
		System.out.println("valido:"+valido);
		
		if(mat.matches()){
			 System.out.print("Dato valido:");
			 valido=true;
		}else{
			 System.out.print("Dato Invalido:");
			 valido=false;
		 }
		System.out.println(dato);
	
		if (valido) {//comprueba caracteres validos
			
		try{	
			parts = dato.split(";");// separa por espacio debe ser ;
			if(parts.length==numParts){// comprueba numero de datos separados
				id = parts[0]; 
				temp = parts[1]; 
				hum = parts[2]; 
				System.out.println("Numero de partes ok");
			/*  System.out.println("id:"+id);
			  System.out.println("temp:"+temp);
			 System.out.println("hum:"+hum);*/
				id_f=Integer.valueOf(id);
				t_f=Float.parseFloat(temp);
				h_f=Float.parseFloat(hum);
				dia =calendario.get(Calendar.DAY_OF_MONTH);
				mes =calendario.get(Calendar.MONTH);
				ano =calendario.get(Calendar.YEAR);
				hora =calendario.get(Calendar.HOUR_OF_DAY);
				minutos = calendario.get(Calendar.MINUTE);
				segundos = calendario.get(Calendar.SECOND);
				//valido=true;
				System.out.print(dia+"-"+mes+"-"+ano+" "+hora + ":" + minutos + ":" + segundos);
				System.out.println(" Id:"+id+"\t T:"+t_f+"\t H:"+h_f+"\t lectura:"+valido);
				conectar.InsertarDato(id_f,t_f,h_f,valido);
		    	}
			else{
		    	 System.out.println("no concuerda el numero de partes");
		    	}
		    
		 	}catch (Exception e) {
				System.out.println("Error comprueba caracteres validos "+e);
		 		}
			
		 }else{//dato invalido
			 
			 conectar.InsertarDato(1,0,0,false);
			 
		 }
	}		
	
	
    public void ProcesoCorto(){
		
		
		String[] parts ;
		int numParts=3;
		String id = null ; 
		int id_f = 0;
		String temp ; 
		String hum ; 
		Calendar calendario = Calendar.getInstance();
		int hora = 0, minutos = 0, segundos = 0,dia = 0,mes = 0,ano = 0;
		float t_f = 0,h_f = 0;
		System.out.println("Procesando el dato:"+dato);
	
	
		try{	
			parts = dato.split(";");// separa por espacio debe ser ;
			System.out.println("numero de partes:"+parts);
			if(parts.length==numParts){// comprueba numero de datos separados
				String[] parteUno ;
				parteUno = parts[0].split("\r");// separa por espacio debe ser ;
				id = parteUno[1]; 
				temp = parts[1]; 
				hum = parts[2]; 
				System.out.println("Numero de partes ok");
		
				id_f=Integer.valueOf(id);
				t_f=Float.parseFloat(temp);
				h_f=Float.parseFloat(hum);
				
				System.out.println(" Id:"+id+"\t T:"+t_f+"\t H:"+h_f);
				conectar.InsertarDato(id_f,t_f,h_f,true);
		    
			}
		 	}catch (Exception e) {
				System.out.println("Error comprueba caracteres validos "+e);
		 		}
		}

}
