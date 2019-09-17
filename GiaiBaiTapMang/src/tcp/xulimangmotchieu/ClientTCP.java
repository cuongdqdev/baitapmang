package tcp.xulimangmotchieu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCP {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost",1234);
			System.out.println("Client started");
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			Scanner sc = new Scanner(System.in);
			int[] arrayNumber;
			int length = 0;
			System.out.println("Enter length of array: ");
			length = sc.nextInt();
			arrayNumber = new int[length];
			createArray(arrayNumber, length);
			
			String message = "";
			for(int i = 0; i < arrayNumber.length; i++) {
				message += arrayNumber[i] + " ";
			}
			
			message = message.trim();
					
			dos.writeUTF(message);
			dos.flush();
			
			String result = "";
			result = dis.readUTF();
			System.out.println("Server said: \n" + result);
			sc.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void createArray(int array[], int length) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Create an array of number");
		for (int i = 0; i < length; i++) {
			array[i] = sc.nextInt();
		}
		sc.close();
	}

}
