package inscription_TRAN_THUY;

public class Etudiant {
	private final int matricule;
	private final String nom;
	private final String prenom;
	public Etudiant(int matricule, String nom, String prenom) {
		this.matricule = matricule;
		this.nom = nom;
		this.prenom = prenom;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + matricule;
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
		Etudiant other = (Etudiant) obj;
		if (matricule != other.matricule)
			return false;
		return true;
	}
	public int getMatricule() {
		return matricule;
	}
	public String getNom() {
		return nom;
	}
	public String getPrenom() {
		return prenom;
	}
	@Override
	public String toString() {
		return prenom+" "+nom;
	}

}
