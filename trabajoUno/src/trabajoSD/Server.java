package trabajoSD;
//token del trabajo
//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
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;


public class Server {	
	public Server(int puerto) {
		ServerSocket ss;
		try {
			ss = new ServerSocket(8080);
			Mesa m = new Mesa();
			Scanner sc = new Scanner(System.in); 
			m.empiezaJuego(1);
				try{
					Socket s=ss.accept();
					ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
					ObjectInputStream inp = new ObjectInputStream(s.getInputStream());
					//DataInputStream inp = new DataInputStream(s.getInputStream());
					while(true) {
						out.writeObject(m);
						out.reset();
						
						try {
							m = (Mesa) inp.readObject();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						//int opc = inp.readInt();
						//m.jugar_1Carta(opc);
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}				
	}
	
	public static void main(String[] args) {
		Server s = new Server(8080);
}
}
