/*Designed and created by Sukhpreet Singh
*
*Dated: 10/07/2019
*A university project for subject Advance Programming Practices
*
*/
package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import database.Database;
import exceptionHandler.HandlerException;

/*
*
*A class for Player 1 i.e. you, who is playing the game. Once user click on "Player with AI", 
*class object will be created of this class and all the data from the front end will be extracted.
*
*
*/

//Remen=mber to close socket 		socket.close();
public class Player1 implements Serializable {
	public HashMap<String, ArrayList> c = new  HashMap<String, ArrayList> ();
	public HashMap<String, String> dir= new HashMap<String, String>();
	static public final HashMap<String, ArrayList> loadShips= new HashMap<String, ArrayList>();
	String modeLoad=null;
	Database db = new Database();
	int player1[][] = new int[10][10];
	Player2 p2;
	final int Carrier = 5;
	final int Battleship = 4;
	final int Cruiser = 3;
	final int Submarine = 3;
	final int Destroyer = 2;
	boolean first_cruise = true;
	boolean check_carrier = true;
	boolean check_Battleship = true;
	boolean check_Cruiser = true;
	boolean check_Submarine = true;
	boolean check_Destroyer = true;
	public  String player;

	ArrayList<Integer> ships = new ArrayList<Integer>();
	ArrayList<String> Carrier_cd = new ArrayList<String>();
	ArrayList<String> Battleship_cd = new ArrayList<String>();
	ArrayList<String> Cruiser_cd = new ArrayList<String>();
	ArrayList<String> Submarine_cd = new ArrayList<String>();
	ArrayList<String> Destroyer_cd = new ArrayList<String>();
	int counter = 0;
	public static  HashMap<String, ArrayList> coordinates = new HashMap<String, ArrayList>();
	public  HashMap<String, String> shipDirection = new HashMap<String, String>();
	public ArrayList<String> p1Hit = new ArrayList<String>();
	public ArrayList<String> p1Miss = new ArrayList<String>();
	public ArrayList<String> p1Distroy = new ArrayList<String>();
	HashMap sunked = new HashMap<String, Integer>();
	/* Networking Support Variables */

	public  static  Socket socket = null;
	private DataOutputStream out = null;
	static ServerSocket s = null;
	static InetAddress ip;
	static String myHost;
	static int port;
	static String enemyHost;
	static int enemyPort;

	/* Networking Support Variables ends */
	static volatile  String response ="";
	@SuppressWarnings("unchecked")
	public Player1() throws IOException

	{

		Carrier_cd = Database.coordinates.get("carrierImg");
		Battleship_cd = Database.coordinates.get("battleShipImg");
		Cruiser_cd = Database.coordinates.get("cruiserImg");
		Submarine_cd = Database.coordinates.get("submarineImg");
		Destroyer_cd = Database.coordinates.get("destroyerImg");
		initialize_board_and_ships();
		
		sunked.put("carrierImg", 0);
		sunked.put("battleShipImg", 0);
		sunked.put("cruiserImg", 0);
		sunked.put("submarineImg", 0);
		sunked.put("destroyerImg", 0);
		
		

	}

	public static Player1 getInstance() throws IOException {
		/* Networking Support */
		Player1 obj = new Player1();
		Player1.s = new ServerSocket(0);
		Player1.ip = InetAddress.getLocalHost();
		Player1.myHost = ip.getHostName();
		Player1.port = s.getLocalPort();
//		obj.registerSelf(myHost, port);

		return obj;
	}

	public String registerSelf(String name, int port,String mode) throws UnknownHostException, IOException {
		socket = new Socket(ip.getHostName(), Database.centeralRepoPort);
		out = new DataOutputStream(socket.getOutputStream());
		out.writeUTF("Register" + "#" + name + "#" + port + "#"+mode+"#" );
        return requestOpponent();
	}

	public String requestOpponent() {
		String opponent = "";
		boolean flag = true;
		try {
			DataInputStream in = new DataInputStream(socket.getInputStream());
			opponent = in.readUTF();
			System.out.println(opponent);
			if (opponent.equalsIgnoreCase("wait")) {
				Database.dice = false;
					Thread defender = new Thread(new PlayerDefender(socket,s));
					defender.start();

				return "wait";
			} else if (opponent.equalsIgnoreCase("invalid")) {
				return "invalid";
			} else if(opponent.equalsIgnoreCase("mismatch")){
				return "mismatch";
			}else {
				
				String[] details = opponent.split("#");
				System.out.println("enemyHost  " + details[0]);
				System.out.println("enemyPort  " + Integer.parseInt(details[1]));
				socket = new Socket(details[0], Integer.parseInt(details[1]));
				out = new DataOutputStream(socket.getOutputStream());

				String query = "Challenger#"+myHost + "#" + port + "#";
				out.writeUTF(query);
				Thread defender2 = new Thread(new PlayerDefender2(socket,s));
				defender2.start();
				
				return "challenge";
			}

		} catch (Exception e) {
			HandlerException.Handler("invalid");
			return "invalid";
		}

	}
	
	public String Attacker(int x, int y)throws IOException, InterruptedException {
		// TODO Auto-generated method stub
	
		out = new DataOutputStream(socket.getOutputStream());
		out.writeUTF("Attack" + "#" + x + "#" + y + "#");
		System.out.println("message sent on"+ socket);
//		DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
       while(response.equalsIgnoreCase("")) {
    	   Thread.sleep(500);
    	   
       }
       String copyResponse = response;
       response = new String("");
	return copyResponse;
}

	public void set_player2_object(Player2 p2) {
		this.p2 = p2;
	}

	public void set_coordinates(int length, String coodindates) {
		if (length == 5)
			Carrier_cd.add(coodindates);
		else if (length == 4)
			Battleship_cd.add(coodindates);
		else if (length == 3 && first_cruise) {
			Cruiser_cd.add(coodindates);
		} else if (length == 3)
			Submarine_cd.add(coodindates);
		else if (length == 2)
			Destroyer_cd.add(coodindates);

	}

	public String get_coordinates(String coodindates) {

		String response = "";

		if (Carrier_cd.contains(coodindates))
			Carrier_cd.remove(coodindates);
		else if (Battleship_cd.contains(coodindates))
			Battleship_cd.remove(coodindates);
		else if (Cruiser_cd.contains(coodindates))
			Cruiser_cd.remove(coodindates);
		else if (Submarine_cd.contains(coodindates))
			Submarine_cd.remove(coodindates);
		else if (Destroyer_cd.contains(coodindates))
			Destroyer_cd.remove(coodindates);

		if (Carrier_cd.isEmpty() && check_carrier) {
			check_carrier = false;
			counter = counter + 1;
			response = "carrier#c#";

		}

		else if (Battleship_cd.isEmpty() && check_Battleship) {
			check_Battleship = false;
			counter = counter + 1;
			response = "battleship#c#";
		}

		else if (Cruiser_cd.isEmpty() && check_Cruiser) {
			check_Cruiser = false;
			counter = counter + 1;
			response = "cruiser#c#";
		}

		else if (Submarine_cd.isEmpty() && check_Submarine) {
			check_Submarine = false;
			counter = counter + 1;
			response = "submarine#c#";
		}

		else if (Destroyer_cd.isEmpty() && check_Destroyer) {
			check_Destroyer = false;
			counter = counter + 1;
			response = "destroyer#c#";
		}

		if (counter == 5) {
			response = response.split("#")[0];
			return response + "#" + "win#";
		} else {
			return response;
		}

	}

	public String initialize_board_and_ships() {
		ships.add(Carrier);
		ships.add(Battleship);
		ships.add(Cruiser);
		ships.add(Submarine);
		ships.add(Destroyer);

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {

				player1[i][j] = 0;
			}
		}
		return " ";
	}
//hashSet  attackedCo
	//attac.add(x+""+y);
//	public BattleControllerHelper bch = BattleControllerHelper.getInstanceOfBCH();
	public String attack_received(int x, int y) {

		if (!Database.set.contains(x + "" + y)) {
			p1Miss.add(x + "" + y);
			return "miss#c#" + x + "#" + y + "#";
		} else if (Database.set.contains(x + "" + y)) {
			String msg = get_coordinates(x + "" + y);
			if (msg.equalsIgnoreCase("")) {
				p1Hit.add(x + "" + y);
//				Database.BattleControllerReference.updateFrontEnd(x, y);
				return "hit#c#" + x + "#" + y + "#";}
			else {
				p1Distroy.add(x + "" + y);
				return msg + x + "#" + y + "#";
			}
		} else {
			return "invalid point";
		}

	}

	public String attack(int x, int y, String Opponent) throws IOException, InterruptedException {
		if(Opponent.equalsIgnoreCase("Computer")) {
			return p2.attack_received(x, y);	
		}else if(Opponent.equalsIgnoreCase("Player")) {
			System.out.println("I m here");
			try {
				return Attacker(x, y);
			} catch (Exception e) {
				HandlerException.Handler("invalid");
				// TODO Auto-generated catch block
//				e.printStackTrace();
				return "invalid";
			}
		}else {
			return "invalid";
				
		}
		
	}

	public void sendMessage(String message) throws IOException {
		out = new DataOutputStream(socket.getOutputStream());
		out.writeUTF("Game Over");
		socket.close();
	}
	
	public static void setSocket(Socket soc) {
		socket = soc;
	}
	public HashSet returnSet() {
		return Database.set;
	}

	public HashMap returnCoordinates() {
		return Database.coordinates;
	}

	public HashMap returnShipDirection() {
		return Database.shipDirection;
	}


}
