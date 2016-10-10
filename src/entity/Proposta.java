package entity;

import java.util.List;

public class Proposta {
	
	private Professor professor;
	private float valor;
	private List<Turma> turmas;
	
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
	
	@Override
	public String toString() {
		return String.format("Professor (%s) \n Turmas = %s", professor.getId(), turmas.toString());
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
