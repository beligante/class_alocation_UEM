package entity;

import java.util.List;

public class PropostaArquivo {

	private List<Integer> idTurmas;
	private Integer idProfessor;
	private float valor;
	public List<Integer> getIdTurmas() {
		return idTurmas;
	}
	public void setIdTurmas(List<Integer> idTurmas) {
		this.idTurmas = idTurmas;
	}
	public Integer getIdProfessor() {
		return idProfessor;
	}
	public void setIdProfessor(Integer idProfessor) {
		this.idProfessor = idProfessor;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	@Override
	public String toString() {
		return "PropostaArquivo [idTurmas=" + idTurmas + ", idProfessor=" + idProfessor + ", valor=" + valor + "]";
	}
}
