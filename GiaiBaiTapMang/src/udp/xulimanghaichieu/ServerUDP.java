
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
			String dataFinal = "\n" + location1 + "\n" + location2 + "\n" + calTotal;
			System.out.println(dataFinal);

			DatagramPacket outsending = new DatagramPacket(dataFinal.getBytes(), dataFinal.length(),
					incoming.getAddress(), incoming.getPort());
			ds.send(outsending);

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
