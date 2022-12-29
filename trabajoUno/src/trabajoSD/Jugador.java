package trabajoSD;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Jugador implements Serializable{
	private String nombre;
	private List<Carta> cartas;
	
	public Jugador(String nombre,ArrayList<Carta> cartas) {
		this.nombre=nombre;
		this.cartas=cartas;
	}
	
	public void mostrar() {
		String s ="";
		for(int i=0;i<this.cartas.size();i++) {
			s=s+i+"."+this.cartas.get(i).toString()+" ";
		}
		System.out.println(s);
	}
	
	public void robarCarta(Carta c) {
		this.cartas.add(c);
	}
	
	public Carta jugar(int i) {
		Carta c = this.cartas.get(i);
		this.cartas.remove(i);
		return c;
	}
	
	public boolean tengoCarta(Carta c) {
		boolean tengo = false;
		for(int i=0;i<this.cartas.size();i++) {
			if(this.cartas.get(i).getColor().equals(c.getColor()) || this.cartas.get(i).getValor().equals(c.getValor())) {
				tengo=true;
			}
		}
		return tengo;
	}
	public boolean vacio() {
		return this.cartas.size()==0;
	}
}
