package restrictions;
import java.util.List;

import entity.Proposta;
import entity.Turma;
import utils.CollectionUtils;

public class RestrictionsChecker {

	public static boolean isRespeitaRestricoes(Proposta p){
		return isCargaHorariaRespeitada(p);
	}
	
	private static boolean isCargaHorariaRespeitada(Proposta p){
		if(CollectionUtils.isNullOrEmpty(p.getTurmas()) 
				&& p.getProfessor().getCargaHorariaMaxima() > 0){
			return false;
		}
		int cargaHoraria = getCargaHoraria(p.getTurmas());
		return p.getProfessor().getCargaHorariaMaxima() >= cargaHoraria 
				&& cargaHoraria >= p.getProfessor().getCargaHorariaMinima();
	}
	
	private static int getCargaHoraria(List<Turma> turmas){
		return turmas
				.parallelStream()
				.reduce(0,
						(sum, p) -> sum += p.getCargaHoraria(),
						(sum1, sum2) -> sum1 + sum2);
	}
}
