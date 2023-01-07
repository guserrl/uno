package trabajoSD;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Cliente{
		
	public static void main(String[] args) {
		try {
			Socket s = new Socket("localhost",8080);
			//DataOutputStream oo=new DataOutputStream(s.getOutputStream());
			ObjectOutputStream oo = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream oi=new ObjectInputStream(s.getInputStream());
			while(true) {
				try {
					Mesa m = (Mesa) oi.readObject();
					int opcion = m.cartaAJugar();
					m.jugar_1Carta(opcion);
					oo.writeObject(m);
					oo.reset();
					//oo.writeInt(opcion);
					//oo.flush();
					//System.out.println(carta.toString());
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				// TODO Auto-generated method stub
				
			}
	}