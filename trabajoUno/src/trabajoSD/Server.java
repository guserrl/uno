package trabajoSD;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
static List<Cliente> jugadores = new ArrayList<Cliente>();

public static void main(String[] args) {
	ServerSocket ss;
	try {
		ss = new ServerSocket(8080);
		Baraja b = new Baraja();
		b.reset();
		Juego j = new Juego(b);
		//Opcion para escoger los jugadores
		while(true) {
			while(jugadores.size()<2) {
				Socket s=ss.accept();			
				jugadores.add(new Cliente(s));
				j.empiezaJuego(jugadores.size());
			}
			try (Socket s = jugadores.get(j.turnoJuego()).socketCliente();
					ObjectOutputStream oO = new ObjectOutputStream(s.getOutputStream());
					ObjectInputStream dI = new ObjectInputStream(s.getInputStream());){								
					oO.writeObject(j);				
					j= (Juego) dI.readObject();
			}						
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

}
