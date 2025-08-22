package elections_TRAN_THUY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultatsElectoraux {

  // TODO: ajouter les attributs n�c�ssaires
  private Map<String, Parti> partis;

  // le constructeur initialise les attributs
  public ResultatsElectoraux(String[] nomsPartis) {
    // TODO
    this.partis = new HashMap<>();
    for (String nom : nomsPartis) {
      partis.put(nom, new Parti(nom));
    }
  }

  // Ajouter nbVotes au parti dont le nom est nomParti
  public void ajouterVotes(String nomParti, int nbVotes) {
    // TODO
    Parti p = partis.get(nomParti);
    if (p != null) {
      p.ajouterVotes(nbVotes);
    } else {
      System.out.println("Parti introuvable : " + nomParti);
    }
  }


  // Affiche les n premiers partis par ordre décroissant du nombre de votes en respectant l'affichage donné dans l'énoncé
  // En cas d��galit� sur le nombre de votes, l�ordre d�affichage entre partis � �galit� n�a pas d�importance.
  public void afficherNPremiers(int n) {
    //TODO
    // Transformer la map en liste
    List<Parti> liste = new ArrayList<>(partis.values());

    // Trier par votes décroissants selon nbVotes
    liste.sort((p1, p2) -> Integer.compare(p2.getNbVotes(), p1.getNbVotes()));

    // Afficher les n premiers
    for (int i = 0; i < Math.min(n, liste.size()); i++) {
      Parti p = liste.get(i);
      System.out.println((i + 1) + ") " + p.getNom() + " : " + p.getNbVotes() + " votes");
    }
  }

}
