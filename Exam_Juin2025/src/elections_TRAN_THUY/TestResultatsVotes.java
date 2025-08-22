package elections_TRAN_THUY;

public class TestResultatsVotes {
    public static void main(String[] args) {
        String[] partis = {"MR", "PS", "Ecolo", "DEFI","Les engagés","NVA","SPA"} ;
        ResultatsElectoraux resultats = new ResultatsElectoraux(partis);
        int n = 5 ;
        System.out.println("---------------------------------------------------");
        resultats.afficherNPremiers(n) ;
        resultats.ajouterVotes("Ecolo",100);
        resultats.ajouterVotes("Les engagés",50);
        resultats.ajouterVotes("DEFI",75);
        resultats.ajouterVotes("MR",20);
        resultats.ajouterVotes("PS",60);
        System.out.println("---------------------------------------------------");
        resultats.afficherNPremiers(n) ;
        resultats.ajouterVotes("Les engagés",80);
        System.out.println("---------------------------------------------------");
        resultats.afficherNPremiers(n) ;
        System.out.println("---------------------------------------------------");

    }
}
