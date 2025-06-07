import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MatriceDAdjacence extends Graph {

    private Map<Integer, Airport> correspondanceIndiceAirport;// Association entre un indice et un aéroport (utile pour retrouver un aéroport à partir de son indice)
    private Map<Airport, Integer> correspondanceAirportIndice;// Association entre un aéroport et son indice (utile pour accéder aux cases de la matrice)
    private Flight[][] matrice = new Flight[0][0];// Matrice d'adjacence pour stocker les vols entre les aéroports
    private int nbAirport = 0;// Nombre total d'aéroports dans le graphe

    public MatriceDAdjacence() {
        super();
        correspondanceAirportIndice = new HashMap<Airport, Integer>();
        correspondanceIndiceAirport = new HashMap<Integer, Airport>();
    }

    @Override
    // Complexité: O(1)
    protected void ajouterSommet(Airport a) {
        //à compléter
        // Associe un nouvel indice unique à l'aéroport dans la correspondance
        correspondanceIndiceAirport.put(nbAirport, a);
        correspondanceAirportIndice.put(a, nbAirport);
        matrice = new Flight[nbAirport][nbAirport];// Initialise une nouvelle matrice d'adjacence
        nbAirport++;// Incrémente le compteur total d'aéroports
    }

    @Override
    // Complexité: O(1)
    protected void ajouterArc(Flight f) {
        //à compléter
        // Ajoute un vol à la matrice d'adjacence en utilisant les indices des aéroports source et destination
        //matrice[source][destination] = f
        matrice[correspondanceAirportIndice.get(f.getSource())][correspondanceAirportIndice.get(f.getDestination())] = f;
    }

    @Override
    // Complexité: O(1)
    public Set<Flight> arcsSortants(Airport a) {
        //à compléter
        Set<Flight> volsSortants = new HashSet<>();
		// Récupère l'indice de l'aéroport dans la matrice
		int indiceDepart = correspondanceAirportIndice.get(a);
		/*
       Vérifie tous les vols partant de cet aéroport :
       - On parcourt la ligne correspondant à l'aéroport de départ dans la matrice
       - Si un vol est trouvé (c'est-à-dire une case non null), on l'ajoute à l'ensemble des vols sortants
    	*/
		/* Exemple
		- aéroport départ : 0=FCO
		- vérifier s'il y a vols avec tt les autres aéroport (ici destination) : 0->1, 0->2, 0->3,...
		- si vols alors l'ajouter ds l'arcsSortants (qd y'a pas de vols l'indice est null)
		 */
        for (int i = 0; i < nbAirport; i++) {
            Flight flight = matrice[indiceDepart][i];

            if (flight != null) {
                volsSortants.add(flight);
            }
        }
        return volsSortants;
    }

    @Override
    // Complexité: ?
    public boolean sontAdjacents(Airport a1, Airport a2) {
        // à compléter
        // Récupère les indices des aéroports
        int indiceA1 = correspondanceAirportIndice.get(a1);
        int indiceA2 = correspondanceAirportIndice.get(a2);

        // Vérifie s'il existe un vol de a1 vers a2 ou de a2 vers a1
        return matrice[indiceA1][indiceA2] != null || matrice[indiceA2][indiceA1] != null;
    }


}
