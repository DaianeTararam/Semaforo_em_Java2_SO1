package view;
import controller.Prato;
import java.util.concurrent.Semaphore;

public class PratoMain {

	public static void main(String[] args) {
		
        Semaphore semaforo = new Semaphore(1);  
        
        for (int i = 1; i <= 5; i++) {
            Prato prato = new Prato(i, semaforo);
            prato.start();
        }
	}
}
