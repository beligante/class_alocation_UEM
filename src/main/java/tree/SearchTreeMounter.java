package tree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

import entity.Folha;
import entity.No;
import entity.Professor;
import entity.Proposta;
import entity.Turma;
import restrictions.RestrictionsChecker;
import utils.CollectionUtils;

public class SearchTreeMounter {

	private List<Folha> folhas;
	private int penultimateLevel;
	private List<Proposta> propostas;
	private	PriorityQueue<Turma> turmas; 
	private Map<Professor, Map<Turma, Float>> valorEstimadoMap;
	private Map<String, Proposta> valorProposta;
	
	public SearchTreeMounter(List<Proposta> propostas, List<Turma> turmas, List<Professor> professores) {
		folhas = new ArrayList<Folha>();
		penultimateLevel = professores.size() - 1;
		this.propostas = propostas;
		this.turmas = new PriorityQueue<Turma>(new Comparator<Turma>() {

			public int compare(Turma o1, Turma o2) {
				return Integer.compare(o1.getId(), o2.getId());
			}
		});
		this.turmas.addAll(turmas);
		mountValorPropostaMap();
		mountValorEstimadoMap();
	}
	
	public List<Folha> mount(){
		
		No raiz = new No(null, null);
		
		mountTree(raiz, new ArrayList<Proposta>(propostas), 0, 0, turmas);
		
		return new ArrayList(folhas);
	}

	private void mountTree(No noPai, List<Proposta> propostas, int nivel, float acumulador, PriorityQueue<Turma> disponiveis) {
		if(CollectionUtils.isNullOrEmpty(propostas)){return;}
		if(nivel == penultimateLevel){
			PriorityQueue<Turma> withoutIntersection = null;
			No filho = null;
			for(Proposta p : propostas){
				if(!RestrictionsChecker.isRespeitaRestricoes(p)){continue;}
				withoutIntersection = new PriorityQueue<Turma>(disponiveis);
				withoutIntersection.removeAll(p.getTurmas());
				if(!withoutIntersection.isEmpty()){
					continue;
				}
				filho = new No(noPai, p);
				folhas.add(new Folha(filho, acumulador + p.getValor()));
			}
		}else{
			Turma pivo = disponiveis.peek();
			List<Proposta> bidsSelected = propostas.parallelStream().filter(p -> p.getTurmas().contains(pivo)).collect(Collectors.toList());
			List<Proposta> bidsToAdd = new ArrayList<Proposta>();
			Set<Proposta> allCombinations = new HashSet<Proposta>();
			int tamanhoInicial = bidsSelected.size();
			Proposta roadBid = null;
			
			for (int i = 0; i < tamanhoInicial; i++) {
				roadBid = bidsSelected.get(i);
				
				allCombinations.addAll(geraCombinacoesPossiveis(roadBid,propostas));
			}
			
			bidsSelected.addAll(allCombinations.parallelStream().filter(p -> p.getTurmas().contains(pivo)).collect(Collectors.toList()));
			bidsToAdd.addAll(allCombinations.parallelStream().filter(p -> !p.getTurmas().contains(pivo)).collect(Collectors.toList()));
			
			No noFilho;
			PriorityQueue<Turma> newTurmasDisponiveis = null;
			List<Proposta> temp = new ArrayList<Proposta>();
			for(Proposta p : bidsSelected){
				if(!RestrictionsChecker.isRespeitaRestricoes(p)){
					continue;
				}
				newTurmasDisponiveis = new PriorityQueue<>(disponiveis);
				newTurmasDisponiveis.removeAll(p.getTurmas());
				if(newTurmasDisponiveis.isEmpty()){
					continue;
				}
				temp = CollectionUtils.union(propostas, bidsToAdd);
				temp = temp.parallelStream()
						.filter(f -> f.getProfessor() != p.getProfessor())
						.filter(f -> Collections.disjoint(f.getTurmas(), p.getTurmas()))
						.collect(Collectors.toList());
				noFilho = new No(noPai, p);
				mountTree(noFilho, temp, nivel + 1, acumulador + p.getValor(), newTurmasDisponiveis);
			}
		}
	}

	private Collection<Proposta> geraCombinacoesPossiveis(Proposta roadBid, List<Proposta> propostas) {
		
		Set<Proposta> result = new HashSet<>();
		List<Turma> intersection = null;
		List<List<Turma>> combinations = null;
		List<Turma> roadBidTurmasCopy = null;
		List<Turma> pTurmasCopy = null;
 		for(Proposta p : propostas){
			if(roadBid == p){continue;}
			intersection = CollectionUtils.intersection(roadBid.getTurmas(), p.getTurmas());
			if(CollectionUtils.isNullOrEmpty(intersection)){
				continue;
			}
			roadBidTurmasCopy = new ArrayList<>(roadBid.getTurmas());
			pTurmasCopy = new ArrayList<>(p.getTurmas());
			combinations = CollectionUtils.allCombinations(intersection);
			roadBidTurmasCopy.removeAll(intersection);
			pTurmasCopy.removeAll(intersection);
			
			for(List<Turma> c : combinations){
				result.add(
						mountPropostaWithValorEstimado(
								roadBid.getProfessor(), 
								CollectionUtils.union(roadBidTurmasCopy, c)));
				result.add(
						mountPropostaWithValorEstimado(
								p.getProfessor(), 
								CollectionUtils.union(pTurmasCopy, c)));
			}
		}
		
 		result.removeAll(propostas);
 		
		return result;
	}
	
	private void mountValorPropostaMap(){
		valorProposta = new HashMap<>();
		for(Proposta p : propostas){
			valorProposta.put(getKey(p.getProfessor(), p.getTurmas()), p);
		}
	}
	
	private String getKey(Professor p, List<Turma> turmas){
		StringBuffer keyBuffer = new StringBuffer();
		keyBuffer.append("P:").append(p.getId()).append("-");
		keyBuffer.append("T:");
		for(Turma t : turmas){
			keyBuffer.append(t.getId()).append(".");
		}
		return keyBuffer.toString();
	}
	
	private void mountValorEstimadoMap(){
		valorEstimadoMap = new HashMap<>();
		float valorEstimado = 0F;
		for(Proposta p : propostas){
			if(valorEstimadoMap.get(p.getProfessor()) == null){
				valorEstimadoMap.put(p.getProfessor(), new HashMap<>());
			}
			valorEstimado = p.getValor() / ((float) p.getTurmas().size());
			
			for(Turma t : p.getTurmas()){
				if(valorEstimadoMap.get(p.getProfessor()).get(t) == null
						|| valorEstimado > valorEstimadoMap.get(p.getProfessor()).get(t)){
					valorEstimadoMap.get(p.getProfessor()).put(t, valorEstimado);
				}
			}
		}
	}
	
	private Proposta mountPropostaWithValorEstimado(Professor professor, List<Turma> turmas){
		
		Collections.sort(turmas, new Comparator<Turma>() {

			@Override
			public int compare(Turma o1, Turma o2) {
				return Integer.compare(o1.getId(), o2.getId());
			}
		});
		
		String key = getKey(professor, turmas);
		
		if(valorProposta.get(key) != null){
			return valorProposta.get(key);
		}
		float valorEstimadoConjunto = 0;
		Map<Turma, Float> turmaValorEstimado = valorEstimadoMap.get(professor);
		for(Turma t : turmas){
			valorEstimadoConjunto += turmaValorEstimado.get(t) != null 
					? turmaValorEstimado.get(t)
					: 0;
		}
		
		return new Proposta(professor,valorEstimadoConjunto, turmas);
	}
	
}
