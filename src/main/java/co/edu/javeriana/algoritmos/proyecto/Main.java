package co.edu.javeriana.algoritmos.proyecto;

import java.util.Scanner;


public class Main {

	private static Scanner sc;

	public static void main(String[] args) {
		sc = new Scanner(System.in);
		JugadorI jugador = new JugadorI();
		int dimension = 10;
		int[] barcos = {3,2,2,2,4};
		jugador.iniciarTablero(dimension, barcos);
		int[] filasRival = {0,1,2,3,2,1,1,1,1,1};
		int[] columnasRival = {0,1,1,0,4,0,2,2,0,3};
		ResumenTablero resumen = new ResumenTablero(filasRival,columnasRival);
		jugador.recibirResumenRival(resumen);
		System.out.println("Jugador: "+ jugador.obtenerNombre());
		int tipo;
		
		int x,y;
		while( true )
		{
			System.out.println("Barcos no undidos: "+jugador.numeroBarcosNoHundidos());
			System.out.println(jugador.realizarDisparo());
			tipo = sc.nextInt();
			if( tipo == 0 ) jugador.procesarResultadoDisparo(RespuestaJugada.AGUA);
			else if( tipo == 1 ) jugador.procesarResultadoDisparo(RespuestaJugada.IMPACTO);
			else if( tipo == 2 ) jugador.procesarResultadoDisparo(RespuestaJugada.HUNDIDO);
			x = sc.nextInt();
			y = sc.nextInt();
			Casilla posicion = new Casilla(x,y);
			System.out.println(jugador.registrarDisparoAPosicion(posicion));
		}
	}

}
