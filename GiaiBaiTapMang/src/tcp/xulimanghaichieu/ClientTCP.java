package tcp.xulimanghaichieu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCP {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 1234);
			System.out.println("Client started");
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			Scanner sc = new Scanner(System.in);

			int row, col;
			System.out.println("Enter row: ");
			row = sc.nextInt();
			System.out.println("Enter column: ");
			col = sc.nextInt();

			String data = "";
			data = Integer.toString(row) + " " + Integer.toString(col) + " " + inputElement(row, col);
			
			dos.writeUTF(data);
			dos.flush();

			String result = "";
			result = dis.readUTF();
			System.out.println("Server said: " + result);
			sc.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
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
