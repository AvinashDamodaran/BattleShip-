package controller;

import static org.junit.Assert.*;


import org.junit.Test;
/**
 * 
 * This test classs covers the following functionalities:
 * 1- preparing the board and also providing the initial values for the AI.
 * 2- all the related Attacking procedures.
 * 3- generation of proper point values.
 * 4- validation of points.
 *
 */
public class Player2tst {

	@Test
	public void player_initialize_board_and_ships()
	{
		Player2 p = new Player2() ;
		assertEquals(" ",p.initialize_board_and_ships());
	}
	
	
	@Test
	public void setup_board_for_AI()
	{
		Player2 p = new Player2();
		p.initialize_board_and_ships();
		assertEquals(" ",p.setup_board_for_AI());
	}
	
	@Test
	public void attack_received()
	{
		Player2 p = new Player2();
		p.initialize_board_and_ships();
		p.setup_board_for_AI();
		
		assertEquals("miss#c#4#2#",p.attack_received(4, 2));
	}
	
	@Test
	public void attack_received_v2()
	{
		Player2 p = new Player2();
		p.initialize_board_and_ships();
		p.setup_board_for_AI();
		
		assertNotEquals("miss#c#8#2#",p.attack_received(4, 2));
	}
	
	
	
	@Test
	public void attack_received_v3()
	{
		Player2 p = new Player2();
		p.initialize_board_and_ships();
		p.setup_board_for_AI();
		
		assertEquals("miss#c#0#0#",p.attack_received(0, 0));
	}
	
	
	@Test
	public void get_coordinates1_eq()
	{
		Player2 p = new Player2();
		p.initialize_board_and_ships();
		p.setup_board_for_AI();
		assertEquals("",p.get_coordinates("75"));
	}

	@Test
	public void get_coordinates2_notNull()
	{
		Player2 p = new Player2();
		p.initialize_board_and_ships();
		p.setup_board_for_AI();
		assertNotNull(p.get_coordinates("77"));
	}
	
	@Test
	public void generate_random_location()
	{
		Player2 p = new Player2();
		p.initialize_board_and_ships();
		p.setup_board_for_AI();
		assertNotNull(p.generate_random_location());
	}

	


}
