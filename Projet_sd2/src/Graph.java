import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Graph.
 */
public class Graph {
  private Map<Integer, Artist> artists;
  private Map<Mention, Artist> mentions;
  private Map<Artist, Set<Mention>> mentionsByArtist;
  private Map<Artist, Artist> previous;

  /**
   * Instantiates a new Graph.
   *
   * @param artistsFile  the artists file
   * @param mentionsFile the mentions file
   */
  public Graph(String artistsFile, String mentionsFile) {

    this.artists = new HashMap<>();
    this.mentions = new HashMap<>();
    this.mentionsByArtist = new HashMap<>();
    this.previous = new HashMap<>();

    try {
      BufferedReader reader = new BufferedReader(new FileReader(artistsFile));
      String line = reader.readLine();

      while (line != null) {
        String[] parts = line.split(",");
        Artist artist = new Artist(Integer.parseInt(parts[0]), parts[1], parts[2]);
        artists.put(artist.getId(), artist);
        line = reader.readLine();
      }
      reader.close();

      reader = new BufferedReader(new FileReader(mentionsFile));
      line = reader.readLine();
      while (line != null) {
        String[] parts = line.split(",");
        int artistSourceId = Integer.parseInt(parts[0]);
        int artistTargetId = Integer.parseInt(parts[1]);
        int nbMentions = Integer.parseInt(parts[2]);

        Artist artistSource = artists.get(artistSourceId);
        Artist artistTarget = artists.get(artistTargetId);

        Mention mention = new Mention(artistSource, artistTarget, nbMentions);
        mentions.put(mention, artistSource);

        mentionsByArtist.computeIfAbsent(artistSource, k-> new HashSet<>()).add(mention);
        line = reader.readLine();
      }
      reader.close();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Finds the shortest path between two artists.
   *
   * @param artistSource the name of the source artist
   * @param artistTarget the name of the target artist
   * @throws IllegalArgumentException if the source or target artist is not found
   * @throws RuntimeException if no path is found between the source and target artist
   */
  public void trouverCheminLePlusCourt(String artistSource, String artistTarget)
      throws IllegalArgumentException, RuntimeException {
    if (artistSource.isEmpty() || artistTarget.isEmpty()) {
      throw new IllegalArgumentException("Artiste source ou cible introuvable.");
    }

    Artist artistStart = artists.get(getArtistId(artistSource));
    Artist artistFinish = artists.get(getArtistId(artistTarget));

    if (artistStart == null || artistFinish == null) {
      throw new IllegalArgumentException("Artiste source ou cible introuvable.");
    }

    Set<Artist> visitedArtists = new HashSet<>();
    Map<Artist, Double> distances = new HashMap<>();
    Queue<Artist> queue = new LinkedList<>();

    boolean found = false;

    queue.add(artistStart);
    distances.put(artistStart, 0.0);
    visitedArtists.add(artistStart);

    while (!queue.isEmpty()) {
      Artist currentArtist = queue.poll();

      // Si on atteint l'artiste cible, on peut arrêter la recherche et imprimer le chemin
      if (currentArtist.equals(artistFinish)) {
        found = true;
        printPath(artistFinish, previous, distances);
        break;
      }

      // Récupération des mentions
      Set<Mention> mentioned = mentionsByArtist.get(currentArtist);
      if (mentioned == null) continue;

      for (Mention mention : mentioned) {
        Artist neighbor = mention.getTarget();
        Double cost = mention.getWeight();
        if (!visitedArtists.contains(neighbor)) {
          visitedArtists.add(neighbor);
          queue.add(neighbor);
          distances.put(neighbor, distances.get(currentArtist) + cost);
          previous.put(neighbor, currentArtist);
        }
      }
    }
    // Si aucun chemin n'est trouvé
    if (!found) {
      throw new RuntimeException("Aucun chemin trouvé entre " + artistSource + " et " + artistTarget + ".");
    }
  }

  /**
   * Finds the path with the maximum number of mentions.
   *
   * @param artistSource the source artist's name
   * @param artistTarget the target artist's name
   * @throws IllegalArgumentException if the source or target artist is not found
   * @throws RuntimeException if no path is found between the source and target artist
   */
  public void trouverCheminMaxMentions(String artistSource, String artistTarget)
      throws IllegalArgumentException, RuntimeException {
    if (artistSource.isEmpty() || artistTarget.isEmpty()) {
      throw new IllegalArgumentException("Artiste source ou cible introuvable.");
    }

    Artist artistStart = artists.get(getArtistId(artistSource));
    Artist artistFinish = artists.get(getArtistId(artistTarget));

    if (artistStart == null || artistFinish == null) {
      System.out.println("Artiste source ou cible introuvable.");
      return;
    }

    Map<Artist, Double> maxMentions = new HashMap<>();
    Map<Artist, Artist> previous = new HashMap<>();
    PriorityQueue<Artist> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(maxMentions::get));
    boolean found = false;

    for (Artist artist : artists.values()) {
      maxMentions.put(artist, Double.MAX_VALUE);
    }

    maxMentions.put(artistStart, 0.0);
    priorityQueue.add(artistStart);

    while (!priorityQueue.isEmpty()) {
      Artist currentArtist = priorityQueue.poll();

      // Si on atteint l'artiste cible, on peut arrêter la recherche et imprimer le chemin
      if (currentArtist.equals(artistFinish)) {
        printPath(artistFinish, previous, maxMentions);
        found = true;
        break;
      }

      // Récupération des mentions
      Set<Mention> mentioned = mentionsByArtist.get(currentArtist);
      if (mentioned == null) continue;

      for (Mention mention : mentioned) {
        Artist neighbor = mention.getTarget();
        double cost = mention.getWeight();
        double newMentions = maxMentions.get(currentArtist) + cost;

        // Mise à jour de la distance si un chemin plus court est trouvé
        if (newMentions < maxMentions.get(neighbor)) {
          maxMentions.put(neighbor, newMentions);
          previous.put(neighbor, currentArtist);
          priorityQueue.add(neighbor);
        }
      }
    }

    // Si aucun chemin n'est trouvé
    if (!found) {
      throw new RuntimeException("Aucun chemin trouvé entre " + artistSource + " et " + artistTarget + ".");
    }
  }

  /**
   * Gets the artist's id by name.
   *
   * @param artistName the artist name
   * @return the artist id
   */
  private int getArtistId(String artistName) {
    for (Artist artist : artists.values()) {
      if (artist.getName().equals(artistName)) {
        return artist.getId();
      }
    }
    return -1;
  }

  /**
   * Print path.
   *
   * @param artistTarget         the artist target
   * @param previousArtist       the previous artist
   * @param costs                the costs
   */
  private void printPath(Artist artistTarget, Map<Artist, Artist> previousArtist,
      Map<Artist, Double> costs) {
    List<Artist> path = new ArrayList<>();
    Artist current = artistTarget;

    while (current != null) {
      path.add(current);
      current = previousArtist.get(current);
    }

    Collections.reverse(path);

    double totalCost = costs.get(artistTarget);
    System.out.println("Longueur du chemin : " + (path.size() - 1));
    System.out.println("Coût total du chemin : " + totalCost);
    System.out.println("Chemin :");
    for (Artist artist : path) {
      System.out.println(artist.getName() + " (" + artist.getCategory() + ")");
    }
  }

}
