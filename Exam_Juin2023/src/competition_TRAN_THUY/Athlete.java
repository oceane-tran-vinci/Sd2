package competition_TRAN_THUY;

//ajouter implements Comparable<Athlete>
public class Athlete implements Comparable<Athlete>{
	private final String nom;
	private int nbSautEffectue = 0;
	private int[] sauts = new int[3];

	public Athlete(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

	public void ajouterSaut(int longueur) {
		if (longueur < 0)
			throw new IllegalArgumentException();
		if (nbSautEffectue == 3)
			throw new RuntimeException();
		sauts[nbSautEffectue] = longueur;
		nbSautEffectue++;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Athlete other = (Athlete) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}

	@Override
	public int compareTo(Athlete autreAthlete) {
		int meilleurSaut = meilleurSaut(); // on récupère le meilleur saut de cet athlète
		int autreMeilleurSaut = autreAthlete.meilleurSaut(); // et celui de l'autre athlète

		// Si les deux ont le même meilleur saut, on les classe par ordre alphabétique du nom
		if (meilleurSaut == autreMeilleurSaut) {
			return nom.compareTo(autreAthlete.nom);
		}

		// Sinon, on classe par ordre décroissant du meilleur saut (plus grand saut d'abord)
		return Integer.compare(autreMeilleurSaut, meilleurSaut);
	}


	private int meilleurSaut() {
		int meilleur = 0; // On commence avec 0 comme valeur par défaut

		// On parcourt les 3 sauts
		for (int saut : sauts) {
			// Si un saut est meilleur que le meilleur actuel, on le garde
			if (saut > meilleur) {
				meilleur = saut;
			}
		}

		// On retourne le meilleur saut trouvé
		return meilleur;
	}

}
