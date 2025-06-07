package ex3;

import java.time.LocalTime;
import java.util.ArrayDeque;
import java.util.HashMap;

import static java.time.temporal.ChronoUnit.MILLIS;

public class LocationPatins {
	private static final int POINTURE_MIN = 33;
	private static final int POINTURE_MAX = 48;

	private HashMap<Integer, ArrayDeque<Integer>> pointures;// Associe chaque pointure avec une file d'attente de casiers disponibles
	private HashMap<Integer, LocalTime> casiersOccupees;// Associe chaque numéro de casier avec le moment où il a été occupé
	private int[] casier;// Tableau représentant les numéros de casiers disponibles

	// Constructeur prenant un tableau de numéros de casiers en paramètre
	public LocationPatins(int[] casiers) {
		// Initialise les attributs
		pointures = new HashMap<>();
		casiersOccupees = new HashMap<>();
		casier = casiers;

		// Pour chaque numéro de casier dans le tableau
		for (int i = 0; i < casiers.length; i++) {
			// Si le numéro de casier est dans la plage de pointures de patins
			if (casiers[i] >= POINTURE_MIN && casiers[i] <= POINTURE_MAX) {
				// Associe le numéro de casier avec une nouvelle file d'attente vide
				// et ajoute le numéro de casier à la file d'attente
				pointures.computeIfAbsent(casiers[i], k -> new ArrayDeque<>()).offer(i);
			}
		}
	}

	// Méthode pour calculer le prix entre deux moments
	// date1 < date2
	private static double prix(LocalTime date1, LocalTime date2) {
		// 1 euro par milliseconde (c'est assez cher en effet)
		return MILLIS.between(date1, date2) ;
	}

	// Méthode pour attribuer un casier avec des patins à une personne en fonction de sa pointure
	public int attribuerCasierAvecPatins(int pointure) {
		if (pointure < 33 || pointure > 48) // Vérifie si la pointure est dans la plage autorisée
			throw new IllegalArgumentException();
		LocalTime l = LocalTime.now(); // Obtient le moment actuel
		
		
		//a compl�ter
		int casierDispo = -1;// Initialise le numéro de casier disponible à -1
		// Parcourt les numéros de casier disponibles
		for (int casier : casier) {
			if (!casiersOccupees.containsKey(casier)) {// Si le casier n'est pas occupé
				casierDispo = casier;// Enregistre le numéro de casier disponible
				// Associe le numéro de casier avec une nouvelle file d'attente vide
				// et ajoute la pointure à la file d'attente
				pointures.computeIfAbsent(casierDispo, cle -> new ArrayDeque<>()).offer(pointure);
				casiersOccupees.put(casier, l);// Enregistre le moment où le casier a été occupé
				break;// Sort de la boucle
			}
		}
		return casierDispo;// Retourne le numéro de casier attribué
	}

	// Méthode pour libérer un casier occupé et calculer le montant à payer
	public double libererCasier(int numeroCasier) {
		//a completer
		if (!casiersOccupees.containsKey(numeroCasier)) {// Si le casier n'est pas occupé
			return -1; // Retourne -1 pour indiquer que le casier n'est pas occupé
		}
		LocalTime now = LocalTime.now();// Obtient le moment actuel
		double montant = prix(casiersOccupees.get(numeroCasier), now);// Calcule le montant à payer en fonction de la durée d'occupation du casier
		pointures.getOrDefault(numeroCasier, new ArrayDeque<>()).poll();// Retire la pointure du casier
		casiersOccupees.remove(numeroCasier);// Retire l'occupation du casier
		return montant;// Retourne le montant à payer
	}

}
