import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ListeDAdjacence extends Graph{

	// Map pour stocker les vols sortants de chaque aéroport
	// La clé est un aéroport, et la valeur est un ensemble de vols (arcs)
	private Map<Airport,Set<Flight>> outputFlights;

	public ListeDAdjacence(){
		super();
		outputFlights=new HashMap<Airport,Set<Flight>>();

	}

	@Override
	// Complexité: O(1)
	protected void ajouterSommet(Airport a) {	
		//à compléter
		outputFlights.put(a, new HashSet<Flight>());
	}

	@Override
	// Complexité: O(1)
	protected void ajouterArc(Flight f) {
		//à compléter
		outputFlights.get(f.getSource()).add(f);
	}

	@Override
	// Complexité: O(1)
	public Set<Flight> arcsSortants(Airport a) {
		//à compléter
		return outputFlights.get(a);
	}

	@Override
	// Complexité: O(n)
	public boolean sontAdjacents(Airport a1, Airport a2) {
		// à compléter
		// Vérifie si un vol part de a1 vers a2
		for (Flight f : outputFlights.get(a1)) {// Parcourt tous les vols sortants de l'aéroport a1
			if (f.getDestination().equals(a2)){ // Si l'un des vols a pour destination a2, alors a1 et a2 sont adjacents
				return true;
			}
		}
		// Vérifie si un vol part de a2 vers a1
		for (Flight f : outputFlights.get(a2)) {// Parcourt tous les vols sortants de l'aéroport a2
			if (f.getDestination().equals(a1)){// Si l'un des vols a pour destination a1, alors a2 et a1 sont adjacents
				return true;
			}
		}
		return false;// Retourne faux si aucune connexion directe n'existe entre les deux aéroports
	}

}
