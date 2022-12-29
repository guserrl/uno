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
//Esta es la mesa donde se juega
//Cada cliente escoge 
public class Juego implements Serializable{

	Baraja b;
	int totaljugadores;
	private Carta cartaenJuego;	
	private int turno = 0;
	private boolean invertido=false;
	private boolean terminar=false;
	
	private int estaJugando=0;
	private List<Jugador> jugadores = new ArrayList<Jugador>();
	private List<Carta> mesa = new ArrayList<Carta>();
	
	private Socket s;
	
	public Juego(Baraja b) {		
		this.b=b;				
	}
	public int turnoJuego() {
		return this.turno;
	}
	public boolean terminar() {
		return this.terminar;
	}
	public void setTerminar() {
		this.terminar=true;
	}
	
	public void empiezaJuego(int jugadores) {
		this.totaljugadores=jugadores;
		for(int i=0;i<jugadores;i++) {
			ArrayList<Carta> cartas = b.robaCarta(7);
			this.jugadores.add(new Jugador("_"+i, cartas));
		}			
	}
	
	public void turnoJugador() throws IOException {
		Jugador j = this.jugadores.get(estaJugando);
		j.mostrar();
		int i = 0;
		System.out.println("Escoge carta");
		DataInputStream di = new DataInputStream(System.in);
		i=di.readInt();
		if(this.mesa.isEmpty()) {
			this.cartaenJuego=j.jugar(i);
		}else {
			while(!j.tengoCarta(this.cartaenJuego)) {j.robarCarta(this.mesa.get(0));this.mesa.remove(0);}
			//System.out.println("Escoge carta");
			i=di.readInt();
			Carta c = j.jugar(i);
			if(c.getColor().equals(Carta.Color.Negro)) {
				this.mesa.add(c);
				this.cartaenJuego=c;
			}
			while(!c.getColor().equals(this.cartaenJuego.getColor()) || !c.getValor().equals(this.cartaenJuego.getValor())) {
				System.out.println("Otro valor");
				i=di.readInt();
				c=j.jugar(i);
			}
			this.mesa.add(c);
			this.cartaenJuego=c;
		}		
		if(this.cartaenJuego.getValor().equals(Carta.Valor.Salta)) {
			if(!this.invertido) {
				this.turno=(this.turno+2)%this.totaljugadores;
			}else {
				this.turno=(this.turno-2)%this.totaljugadores+this.totaljugadores%this.totaljugadores;
			}			
		}		
		if(this.cartaenJuego.getValor().equals(Carta.Valor.Invertir)) {
			this.invertido=!this.invertido;
			this.turno();			
		}		
		if(this.cartaenJuego.getValor().equals(Carta.Valor.Roba2)) {
			ArrayList<Carta> l = this.b.robaCarta(2);
			for(Carta c : l) {
				j.robarCarta(c);
			}
			this.turno();
		}		
		if(this.cartaenJuego.getValor().equals(Carta.Valor.Roba4)) {
			ArrayList<Carta> l = this.b.robaCarta(4);
			for(Carta c : l) {
				j.robarCarta(c);
			}
			this.turno();
		}		
		if(this.cartaenJuego.getValor().equals(Carta.Valor.CambiaColor)) {
			System.out.println("Valor a cambiar");
			System.out.println("1-Rojo,2-Azul,3-Verde,4-Amarillo");
			i=di.readInt();
			switch(i) {
			case 1:{
				this.cartaenJuego.setColor(Carta.Color.Rojo);break;
			}
			case 2:{
				this.cartaenJuego.setColor(Carta.Color.Azul);break;
			}
			case 3:{
				this.cartaenJuego.setColor(Carta.Color.Verde);break;
			}
			case 4:{
				this.cartaenJuego.setColor(Carta.Color.Amarillo);break;
			}
			}
			this.turno();
		}
		if(this.jugadores.get(estaJugando).vacio()) {
			System.out.println("PARTIDA TERMINADA");
		}
	}	
	public void turno() {
		if(!this.invertido) {
			this.turno=(this.turno+1)%this.totaljugadores;
		}else {
			this.turno=(this.turno-1)%this.totaljugadores+this.totaljugadores%this.totaljugadores;
		}
	}
}
