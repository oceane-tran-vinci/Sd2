import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MatriceDAdjacence extends Graph {

    private Map<Integer, Airport> correspondanceIndiceAirport;// Association entre un indice et un a�roport (utile pour retrouver un a�roport � partir de son indice)
    private Map<Airport, Integer> correspondanceAirportIndice;// Association entre un a�roport et son indice (utile pour acc�der aux cases de la matrice)
    private Flight[][] matrice = new Flight[0][0];// Matrice d'adjacence pour stocker les vols entre les a�roports
    private int nbAirport = 0;// Nombre total d'a�roports dans le graphe

    public MatriceDAdjacence() {
        super();
        correspondanceAirportIndice = new HashMap<Airport, Integer>();
        correspondanceIndiceAirport = new HashMap<Integer, Airport>();
    }

    @Override
    // Complexit�: O(1)
    protected void ajouterSommet(Airport a) {
        //� compl�ter
        // Associe un nouvel indice unique � l'a�roport dans la correspondance
        correspondanceIndiceAirport.put(nbAirport, a);
        correspondanceAirportIndice.put(a, nbAirport);
        matrice = new Flight[nbAirport][nbAirport];// Initialise une nouvelle matrice d'adjacence
        nbAirport++;// Incr�mente le compteur total d'a�roports
    }

    @Override
    // Complexit�: O(1)
    protected void ajouterArc(Flight f) {
        //� compl�ter
        // Ajoute un vol � la matrice d'adjacence en utilisant les indices des a�roports source et destination
        //matrice[source][destination] = f
        matrice[correspondanceAirportIndice.get(f.getSource())][correspondanceAirportIndice.get(f.getDestination())] = f;
    }

    @Override
    // Complexit�: O(1)
    public Set<Flight> arcsSortants(Airport a) {
        //� compl�ter
        Set<Flight> volsSortants = new HashSet<>();
		// R�cup�re l'indice de l'a�roport dans la matrice
		int indiceDepart = correspondanceAirportIndice.get(a);
		/*
       V�rifie tous les vols partant de cet a�roport :
       - On parcourt la ligne correspondant � l'a�roport de d�part dans la matrice
       - Si un vol est trouv� (c'est-�-dire une case non null), on l'ajoute � l'ensemble des vols sortants
    	*/
		/* Exemple
		- a�roport d�part : 0=FCO
		- v�rifier s'il y a vols avec tt les autres a�roport (ici destination) : 0->1, 0->2, 0->3,...
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
    // Complexit�: ?
    public boolean sontAdjacents(Airport a1, Airport a2) {
        // � compl�ter
        // R�cup�re les indices des a�roports
        int indiceA1 = correspondanceAirportIndice.get(a1);
        int indiceA2 = correspondanceAirportIndice.get(a2);

        // V�rifie s'il existe un vol de a1 vers a2 ou de a2 vers a1
        return matrice[indiceA1][indiceA2] != null || matrice[indiceA2][indiceA1] != null;
    }


}
