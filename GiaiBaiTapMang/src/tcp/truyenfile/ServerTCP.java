package tcp.truyenfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;


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

			dos.writeUTF(data);
			dos.flush();
			socket.close();
			serverSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
