package trabajoSD;


import java.io.Serializable;

public class Carta implements Serializable{
	
	enum Color{ 
		Rojo,Azul,Verde,Amarillo,Negro;
		
		private static final Color[] colores = Color.values();
		public static Color getColor(int i) {return Color.colores[i];}
	}
	
	enum Valor{ 
		Cero,Uno,Dos,Tres,Cuatro,Cinco,Seis,Siete,Ocho,Nueve,Roba2,Salta,Invertir,Roba4,CambiaColor;
		
		private static final Valor[] valores = Valor.values();
		public static Valor getValor(int i) {return Valor.valores[i];}
	}
	
	private Color color;
	private final Valor valor;
	
	public Carta(final Color color,final Valor valor) {
		this.color=color;
		this.valor=valor;
	}
	public Color getColor() {
		return this.color;
	}
	public Valor getValor() {
		return this.valor;
	}
	public String toString() {
		return this.color+"_"+this.valor;
	}
	public void setColor(Carta.Color c) {
		this.color=c;
	}
}
