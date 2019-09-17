package tcp.xulimangmotchieu;

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

			String message = "";
			message = dis.readUTF();
			System.out.println("Recieve string from Client: " + message);
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

			String result = "\nArray before sort: " + message + "\nArray after sort: ";
			for (int i = 0; i < arrayNumer.length; i++) {
				result += arrayNumer[i] + " ";
			}

			result = result.trim();

			dos.writeUTF(result);
			dos.flush();
			socket.close();
			serverSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
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
