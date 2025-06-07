/**
 * Artist.
 */
public class Artist {
  private int id;
  private String name;
  private String category;

  /**
   * Instantiates a new Artist.
   *
   * @param id       the id
   * @param name     the name
   * @param category the category
   */
  public Artist(int id, String name, String category) {
    this.name = name;
    this.id = id;
    this.category = category;
  }

  /**
   * Gets artist's name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets artist's id.
   *
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * Gets artist's category.
   *
   * @return the category
   */
  public String getCategory() {
    return category;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Artist artist = (Artist) obj;
    return id == artist.id; // Comparer uniquement l'ID
  }

  @Override
  public int hashCode() {
    return Integer.hashCode(id); // Utiliser l'ID pour générer le hash
  }

  @Override
  public String toString() {
    return name + " (" + category + ")";
  }

}
