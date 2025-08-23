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
  private final HashMap<Artist, HashSet<Mention>> listeAdjacence = new HashMap<Artist, HashSet<Mention>>();


  /**
   * Constructs a Graph from the given files.
   *
   * @param artistFile  the file containing artist data
   * @param mentionFile the file containing mention data
   */
  public Graph(String artistFile, String mentionFile) {
    this.artistFile = artistFile;
    this.mentionFile = mentionFile;
    readArtists();
    readMentions();
  }

  private void readArtists() {
    try (BufferedReader br = new BufferedReader(new FileReader(this.artistFile))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] datas = line.split(",");
        int id = Integer.parseInt(datas[0]);
        String name = datas[1];
        String category = datas[2];
        Artist artist = new Artist(id, name, category);
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
   * Affiche tous les artistes accessibles depuis l’artiste donné, en utilisant un parcours en
   * largeur (BFS).
   *
   * @param nomArtist nom de l’artiste de départ
   */
  public void bfs(String nomArtist) {
    //TODO
    //On récupère l'artiste source à partir de son nom (par ex. "The Beatles")
    Artist fromArtist = artistsbyName.get(nomArtist);

    // Si l'artiste n'existe pas dans la map, on affiche un message d'erreur et on quitte la méthode
    if (fromArtist == null) {
      System.out.println("Artiste introuvable : " + nomArtist);
      return;
    }

    HashSet<Artist> visited = new HashSet<>(); // Ensemble des artistes déjà visités (pour ne pas les visiter plusieurs fois)
    Queue<Artist> queue = new LinkedList<>(); // File utilisée pour le parcours en largeur (FIFO = premier entré, premier sorti)

    // On ajoute la source dans la file et on la marque comme visitée
    visited.add(fromArtist);
    queue.add(fromArtist);

    // Tant qu'il reste des artistes à explorer dans la file
    while (!queue.isEmpty()) {
      Artist current = queue.poll(); // On enlève le premier artiste de la file
      System.out.println(current.getName()); // On affiche son nom (ordre de visite BFS)

      for (Mention m : listeAdjacence.getOrDefault(current, new HashSet<>())) {
        Artist next = m.getDestination();
        // Si ce voisin n'a pas encore été visité, on l'ajoute à la file et on le marque comme visité
        if (!visited.contains(next)) {
          visited.add(next);
          queue.add(next);
        }
      }
    }

  }

  /**
   * Chemin le plus court en NOMBRE DE MENTIONS (BFS + parent). Affiche uniquement la suite
   * d’artistes (pas la longueur/cout).
   */
  //TODO : Compléter
  public void trouverCheminLePlusCourt(String from, String to) {
    Artist fromArtist = artistsbyName.get(from);
    Artist toArtist = artistsbyName.get(to);

    // (Optionnel) garde-fous simples
    if (fromArtist == null || toArtist == null) {
      System.out.println("Artiste introuvable.");
      return;
    }

    Queue<Artist> queue = new LinkedList<>();
    queue.add(fromArtist);
    HashSet<Artist> visited = new HashSet<>();
    visited.add(fromArtist);

    // Pour reconstruire le chemin
    HashMap<Artist, Artist> parent = new HashMap<>();
    parent.put(fromArtist, null);

    boolean found = false;

    while (!queue.isEmpty()) {
      Artist current = queue.poll();

      // (On peut supprimer l'affichage BFS initial)
      // System.out.println(current);

      if (current.equals(toArtist)) {
        found = true;
        break; // BFS garantit un plus court chemin en nombre d’arêtes
      }

      for (Mention mention : listeAdjacence.getOrDefault(current, new HashSet<>())) {
        Artist next = mention.getDestination();
        if (!visited.contains(next)) {
          visited.add(next);
          parent.put(next, current);
          queue.add(next);
        }
      }
    }

    if (!found) {
      System.out.println("Aucun chemin trouvé entre " + from + " et " + to + ".");
      return;
    }

    // Reconstruction du chemin to -> from puis affichage dans l'ordre
    Deque<Artist> stack = new ArrayDeque<>();
    for (Artist a = toArtist; a != null; a = parent.get(a)) {
      stack.push(a);
    }
    while (!stack.isEmpty()) {
      System.out.println(stack.pop());
    }


  }

  /**
   * Chemin "max mentions" : on privilégie les arêtes avec un grand nombre de mentions.
   * Implémentation par Dijkstra sur un coût = 1.0 / mentions (plus de mentions => coût plus
   * faible). Affiche la suite d’artistes (comme pour le plus court chemin).
   */
  //TODO : Compléter
  public void trouverCheminMaxMentions(String sourceArtist, String destinationArtist) {
    Artist fromArtist = artistsbyName.get(sourceArtist);
    Artist toArtist = artistsbyName.get(destinationArtist);

    if (fromArtist == null || toArtist == null) {
      System.out.println("Artiste source ou cible introuvable.");
      return;
    }

    Map<Artist, Double> dist = new HashMap<>();
    Map<Artist, Artist> parent = new HashMap<>();
    for (Artist a : artistsById.values()) {
      dist.put(a, Double.POSITIVE_INFINITY);
    }
    dist.put(fromArtist, 0.0);
    parent.put(fromArtist, null);

    Queue<Artist> queue = new PriorityQueue<>(
        Comparator.comparingDouble(a -> dist.getOrDefault(a, Double.POSITIVE_INFINITY)));
    queue.add(fromArtist);

    while (!queue.isEmpty()) {
      Artist current = queue.poll();
      if (current.equals(toArtist)) {
        break;
      }
      Set<Mention> out = listeAdjacence.get(current);
      if (out == null) {
        continue;
      }
      for (Mention m : out) {
        Artist next = m.getDestination();
        int nb = m.getNumber();
        if (nb <= 0) {
          continue;
        }
        double alt = dist.get(current) + 1.0 / nb;
        if (alt < dist.get(next)) {
          dist.put(next, alt);
          parent.put(next, current);
          queue.remove(next);
          queue.add(next);
        }
      }
    }

    if (!parent.containsKey(toArtist)) {
      System.out.println(
          "Aucun chemin trouvé entre " + sourceArtist + " et " + destinationArtist + ".");
      return;
    }
    Deque<Artist> pile = new ArrayDeque<>();
    for (Artist a = toArtist; a != null; a = parent.get(a)) {
      pile.push(a);
    }
    while (!pile.isEmpty()) {
      System.out.println(pile.pop());
    }
  }


}