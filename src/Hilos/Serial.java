package Hilos;

import java.io.IOException;
import java.io.InputStream;

import BBDD.Conexion;

import com.fazecast.jSerialComm.SerialPort;

public class Serial extends Thread {
	Conexion conectar;
	SerialPort comPort;
	char dato;
	boolean control=true;
	StringBuffer txt = new StringBuffer(); 
	
	public Serial(){
				conectar=new Conexion();
	}

	@Override
	public void destroy() {
	
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		comPort = SerialPort.getCommPorts()[0];
		comPort.openPort();
		comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
		InputStream in = comPort.getInputStream();
	/*	try
		{
		   for (int j = 0; j < 46; ++j)
		      System.out.print((char)in.read());
		      

		 //  in.close();
		} catch (Exception e) {
				e.printStackTrace(); }
		*/
		while(true){
			
			try {
				dato=(char)in.read();
				//System.out.print(dato);
				txt.append(dato);
				if(dato=='\n'){
					System.out.println(conectar.ConsultarNombre(4));
					System.out.println(txt);
						
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//comPort.closePort();
		
		
	}

	
	public void Extraer(String cadena){
		
		
		
		
	}
	
	
	
}
