package graphe_TRAN_THUY;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import java.util.Queue;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Graph {

	protected Map<String, Airport> correspondanceIataAirport ;
	private Map<Integer, Airport>  correspondanceIndiceAirport;
	private Map<Airport, Integer>  correspondanceAirportIndice;
	private Flight[][] matrice;
	private int nbAirport=0;
	
	public Graph (String xmlFile) throws Exception {
		correspondanceIataAirport= new HashMap<String, Airport>();
		correspondanceAirportIndice= new HashMap<Airport,Integer>();
		correspondanceIndiceAirport= new HashMap<Integer,Airport>();
		File xml = new File(xmlFile);
		DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
		Document doc = docBuild.parse(xml);
		NodeList airports = doc.getElementsByTagName("airport");
		for (int i = 0; i < airports.getLength(); i++) {
			Node airport = airports.item(i);
			Element elAirport = (Element) airport;
			String iata = elAirport.getAttribute("iata");
			String name = elAirport.getAttribute("name");
			Airport a = new Airport(iata, name);
			correspondanceIataAirport.put(iata, a);
			correspondanceIndiceAirport.put(nbAirport,a);
			correspondanceAirportIndice.put(a, nbAirport);
			nbAirport++;
		}
		matrice= new Flight[nbAirport][nbAirport];
		for (int i = 0; i < airports.getLength(); i++) {
			Node airport = airports.item(i);
			Element elAirport = (Element) airport;
			String iata = elAirport.getAttribute("iata");
			NodeList flights = elAirport.getElementsByTagName("flight");
			for (int j = 0; j < flights.getLength(); j++) {
				Node flight = flights.item(j);
				Element elFlight = (Element) flight;
				String dest = elFlight.getTextContent();
				String airline = elFlight.getAttribute("airline");
				Flight f = new Flight(correspondanceIataAirport.get(iata), correspondanceIataAirport.get(dest),
						airline);
				matrice[correspondanceAirportIndice.get(f.getSource())][correspondanceAirportIndice.get(f.getDestination())]=f;
			}
		}
	}
	
	public Airport getAirport(String iata) {
		return correspondanceIataAirport.get(iata);
	}

	//TODO
	// A compl�ter
	// affiche a la sortie standard les codes iata des differents aeroports 
	// qu il est possible d'atteindre dans l ordre d un parcours en largeur (BFS) depuis l aeroport de a.
	public void bfs(Airport a) {
		ArrayDeque<Airport> file = new ArrayDeque<>();// Création d'une file pour gérer les aéroports à visiter, dans l’ordre BFS
		ArrayList<Airport> visited = new ArrayList<>();// Liste des aéroports déjà visités pour ne pas les visiter plusieurs fois

		// On commence le parcours à partir de l’aéroport de départ
		file.add(a);
		visited.add(a);

		// Tant qu’il reste des aéroports à visiter
		while (!file.isEmpty()) {
			Airport current = file.poll(); // On enlève le premier de la file

			int index = correspondanceAirportIndice.get(current);// On récupère son indice dans la matrice

			// On parcourt toutes les destinations possibles depuis cet aéroport
			for (int i = 0; i < nbAirport; i++) {
				if (matrice[index][i] != null) {
					Airport voisin = correspondanceIndiceAirport.get(i);

					// Si l’aéroport n’a pas encore été visité
					if (!visited.contains(voisin)) {
						file.add(voisin);     // On ajoute à la file
						visited.add(voisin);  // Et on le marque comme visité
					}
				}
			}
		}

		// Une fois le parcours terminé, on affiche les IATA dans l’ordre de visite
		for (Airport airport : visited) {
			System.out.print(airport.getIata() + " ");
		}
		System.out.println(); // Retour à la ligne à la fin
	}

}
