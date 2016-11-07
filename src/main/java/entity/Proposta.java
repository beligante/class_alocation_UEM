package entity;

import java.util.ArrayList;
import java.util.List;

public class Proposta {
	
	private Professor professor;
	private float valor;
	private List<Turma> turmas = new ArrayList<>();
	
	public Proposta(){}
	
	public Proposta(Professor professor, float valor, List<Turma> turmas) {
		super();
		this.professor = professor;
		this.valor = valor;
		this.turmas = turmas;
	}
	public Professor getProfessor() {
		return professor;
	}
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public List<Turma> getTurmas() {
		return turmas;
	}
	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}
	
	public void addTurma(Turma turma) {
		this.turmas.add(turma);
	}
	
	public void removeTurma(Turma turma){
		this.turmas.remove(turma);
	}
	
	
	public int getCargaHoraria(){
		return turmas
				.parallelStream()
				.reduce(0,
						(sum, p) -> sum += p.getCargaHoraria(),
						(sum1, sum2) -> sum1 + sum2);
	}
	
	@Override
	public String toString() {
		return String.format("Professor (%s) - Turmas = %s \n", professor.getId(), turmas.toString());
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.toString().equals(this.toString());
	}
}
