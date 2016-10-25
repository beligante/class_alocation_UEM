package entity;

import java.util.List;

public class Container {

	private List<Turma> turmas;
	private List<Professor> professores;
	private List<PropostaArquivo> propostas;
	
	public List<Turma> getTurmas() {
		return turmas;
	}
	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}
	public List<Professor> getProfessores() {
		return professores;
	}
	public void setProfessores(List<Professor> professores) {
		this.professores = professores;
	}
	public List<PropostaArquivo> getPropostas() {
		return propostas;
	}
	public void setPropostas(List<PropostaArquivo> propostas) {
		this.propostas = propostas;
	}
	@Override
	public String toString() {
		return "Container [turmas=" + turmas + ", professores=" + professores + ", propostas=" + propostas + "]";
	}	
	
}
