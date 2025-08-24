package elections_TRAN_THUY;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ResultatsElectoraux {
  private HashMap<String, Parti> mapPartis;

  // le constructeur initialise les attributs
  public ResultatsElectoraux(String[] nomsPartis) {
    mapPartis = new HashMap<>();
    for (String nomsParti : nomsPartis) {
      mapPartis.put(nomsParti, new Parti(nomsParti));
    }
  }

  // Ajouter nbVotes au parti dont le nom est nomParti
  public void ajouterVotes(String nomParti, int nbVotes) {
    mapPartis.get(nomParti).ajouterVotes(nbVotes);
  }


  // Affiche les n premiers partis par ordre décroissant du nombre de votes en respectant l'affichage donné dans l'énoncé
  // En cas d��galit� sur le nombre de votes, l�ordre d�affichage entre partis � �galit� n�a pas d�importance.
  public void afficherNPremiers(int n) {
    List<Parti> partisTries = mapPartis
        .values()
        .stream()
        .sorted(Comparator.comparingInt(Parti::getNbVotes).reversed())
        .limit(n)
        .toList();

    int i = 1;
    for (Parti parti : partisTries) {
      System.out.println(i + ") " + parti.getNom() + " : " + parti.getNbVotes() + " votes");
      i++;
    }
  }

}