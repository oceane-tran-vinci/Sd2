package ex1;

import java.util.ArrayDeque;
import java.util.HashMap;

public class Ordonnancement {
	public static final int NIVEAU_PRIORITE_MAX=5;

	private HashMap<Integer, ArrayDeque<Tache>> mapOrdonnancement = new HashMap<>();

	/* 2e manière de faire :
	* Et dc initialiser la map ds constructeur
	private Map<Integer, Deque<Tache>> mapOrdonnancement;
	 */
	public Ordonnancement(){
		//mapOrdonnancement = new HashMap<>();

		// Initialisation des files de tâches pour chaque niveau de priorité
		for (int i = 1; i <= NIVEAU_PRIORITE_MAX; i++) {
			mapOrdonnancement.put(i, new ArrayDeque<>());
		}
	}

	public void ajouterTache (String descriptif, int priorite){
		//exception si priorité <1 ou >5
		if (priorite < 1 || priorite > NIVEAU_PRIORITE_MAX){
			throw new IllegalArgumentException("la priorité doit être entre 1 et "+ NIVEAU_PRIORITE_MAX);
		}

		//créer nouvelle tache avec les param
		Tache tache = new Tache(descriptif, priorite);

		//add cette tache dans la map par rapport à la priorité
		mapOrdonnancement.get(priorite).addLast(tache);
	}
	
	//renvoie la tache prioritaire
	//renvoie null si plus de tache presente
	public Tache attribuerTache(){
		//attribue tâche la plus ancienne de la priorité la plus grande.
		// On parcourt les priorités de la plus élevée à la plus basse
		for (int i = NIVEAU_PRIORITE_MAX; i >= 1; i--) {
			ArrayDeque<Tache> taches = mapOrdonnancement.get(i);
			// Retire et renvoie la tâche la plus ancienne
			if (!taches.isEmpty()){
				return taches.pollFirst();
			}
		}
		return null;
	}
}
