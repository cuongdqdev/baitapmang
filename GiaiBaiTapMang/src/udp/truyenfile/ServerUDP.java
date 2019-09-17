
package udp.truyenfile;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.file.Files;
import java.nio.file.Paths;

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

			String[] arrData = message.split(" ");
			String sourcePath = arrData[0];
			String sourDestination = arrData[1];
			String fileName = arrData[2];

			String beginPath = sourcePath + "//" + fileName;
			String finishPath = sourDestination + "//" + fileName;

			String data = "";

			if (Files.exists(Paths.get(beginPath)) && Files.exists(Paths.get(sourcePath))) {
				Files.move(Paths.get(beginPath), Paths.get(finishPath));
				data = "Transfer file success! Check at: " + finishPath;
			} else {
				data = "Fail to transfer. File is not exist at: " + beginPath;
			}
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

}
