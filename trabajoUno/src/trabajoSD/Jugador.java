package trabajoSD;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Jugador implements Serializable{
	private String nombre;
	private Vector<Carta> misCartas = new Vector<Carta>();
	
	public Jugador(String nombre) {
		this.nombre=nombre;
	}
	
	public String nombreJugador() {
		return this.nombre;
	}
	public Vector<Carta> cartasJugador(){return this.misCartas;}
	
	public void mostrarCartasJugador() {
		String s ="";
		for(int i=0;i<misCartas.size();i++) {
			s=s+i+"."+misCartas.get(i).toString()+" ";
		}
		System.out.println(s);
	}
	
	public void anadirCarta(Carta c) {
		this.misCartas.add(c);
	}
	
	public void quitarCarta(int i) {
		this.misCartas.remove(i);
	}
	public Carta jugar(int i) {
		Carta c = this.misCartas.get(i);
		this.quitarCarta(i);
		return c;
	}
	
	public boolean tengoCarta(Carta c) {
		boolean tengo = false;
		for(int i=0;i<this.misCartas.size();i++) {
			if(this.misCartas.get(i).getColor().compareTo(Carta.Color.Negro)==0) {
				return true;
			}
			if(this.misCartas.get(i).getColor().compareTo(c.getColor())==0 || this.misCartas.get(i).getValor().compareTo(c.getValor())==0) {
				tengo=true;
			}
		}
		return tengo;
	}
	public boolean vacio() {
		return this.misCartas.size()==0;
	}
	
}
