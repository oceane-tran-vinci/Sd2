package inscription_TRAN_THUY;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		Etudiant e1= new Etudiant (1,"Christophe","Damas");
		Etudiant e2= new Etudiant (2,"José","Vander Meulen");
		Etudiant e3= new Etudiant (3,"Isabelle","Cambron");
		Etudiant e4= new Etudiant (4,"Gregory","Seront");
		Etudiant e5= new Etudiant (5,"Brigitte","Lehmann");
		Etudiant e6= new Etudiant (6,"Stephanie","Ferneeuw");
		PlageHoraire p1= new PlageHoraire(9, 0);
		PlageHoraire p2= new PlageHoraire(9, 30);
		PlageHoraire p3= new PlageHoraire(10, 0);
		InscriptionsOral i= new InscriptionsOral();
		i.inscrireEtudiant(e1, p1);
		i.inscrireEtudiant(e2, p1);
		i.inscrireEtudiant(e3, p1);
		i.inscrireEtudiant(e4, p2);
		i.inscrireEtudiant(e5, p2);
		i.inscrireEtudiant(e6, p3);
		i.afficherHoraire();
		System.out.println("----------------");
		i.echanger(e1, e6);
		i.afficherHoraire();
		System.out.println();
		Thread.sleep(500);
		i.inscrireEtudiant(e6, p3);


	}
}
