package co.edu.javeriana.algoritmos.proyecto.jugadores.fieras;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.edu.javeriana.algoritmos.proyecto.Casilla;
import co.edu.javeriana.algoritmos.proyecto.RespuestaJugada;
import co.edu.javeriana.algoritmos.proyecto.ResumenTablero;
import co.edu.javeriana.algoritmos.proyecto.Tablero;

public class TableroFieras implements Tablero {

	private int[][] matriz;
	// 0 - neutra,1 -agua , 2- impacto, 3-hundido, 4 - posible
	private int[][] disparos;
	private int[] cantidadBarcosHorizontal;
	private int[] cantidadBarcosVertical;
	private int barcosVivos;
	private List<Barco> barcos; 
	private static int[] dirx = {-1, 0, 1,-1, 1,-1, 0, 1};
	private static int[] diry = {-1,-1,-1, 0, 0, 1, 1, 1};
	private static int[] dirx2 = { 1, 0,-1, 1};
	private static int[] diry2 = { 0, 1, 1, 1};
	
	
	public TableroFieras(ResumenTablero resumen, int longitud, int[] barcos2) {
		
		Arrays.sort(barcos2);
		this.cantidadBarcosHorizontal = new int[longitud];
		this.cantidadBarcosVertical = new int[longitud];
		this.matriz = new int[longitud][longitud];
		this.disparos = new int[longitud][longitud];
		for( int i = 0 ; i < longitud ; ++i )
		{
			this.cantidadBarcosHorizontal[i] = resumen.getCasillasOcupadasEnFila(i);
			this.cantidadBarcosVertical[i] = resumen.getCasillasOcupadasEnColumna(i);
		}
		this.barcos = new ArrayList<Barco>(); 
		for( int i = 0 ; i < barcos2.length ; ++i )
		{
			this.barcos.add(new Barco(barcos2[i]));
		}
		this.limpiarMatriz();
		generarPosiblesTableros(0, 0, 0);
	}


	public TableroFieras(int dimension, int[] barcos2) {
		this.cantidadBarcosHorizontal = new int[dimension];
		this.cantidadBarcosVertical = new int[dimension];
		this.matriz = new int[dimension][dimension];
		this.disparos = new int[dimension][dimension];
		this.barcosVivos = barcos2.length;
		this.barcos = new ArrayList<Barco>(); 
		for( int i = 0 ; i < barcos2.length ; ++i )
		{
			this.barcos.add(new Barco(barcos2[i]));
		}
		inicializarTabler();
		//imprimirMatriz(matriz);
	}

	// TODO - verificiar
	private void inicializarTabler() {
		int tamBarco = this.barcos.get(0).getLenght();
		int x,y;
		y = this.matriz.length-1;
		x = 0;
		for( int i = 0 ; i < tamBarco ; ++i )
		{
			this.barcos.get(0).addCasilla(x, y);
			this.matriz[x][y] = 1;
			x++;
		}
		
		int totBarcos = this.barcos.size();
		x = 0;
		for( int i = 1 ; i < totBarcos ; ++i)
		{
			y = 0;
			tamBarco = this.barcos.get(i).getLenght();
			for( int j = 0 ; j < tamBarco ; ++j )
			{
				this.barcos.get(i).addCasilla(x, y);
				this.matriz[x][y] =1;
				y++;
			}
			x += 2;
		}
	}


	@Override
	public ResumenTablero obtenerResumen() {
		return new ResumenTablero(this.resumenFilas(),this.resumenColumnas());
	}


	@Override
	public List<Casilla> obtenerCasillasOcupadasPorBarco(int numeroBarco) {
		return this.barcos.get(numeroBarco).getCasillas();
	}

	@Override
	public RespuestaJugada dispararACasilla(Casilla casilla) {
		return verificarDisparo(casilla.getFila(),casilla.getColumna());
	}

	@Override
	public int numeroBarcosNoHundidos() {
		return this.barcosVivos;
	}
	
	private RespuestaJugada verificarDisparo(int fila, int columna) {
		if( this.matriz[fila][columna] == 0 ) return RespuestaJugada.AGUA;
		if( this.matriz[fila][columna] == 2 ) return RespuestaJugada.IMPACTO;
		
		this.matriz[fila][columna] = 2;
		if( verificaHundido(fila,columna) )
			return RespuestaJugada.IMPACTO;
		this.barcosVivos--;
		
		return RespuestaJugada.HUNDIDO;
	}
	
	private boolean verificaHundido(int x, int y) {
		if( this.matriz[x][y] == 1 ) return true;
		int posx,posy,aux;
		aux = this.matriz[x][y];
		this.matriz[x][y] = 0;
		boolean res = false;
		for( int i =0 ; i < 8 ; ++i )
		{
			posx = x+dirx[i];
			posy = y +diry[i];
			if( posBuena(posx,posy)  && this.matriz[posx][posy] != 0  )
			{
				
				res = verificaHundido(posx,posy);
				if( res ) break;
			}
		}
		this.matriz[x][y] = aux;
		return res;
	}


	private boolean posBuena(int x, int y) {
		if( x < 0 || y < 0 || x >= this.matriz.length || y >= this.matriz.length  )
			return false;
		return true;
	}


	private int[] resumenFilas()
	{
		int tam = this.matriz.length;
		int[] respuesta= new int[tam];
		int cont = 0;
		for( int i = 0 ; i < tam ; ++i)
		{
			cont = 0;
			for( int j  =0  ;j  < tam ; ++j )
			{
				if( this.matriz[i][j] != 0 )
					cont++;
			}
			respuesta[i] = cont;
		}
		return respuesta;
	}

	private int[] resumenColumnas() {
		int tam = this.matriz.length;
		int[] respuesta= new int[tam];
		int cont = 0;
		for( int i = 0 ; i < tam ; ++i)
		{
			cont = 0;
			for( int j  =0  ;j  < tam ; ++j )
			{
				if( this.matriz[j][i] != 0 )
					cont++;
			}
			respuesta[i] = cont;
		}
		return respuesta;
	}
	
	public Casilla getMayor() 
	{

		/*this.imprimirMatriz(this.disparos);
		System.out.println();
		this.imprimirMatriz(this.matriz);*/
		int tam = this.matriz.length;
		int posx = -1;
		int posy = -1;
		int mayor = -1;
		for( int i =0 ; i < tam ; ++i)
		{
			for( int j = 0 ; j < tam ; ++j)
			{
				if( mayor < this.matriz[i][j] && this.disparos[i][j] == 0 )
				{
					mayor = this.matriz[i][j];
					posx = i;
					posy = j;
				}
			}
		}
		return new Casilla(posx,posy);
	}


	public void prosesarDisparoEnemigo(Casilla ultimaJugada, RespuestaJugada resultado) {
		int x = ultimaJugada.getFila();
		int y = ultimaJugada.getColumna();
		if( RespuestaJugada.AGUA.equals(resultado) )
			this.disparos[x][y] = 1;
		else if( RespuestaJugada.IMPACTO.equals(resultado) )
			this.disparos[x][y] = 2;
		else if( RespuestaJugada.HUNDIDO.equals(resultado) )
			hundirBarcoTamano(procesarHundidos(x,y));
		this.limpiarMatriz();
		this.generarPosiblesTableros(0,0,0);
	}

	private void hundirBarcoTamano(int tamano) {
		//System.out.println("voy a undir el barco de tamano " + tamano);
		for( int i = 0 ; i < this.barcos.size() ; ++i )
			if( this.barcos.get(i).getLenght() == tamano && !this.barcos.get(i).isHundido() )
			{
				this.barcos.get(i).setHundido(true);
				break;
			}
	}


	private int procesarHundidos(int x, int y) {
		int posx,posy;
		int tot = 1;
		this.disparos[x][y] = 3;
		this.cantidadBarcosHorizontal[x]--;
		this.cantidadBarcosVertical[y]--;
		for( int i =0 ; i < 8 ; ++i )
		{
			posx = x+dirx[i];
			posy = y +diry[i];
			if( posBuena(posx,posy) )
			{
				if( this.disparos[posx][posy] == 0 )
					this.disparos[posx][posy] = 1;
				else if( this.disparos[posx][posy] == 2 )
					tot += procesarHundidos(posx,posy);
			}
					
		}
		return tot;
	}

	private void limpiarMatriz() {
		int tam = this.matriz.length;
		for( int i = 0 ; i < tam ; ++i )
			for( int j = 0 ; j < tam; ++j)
				this.matriz[i][j] = 0;
		
	}


	
	
	private void generarPosiblesTableros(int pos ,int x,int y) {
		
		/*Scanner s = new Scanner(System.in);
		s.nextLine();
		System.out.println(pos + " " + x + " " + y);
		this.imprimirMatriz(this.disparos);*/
		if( pos == this.barcos.size() )
		{
			procesarMatrizGenerada();
			return;
		}
		
		if( pos == 0 || this.barcos.get(pos-1).getLenght() != this.barcos.get(pos).getLenght() )
		{
			/*System.out.println("---------"+pos + " " + x + " " + y);
			if( pos > 0)
			System.out.println(this.barcos.get(pos-1).getLenght()+ " " +this.barcos.get(pos).getLenght());
			*/x = 0;
			y = 0;
		}
		
		if( this.barcos.get(pos).isHundido() ) 
		{
			generarPosiblesTableros(1+pos,x,y);
			return;
		}
		int tam = this.matriz.length;
		for( int i =x ; i < tam; ++i )
		{
			for( int j =y ; j < tam ; ++j )
			{
				for( int k = 0 ; k < 4 ; ++k )
				{
					if( posibleColocarBarco(k,i,j,this.barcos.get(pos).getLenght()) )
					{
						colocarBarco(k,i,j,this.barcos.get(pos).getLenght(),4,-1);
						//System.out.println("voy a enviar a " + (1+pos) + " " +i + " " + j);
						generarPosiblesTableros(1+pos,i,j);
						colocarBarco(k,i,j,this.barcos.get(pos).getLenght(),0,1);
					}
				}
			}
			y = 0;
		}
	}


	private void imprimirMatriz(int[][] ma) {
		int tam = ma.length;
		for( int i =0 ; i < tam ; ++i)
		{
			for( int j = 0 ; j < tam ; ++j)
			{
				System.out.print(ma[i][j]+" ");
				
			}
			System.out.println();
		}
		
	}


	private void colocarBarco(int direccion, int x, int y, int casillas_faltantes, int valor,int sumaCantidad) {
		if( casillas_faltantes == 0 ) return;
		if( !posBuena(x,y) ) return ;
		this.cantidadBarcosHorizontal[x] += sumaCantidad;
		this.cantidadBarcosVertical[y] += sumaCantidad;
		if( this.disparos[x][y] == 0 || this.disparos[x][y] == 4)
			this.disparos[x][y] = valor;
		colocarBarco(direccion,x+dirx2[direccion],y+diry2[direccion],casillas_faltantes-1, valor,sumaCantidad);
		
	}


	private boolean posibleColocarBarco(int direccion, int x, int y, int casillas_faltantes) {
		//if( x == 8 && y == 2 )
		//	System.out.println(direccion+" "+x+" "+y+" " +casillas_faltantes);
		if( casillas_faltantes == 0 ) return true;
		if( !this.posBuena(x, y) ) return false;
		if( this.disparos[x][y] != 0 && this.disparos[x][y] != 2) return false; // TODO - verificcar cuando ya disparamos a un barco y le pego
		if( this.cantidadBarcosHorizontal[x] <= 0 ) return false;
		if( this.cantidadBarcosVertical[y] <= 0 ) return false;
		int posx,posy;
		
		for( int i = 0 ; i < 8 ; ++i )
		{
			posx = x+dirx[i];
			posy = y+diry[i];
			if( posBuena(posx,posy) && this.disparos[posx][posy] != 0 && this.disparos[posx][posy] != 1 && this.disparos[posx][posy] != 2  )
				return false;
		}
		boolean respuesta  = false;
		this.cantidadBarcosHorizontal[x]--;
		this.cantidadBarcosVertical[y]--;
		respuesta = posibleColocarBarco(direccion,x+dirx2[direccion],y+diry2[direccion],casillas_faltantes-1);
		this.cantidadBarcosHorizontal[x]++;
		this.cantidadBarcosVertical[y]++;
		return respuesta;
	}


	private void procesarMatrizGenerada() {
		int tam = this.disparos.length;
		for( int i = 0 ; i < tam ; ++i )
			for( int j = 0 ; j < tam ;  ++j )
				if( this.disparos[i][j] == 4 )
					this.matriz[i][j]++;
		
	}

}
