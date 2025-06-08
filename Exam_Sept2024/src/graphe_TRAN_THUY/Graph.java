package graphe_TRAN_THUY;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
	
	public HashMap<Ville, HashSet<Route>> toListeDAdjacence() {
		//TODO
		HashMap<Ville, HashSet<Route>> adjacence = new HashMap<>();//Crée une nouvelle map vide pour stocker la liste d'adjacence

		// Parcourt toutes les routes du graphe
		for (Route r : routes) {
			Ville source = r.getSource(); // On récupère la ville source (départ de la route)
			adjacence.putIfAbsent(source, new HashSet<>());// Si la ville source n’a pas encore d’entrée dans la map, on crée une nouvelle HashSet pour elle
			adjacence.get(source).add(r);// On ajoute la route actuelle dans l’ensemble associé à cette ville source
		}
		return adjacence;// Retourne la liste d'adjacence complète
	}

	public void bfs(String nomSource) {
		Ville source = mapVilleNom.get(nomSource); // Récupère la ville de départ à partir de son nom
		// TODO
		if (source == null) return; // Si la ville de départ n'existe pas dans la map, on arrête

		ArrayDeque<Ville> file = new ArrayDeque<>(); // File pour gérer l’ordre de visite (parcours en largeur)
		ArrayList<Ville> visited = new ArrayList<>();// Liste des villes déjà visitées pour éviter les doublons

		// On commence le parcours depuis la ville source
		file.add(source);
		visited.add(source);

		// Tant qu’il reste des villes à visiter
		while (!file.isEmpty()) {
			Ville current = file.poll(); // On retire la première ville de la file (FIFO)
			System.out.println(current.getNom() + " "); // On affiche le nom de la ville visitée

			// On parcourt toutes les routes du graphe
			for (Route r : routes) {
				// Si la route part de la ville actuelle
				if (r.getSource().equals(current)) {
					Ville voisin = r.getDestination();

					// Si la ville voisine n’a pas encore été visitée
					if (!visited.contains(voisin)) {
						file.add(voisin); // On l’ajoute à la file pour traitement futur
						visited.add(voisin); // Et on la marque comme visitée
					}
				}
			}
		}
	}

}
