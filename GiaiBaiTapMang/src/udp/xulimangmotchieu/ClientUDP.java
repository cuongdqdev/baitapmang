package udp.xulimangmotchieu;

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

			int[] arrayNumber;
			int length = 0;
			System.out.println("Enter length of array: ");
			length = sc.nextInt();
			arrayNumber = new int[length];
			createArray(arrayNumber, length);

			String data = "";
			for (int i = 0; i < arrayNumber.length; i++) {
				data += arrayNumber[i] + " ";
			}

			data = data.trim();

			byte[] dataString = data.getBytes();
			DatagramPacket dp = new DatagramPacket(dataString, dataString.length, server, SERVER_PORT);
			ds.send(dp);

			DatagramPacket incoming = new DatagramPacket(BUFFER, BUFFER.length);
			ds.receive(incoming);
			System.out.println("Server said: \n" + new String(incoming.getData(), 0, incoming.getLength()));

			sc.close();

		} catch (IOException e) {
			System.err.println(e);
		} finally {
			if (ds != null) {
				ds.close();
			}
		}
	}

	public static void createArray(int array[], int length) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Create an array of number");
		for (int i = 0; i < length; i++) {
			array[i] = sc.nextInt();
		}
		sc.close();
	}
}
