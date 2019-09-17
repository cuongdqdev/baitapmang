package tcp.xulimanghaichieu;

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
			System.out.println("Client said: " + message);

			String[] arrStr = message.split(" ");
			int row = Integer.parseInt(arrStr[0]);
			int col = Integer.parseInt(arrStr[1]);

			int[][] matrix = new int[row][col];
			
			int count = 2;
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					matrix[i][j] = Integer.parseInt(arrStr[count]);
					count++;
				}
			}

			System.out.println("============= Matrix recieve from Client =============");
			for (int i = 0; i < row; i++) {
				System.out.print("\n");
				for (int j = 0; j < col; j++) {
					System.out.print("a[" + i + "][" + j + "] = " + matrix[i][j] + "   ");
				}
			}

			int max1 = 0, max2 = 0;
			String location1 = "", location2 = "";

			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					if (max1 < matrix[i][j]) { // <
						max1 = matrix[i][j];
						location1 = String.valueOf("The first max: " + max1 + " at [" + i + "][" + j + "]" + "\n");
					} else if (max1 == matrix[i][j]) {
						location1 += "\n"
								+ String.valueOf("The first max: " + max1 + " at [" + i + "][" + j + "]" + "\n");
					}

				}
			}
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					if (max2 < matrix[i][j] && matrix[i][j] < max1) { // <
						max2 = matrix[i][j];
						location2 = String.valueOf("The second max: " + max2 + " at [" + i + "][" + j + "]" + "\n");
					} else if (max2 == matrix[i][j]) {
						location2 += "\n"
								+ String.valueOf("The second max: " + max2 + " at [" + i + "][" + j + "]" + "\n");
					}
				}
			}

			int sum = 0;
			String listPrimeNumber = "";
			for (int i = 2; i < arrStr.length; i++) {
				if (checkPrime(Integer.parseInt(arrStr[i])) == true) {
					sum += Integer.parseInt(arrStr[i]);
					listPrimeNumber += arrStr[i] + " ";
				}
			}

			String calTotal = "Total of prime number " + listPrimeNumber.trim().replaceAll(" ", "+") + " = " + sum;
			String data = "\n" + location1 + "\n" + location2 + "\n" + calTotal;
			System.out.println(data);

			dos.writeUTF(data);
			dos.flush();
			socket.close();
			serverSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	public static boolean checkPrime(int number) {
		if (number == 0 || number == 1) {
			return false;
		}
		if (number == 2) {
			return true;
		}
		for (int i = 2; i < number; i++) {
			if (number % i == 0) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
}
