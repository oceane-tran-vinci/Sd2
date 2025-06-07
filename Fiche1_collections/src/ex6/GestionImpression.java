package ex6;

import java.util.LinkedList;
import java.util.Queue;

public class GestionImpression {

	private Queue<Impression> file;

	public GestionImpression() {
		file = new LinkedList<>();
	}

	public void ajouterImpression(Impression impr) {
		file.add(impr);
	}

	public Impression selectionnerImpression() {
		return file.poll(); // retire et retourne la première impression
	}
}
