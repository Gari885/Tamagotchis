package org.Cuatrovientos.Dam.Psp.Tamagotchi;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.Cuatrovientos.Dam.Psp.Tamagotchi.Tamagotchi.Estados;


public class Cuidador implements Runnable {
	
	//Variables
	private ArrayList<Tamagotchi> tamas;
	private Boolean activo;
	private Random random;
	final int NUMTAMAGOTCHIS = 3;
	private int input;
	private Scanner scanner;
	private Thread thread;

	
	//Constructor creamos los tamagotchis
	public Cuidador() {
		crearTamagotchis();
		this.activo = true;
		this.random = new Random();
		input = 0;
		scanner = new Scanner(System.in);
	}

	@Override
	public void run() {
        System.out.println(" El cuidador ha comenzado a vigilar a los tamagotchis...");
        // Bucle inf
        while (activo) {
        	//Menu acciones
        	System.out.println("MENU TAMAGOTCHI");
        	System.out.println("1. LIMPAR");
        	System.out.println("2. ALIMENTAR");
        	System.out.println("3. JUGAR");
        	System.out.println("4. COMPROBAR ESTADOS");
        	System.out.println("5. MATAR");
        	System.out.println("6. SALIR");
        	System.out.print("ESCOGE UNA OPCION: ");
        	try {
        		input = Integer.parseInt(scanner.nextLine());
        	}catch(Exception e) {
        		System.out.println("Debes ingresar una opcion valida");
        	}
        	//Switch para recojer el input y hacer las diferentes opciones
        	switch (input) {
        	case 1:
        		//Validamos de que ha ingresado un numero correcto
        		if (elegirTamagotchi()) {
        			tamas.get(input).limpiarse();
        			break;
        		}else {
        			break;
        		}
        		
        	case 2:
        		if (elegirTamagotchi()) {
        			tamas.get(input).comer();
        			break;
        		}else {
        			break;
        		}
        	case 3:
        		if (elegirTamagotchi()) {
        			tamas.get(input).jugar();
        		}else {
        			break;
        		}
        	case 4:
        			System.out.println("Elige un tamagotchi para comprobar el estado");
        			if (elegirTamagotchi()) {
        				int tamaEstado = input;
        				System.out.println("1. SUCIEDAD");
        				System.out.println("2. HAMBRE");
        				System.out.println("3. VIVO");
        				System.out.println("OPCION: ");
        				try {
        					input = Integer.parseInt(scanner.nextLine());
        				} catch (Exception e){
        					System.out.println("Debes ingresar un numero");
        				}
        				if (input < 0 || input > 3) {
        					System.out.println("Debes ingresar una opcion valida");
        					break;
 
        				} else {
        					switch (input) {
            				case 1:
            					System.out.println("El tamagotchi " + tamaEstado+ " tiene " + tamas.get(tamaEstado).getSuciedad() + " de suciedad.");
            					break;
            				case 2:
            					System.out.println("El tamagotchi " + tamaEstado+ " tiene " + tamas.get(tamaEstado).getHambre() + " de hambre.");
            					break;
            				case 3:
            					boolean vivo = tamas.get(tamaEstado).getVivo();
            					if (vivo) {
            						System.out.println("Tu tamagotchi esta vivo");
            						break;
            					} else {
            						System.out.println("Tu tamagotchi esta muerto");
            						break;
            					}
            				default:
            	        		System.out.println("Opcion incorrecta");
            	        		break;
            				}
        				}
        				break;

                }

        	case 5:
        		if (elegirTamagotchi()) {
        			if (tamas.get(input).getEstado().equals(Estados.NADA) ) {
        				tamas.get(input).morir();
        			}else {
        				System.out.println("No puedes matar el tamagotchi en este momenot ya que esta realizando una accion");
        				break;
        			}
        			
        		}else {
        			break;
        		}       	
    		case 6:
        		System.out.println("Saliendo del programa....");
        		System.exit(0);
        	default:
        		System.out.println("Opcion incorrecta");
        		break;
        	}
            
        	//Miramos cada 2 segundos
            try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            //Comprobamos si hay alguno vivo o han muerto todos, si todos han muerto finalizamos el programa
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
	
	//Metodo para elegir un tamagotchi y validaciones
	private boolean elegirTamagotchi() {
		//Booleano para verificar de que el numero sea correcto
		System.out.println("Tamagotchis disponibles(Empezando desde el 0): " + (tamas.size() - 1));
		System.out.print("Elige un tamagotchi: ");
		try {
			input = Integer.parseInt(scanner.nextLine());
		}catch(Exception e) {
			System.out.println("Debes ingresar un numero");
			return false;
		}
		if (input < 0 || input > tamas.size() -1) {
			System.out.println("Debes ingresar un tamagotchi que exista");
			return false;
		}
		return true;
		
	}
	//Metodo para crear tamagotchi en este caso hacemos 3 pero se podria preguntar al usuario y pasar como parametro
	public void crearTamagotchis() {
		tamas = new ArrayList<>();
		Thread[] hilos = new Thread[3];
		
		for(int i = 0;i<3;i++) {
			tamas.add(new Tamagotchi(i + 1));
			hilos[i] = new Thread(tamas.get(i));
			hilos[i].start();
		}
	}


}

