package graphe_TRAN_THUY;

public class Main {
	public static void main(String[] args) throws Exception {
		Graph g = new Graph("Exam_Sept2022/flight.xml");
		g.bfs(g.getAirport("JFK"));
	}
}
