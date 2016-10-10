package entity;

public class Turma {
	
	private int id;
	private String nome;
	private int cargaHoraria;
	
	public Turma() {}
	
	public Turma(int id, String nome, int cargaHoraria) {
		super();
		this.id = id;
		this.nome = nome;
		this.cargaHoraria = cargaHoraria;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getCargaHoraria() {
		return cargaHoraria;
	}
	public void setCargaHoraria(int cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Turma id(int id){
		this.id = id;
		return this;
	}
	
	@Override
	public String toString() {
		return "ID =" + id ;
	}
	
	@Override
	public int hashCode() {
		return Integer.hashCode(id);
	}
	
	@Override
	public boolean equals(Object obj) {
	
		if(obj != null && obj instanceof Turma){
			return ((Turma) obj).id  == id;
		}
		return false;
	}
}
