package temperatura;

import Hilos.Serial;

public class server {
	
	static Serial com;
    static int pooling=15;
  
    public static void main(String[] args) {
 
    
		com=new Serial(pooling);
		com.run();

}

	
	}


