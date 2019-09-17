
package udp.xulimangmotchieu;

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

			String[] tempArray = message.split(" ");
			int length = tempArray.length;
			int[] arrayNumer = new int[length];

			for (int i = 0; i < tempArray.length; i++) {
				arrayNumer[i] = Integer.parseInt(tempArray[i]);
			}

			sortAscendingArray(arrayNumer, length);

			for (int i = 0; i < arrayNumer.length; i++) {
				System.out.println(arrayNumer[i]);
			}

			String data = "\nArray before sort: " + message + "\nArray after sort: ";
			for (int i = 0; i < arrayNumer.length; i++) {
				data += arrayNumer[i] + " ";
			}

			data = data.trim();

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

	public static void sortAscendingArray(int arrayNumber[], int length) {
		int tempSort = 0;

		for (int i = 0; i < length - 1; i++) {
			for (int j = length - 1; j >= 1; j--) {
				if (arrayNumber[j] < arrayNumber[j - 1]) {
					tempSort = arrayNumber[j];
					arrayNumber[j] = arrayNumber[j - 1];
					arrayNumber[j - 1] = tempSort;
				}
			}
		}
	}
}
