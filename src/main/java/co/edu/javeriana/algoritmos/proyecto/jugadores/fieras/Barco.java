package co.edu.javeriana.algoritmos.proyecto.jugadores.fieras;

import java.util.ArrayList;
import java.util.List;

import co.edu.javeriana.algoritmos.proyecto.Casilla;

public class Barco {
	private List<Casilla> casillas;
	private int longitud;
	private boolean hundido ;
	
	
	public Barco(int longitud) {
		this.casillas = new ArrayList<Casilla>();
		this.longitud = longitud;
	}


	public List<Casilla> getCasillas() {
		return casillas;
	}


	public int getLenght() {
		return this.longitud;
	}
	
	public void addCasilla(int x,int y) 
	{
		casillas.add(new Casilla(x,y));
	}


	public boolean isHundido() {
		return hundido;
	}


	public void setHundido(boolean hundido) {
		this.hundido = hundido;
	}
	
}
