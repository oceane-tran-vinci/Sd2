package graphe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class Graph {
  private final String artistFile;
  private final String mentionFile;

  private Map<Integer, Artist> artistsById = new HashMap<>();
  private Map<String, Artist> artistsbyName = new HashMap<>();
  private final HashMap<Artist, HashSet<Mention>> listeAdjacence = new HashMap<Artist,HashSet<Mention>>();


  /**
   * Constructs a Graph from the given files.
   *
   * @param artistFile  the file containing artist data
   * @param mentionFile the file containing mention data
   */
  public Graph(String artistFile, String mentionFile) {
    this.artistFile=artistFile;
    this.mentionFile=mentionFile;
    readArtists();
    readMentions();
  }

  private void readArtists() {
    try (BufferedReader br = new BufferedReader(new FileReader(this.artistFile))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] datas = line.split(",");
        int id = Integer.parseInt(datas[0]) ;
        String name = datas[1];
        String category = datas[2];
        Artist artist = new Artist(id,name,category) ;
        artistsbyName.put(name, artist);
        artistsById.put(id, artist);
        listeAdjacence.put(artist, new HashSet<>());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private void readMentions() {
    try (Scanner sc = new Scanner(new File(this.mentionFile))) {
      while (sc.hasNextLine()) {
        String[] line = sc.nextLine().split(",");
        int from = Integer.parseInt(line[0]);
        int to = Integer.parseInt(line[1]);
        int number = Integer.parseInt(line[2]);
        Artist fromArtist = artistsById.get(from);
        Artist toArtist = artistsById.get(to);
        Mention mention = new Mention(fromArtist, toArtist, number);
        listeAdjacence.get(fromArtist).add(mention);
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }


  /***********************************************************
   MÉTHODES A COMPLETER (possible à l'exam)
   ***********************************************************/

  /**
   * Affiche tous les artistes accessibles depuis l’artiste donné,
   * en utilisant un parcours en largeur (BFS).
   *
   * @param nomArtist nom de l’artiste de départ
   */
  public void bfs(String nomArtist) {
    //TODO

  }

  /**
   * Chemin "max mentions" : on privilégie les arêtes avec un grand nombre de mentions.
   * Implémentation par Dijkstra sur un coût = 1.0 / mentions (plus de mentions => coût plus faible).
   * Affiche la suite d’artistes (comme pour le plus court chemin).
   */
  //TODO : Compléter
  public void trouverCheminLePlusCourt(String from, String to) {
    Artist fromArtist = artistsbyName.get(from);
    Artist toArtist = artistsbyName.get(to);

    Queue<Artist> queue = new LinkedList<>();
    queue.add(fromArtist);
    HashSet<Artist> visited = new HashSet<>();
    visited.add(fromArtist);

    while (!queue.isEmpty() ) {
      Artist current = queue.poll();
      System.out.println(current);
      for (Mention mention : listeAdjacence.get(current)) {
        Artist next = mention.getDestination();
        if (!visited.contains(next)) {
          visited.add(next);
          queue.add(next);
        }
      }
    }


  }

  /**
   * Chemin "max mentions" : on privilégie les arêtes avec un grand nombre de mentions.
   * Implémentation par Dijkstra sur un coût = 1.0 / mentions (plus de mentions => coût plus faible).
   * Affiche la suite d’artistes (comme pour le plus court chemin).
   */
  // TODO : Compléter
  public void trouverCheminMaxMentions(String sourceArtist, String destinationArtist) {
    Artist fromArtist = artistsbyName.get(sourceArtist);
    Artist toArtist   = artistsbyName.get(destinationArtist);

    Map<Artist, Double> dist   = new HashMap<>();
    Map<Artist, Artist> parent = new HashMap<>();

    // File de priorité ordonnée par la distance courante (on garde le nom 'queue')
    Queue<Artist> queue = new PriorityQueue<>(
        Comparator.comparingDouble(a -> dist.getOrDefault(a, Double.POSITIVE_INFINITY))
    );
    queue.add(fromArtist);

    // --- Boucle principale de Dijkstra ---
    while (!queue.isEmpty()) {
      Artist current = queue.poll();

      // Optionnel: arrêt anticipé
      if (current.equals(toArtist)) {
        break;
      }

      Set<Mention> out = listeAdjacence.get(current);
      if (out == null) continue;

      for (Mention m : out) {
        Artist next = m.getDestination();
        int nb = m.getNumber();
        if (nb <= 0) continue; // sécurité
      }
    }

  }


}