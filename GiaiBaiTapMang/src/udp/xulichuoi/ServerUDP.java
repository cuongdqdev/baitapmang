
package udp.xulichuoi;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerUDP {

	public final static int SERVER_PORT = 1234;
	public final static byte[] BUFFER = new byte[4096];

	public static void main(String args[]) {
		DatagramSocket ds = null;
		try {
			ds = new DatagramSocket(SERVER_PORT);
			System.out.println("Server started ");

			DatagramPacket incoming = new DatagramPacket(BUFFER, BUFFER.length);
			ds.receive(incoming);

			String message = new String(incoming.getData(), 0, incoming.getLength());
			System.out.println("Client said: " + message);

			String data = handleString(message);

			System.out.println("Result: " + data);

			DatagramPacket outsending = new DatagramPacket(data.getBytes(), data.length(), incoming.getAddress(),
					incoming.getPort());
			ds.send(outsending);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ds != null) {
				ds.close();
			}
		}

	}

	public static String handleString(String inputString) {

		String handledString = inputString;
		handledString = handledString.trim().replaceAll("[^a-zA-Z]", " ").replaceAll("\\s+", " ");

		String tempString[] = handledString.split(" ");

		handledString = "";

		for (int i = 0; i < tempString.length; i++) {
			handledString += String.valueOf(tempString[i].charAt(0)).toUpperCase() + tempString[i].substring(1);
			if (i < tempString.length - 1) {
				handledString += " ";
			}
		}
		return handledString;
	}
}
