package trabajoSD;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {
	private static Socket socket;
	
	public Cliente(Socket s) {
		this.socket = s;					
	}
	public Socket socketCliente() {
		return this.socket;
	}
	public static void main(String[] args) {
		try {
			Socket s = new Socket("localhost", 8080);
			while(true) {
				try {			
					ObjectInputStream oi=new ObjectInputStream(socket.getInputStream());
					Juego j = (Juego) oi.readObject();
					j.turnoJugador();
					ObjectOutputStream oo = new ObjectOutputStream(socket.getOutputStream());
					oo.writeObject(j);
				}catch (Exception e) {
					// TODO: handle exception
				}
			}		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
