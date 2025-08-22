package elections_TRAN_THUY;

public class Parti {
    private String nom ;
    private int nbVotes ;

    public Parti(String nom) {
        this.nom = nom;
    }

    public Parti(String nom, int nbVotes) {
        this(nom) ;
        this.nbVotes = nbVotes;
    }

    public String getNom() {
        return nom;
    }

    public int getNbVotes() {
        return nbVotes;
    }

    public void ajouterVotes(int nbVotes) {
        this.nbVotes += nbVotes;
    }

    @Override
    public String toString() {
        return nom + " : " + nbVotes + " votes";
    }

}
