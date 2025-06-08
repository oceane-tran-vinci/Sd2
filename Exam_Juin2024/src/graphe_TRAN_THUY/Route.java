package graphe_TRAN_THUY;

public class Route {

  private final Ville source;
  private final Ville destination;
  private final double distance;

  public Route(Ville source, Ville destination) {
    this.source = source;
    this.destination = destination;
    this.distance = Util.distance(source.getLongitude(), source.getLatitude(), destination.getLongitude(), destination.getLatitude());
  }

  public Ville getSource() {
    return source;
  }

  public Ville getDestination() {
    return destination;
  }

  public double getDistance() {
    return distance;
  }

  public String toString() {
   return source.getNom() + " -> " + destination.getNom() + " (" + Math.round(distance * 100.0) / 100.0 + " km)";
  }
}
