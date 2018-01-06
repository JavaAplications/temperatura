package Hilos;


public class Hilo extends Thread{
	
	int c=0;
	boolean control=true;
	static boolean controlHilo=false;
	private String nombre;
	
	public Hilo(String nombre) {
}

	@Override
	public void run() {
		try {
			sleep(3000);
			Serial.control=false;
			System.out.println(nombre+" pasaron 2 seg y control= "+ Serial.control);
			
			//System.out.println(nombre+": "+c+" seg.");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	
		
	}

	
	
	
	
}
