package graphe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Graph {

  private Map<Integer, Artist> artists = new HashMap<>();
  private Map<String, Artist> artistsName = new HashMap<>();
  private Map<Artist, Set<Mention>> mentions = new HashMap<>();


  /**
   * Constructs a Graph from the given files.
   *
   * @param artistsFile  the file containing artist data
   * @param mentionsFile the file containing mention data
   */
  public Graph(String artistsFile, String mentionsFile) {
    loadArtists(artistsFile);
    loadMentions(mentionsFile);
  }

  /**
   * Loads artists from the given file.
   *
   * @param file the file containing artist data
   */
  private void loadArtists(String file) {
    try (BufferedReader scanner = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = scanner.readLine()) != null) {
        String[] infos = line.split(",", 3);
        int idArtist = Integer.parseInt(infos[0]);
        String name = infos[1];
        String category = infos[2];
        Artist artist = new Artist(idArtist, name, category);
        artists.put(idArtist, artist);
        artistsName.put(name, artist);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Loads mentions (connections) from the given file.
   *
   * @param file the file containing mention data
   */
  private void loadMentions(String file) {
    try (BufferedReader scanner = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = scanner.readLine()) != null) {
        String[] infos = line.split(",", 3);
        Artist sourceArtist = artists.get(Integer.parseInt(infos[0]));
        Artist destinationArtist = artists.get(Integer.parseInt(infos[1]));
        int mentionCount = Integer.parseInt(infos[2]);
        addMention(sourceArtist, destinationArtist, mentionCount);
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * Adds a mention (connection) between two artists.
   *
   * @param sourceArtist      the source artist
   * @param destinationArtist the destination artist
   * @param mentionCount      the number of mentions
   */
  private void addMention(Artist sourceArtist, Artist destinationArtist, int mentionCount) {
    Mention mention = new Mention(sourceArtist, destinationArtist, mentionCount);
    Set<Mention> mentionsSrcArtist = mentions.get(sourceArtist);
    if (mentionsSrcArtist == null) {
      mentionsSrcArtist = new HashSet<>();
      mentions.put(sourceArtist, mentionsSrcArtist);
    }

    mentionsSrcArtist.add(mention);
  }

  public void trouverCheminLePlusCourt(String sourceArtist, String destinationArtist) {
    Artist src = artistsName.get(sourceArtist);
    Artist dst = artistsName.get(destinationArtist);

    Set<Artist> visited = new HashSet<>();
    Deque<Artist> artistQueue = new LinkedList<>();
    Map<Artist, Mention> artistMentions = new HashMap<>();
    boolean isFound = false;

    artistQueue.add(src);
    visited.add(src);

    while (!artistQueue.isEmpty()) {
      Artist currentArtist = artistQueue.poll();
      Set<Mention> mentionsSrcArtist = mentions.get(currentArtist);

      if (mentionsSrcArtist == null) {
        continue;
      }

      if (currentArtist.equals(dst)) {
        isFound = true;
        break;
      }

      for (Mention mention : mentionsSrcArtist) {
        Artist a = mention.getDestinationArtist();
        if (!visited.contains(a)) {
          visited.add(a);
          artistQueue.add(a);
          artistMentions.put(a, mention);
        }
      }
    }
    if (!isFound) {
      throw new RuntimeException("Aucun chemin entre " + sourceArtist + " et " + destinationArtist);
    }
    System.out.println(print(artistMentions, src, dst));
  }

  public void trouverCheminMaxMentions(String sourceArtist, String destinationArtist) {
    Artist src = artistsName.get(sourceArtist);
    Artist dst = artistsName.get(destinationArtist);

    Artist currentArtist;
    boolean isFound = false;

    Map<Artist, Mention> artistMentions = new HashMap<>();
    TreeMap<Artist, Double> artistsToExplore = new TreeMap<>(
        Comparator.comparing(Artist::getDistanceFromSource).thenComparing(Artist::getIdArtist));
    Set<Artist> exploredArtists  = new HashSet<>();

    src.setDistanceFromSource(0.0);
    artistsToExplore.put(src, 0.0);

    while (!artistsToExplore.isEmpty()) {
      currentArtist = artistsToExplore.pollFirstEntry().getKey();
      exploredArtists.add(currentArtist);

      if (currentArtist.equals(dst)) {
        isFound = true;
        break;
      }

      Set<Mention> outgoingMentions  = mentions.get(currentArtist);
      if (outgoingMentions == null) {
        continue;
      }

      for (Mention mention : outgoingMentions) {
        Artist nextArtist  = mention.getDestinationArtist();
        if (exploredArtists .contains(nextArtist )) {
          continue;
        }

        double weight = 1.0 / mention.getMention();
        double newDistance = currentArtist.getDistanceFromSource() + weight;

        if (!artistsToExplore.containsKey(nextArtist ) || newDistance < artistsToExplore.get(nextArtist)) {
          artistsToExplore.remove(nextArtist );
          nextArtist.setDistanceFromSource(newDistance);
          artistsToExplore.put(nextArtist, newDistance);
          artistMentions.put(nextArtist, mention);
        }
      }
    }
    if (!isFound) {
      throw new RuntimeException(
          "Aucun chemin trouvé entre " + sourceArtist + " et " + destinationArtist);
    }

    System.out.println(print(artistMentions, src, dst));
  }

  public String print(Map<Artist, Mention> artistMentions, Artist src, Artist dst) {
    String output = "";
    double mentionCount = 0;
    int pathLength = 0;

    Artist currentArtist = dst;
    while (currentArtist != null && !currentArtist.equals(src)) {
      Mention mention = artistMentions.get(currentArtist);
      if (mention != null) {
        mentionCount += 1.0 / mention.getMention();
        pathLength++;
      }
      output = currentArtist.getName() + " (" + currentArtist.getCategory() + ")\n" + output;

      currentArtist = artistMentions.get(currentArtist).getSourceArtist();
    }

    output = src.getName() + " (" + src.getCategory() + ")\n" + output;

    return "Longueur du chemin : " + pathLength + "\n" + "Coût total du chemin : " + mentionCount
        + "\n" + "Chemin :\n" + output;
  }


  /***********************************************************
   MÉTHODE GÉNÉRÉE (possible à l'exam de Juin 2025)
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
   * Affiche le chemin avec le plus petit nombre de mentions
   * entre deux artistes (d’un artiste source à un artiste destination).
   * Utilise un parcours en largeur (BFS).
   *
   * @param sourceArtist nom de l’artiste de départ
   * @param destinationArtist nom de l’artiste d’arrivée
   */
  public void cheminMinimisantNombreDeMentions(String sourceArtist, String destinationArtist) {
    //TODO
    // A ADAPTER

    Set<Artist> visited = new HashSet<>();
    Deque<Artist> queue = new LinkedList<>();

    // On récupère les objets Artist
    Artist artistDepart = artistsName.get(sourceArtist);
    Artist artistArrivee = artistsName.get(destinationArtist);

    queue.add(artistDepart);
    visited.add(artistDepart);

    while (!queue.isEmpty()) {
      Artist current = queue.poll();

      if (current.equals(artistArrivee)) {
        break;
      }

      // Pour chaque mention sortante depuis current...
      Set<Mention> mentionsSortantes = mentions.get(current);
      if (mentionsSortantes != null) {
        for (Mention m : mentionsSortantes) {
          Artist voisin = m.getDestinationArtist();
          if (!visited.contains(voisin)) {
            queue.add(voisin);
            visited.add(voisin);
          }
        }
      }
    }

  }


  /**
   * Renvoie toutes les mentions entrantes vers un artiste donné.
   * Autrement dit, toutes les mentions dont la destination est cet artiste.
   *
   * @param nomArtist nom de l’artiste ciblé
   * @return ensemble des mentions entrantes (Set<Mention>), ou un ensemble vide si aucun trouvé
   */
  public Set<Mention> mentionsEntrantes(String nomArtist) {
    //TODO
    Artist target = artistsName.get(nomArtist); // On récupère l’objet Artist correspondant au nom donné
    Set<Mention> entrantes = new HashSet<>(); // On crée un ensemble vide pour stocker les mentions entrantes

    // Si l’artiste n’existe pas dans la map, on retourne l’ensemble vide directement
    if (target == null) {
      return entrantes; // Artiste introuvable → retourne un ensemble vide
    }

    // On parcourt toutes les entrées (source → ensemble de mentions sortantes) du graphe
    for (Map.Entry<Artist, Set<Mention>> entry : mentions.entrySet()) {
      // Pour chaque mention sortante de l’artiste courant
      for (Mention m : entry.getValue()) {
        // Si la mention aboutit à l’artiste ciblé → c’est une mention entrante
        if (m.getDestinationArtist().equals(target)) {
          entrantes.add(m); // On l’ajoute à l’ensemble résultat
        }
      }
    }

    // On retourne toutes les mentions entrantes trouvées
    return entrantes;
  }


  /**
   * Construit et retourne une liste d'adjacence simplifiée :
   * chaque artiste est associé aux artistes qu'il mentionne.
   */
  public Map<Artist, Set<Artist>> toListeDAdjacence() {
    //TODO
    return null;
  }

}