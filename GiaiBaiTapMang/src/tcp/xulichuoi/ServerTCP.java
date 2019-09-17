package tcp.xulichuoi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(1234);
			System.out.println("Server is running at port 1234");
			System.out.println("Server started");
			Socket socket = serverSocket.accept();
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			String inputString = "";
			inputString = dis.readUTF();
			System.out.println("Client said: " + inputString);
			String result = handleString(inputString);
			System.out.println("Result: " + result);
			dos.writeUTF(result);
			dos.flush();
			socket.close();
			serverSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
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
