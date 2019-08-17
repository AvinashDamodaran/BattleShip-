package database;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javafx.scene.Node;


/**
 * This test class the methods that are pertain to placing each kind of ship on board with their dedicated specifications.
 * 
 * 
 *
 */


public class Dbtst {

	@Test
	public void t_addCoordinates()
	{
		Database db = new Database();
	
		//assertEquals(true,db.addCoordinates("battleShip", 4, "44", 9, 9)); 
		assertEquals(false,db.addCoordinates("battleShip", 4, "11", 9, 9)); 
	}
	
	@Test
	public void t_addCoordinates2()
	{
		Database db2 = new Database();
		assertEquals(true,db2.addCoordinates("carrier", 5, "44", 12, 12));
	}
	
	@Test
	public void t_addCoordinates3()
	{
		Database db = new Database();
		assertEquals(true,db.addCoordinates("Cruiser", 3, "25", 12, 12));
	}
	
	
	@Test
	public void t_addCoordinates4()
	{
		Database db = new Database();
		assertEquals(true,db.addCoordinates("Submarine", 3, "74", 9, 9));
	}
	
	@Test
	public void t_addCoordinates5()
	{
		Database db = new Database();
		assertEquals(true,db.addCoordinates("Destroyer", 2, "84", 9, 9));
	}
	
	@Test
	public void t2_addCoordinates5()
	{
		Database db = new Database();
		assertTrue(db.addCoordinates("Destroyer2", 2, "94", 9, 9));
	}
	

	

}
