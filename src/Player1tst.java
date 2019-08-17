package controller;

import static org.junit.Assert.*;


import java.io.IOException;

import org.junit.Test;

import database.Database;

/**
 * 
 * This test classs covers the following functionalities:
 * 1- preparing the board and also providing the initial values.
 * 2- all the related Attacking procedures.
 * 3- configuring the specific networking configuration.
 *
 */
public class Player1tst {

	@Test
	public void player_initialize_board_and_ships() throws IOException
	{
		Player1 p = new Player1() ;
		assertEquals(" ",p.initialize_board_and_ships());
	}
	
	@Test
	public void player_attack() throws IOException
	{
		Database db = new Database();
		Player1 p = new Player1() ;
		assertEquals("miss#c#0#4#",p.attack_received(0, 4));
	}
	
	@Test
	public void player_attack2() throws IOException
	{
		Database db = new Database();
		Player1 p = new Player1() ;
		assertEquals("miss#c#9#9#",p.attack_received(9, 9));
	}
	
	///////////////////////////////////
	
	
	
	@Test
	public void player_attack_refactored() throws IOException
	{
		Database db = new Database();
		Player1 p = new Player1() ;
		assertNotEquals("miss#c#0#0#",p.attack_received(0, 11));
	}
	
	
	@Test
	public void player_attack_refactored_v2() throws IOException
	{
		Database db = new Database();
		Player1 p = new Player1() ;
		assertNotEquals("miss#c#3#5#",p.attack_received(9, 9));
	}
	
	@Test
	public void Registering() throws IOException
	{
		Database db = new Database();
		Player1 p = new Player1() ;
		p.getInstance();
		assertEquals("wait",p.registerSelf("t", 60000, "regular"));
	}
	
	@Test
	public void attack() throws IOException, InterruptedException
	{
		Database db = new Database();
		Player1 p = new Player1() ;
		Player2 p2 = new Player2() ;
		p.set_player2_object(p2);
		p2.initialize_board_and_ships();
		p2.setup_board_for_AI();
		p.getInstance();
		assertEquals("miss#c#9#9#",p.attack(9, 9, "computer"));
	}
	
	@Test
	public void getInstance() throws IOException
	{
		Database db = new Database();
		Player1 p = new Player1() ;
		
		assertNotSame(p,p.getInstance());
	}

	

}
