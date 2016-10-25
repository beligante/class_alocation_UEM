package entity;

public class Folha {
	private No no;
	private float valor;
	
	public Folha(No no, float valor) {
		super();
		this.no = no;
		this.valor = valor;
	}
	public No getNo() {
		return no;
	}
	public void setNo(No no) {
		this.no = no;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
}
