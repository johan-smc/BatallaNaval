package co.edu.javeriana.algoritmos.proyecto;

public class JugadorI implements Jugador{

	private String nombre;
	private TableroJ propio;
	private TableroJ enemigo;
	private Casilla ultimaJugada;
	private int dimension ;
	private int[] barcos;
	
	public JugadorI()
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
		this.propio = new TableroJ(dimension,barcos);
		Tablero res = this.propio;
		return res;
	}

	@Override
	public void recibirResumenRival(ResumenTablero resumen) {
		this.enemigo = new TableroJ(resumen,this.dimension,this.barcos);
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
