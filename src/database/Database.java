/*Designed and created by Pulkit Ghai
*
*Dated: 10/07/2019
*A university project for subject Advance Programming Practices
*
*/
package database;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import controller.BattleController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 *
 * A database class for storing all the objects and data of the front end.
 * DataStucture use : 1. HashMaps,2. Sets
 *
 *
 * coordinates store the ship location as on the grids example, {carrier=[21,
 * 31, 41, 51, 61], subMarine=[46, 47], battleShip=[54, 64, 74, 84],
 * cruiser=[97, 87, 77]} shipDirection<HashMap> store the direction of the ships
 * i.e. +x,-x,+y,-y for further validation set are the collection of all
 * coordinates
 * 
 * 
 */
public class Database implements Serializable {

	@SuppressWarnings("rawtypes")
	/*
	 * Database Used statically to make it singleton
	 */
	static public HashMap<String, ArrayList> coordinates = new HashMap<String, ArrayList>();
	static HashMap<String, Node> list_of_buttons = new HashMap<String, Node>();
	public static HashMap<String, String> shipDirection = new HashMap<String, String>();
	static public HashSet<String> set = new HashSet<String>();
	static HashMap<String, Integer> ShipsData = new HashMap<String, Integer>();
	public static boolean dice = true;
	public static String Mode = null;
	public static Socket socket = null;
	public static int centeralRepoPort = 8081;
	public static FXMLLoader fxmlLoader= null;
	public static BattleController BattleControllerReference = null;
	public void clearDatabse()

	{
		coordinates.clear();
		shipDirection.clear();
		dice = true;
		set.clear();
		ShipsData.clear();
		list_of_buttons.clear();
	}

	/*
	 * public boolean validateCoordinates(String ship, int shipLength, int x, int y,
	 * int maxWidth, int maxHeight)
	 * 
	 * {
	 * 
	 * if (coordinates.get(ship).size() == 1) { String[] xy = (String[]) ((String)
	 * coordinates.get(ship).get(0)).split(""); int x_dir = Integer.parseInt(xy[0]);
	 * int y_dir = Integer.parseInt(xy[1]); if (x == x_dir + 1 && y == y_dir &&
	 * (x_dir + shipLength) <= maxWidth && doValidateDirection(shipLength, x, y,
	 * "x_direction")) { shipDirection.put(ship, "+x");
	 * 
	 * return true; } else if (x == x_dir - 1 && y == y_dir && (x_dir - shipLength)
	 * >= 0 && doValidateDirection(shipLength, x, y, "neg-x_direction")) {
	 * shipDirection.put(ship, "-x");
	 * 
	 * return true; } else if (y == y_dir + 1 && x == x_dir && (y_dir + shipLength)
	 * <= maxHeight && doValidateDirection(shipLength, x, y, "y_direction")) {
	 * shipDirection.put(ship, "+y");
	 * 
	 * return true; } else if (y == y_dir - 1 && x == x_dir && (y_dir - shipLength)
	 * >= 0 && doValidateDirection(shipLength, x, y, "neg-y_direction")) {
	 * shipDirection.put(ship, "-y");
	 * 
	 * return true; } else { return false; } } else if (coordinates.get(ship).size()
	 * > 1) { String[] xy = (String[]) ((String)
	 * coordinates.get(ship).get(coordinates.get(ship).size() - 1)).split(""); int
	 * x_dir = Integer.parseInt(xy[0]); int y_dir = Integer.parseInt(xy[1]); switch
	 * (shipDirection.get(ship)) {
	 * 
	 * case "+x": if (x == x_dir + 1 && y == y_dir) { return true; } break; case
	 * "-x": if (x == x_dir - 1 && y == y_dir) { return true; } break; case "+y": if
	 * (x == x_dir && y == y_dir + 1) { return true; } break; case "-y": if (x ==
	 * x_dir && y == y_dir - 1) { return true; } break;
	 * 
	 * } return false;
	 * 
	 * }
	 * 
	 * return true; }
	 */

	/*
	 * private boolean doValidateDirection(int shipLength, int x, int y, String
	 * direction)
	 * 
	 * {
	 * 
	 * boolean returnVal = true; switch (direction) {
	 * 
	 * case "x_direction": for (int i = x; i < x + shipLength; i++) { if
	 * (set.contains(i + "" + y)) { returnVal = false; break; } } break; case
	 * "neg-x_direction": for (int i = x; i > x - shipLength; i--) { if
	 * (set.contains(i + "" + y)) { returnVal = false; break; } } break; case
	 * "y_direction": for (int i = y; i < y + shipLength; i++) {
	 * 
	 * if (set.contains(x + "" + i)) { returnVal = false; break; } } break; case
	 * "neg-y_direction": for (int i = y; i > y - shipLength; i--) { if
	 * (set.contains(i + "" + y)) { returnVal = false; break; } } break;
	 * 
	 * } // TODO Auto-generated method stub return returnVal; }
	 */
	/*
	 * public boolean addCoordinates(String ship, int shipLength, String
	 * coordinates, int maxWidth, int maxHeight)
	 * 
	 * { String[] xy = coordinates.split(""); int x = Integer.parseInt(xy[0]); int y
	 * = Integer.parseInt(xy[1]);
	 * 
	 * if (Database.coordinates.containsKey(ship)) {
	 * 
	 * if (validateCoordinates(ship, shipLength - 1, x, y, maxWidth, maxHeight)) {
	 * 
	 * @SuppressWarnings("unchecked") ArrayList<String> value =
	 * Database.coordinates.get(ship); value.add(coordinates); set.add(coordinates);
	 * Database.coordinates.put(ship, value); } else { return false; } } else {
	 * 
	 * if (doValidateFirst(x, y, shipLength)) { ArrayList<String> value = new
	 * ArrayList<>(); value.add(coordinates); set.add(coordinates);
	 * Database.coordinates.put(ship, value); } else { return false; } }
	 * 
	 * 
	 * return true; }
	 */

	/*
	 * @SuppressWarnings("unused") private boolean doValidateFirst(int x, int y, int
	 * shipLength)
	 * 
	 * {
	 * 
	 * // TODO Auto-generated method stub // case where ship location is occupied in
	 * // all four direction if (!set.contains((x + 1) + "" + y) && (x + 1) < 9) {
	 * boolean r = true; for (int i = x; i < x + shipLength; i++) { if
	 * (set.contains(i + "" + y)) { r = false; break; } return r; } } else if
	 * (!set.contains((x - 1) + "" + y) && (x - 1) > 0) { boolean r = true; for (int
	 * i = x; i > x - shipLength; i++) { if (set.contains(i + "" + y)) { r = false;
	 * break; } return r; } } else if (!set.contains(x + "" + (y + 1)) && (y + 1) <
	 * 9) { boolean r = true; for (int i = y; i < y + shipLength; i++) { if
	 * (set.contains(x + "" + i)) { r = false; break; } return r; } } else if
	 * (!set.contains(x + "" + (y - 1)) && (y - 1) > 0) { boolean r = true; for (int
	 * i = y; i > y - shipLength; i++) { if (set.contains(x + "" + i)) { r = false;
	 * break; } return r; } }
	 * 
	 * return false; }
	 */
	
	public void addButtonToMap(String text, Node node) {
		list_of_buttons.put(text, node);
	}

	public Node getNode(String text) {
		if (list_of_buttons.containsKey(text)) {
			return list_of_buttons.get(text);
		} else {
			return null;
		}
	}

	public boolean validateNeighbour(int x, int y, int length_of_ship, String direction) {
		boolean returnVal = true;
		// check for three neighbour of start point and end point
		// check for all neighbours

		System.out.println(set);
		System.out.println(x + " " + y);
		System.out.println(x + 1 + " " + y);
		System.out.println(x - 1 + " " + y);
		System.out.println(x + " " + (y + 1));
		System.out.println(x + " " + (y - 1));
		System.out.println(x + 1 + " " + (y + 1));
		System.out.println(x - 1 + " " + (y - 1));
		System.out.println(x + 1 + " " + (y - 1));
		System.out.println(x - 1 + " " + (y + 1));

		int x_ends = x + length_of_ship;
		int y_ends = y + length_of_ship;
		if (direction.equalsIgnoreCase("+x") || direction.equalsIgnoreCase("-x")) {
			for (int i = x; i <= x_ends; i++) {
				System.out.println("validating points: " + y + " " + i);
				if (set.contains(y + "" + i) || set.contains(y + "" + (i + 1)) || set.contains(y + "" + (i - 1))
						|| set.contains((y + 1) + "" + (i + 1)) || set.contains((y + 1) + "" + i)
						|| set.contains((y - 1) + "" + i) || set.contains((y - 1) + "" + (i + 1))
						|| set.contains((y - 1) + "" + (i - 1)) || set.contains((y + 1) + "" + (i - 1))) {

					returnVal = false;

					break;
				} else {
					System.out.println(i);
				}
			}
		} else {
			System.out.println(y_ends + "---this i ends");
			for (int i = y; i <= y_ends; i++) {
				System.out.println("validating points: " + i + " " + x);
				if (set.contains(i + "" + x) || set.contains((i + 1) + "" + x) || set.contains((i - 1) + "" + x)
						|| set.contains((i + 1) + "" + (x + 1)) || set.contains(i + "" + (x + 1))
						|| set.contains(i + "" + (x - 1)) || set.contains((i + 1) + "" + (x - 1))
						|| set.contains((i - 1) + "" + (x - 1)) || set.contains((i - 1) + "" + (x + 1))) {

					returnVal = false;

					break;
				} else {
					System.out.println(i);
				}
			}
		}

		return returnVal;
	}

	/**
	 * Validation of coordinate on the basis of below: 1.Check for validate of
	 * successor coordinate. Ship can be put. 2. No two ships can be adjacent, even
	 * diagonal coordinates. 3.OverLapping coordinates.
	 * 
	 * @param coordinates
	 * @param direction
	 * @return
	 */
	public boolean validateDrag(String[] coordinates, String direction, String ShipName) {

		int y_index = Integer.parseInt(coordinates[0].trim());
		int x_index = Integer.parseInt(coordinates[1].trim());
		System.out.println(x_index + " " + y_index);
		boolean returnVal = false;
		int length_of_ship = ShipsData.get(ShipName) - 1;
		System.out.println(length_of_ship);
//Validate Length with the available space
		switch (direction) {

		case "+x":
			if (x_index + length_of_ship <= 9) {
				System.out.println(
						"this is validation " + validateNeighbour(x_index, y_index, length_of_ship, direction));
				int x_ends = x_index + length_of_ship;
				if (validateNeighbour(x_index, y_index, length_of_ship, direction)) {

					returnVal = true;
				}
			}
			break;
		case "-x":
			if (x_index - length_of_ship >= 0) {
				if (validateNeighbour((x_index - length_of_ship), y_index, length_of_ship, direction)) {
					returnVal = true;
				}
			}
			break;
		case "+y":
			if (y_index + length_of_ship <= 9) {
//				System.out.println("this is validation "+ validateNeighbour(x_index, y_index, length_of_ship,direction));

				if (validateNeighbour(x_index, y_index, length_of_ship, direction)) {

					returnVal = true;
				}
			}

			break;
		case "-y":
			if (y_index - length_of_ship >= 0) {
				if (validateNeighbour((x_index), (y_index - length_of_ship), length_of_ship, direction)) {
					returnVal = true;
				}
			}

			break;
		}
		return returnVal;
	}

	public static void loadShipsData() {
		// TODO Auto-generated method stub
		ShipsData.put("destroyerImg", 2);
		ShipsData.put("submarineImg", 3);
		ShipsData.put("battleShipImg", 4);
		ShipsData.put("carrierImg", 5);
		ShipsData.put("cruiserImg", 3);
	}

}
