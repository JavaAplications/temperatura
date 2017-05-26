package Hilos;

import java.util.Timer;

public class runables implements Runnable {
int c=0;
	@Override
	public void run() {
		while(true){
			
			
			try {
				Thread.sleep(1000);
				c++;
				System.out.println(c+" seg.");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(c>3){
				System.out.println("Termino proceso");
				break;
				
				
			}
			
		}
	}

}
