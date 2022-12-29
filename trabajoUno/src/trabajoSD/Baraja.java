package trabajoSD;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

//Tamaño baraj 108 cartas
// Consta de 1 carta 0 y 2 cartas del resto de cada color excepto negro 
// Las unicas Negras son cambia color y roba 4 que son 4 cartas cada una

public class Baraja implements Serializable{
	private List<Carta> cartas; 
	
	public Baraja() {
		cartas = new ArrayList<Carta>();
	}
	//Crea o reseea la baraja de cartas
	public void reset() {
		Carta.Color[] colores = Carta.Color.values();
		
		for(int i=0;i<colores.length-1;i++) {
			Carta.Color color = colores[i];
			this.cartas.add(new Carta(color, Carta.Valor.getValor(0)));
			for(int j=1;j<10;j++) {
				this.cartas.add(new Carta(color, Carta.Valor.getValor(j)));
				this.cartas.add(new Carta(color, Carta.Valor.getValor(j)));
			}
			Carta.Valor[] valor = new Carta.Valor[] {Carta.Valor.Roba2,Carta.Valor.Salta,Carta.Valor.Invertir};
			
			for(Carta.Valor v : valor) {
				this.cartas.add(new Carta(color, v));
				this.cartas.add(new Carta(color, v));
			}
		}
		
		Carta.Valor[] valor = new Carta.Valor[] {Carta.Valor.Roba4,Carta.Valor.CambiaColor};
		for(Carta.Valor v : valor) {
			for(int k=0;k<4;k++) {
				this.cartas.add(new Carta(Carta.Color.Negro, v));			
			}			
		}
	}
	
	//Cambia la baraja con una array de cartas
	public void reemplazar(ArrayList<Carta> cartas) {
		this.cartas=cartas;
	}
	//Comprueba el tamaño de la baraja
	public boolean barajaVacia() {
		return this.cartas.isEmpty();
	}
	
	public void barajea() {
		int n = this.cartas.size();
		Random random = new Random();
		
		for(int i=0;i<this.cartas.size();i++) {
			int valorRandom = i+random.nextInt(n-i);
			Carta cartaAleatoria = this.cartas.get(valorRandom);
			this.cartas.set(valorRandom, this.cartas.get(i));
			this.cartas.set(i, cartaAleatoria);
		}
	}
	
	public Carta robaCarta() throws IllegalArgumentException{
		if(barajaVacia()) {
			throw new IllegalArgumentException("No quedan cartas que robar");
		}
		Carta c = this.cartas.get(0);
		this.cartas.remove(0);
		return c;
	}
	/*
	public ImageIcon robaCartaImagen() throws IllegalArgumentException{
		if(barajaVacia()) {
			throw new IllegalArgumentException("No quedan cartas que robar");
		}
		return new ImageIcon(this.cartas[--this.tamañoBaraja].toString()+".png");
	}*/
	
	public ArrayList<Carta> robaCarta(int n) throws IllegalArgumentException{
		if(n<0) {
			throw new IllegalArgumentException("Tienes que robar un numero positivo");
		}
		if(n>this.cartas.size()) {
			throw new IllegalArgumentException("No hay suficientes cartas en la baraja para robar");
		}
		ArrayList<Carta> c = new ArrayList<Carta>();
		
		for(int i=0;i<n;i++) {
			c.add(this.cartas.get(0));
			this.cartas.remove(0);
		}
		return c;
	}
}
