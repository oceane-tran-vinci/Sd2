import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ListeDArc extends Graph{

	private Set<Airport> airports = new HashSet<>();// Ensemble des aéroports (sommets du graphe)
	private ArrayList<Flight> flights;// Liste des vols (arcs du graphe)

	public ListeDArc() {
		super();
		flights=new ArrayList<Flight>();
	}

	@Override
	// Complexit�: O(1)
	protected void ajouterSommet(Airport a) {	
		//� compl�ter
		airports.add(a);
	}

	@Override
	// Complexit�: O(1)
	protected void ajouterArc(Flight f) {
		//� compl�ter
		flights.add(f);
	}

	@Override
	// Complexit�: O(1)
	public Set<Flight> arcsSortants(Airport a) {
		//� compl�ter
		Set<Flight> volsSortants = new HashSet<>(); // Création d'un ensemble pour stocker les vols sortants
		// Parcours de tous les vols enregistrés
		for (Flight f : flights) {
			if (f.getSource().equals(a)){ // Vérifie si l'aéroport de départ du vol est égal à l'aéroport donné
				volsSortants.add(f); // Ajout du vol à l'ensemble des vols sortants
			}
		}
		return volsSortants;
	}

	@Override
	// Complexit�: O(1)
	public boolean sontAdjacents(Airport a1, Airport a2) {
		// � compl�ter
		for (Flight f : flights) { // Parcours de tous les vols enregistrés
			// Vérifie si le vol relie les deux aéroports dans un sens ou dans l'autre
			if (f.getSource().equals(a1) && f.getDestination().equals(a2)
			|| f.getSource().equals(a2) && f.getDestination().equals(a1)){
				return true; // Retourne vrai si une connexion est trouvée
			}
		}
		return false; // Retourne faux si aucune connexion directe n'existe
	}

}
