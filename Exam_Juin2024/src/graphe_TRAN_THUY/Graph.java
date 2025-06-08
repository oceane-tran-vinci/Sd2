package graphe_TRAN_THUY;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

public class Graph {
	private Map<Integer, Ville> mapVilleEntier = new HashMap<>();
	private Map<String, Ville> mapVilleNom = new HashMap<>();
	private LinkedList<Route> routes = new LinkedList<Route>();

	public Graph(File citiesFile, File roadsFile) {
		loadCities(citiesFile);
		loadRoads(roadsFile);
	}

	private void loadCities(File file) {
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNext()) {
				String[] infos = scanner.nextLine().split(",");
				int id_city = Integer.parseInt(infos[0]);
				String name = infos[1];
				double longitude = Double.parseDouble(infos[2]);
				double latitude = Double.parseDouble(infos[3]);
				Ville city = new Ville(id_city, name, longitude, latitude);
				mapVilleEntier.put(city.getId(), city);
				mapVilleNom.put(city.getNom(), city);
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private void loadRoads(File file) {
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNext()) {
				String[] infos = scanner.nextLine().split(",");
				Ville city_src = mapVilleEntier.get(Integer.parseInt(infos[0]));
				Ville city_dest = mapVilleEntier.get(Integer.parseInt(infos[1]));
				ajouterRoute(city_src, city_dest);
				ajouterRoute(city_dest, city_src);
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private void ajouterRoute(Ville source, Ville destination) {
		Route road = new Route(source, destination);
		routes.add(road);
	}

	// A compl�ter
	// affiche a la sortie standard les noms des differentes villes
	// qu il est possible d'atteindre dans l ordre d un parcours en largeur (BFS)
	// depuis la ville de depart.
	public void bfs(String nomSource) {
		Ville source = mapVilleNom.get(nomSource);
		// TO COMPLETE
	}

}
