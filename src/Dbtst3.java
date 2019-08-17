package database;

import static org.junit.Assert.*;

import org.junit.Test;

import javafx.scene.Node;


/**
 * 
 * This test classs is dedicated to check specific points and also nodes that should be validated as necessary in diffrent situations.
 *
 */
public class Dbtst3 {

	@Test
	public void t1_validateN()
	{
		Database db = new Database();
	
		db.loadShipsData();
		
		
		assertTrue(db.validateNeighbour(4, 2, 5,"+x"));
	}
	@Test
	public void t2_validateN()
	{
		Database db = new Database();
		
		db.loadShipsData();
		
		
		assertTrue(db.validateNeighbour(4, 2, 5,"-x"));
	}
	@Test
	public void t3_validateN()
	{
		Database db = new Database();
		
		db.loadShipsData();
		
		
		assertTrue(db.validateNeighbour(4, 2, 5,"+y"));
	}
	@Test
	public void t4_validateN()
	{
		Database db = new Database();
		
		db.loadShipsData();
		
		
		assertTrue(db.validateNeighbour(4, 2, 5,"-y"));
	}
	
	
	@Test
	public void t5_validateN()
	{
		Database db = new Database();
	
		//db.loadShipsData();
		
		db.validateNeighbour(0, 0, 5,"-x");
		assertEquals(true,db.validateNeighbour(0, 0, 5,"-x"));
	}

//////////////////////////////////////////
	
	
	@Test
	public void getNode()
	{
		Database db = new Database();
		Node n = null;
	db.addButtonToMap("t",n );
		//db.loadShipsData();
		
		
		assertNull(db.getNode("tnode"));
	}
	
	
	
	@Test
	public void getNode2()
	{
		Database db = new Database();
		Node n =null;
     	db.addButtonToMap("t2",n );
		//db.loadShipsData();
		
		
		assertEquals(null,db.getNode("t2"));
	}

	
}
