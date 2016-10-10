import java.util.Arrays;
import java.util.List;

import entity.Folha;
import entity.Professor;
import entity.Proposta;
import entity.Turma;

public class Test {

	public static void main(String[] args) {

		Professor p1 = new Professor(1, "L", 20, 40);
		Professor p2 = new Professor(2, "U", 20, 40);

		Turma t1 = new Turma(1, "MD", 20);
		Turma t2 = new Turma(2, "MD2", 20);

		Proposta pro1 = new Proposta(p1, 10, Arrays.asList(t1,t2));
		Proposta pro2 = new Proposta(p2, 10, Arrays.asList(t1, t2));
		
		List<Folha> folhas = CombinatorialAuctionSolver.solve(Arrays.asList(pro1, pro2), 
				Arrays.asList(t1, t2), Arrays.asList(p1, p2));
		
		System.out.println(folhas);
	}
}
