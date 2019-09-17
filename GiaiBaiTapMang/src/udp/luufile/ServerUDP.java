
package udp.luufile;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

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

			String[] arrData = message.split("-");
			String contentFile = arrData[0];
			String sourDestination = arrData[1];

			String[] arrContent = contentFile.trim().split(" ");

			int amoutOfWord = arrContent.length;
			System.out.println("A mout of word: " + amoutOfWord);

			String theLengthestWord = "";
			int max = 0;
			for (int i = 0; i < arrContent.length; i++) {
				if (arrContent[i].length() > max) {
					max = arrContent[i].length();
					theLengthestWord = arrContent[i];
				} else if (arrContent[i].length() == max) {
					theLengthestWord += " " + arrContent[i];
				}
			}

			System.out.println("The lengthest word: " + theLengthestWord);

			Date date = new Date();

			String data = "";

			String fileLocation = sourDestination + "//" + date.getTime() + ".doc";

			if (Files.exists(Paths.get(sourDestination))) {
				data = "Content: " + contentFile + "\nThe lengthest word: " + theLengthestWord + "\nAmout of word: "
						+ amoutOfWord;
				try {
					Path path = Paths.get(fileLocation);
					Files.write(path, data.getBytes());
					data = "Save file success. Check at: " + fileLocation;
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				data = "Destination is not exist at: " + sourDestination;
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
