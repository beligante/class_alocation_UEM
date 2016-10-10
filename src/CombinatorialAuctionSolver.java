

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import entity.Folha;
import entity.Professor;
import entity.Proposta;
import entity.Turma;
import utils.CollectionUtils;

public class CombinatorialAuctionSolver {
	
	public static List<Folha> solve(List<Proposta> propostas, 
			List<Turma> turmas, List<Professor> professores){
		SearchTreeMounter s = new SearchTreeMounter(propostas, turmas, professores);
		
		List<Folha> folhas = s.mount();
		Collections.sort(folhas, new Comparator<Folha>() {

			@Override
			public int compare(Folha o1, Folha o2) {
				return Float.compare(o2.getValor(), o1.getValor());
			}
			
		});
		
		if(CollectionUtils.isNullOrEmpty(folhas)){
			System.out.println("Nenhuma solução foi encontrada");
			return new ArrayList<>();
		}
		
		List<Folha> result = new ArrayList<>();
		result.add(folhas.get(0));
		int index = 1;
		while(index < folhas.size() && folhas.get(index).getValor() == result.get(0).getValor()){
			result.add(folhas.get(index));
			index++;
		}
		return result;
	}

}
