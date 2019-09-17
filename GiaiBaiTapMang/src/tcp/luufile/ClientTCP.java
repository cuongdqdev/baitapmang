package tcp.luufile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCP {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 1234);
			System.out.println("Client started");
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			Scanner sc = new Scanner(System.in);

			String contentFile = "", sourceDestination = "";
			System.out.println("Enter content of file: ");
			contentFile = sc.nextLine();

			System.out.println("Enter source destination: ");
			sourceDestination = sc.nextLine();

			String data = contentFile + "-" + sourceDestination;

			dos.writeUTF(data);
			dos.flush();

			String result = "";
			result = dis.readUTF();
			System.out.println("Server said: " + result);
			sc.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
