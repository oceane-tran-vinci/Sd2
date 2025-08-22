package graphe;

public class Mention {

  private final Artist source;
  private final Artist destination;
  private final int number;
  public Mention(Artist source, Artist destination, int number) {
    super();
    this.source = source;
    this.destination = destination;
    this.number = number;
  }
  public Artist getSource() {
    return source;
  }
  public Artist getDestination() {
    return destination;
  }
  public int getNumber() {
    return number;
  }
  @Override
  public String toString() {
    return "Mention [source=" + source + ", destination=" + destination + ", number=" + number + "]";
  }
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((destination == null) ? 0 : destination.hashCode());
    result = prime * result + ((source == null) ? 0 : source.hashCode());
    return result;
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Mention other = (Mention) obj;
    if (destination == null) {
      if (other.destination != null)
        return false;
    } else if (!destination.equals(other.destination))
      return false;
    if (source == null) {
      if (other.source != null)
        return false;
    } else if (!source.equals(other.source))
      return false;
    return true;
  }
}
