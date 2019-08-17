package controller;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import database.Database;

public class PlayerDefender extends Player1 implements Runnable{

	private Socket socket = null;
	private DataOutputStream out = null;
	static ServerSocket s = null;
	static InetAddress ip;
	static String myHost;
	static int port;
	static String enemyHost;
	static int enemyPort;
	DataInputStream in = null;
	Player1 p1 = new Player1();
	public PlayerDefender(Socket socket, ServerSocket s) throws IOException {
		this.socket = socket;
		this.s = s;
        in = new DataInputStream(socket.getInputStream());
	}
	
	public void sendResponse(String message) throws IOException {
		out = new DataOutputStream(socket.getOutputStream());
		out.writeUTF(message);
		if(message.split("#")[1].equalsIgnoreCase("win")) {
			socket.close();
		}

	}
	@Override
	public void run() {
		try {
			
			socket = s.accept();
			Player1.socket = socket;
			System.out.println(socket);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// TODO Auto-generated method stub
		while(true) {
			try {
			
				in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				String line = in.readUTF();
				System.out.println("Message in defender "+line);
				if(!line.split("#")[0].equalsIgnoreCase("Attack") && !line.split("#")[0].equalsIgnoreCase("Challenger")) {
				synchronized(this){
					response = line;
					
				}
				}else {
					Operations(line);	
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
 
	public void Operations(String line) throws IOException {

		String[] details = line.split("#");

		switch (details[0]) {

		case "Challenger":
			String[] enemyDetails = line.split("#");
			enemyHost = enemyDetails[1];
			Database.dice = false;
			enemyPort = Integer.parseInt(enemyDetails[2]);
			System.out.println("enemyHost  " + enemyHost);
			System.out.println("enemyPort  " + enemyPort);
			
			break;

		case "Attack":
			System.out.println(line);
			Database.dice = true;
			sendResponse(attack_received(Integer.parseInt(details[1]),Integer.parseInt(details[2])));
			break;
		case "Game Over":
			socket.close();
			break;
			
		}

	}
}
