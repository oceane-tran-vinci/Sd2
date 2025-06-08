package courses_TRAN_THUY;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Course {

	//TODO : ajouter attributs ici
	//Liste coureurs inscrits à la course, Contiendra tous les coureurs avec leur départ et leur arrivée
	List<Coureur> coureurs;

	//TODO : initialise ce qu'il faut initialiser si n�c�ssaire
	public Course() {
		coureurs= new ArrayList<>(); //initialise la liste des coureurs
	}

	// encode l'heure de d�part pour un coureur (en utilisant la m�thode setDepart de Coureur)
	public void encoderDepart(Coureur coureur, LocalTime l) {
		//TODO
		coureur.setDepart(l);     // Enregistre l'heure de départ
		coureurs.add(coureur);    // Ajoute le coureur à la liste
	}


	// encode l'heure d'arrivee pour un coureur (en utilisant la m�thode setArrivee de Coureur)
	// si le coureur en param�tre n'a pas de temps de d�part encod� alors la m�thode lance une IllegalArgumentException
	public void encoderArrivee(Coureur coureur, LocalTime l) {
		//TODO
		if (coureur.getDepart()==null) {
			throw new IllegalArgumentException("le coureur n'a pas de temps de départ encodé");
		}
		coureur.setArrivee(l); // Enregistre l'heure d'arrivée
	}

	// affiche (en utilisant le toString de coureur) les coureurs arriv�s en triant selon le temps mis (les coureurs ayant mis moins de temps apparaissent en premier)
	// si deux coureurs ont le m�me temps, l'ordre n'a pas d'importance
	// � la fin, la m�thode affiche une ligne de s�paration constitu� de '-'.
	public void afficherClassementProvisoire() {
		//TODO
		List<Coureur> sortedCoureurs = new ArrayList<>(coureurs); // Copie de la liste
		sortedCoureurs.removeIf(c -> c.getArrivee() == null); // Garde uniquement les arrivés
		sortedCoureurs.sort(Comparator.comparing(Coureur::obtenirTemps)); // Trie par temps

		// Affichage
		for (Coureur coureur : sortedCoureurs) {
			System.out.println(coureur); // Affiche via toString()
		}

		System.out.println("-----------------------------");
	}
	
	public static void main(String[] args) {
		Coureur c1= new Coureur(1, "jean");
		Coureur c2= new Coureur(2, "pol");
		Coureur c3= new Coureur(3, "marc");
		Coureur c4= new Coureur(4, "luc");
		Coureur c5= new Coureur(5, "jim");
		Course c= new Course();
		
		c.encoderDepart(c1, LocalTime.of(2, 0, 0));
		c.encoderDepart(c2, LocalTime.of(2, 1, 0));
		c.encoderDepart(c3, LocalTime.of(2, 2, 0));
		c.encoderArrivee(c2, LocalTime.of(2, 2, 30));
		c.encoderArrivee(c1, LocalTime.of(2, 2, 45));
		c.encoderDepart(c4, LocalTime.of(2, 3, 0));
		c.encoderArrivee(c3, LocalTime.of(2, 3, 15));

		c.afficherClassementProvisoire();
		
		c.encoderDepart(c5, LocalTime.of(2, 4, 0));
		c.encoderArrivee(c4, LocalTime.of(2, 4, 20));
		c.encoderArrivee(c5, LocalTime.of(2, 6, 45));

		c.afficherClassementProvisoire();
 
	}
}
