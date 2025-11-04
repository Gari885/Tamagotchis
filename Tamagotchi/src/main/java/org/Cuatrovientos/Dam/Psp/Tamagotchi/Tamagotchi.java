package org.Cuatrovientos.Dam.Psp.Tamagotchi;

import java.util.Random;
import java.util.Scanner;

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
	    long tiempoInicio = System.currentTimeMillis();
	    long CINCO_MINUTOS = 5 * 60 * 1000; // 300000 ms
        long ultimoEnsuciamiento = System.currentTimeMillis();
	    
	    while ((System.currentTimeMillis() - tiempoInicio < CINCO_MINUTOS) && vivo && suciedad < 10) {
	    	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	if (System.currentTimeMillis() - ultimoEnsuciamiento >= 20000) {
		    	suciedad ++;
		    	ultimoEnsuciamiento = System.currentTimeMillis();
		    	if (suciedad == 5) {
		    		System.out.println("Tamagochi " + id + " esta muy sucio");
		    	}else if(suciedad == 10) {
		    		morir();
		    	}
	    	}
	    	
	    	if (estadoTamagotchi == Estados.JUGAR) {
	    		int num1;
	    		int num2;
	    		int sumaUsuario;
	    		boolean jugar = true;
	    		while (jugar) {
	    			do {
	    				num1 = random.nextInt(10);
	    				num2 = random.nextInt(10);
	    			}while((num1 + num2) >= 10);
	    			System.out.print("Ingrese la suma de los siguientes numeros " + num1 + " + " + num2 + " =");
	    			sumaUsuario = Integer.parseInt(scanner.nextLine());
	    			if (sumaUsuario == num1 + num2) {
	    				jugar = false;
	    			}
	    		}
	    		estadoTamagotchi = Estados.NADA;
	    	}else if (estadoTamagotchi == Estados.LIMPIARSE) {
	    		System.out.println("Tamagotchi " + id + " ha empezado a limpiarse");
	    		try {
	    			Thread.sleep(5000);
	    		} catch (InterruptedException e) {
	    			e.printStackTrace();
	    		}
	    		System.out.println("Tamagotchi " + id + " ha terminado de limpiarse");
	    		suciedad = 0;
	    		estadoTamagotchi = Estados.NADA;

	    	}else if (estadoTamagotchi == Estados.COMER) {
	    		System.out.println("Tamagotchi " + id + " empezo a comer");
	    		try {
	    			Thread.sleep(1000 + random.nextInt(4000));
	    		} catch (InterruptedException e) {
	    			e.printStackTrace();
	    		}
	    		System.out.println("Tamagotchi " + id + " finalizo de comer");
	    		estadoTamagotchi = Estados.NADA;
	    	}
	    	
	    	
	    	    	
	    }
	    
        if (vivo) {
            System.out.println("Tamagotchi " + id + " ha cumplido su tiempo de vida.");
            morir();
        }	    
	}

	public void comer() {
		estadoTamagotchi = Estados.COMER;
	
	}
	
	public void jugar() {
		estadoTamagotchi = Estados.JUGAR;
	}
	
	public void limpiarse() {
		estadoTamagotchi = Estados.LIMPIARSE;

	}
	
	public void morir() {
		vivo = false;
		System.out.println("Tamagotchi " + id + " ha muerto");
	}

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

