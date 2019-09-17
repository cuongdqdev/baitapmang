package udp.xulichuoi;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClientUDP {
	public final static String SERVER_IP = "127.0.0.1";
	public final static int SERVER_PORT = 1234;
	public final static byte[] BUFFER = new byte[4096];

	public static void main(String args[]) throws Exception {

		DatagramSocket ds = null;
		try {
			ds = new DatagramSocket();
			System.out.println("Client started ");

			InetAddress server = InetAddress.getByName(SERVER_IP);

			Scanner sc = new Scanner(System.in);

			String strData = "";
			System.out.println("Enter a string: ");
			strData = sc.nextLine();

			byte[] dataString = strData.getBytes();
			DatagramPacket dp = new DatagramPacket(dataString, dataString.length, server, SERVER_PORT);
			ds.send(dp);

			DatagramPacket incoming = new DatagramPacket(BUFFER, BUFFER.length);
			ds.receive(incoming);
			System.out.println("Server said: " + new String(incoming.getData(), 0, incoming.getLength()));

			sc.close();

		} catch (IOException e) {
			System.err.println(e);
		} finally {
			if (ds != null) {
				ds.close();
			}
		}

	}
}
