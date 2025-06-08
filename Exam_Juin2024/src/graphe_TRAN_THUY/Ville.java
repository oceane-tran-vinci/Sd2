package graphe_TRAN_THUY;

public class Ville {

	private final int id;
	private final String nom;

	private final double longitude;
	private final double latitude;

	public Ville(int id, String nom, double longitude, double latitude) {
		this.id = id;
		this.nom = nom;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public int getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Ville other = (Ville) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
