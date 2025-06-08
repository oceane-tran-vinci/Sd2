package inscription_TRAN_THUY;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InscriptionsOral {
	
	// Attributs � ajouter ici
	private final Map<PlageHoraire, Set<Etudiant>> mapPlageEtudiants; // Map pour savoir quels étudiants sont dans chaque plage
	private final Map<Etudiant, PlageHoraire> mapEtudiantPlage;// Map pour savoir dans quelle plage est chaque étudiant

	public InscriptionsOral() {
		// Initialiser les attributs ici
		mapPlageEtudiants = new HashMap<>();
		mapEtudiantPlage = new HashMap<>();
	}
	
	// Inscrit l'�tudiant � une plage horaire. Plusieurs �tudiants peuvent s'inscrire � la m�me plage horaire.
	// Cette m�thode v�rifie si l'�tudiant est d�j� inscrit � une autre plage horaire.
    // Si oui, une RuntimeException est lanc�e
	public void inscrireEtudiant(Etudiant e, PlageHoraire p) {
		// TODO

		// Si l'étudiant est déjà inscrit (présent dans la map), on lève une exception
		if (mapEtudiantPlage.containsKey(e)) {
			throw new RuntimeException("L'étudiant est déjà inscrit");
		}
		mapPlageEtudiants.putIfAbsent(p, new HashSet<>()); // On ajoute une entrée pour la plage si elle n’existe pas encore
		mapPlageEtudiants.get(p).add(e);// On ajoute l’étudiant dans l’ensemble des étudiants pour cette plage
		mapEtudiantPlage.put(e, p);// On enregistre que cet étudiant est inscrit à cette plage
	}
	
	// Affiche l'horaire des examens en ordre chronologique.
    // Elle affiche les �tudiants inscrits pour chaque plage horaire. 
	// L'ordre des �tudiants au sein d'une plage n'a pas d'importance
	public void afficherHoraire() {
		// TODO
		List<PlageHoraire> plages = new ArrayList<>(mapPlageEtudiants.keySet());// On récupère la liste des plages horaires à trier

		// Tri par heure puis par minute croissante
		plages.sort(Comparator.comparingInt(PlageHoraire::getHeure)
				.thenComparingInt(PlageHoraire::getMinute));

		// Affiche les étudiants pour chaque plage dans l’ordre trié
		for (PlageHoraire p : plages) {
			System.out.print(p + " : ");
			for (Etudiant e : mapPlageEtudiants.get(p)) {
				System.out.print(e + "/");
			}
			System.out.println(); // retour à la ligne après chaque plage
		}
	}
	
	// Echange les plages horaires de deux �tudiants
    // Si l'un des deux �tudiants n'est pas inscrit, une RuntimeException est lanc�e
	public void echanger(Etudiant e1, Etudiant e2) {
		// TODO

		// On vérifie que les deux étudiants sont bien inscrits
		if (!mapEtudiantPlage.containsKey(e1) || !mapEtudiantPlage.containsKey(e2)) {
			throw new RuntimeException("Un des étudiants n'est pas inscrit");
		}

		// On récupère leurs plages horaires respectives
		PlageHoraire p1 = mapEtudiantPlage.get(e1);
		PlageHoraire p2 = mapEtudiantPlage.get(e2);

		// On enlève les étudiants de leurs plages actuelles
		mapPlageEtudiants.get(p1).remove(e1);
		mapPlageEtudiants.get(p2).remove(e2);

		// Et on les ajoute dans les plages inversées
		mapPlageEtudiants.get(p1).add(e2);
		mapPlageEtudiants.get(p2).add(e1);

		// Mise à jour dans la map des inscriptions
		mapEtudiantPlage.put(e1, p2);
		mapEtudiantPlage.put(e2, p1);
	}
	
}
