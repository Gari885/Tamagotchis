package org.Cuatrovientos.Dam.Psp.Tamagotchi;

import java.util.Random;
import java.util.Scanner;

public class Cuidador implements Runnable {

	private Tamagotchi[] tamas;
	private Boolean activo;
	private Random random;
	final int NUMTAMAGOTCHIS = 3;
	private int input;
	private Scanner scanner;
	
	public Cuidador() {
		crearTamagotchis();
		this.activo = true;
		this.random = new Random();
		input = 0;
		scanner = new Scanner(System.in);
	}

	@Override
	public void run() {
        System.out.println("👩‍⚕️ El cuidador ha comenzado a vigilar a los tamagotchis...");

        while (activo) {
        	System.out.println("MENU TAMAGOTCHI");
        	System.out.println("1. LIMPAR");
        	System.out.println("2. ALIMENTAR");
        	System.out.println("3. JUGAR");
        	System.out.print("ESCOGE UNA OPCION: ");
        	try {
        		input = Integer.parseInt(scanner.nextLine());
        	}catch(Exception e) {
        		System.out.println("Debes ingresar una opcion valida");
        	}
        	switch (input) {
        	case 1:
        		elegirTamagotchi();
        		tamas[input].limpiarse();
        		break;
        	case 2:
        		elegirTamagotchi();
        		tamas[input].comer();
        		break;
        	case 3:
        		elegirTamagotchi();
        		tamas[input].comer();
        		break;
        	default:
        		System.out.println("Opcion incorrecta");
        	}
            
            try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            boolean algunovivo = false;
            for (Tamagotchi t : tamas) {
            	if (t.getVivo()) {
            		algunovivo = true;
            	}
            }
            
            if (!algunovivo) {
            	System.out.println("Se ha acabado el juego, han muerto todos los tamagotchis");
            	activo = false;
            }
            
        }

	}

	private void elegirTamagotchi() {
		System.out.println("Tamagotchis disponibles: " + tamas.length);
		System.out.print("Elige un tamagotchi: ");
		try {
			input = Integer.parseInt(scanner.nextLine());
		}catch(Exception e) {
			System.out.println("Debes ingresar un numero");
		}
		
		if (input < 0 || input > tamas.length) {
			System.out.println("Debes ingresar un tamagotchi que exista");
		}
		
	}
	
	public void crearTamagotchis() {
		tamas = new Tamagotchi[3];
		Thread[] hilos = new Thread[3];
		
		for(int i = 0;i<3;i++) {
			tamas[i] = new Tamagotchi(i + 1);
			hilos[i] = new Thread(tamas[i]);
			hilos[i].start();
		}
	}
}

