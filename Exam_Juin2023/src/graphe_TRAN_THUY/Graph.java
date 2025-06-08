package graphe_TRAN_THUY;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

public class Graph {

	private final Map<Station, Set<Troncon>> mapStationTroncons;
	private final Map<String, Station> mapNomStation;
	private final Map<Integer, Ligne> mapLignes;

	public Graph(File lignes, File troncons) throws Exception {
		mapLignes = new HashMap<>();
		mapStationTroncons = new HashMap<>();
		mapNomStation = new HashMap<>();

		List<String> allLines = Files.readAllLines(lignes.toPath());
		for (String line : allLines) {
			String[] attributes = line.split(",");
			int id = Integer.parseInt(attributes[0]);
			String numero = attributes[1];
			Station depart = getStation(attributes[2]);
			Station destination = getStation(attributes[3]);
			String transport = attributes[4];
			int tempsAttenteMoyen = Integer.parseInt(attributes[5]);
			mapLignes.put(id, new Ligne(id, numero, depart, destination, transport, tempsAttenteMoyen));
		}

		allLines = Files.readAllLines(troncons.toPath());

		for (String line : allLines) {
			String[] attributes = line.split(",");
			Ligne ligne = mapLignes.get(Integer.parseInt(attributes[0]));
			Station depart = getStation(attributes[1]);
			Station arrivee = getStation(attributes[2]);
			int duree = Integer.parseInt(attributes[3]);
			Troncon troncon = new Troncon(ligne, depart, arrivee, duree);
			if (mapStationTroncons.containsKey(depart)) {
				mapStationTroncons.get(depart).add(troncon);
			} else {
				HashSet<Troncon> tronconsSet = new HashSet<>();
				tronconsSet.add(troncon);
				mapStationTroncons.put(depart, tronconsSet);
			}
		}
	}

	public Station getStation(String nomStation) {
		Station station;
		if (!mapNomStation.containsKey(nomStation)) {
			station = new Station(nomStation);
			mapNomStation.put(station.getNom(), station);
		} else {
			station = mapNomStation.get(nomStation);
		}
		return station;
	}

	// renvoie l'ensemble des tron�ons entrants de la station
	// c�d les tron�ons dont l'arrivee est la station en param�tre
	// Cette méthode retourne tous les tronçons qui arrivent à la station 's' donnée en paramètre
	public Set<Troncon> tronconsEntrants(Station s){
		//TODO
		Set<Troncon> set = new HashSet<>(); //On crée un set vide pour stocker les tronçons trouvés
		// On parcourt toute la map station -> tronçons sortants
		// (mapStationTroncons contient les tronçons qui partent de chaque station)
		for (Entry<Station, Set<Troncon>> stationSetEntry : mapStationTroncons.entrySet()) {
			Set<Troncon> toVerify = stationSetEntry.getValue();// On récupère tous les tronçons sortants de cette station

			// On vérifie chaque tronçon un par un
			for (Troncon troncon : toVerify) {
				// Si la station d'arrivée du tronçon est celle qu'on cherche (s),
				// alors c'est un tronçon entrant : on l'ajoute au set résultat
				if (troncon.getArrivee().equals(s)) {
					set.add(troncon);
				}
			}
		}
		return set; // Une fois tout parcouru, on retourne l'ensemble des tronçons entrants trouvés
	}

	// affiche le chemin le plus court (en terme de nombre de troncon) entre la station de d�part et la station d'arriv�e
	// On utilise un parcours en largeur (BFS). Pour afficher les troncons, on utilise simplement la m�thode toString() de Troncon
	public void calculerCheminMinimisantNombreTroncons(String depart, String arrivee) {
		// A ADAPTER
		Set<Station> visites = new HashSet<>(); // Set pour mémoriser les stations déjà visitées (évite de tourner en rond)
		Queue<Station> file = new ArrayDeque<Station>(); // File pour le parcours en largeur (BFS)
		// AJOUTÉ : Map pour retenir quel tronçon a permis d'atteindre chaque station
		// => utile pour reconstruire le chemin à la fin
		Map<Station, Troncon> tronconPrecedent = new HashMap<>();

		// On récupère les objets Station depuis leurs noms
		Station stationDepart= mapNomStation.get(depart);
		Station stationArrivee= mapNomStation.get(arrivee);

		// On commence le parcours BFS à partir de la station de départ
		file.add(stationDepart);
		visites.add(stationDepart);

		// Parcours en largeur (BFS)
		while (!file.isEmpty()) {
			Station current = file.remove(); // On enlève la station en tête de file

			// Si on atteint la station d’arrivée, on peut arrêter le parcours
			if (current.equals(stationArrivee)){
				break;
			}

			// Pour chaque tronçon sortant de la station actuelle
			for (Troncon t :  mapStationTroncons.get(current)) {
				Station nextStation = t.getArrivee();

				// Si la station d’arrivée de ce tronçon n’a pas encore été visitée
				if (!visites.contains(nextStation)) {
					file.add(nextStation); // On l’ajoute à la file pour qu’elle soit traitée plus tard
					visites.add(nextStation); // On marque cette station comme visitée
					tronconPrecedent.put(nextStation, t); // On retient par quel tronçon on est arrivé ici
				}
			}
		}

		// On va maintenant reconstruire le chemin le plus court en partant de la station d'arrivée
		List<Troncon> path = new ArrayList<>();
		Station current = stationArrivee;

		// On remonte tronçon par tronçon depuis l’arrivée jusqu’au départ
		while (!current.equals(stationDepart)) {
			Troncon t = tronconPrecedent.get(current); // On récupère le tronçon qui a mené à cette station
			// Si aucun tronçon trouvé → le chemin est incomplet (bug, ou stations non connectées)
			if (t == null) {
				System.out.println("Aucun chemin trouvé.");
				return;
			}
			path.add(t); // On ajoute ce tronçon au chemin
			current = t.getDepart(); // Et on remonte à la station de départ du tronçon
		}

		Collections.reverse(path); // On a reconstruit le chemin à l’envers, donc on le renverse

		// Enfin, on affiche chaque tronçon du chemin
		for (Troncon t : path) {
			System.out.println(t);
		}
	}

}
