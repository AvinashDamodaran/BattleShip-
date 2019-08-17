package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import database.Database;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
/**
 * Class: ShipPlacer is  a helper class to help the controller set places of the ships and update the entry in database.
 * 
 * @author admin
 *
 * @param <E>
 */
public class ShipsPlacer<E> {



	public void finalPlacer(int x, int y, String dir, ArrayList images, GridPane ocean, String ShipName,Player1 p1) {
		ArrayList<String> coordinates = new ArrayList();
		ArrayList<String> coordinate1 = new ArrayList();
		switch (dir) {
		case "+x":
			Iterator<E> itr = images.iterator();
			while (itr.hasNext()) {
				Node temp = (Node) itr.next();
				ocean.add(temp, x, y, 1, 1);

				coordinates.add(y + "" + x);
				coordinate1.add(y + "" + x);

				Database.set.add(y + "" + x);
				x++;
			}

			break;
		case "-x":
			itr = images.iterator();
			while (itr.hasNext()) {
				Node temp = (Node) itr.next();
				temp.setRotate(temp.getRotate() + 180);
				ocean.add(temp, x, y, 1, 1);

				coordinates.add(y + "" + x);
				coordinate1.add(y + "" + x);
				Database.set.add(y + "" + x);
				x--;
			}
			break;
		case "+y":
			itr = images.iterator();
			while (itr.hasNext()) {
				Node temp = (Node) itr.next();
				temp.setRotate(temp.getRotate() + 90);
				ocean.add(temp, x, y, 1, 1);

				coordinates.add(y + "" + x);
				coordinate1.add(y + "" + x);
				Database.set.add(y + "" + x);
				y++;
			}
			break;
		case "-y":
			itr = images.iterator();
			while (itr.hasNext()) {
				Node temp = (Node) itr.next();
				temp.setRotate(temp.getRotate() + 270);
				ocean.add(temp, x, y, 1, 1);

				coordinates.add(y + "" + x);
				coordinate1.add(y + "" + x);
				Database.set.add(y + "" + x);
				y--;
			}
			break;

		}
		System.out.println(ShipName + " " + coordinates);
		Database.coordinates.put(ShipName, coordinates);
		Player1.loadShips.put(ShipName, coordinate1);
	
	}

	public void ShipsPlacer(String ShipName, String coordinates[], String direction, GridPane oceanGridWrappper1, Player1 p1) {

		ArrayList<ImageView> images = new ArrayList<ImageView>();
		int angle = 0;
		int x = Integer.parseInt(coordinates[1]);
		int y = Integer.parseInt(coordinates[0]);
		if (direction.equalsIgnoreCase("+y")) {
			angle = 90;
		} else if (direction.equalsIgnoreCase("-y")) {
			angle = 270;
		} else if (direction.equalsIgnoreCase("-x")) {
			angle = 180;
		}

		switch (ShipName) {
		case "cruiserImg":
			try {

				File file = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/cruiser1.1.png");
				File file1 = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/cruiser1.2.png");
				File file2 = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/cruiser1.3.png");
				ImageView iv = new ImageView(file.toURI().toString());
				ImageView iv1 = new ImageView(file1.toURI().toString());
				ImageView iv2 = new ImageView(file2.toURI().toString());
				images.add(iv);
				images.add(iv1);
				images.add(iv2);
				finalPlacer(x, y, direction, images, oceanGridWrappper1, ShipName,p1);

			} catch (Exception e) {
					e.printStackTrace();
			}
			break;
		case "carrierImg":
			try {

				File file = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/carrier_main5.png");
				File file1 = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/carrier_main4.png");
				File file2 = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/carrier_main3.png");
				File file3 = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/carrier_main2.png");
				File file4 = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/carrier_main1.png");
				ImageView iv = new ImageView(file.toURI().toString());
				ImageView iv1 = new ImageView(file1.toURI().toString());
				ImageView iv2 = new ImageView(file2.toURI().toString());
				ImageView iv3 = new ImageView(file3.toURI().toString());
				ImageView iv4 = new ImageView(file4.toURI().toString());
				images.add(iv);
				images.add(iv1);
				images.add(iv2);
				images.add(iv3);
				images.add(iv4);
				finalPlacer(x, y, direction, images, oceanGridWrappper1, ShipName,p1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case "battleShipImg":
			try {

				File file = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/battleShip_main1.png");
				File file1 = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/battleShip_main2.png");
				File file2 = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/battleShip_main3.png");
				File file3 = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/battleShip_main4.png");

				ImageView iv = new ImageView(file.toURI().toString());
				ImageView iv1 = new ImageView(file1.toURI().toString());
				ImageView iv2 = new ImageView(file2.toURI().toString());
				ImageView iv3 = new ImageView(file3.toURI().toString());
				images.add(iv);
				images.add(iv1);
				images.add(iv2);
				images.add(iv3);
				finalPlacer(x, y, direction, images, oceanGridWrappper1, ShipName,p1);


			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "submarineImg":
			try {

				File file = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/submarine_main1.png");
				File file1 = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/submarine_main2.png");
				File file2 = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/submarine_main3.png");

				// File file4 = new
				// File("C:/User/admin/eclipse-workspace/BattleShip/image/carrier_main1.png");
				ImageView iv = new ImageView(file.toURI().toString());
				ImageView iv1 = new ImageView(file1.toURI().toString());
				ImageView iv2 = new ImageView(file2.toURI().toString());
				images.add(iv);
				images.add(iv1);
				images.add(iv2);
				finalPlacer(x, y, direction, images, oceanGridWrappper1, ShipName,p1);


			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "destroyerImg":
			try {

				File file = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/destroyer_main1.png");
				File file1 = new File("C:/Users/admin/eclipse-workspace/BattleShip/image/destroyer_main2.png");

				ImageView iv = new ImageView(file.toURI().toString());
				ImageView iv1 = new ImageView(file1.toURI().toString());
				images.add(iv);
				images.add(iv1);
				finalPlacer(x, y, direction, images, oceanGridWrappper1, ShipName,p1);


			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

}