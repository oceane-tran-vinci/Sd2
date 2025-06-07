package ex2;

import java.util.HashMap;
import java.util.HashSet;

public class ControleDAcces {

	private HashMap<Badge, Employe> badgeEmploye = new HashMap<>();// HashMap pour associer chaque badge à un employé
	private HashSet<Employe> employesPresents = new HashSet<>();// HashSet pour stocker les employés actuellement présents dans le bâtiment

	public ControleDAcces(){
		
	}
	
	// associe le badge � un employ�
	public void donnerBadge (Badge b, Employe e){
		if (badgeEmploye.containsKey(b)){//si la map contient déjà badge-employé return exception
			throw new IllegalArgumentException("ex2.Badge déjà attribué");
		}
		badgeEmploye.put(b, e);//sinon l'add dans la map
	}
	
	// met � jour les employ�s pr�sents dans le batiment
	public void entrerBatiment (Badge b){
		Employe e = badgeEmploye.get(b);//Récupère l'employé associé au badge
		if (estDansBatiment(e)){// Vérifie si l'employé est déjà dans le bâtiment
			throw new IllegalArgumentException("Déjà dans le batiment");
		}
		employesPresents.add(e);// Ajoute l'employé à la liste des employés présents
	}

	// met � jour les employ�s pr�sents dans le batiment
	public void sortirBatiment (Badge b){
		Employe e = badgeEmploye.get(b);//Récupère l'employé associé au badge
		if (!estDansBatiment(e)){// Vérifie si l'employé n'est pas dans le bâtiment
			throw new IllegalArgumentException("Déjà sorti du batiment");
		}
		employesPresents.remove(e);// Retire l'employé de la liste des employés présents
	}
	
	// renvoie vrai si l'employ� est dans le batiment, faux sinon
	public boolean estDansBatiment (Employe e){
		return employesPresents.contains(e);
	}

}
