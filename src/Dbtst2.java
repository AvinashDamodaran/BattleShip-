package database;

import static org.junit.Assert.*;


import org.junit.Test;

/**
 * 
 * This test class covers the methods related to validation of placing the different ships by the drag and drop procedure.
 *
 */

public class Dbtst2 {

	
	
	@Test
	public void t2_validateDrag()
	{
		Database db = new Database();
	//	db.addCoordinates("Destroyer", 2, "33", 12, 12);
		db.loadShipsData();
		String[] a = {"2","2"};
		
		assertTrue(db.validateDrag(a, "+y", "destroyerImg"));
	}
	
	@Test
	public void t3_validateDrag()
	{
		Database db = new Database();
		
		db.loadShipsData();
		String[] a = {"4","4"};
		
		assertFalse(db.validateDrag(a, "+y", "submarineImg"));
	}
	
	
	@Test
	public void t4_validateDrag()
	{
		Database db = new Database();
		
		db.loadShipsData();
		String[] a = {"5","5"};
		
		assertFalse(db.validateDrag(a, "+y", "battleShipImg"));
	}
	
	@Test
	public void t5_validateDrag()
	{
		Database db = new Database();
		
		db.loadShipsData();
		String[] a = {"5","5"};
		
		assertFalse(db.validateDrag(a, "+y", "carrierImg"));
	}
	
	@Test
	public void t6_validateDrag()
	{
		Database db = new Database();
		
		db.loadShipsData();
		String[] a = {"5","5"};
		
		assertFalse(db.validateDrag(a, "+y", "cruiserImg"));
	}
	
	
	@Test
	public void validation()
	{
		Database db = new Database();
		db.addCoordinates("battleShip", 4, "55", 9, 9);
		assertTrue(db.validateCoordinates("battleShip", 4, 5, 4, 9, 9));
	}
	
	@Test
	public void t7_validateDrag()
	{
		Database db = new Database();
		
		db.loadShipsData();
		String[] a = {"5","5"};
		
		assertFalse(db.validateDrag(a, "+x", "battleShipImg"));
	}
	
	@Test
	public void t8_validateDrag()
	{
		Database db = new Database();
		
		db.loadShipsData();
		String[] a = {"0","0"};
		
		assertFalse(db.validateDrag(a, "-x", "battleShipImg"));
	}
	
	@Test
	public void t9_validateDrag()
	{
		Database db = new Database();
		
		db.loadShipsData();
		String[] a = {"5","5"};
		
		assertTrue(db.validateDrag(a, "-y", "battleShipImg"));
	}
	
	@Test
	public void t10_validateDrag()
	{
		Database db = new Database();
		
		db.loadShipsData();
		String[] a = {"4","0"};
		
		assertTrue(db.validateDrag(a, "-y", "submarineImg"));
	}
	
	@Test
	public void t11_validateDrag()
	{
		Database db = new Database();
		
		db.loadShipsData();
		String[] a = {"4","0"};
		
		assertFalse(db.validateDrag(a, "-x", "submarineImg"));
	}
	
	@Test
	public void t12_validateDrag()
	{
		Database db = new Database();
		
		db.loadShipsData();
		String[] a = {"4","9"};
		
		assertFalse(db.validateDrag(a, "+x", "submarineImg"));
	}


}
