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
    //On récupère l'artiste source à partir de son nom (par ex. "The Beatles")
    Artist source = artistsbyName.get(nomArtist);

    // Si l'artiste n'existe pas dans la map, on affiche un message d'erreur et on quitte la méthode
    if (source == null) {
      System.out.println("Artiste introuvable : " + nomArtist);
      return;
    }

    Set<Artist> visited = new HashSet<>(); // Ensemble des artistes déjà visités (pour ne pas les visiter plusieurs fois)
    Deque<Artist> queue = new LinkedList<>(); // File utilisée pour le parcours en largeur (FIFO = premier entré, premier sorti)

    // On ajoute la source dans la file et on la marque comme visitée
    queue.add(source);
    visited.add(source);

    // Tant qu'il reste des artistes à explorer dans la file
    while (!queue.isEmpty()) {
      Artist current = queue.poll(); // On enlève le premier artiste de la file
      System.out.println(current.getName()); // On affiche son nom (ordre de visite BFS)

      /* ITER :
      for (Mention m : listeAdjacence.getOrDefault(current, new HashSet<>())) {
        Artist voisin = m.getDestination();
        if (visited.add(voisin)) {
          queue.add(voisin);
        }
      }
      */

      /* SANS ITER : */
      // On récupère toutes les mentions sortantes depuis cet artiste (ses voisins)
      Set<Mention> out = listeAdjacence.get(current);

      // Si l’artiste a effectivement des voisins (il peut n’en avoir aucun → null), on les explore
      if (out != null) {
        for (Mention m : out) {
          Artist voisin = m.getDestination(); // ou getDestinationArtist() selon ta classe Mention

          // Si ce voisin n'a pas encore été visité, on l'ajoute à la file et on le marque comme visité
          if (!visited.contains(voisin)) {
            queue.add(voisin);
            visited.add(voisin);
          }
        }
      }

    }
  }

  /**
   * Chemin le plus court en NOMBRE DE MENTIONS (BFS + parent).
   * Affiche uniquement la suite d’artistes (pas la longueur/cout).
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
  //TODO : Compléter
  public void trouverCheminMaxMentions(String sourceArtist, String destinationArtist) {
    Artist src = artistsbyName.get(sourceArtist);
    Artist dst = artistsbyName.get(destinationArtist);

    if (src == null || dst == null) {
      System.out.println("Artiste source ou cible introuvable.");
      return;
    }

    // Dijkstra : dist stocke le "coût" cumulé (somme des 1/mentions)
    Map<Artist, Double> dist = new HashMap<>();
    Map<Artist, Artist> parent = new HashMap<>();
    // File de priorité sur dist
    java.util.PriorityQueue<Artist> pq = new java.util.PriorityQueue<>(Comparator.comparingDouble(a -> dist.getOrDefault(a, Double.POSITIVE_INFINITY)));

    // Init
    for (Artist a : artistsById.values()) {
      dist.put(a, Double.POSITIVE_INFINITY);
    }
    dist.put(src, 0.0);
    parent.put(src, null);
    pq.add(src);

    boolean found = false;

    while (!pq.isEmpty()) {
      Artist u = pq.poll();

      if (u.equals(dst)) {
        found = true;
        break;
      }

      for (Mention m : listeAdjacence.getOrDefault(u, new HashSet<>())) {
        Artist v = m.getDestination();
        // On suppose que Mention possède un entier "nombre de mentions" accessible via getMention()
        int nbMentions = m.getNumber();
        if (nbMentions <= 0) continue; // sécurité

        double w = 1.0 / nbMentions;
        double alt = dist.get(u) + w;

        if (alt < dist.get(v)) {
          dist.put(v, alt);
          parent.put(v, u);
          pq.remove(v); // réactualiser sa position si déjà présent
          pq.add(v);
        }
      }
    }

    if (!found) {
      System.out.println("Aucun chemin trouvé entre " + sourceArtist + " et " + destinationArtist + ".");
      return;
    }

    // Reconstruction et affichage
    Deque<Artist> pile = new ArrayDeque<>();
    for (Artist a = dst; a != null; a = parent.get(a)) {
      pile.push(a);
    }
    while (!pile.isEmpty()) {
      System.out.println(pile.pop());
    }
  }

}