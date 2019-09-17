package tcp.luufile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class ServerTCP {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(1234);
			System.out.println("Server is running at port 1234");
			System.out.println("Server started");
			Socket socket = serverSocket.accept();
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

			String message = "";
			message = dis.readUTF();
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

			dos.writeUTF(data);
			dos.flush();
			socket.close();
			serverSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
