
package udp.xulimanghaichieu;

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

			DatagramPacket outsending = new DatagramPacket(message.getBytes(), message.length(), incoming.getAddress(),
					incoming.getPort());
			ds.send(outsending);

			int row = 0, col = 0;
			String[] arr;
			arr = message.split(" ");

			row = Integer.parseInt(arr[0]);
			col = Integer.parseInt(arr[1]);

			arr = null;

			DatagramPacket incoming2 = new DatagramPacket(BUFFER, BUFFER.length);
			ds.receive(incoming2);
			String message2 = new String(incoming2.getData(), 0, incoming2.getLength());
			System.out.println("Client said: " + message2);

			int[][] matrix = new int[row][col];
			arr = message2.split(" ");
			int count = 0;
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					matrix[i][j] = Integer.parseInt(arr[count]);
					count++;
				}
			}

			System.out.println("============= Matrix recieve from Server =============");
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
			for (int i = 0; i < arr.length; i++) {
				if (checkPrime(Integer.parseInt(arr[i])) == true) {
					sum += Integer.parseInt(arr[i]);
					listPrimeNumber += arr[i] + " ";
				}
			}

			String calTotal = "Total of prime number " + listPrimeNumber.trim().replaceAll(" ", "+") + " = " + sum;
			String dataFinal = "\n" + location1 + "\n" + location2 + "\n" + calTotal;
			System.out.println(dataFinal);

			DatagramPacket outsending2 = new DatagramPacket(dataFinal.getBytes(), dataFinal.length(),
					incoming2.getAddress(), incoming2.getPort());
			ds.send(outsending2);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ds != null) {
				ds.close();
			}
		}

	}

	@SuppressWarnings("unused")
	public static boolean checkPrime(int number) {
		if (number == 0 || number == 1) {
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
