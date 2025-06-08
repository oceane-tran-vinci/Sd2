package inscription_TRAN_THUY;

public class PlageHoraire {
	private final int heure;
	private final int minute;
	public PlageHoraire(int heure, int minute) {
		super();
		this.heure = heure;
		this.minute = minute;
	}
	public int getHeure() {
		return heure;
	}
	public int getMinute() {
		return minute;
	}
	@Override
	public int hashCode() {
		return 60*heure + minute;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlageHoraire other = (PlageHoraire) obj;
		if (heure != other.heure)
			return false;
		if (minute != other.minute)
			return false;
		return true;
	}
	@Override
	public String toString() {
		if (minute== 0) return heure+"h";
		return heure+"h"+minute;
	}
	
}
