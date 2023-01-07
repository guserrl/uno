package trabajoSD;


import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
//Esta es la mesa donde se juega 
public class Mesa implements Serializable{
	//Baraja de cartas con la que se va a jugar
	Baraja b=new Baraja();
	//Numero total de jugadores
	private int totaljugadores;
	//Carta encima de la mesa
	private Carta cartaenJuego;	
	//Indica a que jugador le toca
	private int turno = 0;
	//Indica el fin de la partida
	private boolean terminar=false;
	//Forma de controlar el orden de los turnos
	private boolean invertido=false;
	//Jugadores en la mesa
	private Vector<Jugador> jugadores = new Vector<Jugador>();
	//Cartas en la mesa
	private Vector<Carta> mesa = new Vector<Carta>();
	
	public Mesa() {
		//Inicio la baraja de cartas con la que jugamos
		b.reset();b.barajea();
		//Añado jugadores
		for(int i=0;i<20;i++) {
			this.jugadores.add(new Jugador("Jugador_"+i));
		}
		//Coloco una carta en la mesa
		Carta c = b.robaCarta();
		mesa.add(c);
		//la pongo a jugar
		cartaenJuego=c;
	}
	
	public int getTurno() {return this.turno;}
	public Carta getCartaJuego() {return this.cartaenJuego;}
	public int totalJugadores() {return totaljugadores+1;}
	public boolean getTerminar() {return terminar;}
	//Robo carta de la mesa
	public void robar() {jugadores.get(turno).anadirCarta(b.robaCarta());}
	
	//Creo un juego con i jugadores
	public void empiezaJuego(int i) {
		this.totaljugadores=i;
		for(int j=0;j<this.totaljugadores;j++) {
			for(int k=0;k<10;k++) {
				this.jugadores.get(j).cartasJugador().add(b.robaCarta());
			}
		}
		System.out.println(totaljugadores);
	}			
	
	public void jugar_1Carta(int i) {
		//this.jugadores.get(turno).mostrarCartasJugador();
		if(!roba4()) {
			if(!saltar()) {
				this.roba2();
				boolean invert = this.invertir();
				
				if(!invert) {
						Carta c = this.jugadores.get(turno).jugar(i);
						if(c.getValor().compareTo(Carta.Valor.CambiaColor)!=0) {
							if(this.cartaenJuego.getColor().compareTo(c.getColor())==0
									|| this.cartaenJuego.getValor().compareTo(c.getValor())==0) {
								this.mesa.add(c);
								this.cartaenJuego=c;
								turno();
							}
						}else {
							this.cambiaColor();
						}											
				}				
			}			
		}
	}
	
	public int cartaAJugar() {				
		Jugador j = this.jugadores.get(turno);
		if(j.vacio()) {
			System.out.println("PARTIDA TERMINADA");
			this.terminar=true;
			//this.Terminar();
		}
		while(!j.tengoCarta(cartaenJuego)){
			j.anadirCarta(b.robaCarta());
		}
		System.out.println("Carta en juego: "+cartaenJuego.toString());		
		System.out.println("-------------------------");		
		j.mostrarCartasJugador();
		Scanner scan = new Scanner(System.in);
		System.out.println("Escoge carta");
		int i=scan.nextInt();
		Carta c = j.cartasJugador().get(i);
		if(c.getColor().compareTo(Carta.Color.Negro)==0) {
			return i;
		}else {
			int color =c.getColor().compareTo(this.cartaenJuego.getColor());
			int valor=c.getValor().compareTo(this.cartaenJuego.getValor());
			boolean b=false;
			while(!b) {
				if(color!=0) {
					if(valor!=0) {
						System.out.println("Otro valor");
						i=scan.nextInt();
						c=j.cartasJugador().get(i);
						color =c.getColor().compareTo(this.cartaenJuego.getColor());
						valor=c.getValor().compareTo(this.cartaenJuego.getValor());
					}else { b=true;}
				}else {
					b=true;
				}					
			}
		}						
		return i;
	}	
	
	public boolean saltar() {
		if(this.cartaenJuego.getValor().compareTo(Carta.Valor.Salta)==0) {
			if(!this.invertido) {
				this.turno=(this.turno+2)%this.totaljugadores;return true;
			}else {
				this.turno=(this.turno-2)%this.totaljugadores+this.totaljugadores%this.totaljugadores;return true;
			}			
		}return false;		
	}
	public boolean invertir() {
		if(this.cartaenJuego.getValor().compareTo(Carta.Valor.Invertir)==0) {
			this.invertido=!this.invertido;
			this.turno();
			return true;
		}
		return false;
	}
	
	public boolean roba2() {
		if(this.cartaenJuego.getValor().compareTo(Carta.Valor.Roba2)==0) {
			Vector<Carta> l = this.b.robaCarta(2);
			for(Carta c1 : l) {
				this.jugadores.get(turno).anadirCarta(c1);
			}
			this.turno();
			return true;
		}
		return false;
	}
	
	public boolean cambiaColor() {
			System.out.println("Valor a cambiar");
			System.out.println("1-Rojo,2-Azul,3-Verde,4-Amarillo");
			//i=di.readInt();
			Scanner scan = new Scanner(System.in);
			int i=scan.nextInt();
			switch(i) {
			case 1:{
				this.cartaenJuego.setColor(Carta.Color.Rojo);this.turno();return true;
			}
			case 2:{
				this.cartaenJuego.setColor(Carta.Color.Azul);this.turno();return true;
			}
			case 3:{
				this.cartaenJuego.setColor(Carta.Color.Verde);this.turno();return true;
			}
			case 4:{
				this.cartaenJuego.setColor(Carta.Color.Amarillo);this.turno();return true;
			}
			}			
		return false;
	}
	
	public boolean roba4() {
		if(this.cartaenJuego.getValor().compareTo(Carta.Valor.Roba4)==0) {
			Vector<Carta> l = this.b.robaCarta(4);
			for(Carta c1 : l) {
				this.jugadores.get(turno).anadirCarta(c1);
			}
			System.out.println("Valor a cambiar");
			System.out.println("1-Rojo,2-Azul,3-Verde,4-Amarillo");
			//i=di.readInt();
			Scanner scan = new Scanner(System.in);
			int i=scan.nextInt();
			switch(i) {
			case 1:{
				this.cartaenJuego.setColor(Carta.Color.Rojo);this.turno();return true;
			}
			case 2:{
				this.cartaenJuego.setColor(Carta.Color.Azul);this.turno();return true;
			}
			case 3:{
				this.cartaenJuego.setColor(Carta.Color.Verde);this.turno();return true;
			}
			case 4:{
				this.cartaenJuego.setColor(Carta.Color.Amarillo);this.turno();return true;
			}
			}
		}
		return false;
	}
	
	public void turno() {
		if(!this.invertido) {
			this.turno=(this.turno+1)%this.totaljugadores;
		}else {
			this.turno=(this.turno-1)%this.totaljugadores+this.totaljugadores%this.totaljugadores;
		}
	}
	public void Terminar() {
		System.exit(0);
	}
	
}
