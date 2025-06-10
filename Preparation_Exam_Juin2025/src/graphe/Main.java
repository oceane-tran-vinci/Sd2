package graphe;

import java.util.Map;
import java.util.Set;

public class Main {
  public static void main(String[] args) {
    Graph graph = new Graph("Preparation_Exam_Juin2025/artists.txt", "Preparation_Exam_Juin2025/mentions.txt");
//    graph.trouverCheminLePlusCourt("The Beatles", "Kendji Girac");
//    System.out.println("--------------------------");
//    graph.trouverCheminMaxMentions("The Beatles", "Kendji Girac");

    /*AJOUTER pour examen !!!
    * A décommenter et tester un par un sinon terminal très long */
//    System.out.println("--------------------------");
//    graph.bfs("The Beatles");

//    System.out.println("--------------------------");
//    System.out.println("Nombre d’artistes atteignables : " + graph.nbArtistesAtteignables("The Beatles"));

//    System.out.println("--------------------------");
//    Artist populaire = graph.artisteLePlusPopulaire("The Beatles");
//    if (populaire != null) System.out.println("Artiste le plus populaire : " + populaire.getName());

//    System.out.println("--------------------------");
//    graph.cheminMinimisantNombreDeMentions("The Beatles", "Kendji Girac");

//    System.out.println("--------------------------");
//    graph.mentionsEntrantes("Kendji Girac").forEach(System.out::println);

//    System.out.println("--------------------------");
//    Map<Artist, Set<Artist>> listeAdjacence = graph.toListeDAdjacence();
//    System.out.println(listeAdjacence.size());
  }
}
