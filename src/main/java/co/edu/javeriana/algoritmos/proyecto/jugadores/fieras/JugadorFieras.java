package co.edu.javeriana.algoritmos.proyecto.jugadores.fieras;

import co.edu.javeriana.algoritmos.proyecto.Casilla;
import co.edu.javeriana.algoritmos.proyecto.Jugador;
import co.edu.javeriana.algoritmos.proyecto.RespuestaJugada;
import co.edu.javeriana.algoritmos.proyecto.ResumenTablero;
import co.edu.javeriana.algoritmos.proyecto.Tablero;

public class JugadorFieras implements Jugador{

	private String nombre;
	private TableroFieras propio;
	private TableroFieras enemigo;
	private Casilla ultimaJugada;
	private int dimension ;
	private int[] barcos;
	
	public JugadorFieras()
	{
		this.nombre = "Fieras";
	}
	
	@Override
	public String obtenerNombre() {
		return this.nombre;
	}

	@Override
	public Tablero iniciarTablero(int dimension, int[] barcos) {
		this.dimension = dimension;
		this.barcos = barcos;
		this.propio = new TableroFieras(dimension,barcos);
		Tablero res = this.propio;
		return res;
	}

	@Override
	public void recibirResumenRival(ResumenTablero resumen) {
		this.enemigo = new TableroFieras(resumen,this.dimension,this.barcos);
	}

	@Override
	public RespuestaJugada registrarDisparoAPosicion(Casilla posicion) {
		return this.propio.dispararACasilla(posicion);
	}

	@Override
	public Casilla realizarDisparo() {
		this.ultimaJugada = this.enemigo.getMayor();
		return this.ultimaJugada;
	}

	@Override
	public void procesarResultadoDisparo(RespuestaJugada resultado) {
		this.enemigo.prosesarDisparoEnemigo(this.ultimaJugada,resultado);
	}

	@Override
	public int numeroBarcosNoHundidos() {
		return this.propio.numeroBarcosNoHundidos();
	}

}
