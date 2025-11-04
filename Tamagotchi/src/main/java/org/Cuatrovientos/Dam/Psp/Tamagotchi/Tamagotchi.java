package org.Cuatrovientos.Dam.Psp.Tamagotchi;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.Random;
import java.util.Scanner;

public class Tamagotchi implements Runnable {
	private int id;
	private int suciedad;
	private boolean vivo;
	private int hambre;
	private Random random;
	private Scanner scanner;
	private Cuidador cuidador;
	private CountDownLatch latch;
	//Enum para controlar los estados del Tamagotchi
	private enum Estados {
		NADA,
		COMER,
		JUGAR,
		LIMPIARSE
	}
	private Estados estadoTamagotchi;
	
	public Tamagotchi(int id) {
		this.id = id;
		suciedad = 0;
		vivo = true;
		hambre = 0;
		random = new Random();
		scanner = new Scanner(System.in);
	}
	
	public void run() {
		
		//Variables de tiempo de inicio para comprobar por ejemplo la suciedad y el tiempo de vida
	    long tiempoInicio = System.currentTimeMillis();
	    long CINCO_MINUTOS = 5 * 60 * 1000; // 300000 ms
        long ultimoEnsuciamiento = System.currentTimeMillis();
	    
        //Comprobamos que no hayan pasado 5 min, que este vivo y que la suciedad no sea mayor que 10
	    while ((System.currentTimeMillis() - tiempoInicio < CINCO_MINUTOS) && vivo && suciedad < 10) {
	    	//Comprobamos el bucle cada segundo
	    	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	//Si han pasado 20 segundos se suma uno de suciedad, si hay mas de 5 de suciedad sacamos mensaje si hay 10 se muere
	    	if (System.currentTimeMillis() - ultimoEnsuciamiento >= 20000) {
		    	suciedad ++;
		    	ultimoEnsuciamiento = System.currentTimeMillis();
		    	if (suciedad == 5) {
		    		System.out.println("Tamagochi " + id + " esta muy sucio");
		    	}else if(suciedad == 10) {
		    		morir();
		    	}
	    	}
	    	
	    	//Comprobamos los 3 estados posibles y en cada caso ejecutamos su codigo
	    	if (estadoTamagotchi == Estados.JUGAR) {
	    		int num1;
	    		int num2;
	    		int sumaUsuario;
	    		boolean jugar = true;
	    		while (jugar) {
	    			//Bucle infinito para comprobar que la suma no sea mayor que 10
	    			do {
	    				num1 = random.nextInt(10);
	    				num2 = random.nextInt(10);
	    			}while((num1 + num2) >= 10);
	    			//Si el cuidador ingresa la suma correcta se sale del bucle y del metodo
	    			System.out.print("Ingrese la suma de los siguientes numeros " + num1 + " + " + num2 + " =");
	    			sumaUsuario = Integer.parseInt(scanner.nextLine());
	    			if (sumaUsuario == num1 + num2) {
	    				jugar = false;
	    			}
	    		}
	    	    latch.countDown(); // Libera la espera
	    		//Cambiamos el estado a nada para que no se repita
	    		estadoTamagotchi = Estados.NADA;
	    	}else if (estadoTamagotchi == Estados.LIMPIARSE) {
	    		//Sacamos 2 mensajes y esperamos 5 segundos
	    		System.out.println("Tamagotchi " + id + " ha empezado a limpiarse");
	    		try {
	    			Thread.sleep(5000);
	    		} catch (InterruptedException e) {
	    			e.printStackTrace();
	    		}
	    		System.out.println("Tamagotchi " + id + " ha terminado de limpiarse");
	    		suciedad = 0;
	    	    latch.countDown(); // Bajamos el contador una posicion, osea a 0 y liberamos el hilo principal
	    		//Cambiamos el estado a nada para que no se repita
	    		estadoTamagotchi = Estados.NADA;

	    	}else if (estadoTamagotchi == Estados.COMER) {
	    		//Simplemente sacamos 2 mensajes y esperamos 4 segundos
	    		System.out.println("Tamagotchi " + id + " empezo a comer");
	    		try {
	    			Thread.sleep(1000 + random.nextInt(4000));
	    		} catch (InterruptedException e) {
	    			e.printStackTrace();
	    		}
	    		System.out.println("Tamagotchi " + id + " finalizo de comer");
	    	    latch.countDown(); // Bajamos el contador una posicion, osea a 0 y liberamos el hilo principal
	    		//Cambiamos el estado a nada para que no se repita
	    		estadoTamagotchi = Estados.NADA;
	    	}
	    	
	    	
	    	    	
	    }
	    
	    //Si pasan los 5 min y sigue vivo se muere solo
        if (vivo) {
            System.out.println("Tamagotchi " + id + " ha cumplido su tiempo de vida.");
            morir();
        }	    
	}
	
	//Variables para las funcionalidades del tamagotchi, en este caso cambiamos el estadoTamagotchi para luego verificar en el run()
	public void comer() {
		//Igual que en jugar solo que cambiamos el metodo
	    latch = new CountDownLatch(1);
	    estadoTamagotchi = Estados.COMER;
	    try {
	        latch.await(); // Espera hasta que termine
	    } catch (InterruptedException e) {
			// TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}
	
	public void jugar() {
		//CountDownLatch basicamente es un contador, que inizializamos en cada metodo con un uno 1, y usando el await, paramos el hilo principal
		//Y cuando el contador llegue a 0 (eso lo hacemos en el metodo) se libera el hilo principal	
	    latch = new CountDownLatch(1);
		estadoTamagotchi = Estados.JUGAR;
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void limpiarse() {
		//Igual que en jugar solo que cambiamos el estado
	    latch = new CountDownLatch(1);
		estadoTamagotchi = Estados.LIMPIARSE;
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Simplemente cambiamos el booleano y mostramos que ha muerto
	public void morir() {
		vivo = false;
		System.out.println("Tamagotchi " + id + " ha muerto");
	}
	
	
	//Getters para comprobar cada tamagotchi desde el cuidador
	public int getSuciedad() {
		return suciedad;
	}

	public Boolean getVivo() {
		return vivo;
	}


	public int getHambre() {
		return hambre;
	}
	
	public int getId() {
		return id;
	}

	
	
}

