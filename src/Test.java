import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entity.Folha;
import entity.No;
import entity.Professor;
import entity.Proposta;
import entity.Turma;

public class Test {

	public static void main(String[] args) {

		List<Turma> turmas = new ArrayList<>();
		List<Professor> profs = new ArrayList<>();
		List<Proposta> props = new ArrayList<>();
		turmas.add(new Turma(0, "T0", 20));
		for(int i = 1; i < 90; i++){
			turmas.add(new Turma(i, "T"+i, 20));
		}
		
		int offset = 0;
		for(int i = 0; i < 30; i++){
			profs.add(new Professor(i, "P"+i, 20, 60));
			offset = i * 3;
			props.add(new Proposta(profs.get(i), 10, 
					Arrays.asList(turmas.get(offset), 
							turmas.get(offset + 1), 
							turmas.get(offset + 2))));
		}
		props.add(new Proposta(profs.get(2), 10, props.get(0).getTurmas()));
		props.add(new Proposta(profs.get(3), 10, props.get(0).getTurmas()));
		props.add(new Proposta(profs.get(4), 10, props.get(2).getTurmas()));
		props.add(new Proposta(profs.get(5), 10, props.get(3).getTurmas()));
		props.add(new Proposta(profs.get(6), 10, props.get(4).getTurmas()));
		props.add(new Proposta(profs.get(7), 10, props.get(5).getTurmas()));
		props.add(new Proposta(profs.get(8), 10, props.get(6).getTurmas()));
		props.add(new Proposta(profs.get(9), 10, props.get(7).getTurmas()));
		props.add(new Proposta(profs.get(10), 10, props.get(7).getTurmas()));
		props.add(new Proposta(profs.get(11), 10, props.get(7).getTurmas()));
		props.add(new Proposta(profs.get(12), 10, props.get(7).getTurmas()));
		props.add(new Proposta(profs.get(13), 10, props.get(7).getTurmas()));
		props.add(new Proposta(profs.get(14), 10, props.get(7).getTurmas()));
		//props.forEach(System.out::println);
		

		long time = System.currentTimeMillis();
		//System.out.println(time);
		List<Folha> folhas = CombinatorialAuctionSolver.solve(props, turmas, profs);
		System.out.println(System.currentTimeMillis() - time);
		
		folhas.forEach(f -> printCaminho(f.getNo()));
		folhas.forEach(f -> System.out.println(f.getValor()));
	}
	
	private static void printCaminho(No no){
		if(no.getPai() == null){
			System.out.println("===========================");
			return;
		}
		System.out.println(no.getProposta());
		printCaminho(no.getPai());
	}
}
