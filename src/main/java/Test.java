import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import entity.Container;
import entity.Folha;
import entity.No;
import entity.Professor;
import entity.Proposta;
import entity.Turma;
import input.ContainerFileInputParser;
import restrictions.RestrictionsChecker;
import solver.CombinatorialAuctionSolver;
import utils.CollectionUtils;

public class Test {
	
	static int getCargaHoraria(List<Turma> turmas){
		return turmas
				.parallelStream()
				.reduce(0,
						(sum, p) -> sum += p.getCargaHoraria(),
						(sum1, sum2) -> sum1 + sum2);
	}
	
	public static void main(String[] args) throws FileNotFoundException{
		File f = new File("src/main/java/entrada.json");	
		Container c = ContainerFileInputParser.readFile(f);
		
		Collections.shuffle(c.getTurmas());
		
		List<Turma> turmasSemestre1 = c.getTurmas().stream().filter(t -> t.getSemestre() == 1).collect(Collectors.toList());
		List<Turma> turmasSemestre2 = c.getTurmas().stream().filter(t -> t.getSemestre() == 2).collect(Collectors.toList());

		System.out.println(getCargaHoraria(turmasSemestre1));
		
		System.out.println("Semestre 1 QTDE: " + turmasSemestre1.size());
		System.out.println("Semestre 2 QTDE: " + turmasSemestre2.size());
		
		List<Professor> professores = new ArrayList<>();
		//EFETIVOS
		professores.addAll(geraListaProfessor(26, 8, 20, 0));
		//Colabs
		professores.addAll(geraListaProfessor(6, 16, 20, professores.size()));

		runTestForTurmas(turmasSemestre1, professores);
		runTestForTurmas(turmasSemestre2, professores);
		
		
	}
	
	private static void runTestForTurmas(List<Turma> turmas, List<Professor> professores){
		System.out.println("profs gerados");
		List<Proposta> propostas = new ArrayList<>();
		propostas.addAll(geraPropostasAleatoriamente(turmas, professores));
		
		System.out.println(propostas.size());
		System.out.println(propostas);

		System.out.println("props geradas");
		List<Folha> folhas = CombinatorialAuctionSolver.solve(propostas, turmas, professores);

		System.out.println(folhas.size());
		System.out.println(folhas);
	}
	
	private static List<Proposta> geraPropostasAleatoriamente(List<Turma> turmas, List<Professor> professores){
		
		List<Turma> tIteration = CollectionUtils.clone(turmas);
		//All turmas covered
		List<Proposta> propostas = new ArrayList<>();
		for(Professor p : professores){

			propostas.add(mountValidProposta(tIteration, turmas, p));
		}
		
		int counter = 0;
		int half = propostas.size() / 2;
		int addPoint = half + (half / 2);
		for(Proposta p : propostas){
			if(counter++ >= addPoint){
				addNTurma(tIteration, turmas, p, 1);
			}
		}
		
		
		return propostas;
	}
	
	private static void addNTurma(List<Turma> tIteration, List<Turma> turmas, Proposta p, int i) {
		
		Collections.shuffle(tIteration);
		Turma t = null;
		int counter = 0;
		int counter2 = 0;
		if(tIteration.isEmpty()){
			Collections.shuffle(turmas);
			tIteration = CollectionUtils.clone(turmas);
		}
		while (RestrictionsChecker.isRespeitaRestricoes(p) && counter <= i) {
			t = tIteration.remove(0);
			p.addTurma(t);
			if(p.getCargaHoraria() > p.getProfessor().getCargaHorariaMaxima()){
				p.removeTurma(t);
				tIteration.add(t);
				counter2++;
				if(counter2 == tIteration.size()){
					tIteration.clear();
				}
			}
			
			if(tIteration.isEmpty()){
				Collections.shuffle(turmas);
				tIteration = CollectionUtils.clone(turmas);
			}
			counter++;
		}
		
	}

	private static Proposta mountValidProposta(List<Turma> tIteration, List<Turma> turmas, Professor p ){
		Proposta prop = new Proposta();
		prop.setProfessor(p);
		prop.setValor(floatRangedRandom(6, 10));
		
		Turma t = null;
		int counter = 0;
		if(tIteration.isEmpty()){
			Collections.shuffle(turmas);
			tIteration = CollectionUtils.clone(turmas);
		}
		while (!RestrictionsChecker.isRespeitaRestricoes(prop)) {
			t = tIteration.remove(0);
			prop.addTurma(t);
			if(prop.getCargaHoraria() > p.getCargaHorariaMaxima()){
				prop.removeTurma(t);
				tIteration.add(t);
				counter++;
				if(counter == tIteration.size()){
					tIteration.clear();
				}
			}
			
			if(tIteration.isEmpty()){
				Collections.shuffle(turmas);
				tIteration = CollectionUtils.clone(turmas);
			}
		}
		
		return prop;
	}
	
	private static int roudUp(int i, int j){
		return (int) Math.ceil(i / (double) j);
	}
	
	public static List<Professor> geraListaProfessor(int qtde, int chMin, int chMax, int countStart){
		List<Professor> retorno = new ArrayList<>();
		for(int i = 0; i < qtde; i++){
			retorno.add(new Professor(countStart + i, "P" + (countStart + i), chMin, chMax));
		}
		
		return retorno;
	}
	
	private static float floatRangedRandom(int max, int min){
		
		return (float) Math.min(min + (Math.random() * max), max) ;
		
	}
	
	private static int intRangedRandom(int max, int min){
		
		return (int) Math.min(min + (Math.random() * max), max) ;
		
	}

}