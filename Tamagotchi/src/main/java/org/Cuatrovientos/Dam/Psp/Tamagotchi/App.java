package org.Cuatrovientos.Dam.Psp.Tamagotchi;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {	
    	
    	Cuidador cuidador = new Cuidador();
    	Thread hiloCuidador = new Thread(cuidador);
    	hiloCuidador.start();
    	
    	
    }
}
