/*Designed and created by Sukhpreet Singh
*
*Dated: 10/07/2019
*A university project for subject Advance Programming Practices
*
*/
package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

import database.Database;
import java.io.Serializable;

/**
 * The AI class serving as player 2 in game.This class handles the action i.e. attack on the AI and the validating the opponent move.
 * @author admin
 *
 */
public class Player2 implements Serializable{
	
	int player2[][]=new int[10][10];
	Player1 p1;
	final int Carrier=5;	
	final int Battleship=4;
	final int Cruiser=3;
	final int Submarine=3;
	final int Destroyer=2;
	int last_x;
	int last_y;
	int last_hit_x;
	int last_hit_y;
	boolean first_cruise=true;
	boolean check_carrier=true;
	boolean check_Battleship=true;
	boolean check_Cruiser=true;
	boolean check_Submarine=true;
	boolean check_Destroyer=true;
	boolean check_neighbour=false;
	boolean checkup=true;
	boolean checkdown=true;
	boolean checkleft=true;
	boolean checkright=true;
	ArrayList<Integer> ships=new ArrayList<Integer>();	
	ArrayList<String> Carrier_cd=new ArrayList<String>();	
	ArrayList<String> Battleship_cd=new ArrayList<String>();	
	ArrayList<String> Cruiser_cd=new ArrayList<String>();	
	ArrayList<String> Submarine_cd=new ArrayList<String>();	
	ArrayList<String> Destroyer_cd=new ArrayList<String>();	
	HashSet attackedPositions = new HashSet<String>();
	HashMap sunked = new HashMap<String, Integer>();
	int counter =0;
	public Player2() {
		initialize_board_and_ships();
		setup_board_for_AI();
		sunked.put("destroyer", 0);
		sunked.put("carrier", 0);
		sunked.put("submarine", 0);
		sunked.put("cruiser", 0);
		sunked.put("battleship", 0);
	}
	public void set_player1_object(Player1 p1) {
		this.p1=p1;
	}
	
	public void set_coordinates(int length, String coodindates) {
		if (length==5)
			Carrier_cd.add(coodindates);
		else if (length==4)
			Battleship_cd.add(coodindates);
		else if (length==3 && first_cruise) {
			Cruiser_cd.add(coodindates);
		}
		else if (length==3)
			Submarine_cd.add(coodindates);
		else if (length ==2)
			Destroyer_cd.add(coodindates);
			
	}
	
	public String get_coordinates(String coodindates) {
		
		String response ="";
		
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

		if (Carrier_cd.isEmpty()&& check_carrier) {
			check_carrier=false;
			counter++;
			response = "carrier#c#";
//			return "carrier";
		}
			
		else if(Battleship_cd.isEmpty() && check_Battleship) {
			check_Battleship=false;
			counter++;
			response = "battleship#c#";
		}
			
		else if(Cruiser_cd.isEmpty() && check_Cruiser)
		{
			check_Cruiser=false;
			counter++;
			response = "cruiser#c#";
		}
			
		else if(Submarine_cd.isEmpty()&& check_Submarine) {
			check_Submarine=false;
			counter++;
			response = "submarine#c#";
		}
			
		else if(Destroyer_cd.isEmpty()&& check_Destroyer) {
			check_Destroyer=false;
			counter++;
			response = "destroyer#c#";
		}
		
			if(counter == 5) {
				response= response.split("#")[0];
				return response+"#"+"win#";
			}else {
				return response;
			}

		
	}
	
	public String initialize_board_and_ships() {
		ships.add(Carrier);
		ships.add(Battleship);
		ships.add(Cruiser);
		ships.add(Submarine);
		ships.add(Destroyer);
		
		
		
		for (int i=0;i<10;i++)
		{
			for (int j=0;j<10;j++) {
				player2[i][j]=0;
			}
		}
		return " ";
	}
	
	public String setup_board_for_AI() {

		while(true) {
//		System.out.println("shshhshs"+ships.size());
		int x = ThreadLocalRandom.current().nextInt(0, 10);
		int y = ThreadLocalRandom.current().nextInt(0, 10);
		ships.get(0);
		int ship_idx = ThreadLocalRandom.current().nextInt(0, ships.size());
//		System.out.println( ship_idx );
		int ship_size = ships.get(ship_idx);
//		System.out.println( ship_size );
		boolean up = false;
		boolean down = false;
		boolean left = false;
		boolean right = false;
		boolean done =false;
		boolean process=false;
		try {
			int test = player2[x-ship_size][y]; //left 
			if (y!=9) {
				if (player2[x][y+1]==1 ) {
					left=true;
				}
			}
			if (x!=9) {
				if (player2[x+1][y]==1)
					left=true;
			}
			if (y!=0) {
				if (player2[x][y-1]==1)
					left=true;
			}
			if((x-ship_size)!=0) {
				if (player2[(x-ship_size)-1][y]==1)
					left=true;
			}
			for (int i=x-ship_size;i<x;i++) {
				if (player2[i][y]==1)
				{
					left=true;
					break;
				}
				if ((y!=9 && player2[i][y+1]==1) || (y!=0 && player2[i][y-1]==1)) {
					left=true;
					break;
				}
				
			}
			if (left==false) {
				for (int i=x-ship_size;i<x;i++) {
					player2[i][y]=1;
					set_coordinates(ship_size,i+""+y);
					done=true;
				}
			}
			
		}
		catch(Exception e) {
		}
		try {
			int test=player2[x][y+ship_size]; //up
//			if (player2[x][y+ship_size+1]==1 || player2[x][y-1]==1 || player2[x+1][y]==1 || player2[x-1][y]==1) {
//				up=true;
//			}
			if (player2[x][y+ship_size]!=9) {
				if (player2[x][y+ship_size+1]==1)
					up=true;
			}
			if (y!=0) {
				if (player2[x][y-1]==1) {
					up=true;
				}
			}
			if (x!=9) {
				if (player2[x+1][y]==1)
					up=true;
			}
			if(x!=0) {
				if (player2[x-1][y]==1)
					up=true;
			}
			for (int i=y;i<y+ship_size;i++) {
				if (player2[x][i]==1)
				{
					up=true;
					break;
				}
				
				if((x!=9&&player2[x+1][i]==1) || (x!=0&&player2[x-1][i]==1)) {
					up=true;
					break;					
				}
			}
			if (up==false && done==false) {
				for (int i=y;i<y+ship_size;i++) {
					player2[x][i]=1;
					set_coordinates(ship_size,x+""+i);
					process=true;
				}
			}
			if (process==true && up==false && done==false) {
				done=true;
			}
		}
		catch(Exception e1) {
		}
		try {
			int test=player2[x+ship_size][y]; //right
//			if (player2[x-1][y]==1 || player2[(x+ship_size)+1][y]==1 || player2[x][y+1]==1 ||player2[x][y-1]==1) {
//				right=true;
//			}
			if (x!=0) {
				if (player2[x-1][y]==1)
					right=true;
			}
			if (x+ship_size!=9) {
				if(player2[(x+ship_size)+1][y]==1)
					right=true;
			}
			if (y!=9) {
				if(player2[x][y+1]==1)
					right=true;
			}
			if (y!=0) {
				if(player2[x][y-1]==1)
					right=true;
			}
			for (int i=x;i<x+ship_size;i++) {
				if (player2[i][y]==1)
				{
					right=true;
					break;
				}
				if ((y!=9 && player2[i][y+1]==1) || (y!=0 && player2[i][y-1]==1)) {
					right=true;
					break;
				}

			}
			
			if (right==false && done==false) {
				for (int i=x;i<x+ship_size;i++) {
					player2[i][y]=1;
					set_coordinates(ship_size,i+""+y);
					process=true;
				}
			}
			if (process==true && right==false && done==false) {
				done=true;
			}

		}
		
		catch(Exception e2) {
		}

		try {
			int test=player2[x][y-ship_size]; //down
//			if (player2[x][(y-ship_size)-1]==1 || player2[x][y+1]==1 || player2[x+1][y]==1 || player2[x-1][y]==1) {
//				down=true;
//			}
			if ((y-ship_size)!=0) {
				if (player2[x][(y-ship_size)-1]==1)
					down=true;
			}
			if (y!=9) {
				if (player2[x][y+1]==1)
					down=true;
			}
			if (x!=9) {
				if (player2[x+1][y]==1){
					down=true;
				}
			}
			if (x!=0) {
				if(player2[x-1][y]==1) {
					down=true;
				}
			}
			for (int i=y-ship_size;i<y;i++) {
				if (player2[x][i]==1)
				{
					down=true;
					break;
				}
				if((x!=9&&player2[x+1][i]==1) || (x!=0&&player2[x-1][i]==1)) {
					down=true;
					break;					
				}

			}
			if (down==false && done==false) {
				for (int i=y-ship_size;i<y;i++) {
					player2[x][i]=1;
					set_coordinates(ship_size,x+""+i);
					process=true;
				}
			}
			if (process==true && down==false && done==false) {
				done=true;
			}

		}
		catch(Exception e3) {
			
		}

			
			 for (int i=0;i<10;i++) { for (int j=0;j<10;j++) { //
			 System.out.print(player2[i][j]+" "); } System.out.println(); }
			 


		if (done==true) {
			ships.remove(ship_idx);
		}
		if(ships.isEmpty()) {
			break;
		}
		
		if(ship_size==3 && first_cruise) {
			first_cruise=false;
		}
		
		}
		 
		return " ";
	}
	
	public void check_hit_ship() {
		if(checkup) {
			if (last_y==9) {
				checkup=false;
			}
			else {
				last_y=last_y+1;
			}	
		}
		else if (checkdown) {
			if (last_y==0) {
				checkdown=false;
			}
			else {
				last_y=last_y-1;
			}		
		}
		else if (checkright) {
			if (last_x==9) {
				checkright=false;
			}
			else {
				last_x=last_x+1;
			}
		}
		else if(checkleft) {
			if (last_x==0) {
				checkleft=false;
			}
			else {
				last_x=last_x-1;
			}		
		}
	}
	
	public String generate_random_location() {
		int x=ThreadLocalRandom.current().nextInt(0, 10);
		int y=ThreadLocalRandom.current().nextInt(0, 10);
		if(!attackedPositions.contains(x+""+y)) {
			return x+"#"+y;
		}
		else {
			return generate_random_location();
		}
	}
	
	public String attack_AI() {
		if (!Database.dice) {
			int x=0;
			int y=0;
			if(check_neighbour) {
				check_hit_ship();
				x=last_x;
				y=last_y;
			}else {
//				x = ThreadLocalRandom.current().nextInt(0, 10);
//				y = ThreadLocalRandom.current().nextInt(0, 10);
				String xy=generate_random_location();
				x =Integer.parseInt(xy.substring(0, 1));
				y= Integer.parseInt(xy.substring(2, 3));
			}
//			System.out.println("Enemy hitting on loc: "+x+"--"+y);
			
			if(!attackedPositions.contains(x+""+y)) {
				
				attackedPositions.add(x+""+y);
				String msg = p1.attack_received(x, y);
				if (msg.equalsIgnoreCase("invalid point")) {
					if (check_neighbour) {
						if(checkup) {
							checkup=false;
							//last_y=last_y-1;
							last_y=last_hit_y;
						}
						else if (checkdown) {
							checkdown=false;
							last_y=last_hit_y;
						}
						else if (checkright) {
							checkright=false;	
							last_x=last_hit_x;
						}
						else if (checkleft) {
							checkleft=false;
							last_x=last_hit_x;
						}
					}
					return attack_AI();
				} else {
					Database.dice =true;
					if (msg.substring(0, 4).equalsIgnoreCase("miss")) {
						if (check_neighbour) {
							if(checkup) {
								checkup=false;
								//last_y=last_y-1;
								last_y=last_hit_y;
							}
							else if (checkdown) {
								checkdown=false;
								last_y=last_hit_y;
							}
							else if (checkright) {
								checkright=false;
								last_x=last_hit_x;
							}
							else if (checkleft) {
								checkleft=false;
								last_x=last_hit_x;
							}
						}
					}
					else if (msg.substring(0, 3).equalsIgnoreCase("hit")) {
						last_hit_x=x;
						last_hit_y=y;
						check_neighbour=true;
						last_x=x;
						last_y=y;
					}
					else {
						check_neighbour=false;
						checkup=true;
						checkdown=true;
						checkright=true;
						checkup=true;
					}
					return msg;
				}
			}else {
				return attack_AI();
			}
			 
		}else {
			return null;
		}
	}
	public String attack_received(int x,int y) {
		
		if (player2[x][y]==0) {
			player2[x][y]=-1;
			return "miss#c#"+x+"#"+y+"#";
			
		}
		else if (player2[x][y]==1) {
			player2[x][y]=2;
			String msg=get_coordinates(x+""+y);
			if (msg.equalsIgnoreCase(""))
				return "hit#c#"+x+"#"+y+"#";
			else {
				return msg+x+"#"+y+"#";
			}
		}
		else {
			return "invalid point";
		}

		
	}
	
	public String attack(int x,int y) {
		return p1.attack_received(x, y);
	}



}
