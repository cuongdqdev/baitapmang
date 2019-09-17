package udp.xulimanghaichieu;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClientUDP {
	public final static String SERVER_IP = "127.0.0.1";
	public final static int SERVER_PORT = 1234;
	public final static byte[] BUFFER = new byte[4096];

	public static void main(String args[]) throws Exception {

		DatagramSocket ds = null;
		try {
			ds = new DatagramSocket();
			System.out.println("Client started ");

			InetAddress server = InetAddress.getByName(SERVER_IP);

			Scanner sc = new Scanner(System.in);
			int row, col;
			System.out.println("Enter row: ");
			row = sc.nextInt();
			System.out.println("Enter column: ");
			col = sc.nextInt();

			String data;

			data = inputArray(row, col);

			byte[] dataString = data.getBytes();
			DatagramPacket dp = new DatagramPacket(dataString, dataString.length, server, SERVER_PORT);
			ds.send(dp);

			DatagramPacket incoming = new DatagramPacket(BUFFER, BUFFER.length);
			ds.receive(incoming);

			System.out.println("Server said: " + new String(incoming.getData(), 0, incoming.getLength()));

			String data2;
			data2 = inputElement(row, col);

			byte[] dataString2 = data2.getBytes();
			DatagramPacket dp2 = new DatagramPacket(dataString2, dataString2.length, server, SERVER_PORT);
			ds.send(dp2);

			DatagramPacket incoming2 = new DatagramPacket(BUFFER, BUFFER.length);
			ds.receive(incoming2);
			System.out.println("\nResult: " + new String(incoming2.getData(), 0, incoming2.getLength()));
			sc.close();

		} catch (IOException e) {
			System.err.println(e);
		} finally {
			if (ds != null) {
				ds.close();
			}
		}

	}

	public static String inputArray(int a, int b) {
		return (String.valueOf(a + " " + b));
	}

	public static String inputElement(int row, int col) {
		Scanner sc = new Scanner(System.in);
		int[][] matrix = new int[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				System.out.println("Enter element a[" + i + "][" + j + "]: ");
				matrix[i][j] = sc.nextInt();
			}
		}
		System.out.println("============= Matrix send to Server =============");
		String data = new String("");
		for (int i = 0; i < row; i++) {
			System.out.print("\n");
			for (int j = 0; j < col; j++) {
				System.out.print(matrix[i][j] + " ");
				data = data + String.valueOf(matrix[i][j]) + " ";
			}
		}
		sc.close();
		return data;
	}
}
