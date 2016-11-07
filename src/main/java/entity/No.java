package entity;

public class No {

	private No pai;
	private Proposta proposta;
	
	public No(No pai, Proposta proposta) {
		super();
		this.pai = pai;
		this.proposta = proposta;
	}
	
	public No getPai() {
		return pai;
	}
	public void setPai(No pai) {
		this.pai = pai;
	}
	public Proposta getProposta() {
		return proposta;
	}
	public void setProposta(Proposta proposta) {
		this.proposta = proposta;
	}
	
	public void printDecendency(){
		System.out.println(proposta);
		if(pai != null){
			pai.printDecendency();
		}
	}
}
