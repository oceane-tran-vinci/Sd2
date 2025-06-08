package film_TRAN_THUY;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Catalogue {

  //Vous pouvez ajouter des attributs et/ou des constructeurs
  // On utilise une map pour stocker les films selon leur année
  // La clé (Integer) est l'année, la valeur est la liste des films sortis cette année
  Map<Integer, List<Film>> catalogue = new HashMap<>();


  //ajoute le film au catalogue
  public void ajouterFilm(Film f) {
    int year = f.getAnnee(); // on récupère l'année du film

    // Si l'année n'est pas encore dans la map, on ajoute une nouvelle liste vide
    if (!catalogue.containsKey(year)) {
      catalogue.put(year, new ArrayList<>());
    }

    // On ajoute le film à la liste correspondant à son année
    catalogue.get(year).add(f);
  }

  // affiche tous les films de l'ann�e en param�tre par ordre alphab�tique
  // utilise le toString() de Film
  public void afficherFilmParOrdreAlphabetique(int annee) {
    List<Film> films = catalogue.get(annee); // On récupère la liste des films pour cette année

    // Si aucun film n'a été enregistré pour cette année, on affiche le message prévu
    if (films == null || films.isEmpty()) {
      System.out.println("pas de film en " + annee);
    } else {
      // Sinon, on trie les films par nom (ordre alphabétique)
      films.sort(Comparator.comparing(Film::getNom));

      // On affiche chaque film avec son toString()
      for (Film film : films) {
        System.out.println(film);
      }
    }
  }
}
