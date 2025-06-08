package jo_TRAN_THUY;

public class Pays implements Comparable<Pays>{
	private final String name;
	private int nombreMedailleOr = 0;
	private int nombreMedailleArgent = 0;
	private int nombreMedailleBronze = 0;

	public Pays(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getNombreMedialleOr() {
		return nombreMedailleOr;
	}

	public int getNombreMedailleArgent() {
		return nombreMedailleArgent;
	}

	public int getNombreMedailleBronze() {
		return nombreMedailleBronze;
	}

	public void ajouterMedailleOr() {
		nombreMedailleOr++;
	}

	public void ajouterMedailleArgent() {
		nombreMedailleArgent++;
	}

	public void ajouterMedailleBronze() {
		nombreMedailleBronze++;
	}

	public String toString() {
		String separateur = "\t\t";
		if (name.length() >= 8) {
			separateur = "\t";
		}
		return name + separateur + nombreMedailleOr + "\t" + nombreMedailleArgent + "\t" + nombreMedailleBronze;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Pays other = (Pays) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(Pays other) {
		//TODO
		//Comparaison par nombre de médailles d'or
		if (this.nombreMedailleOr != other.nombreMedailleOr) {
			return Integer.compare(other.nombreMedailleOr, this.nombreMedailleOr);
		}

		// Si égalité, comparaison par nombre d'argent
		if (this.nombreMedailleArgent != other.nombreMedailleArgent) {
			return Integer.compare(other.nombreMedailleArgent, this.nombreMedailleArgent);
		}

		// Si encore égalité, comparaison par nombre de bronze
		if (this.nombreMedailleBronze != other.nombreMedailleBronze) {
			return Integer.compare(other.nombreMedailleBronze, this.nombreMedailleBronze);
		}

		// Égalité parfaite : peu importe l’ordre → on compare les noms (pour éviter les doublons en TreeSet)
		return this.name.compareTo(other.name);
	}
}
