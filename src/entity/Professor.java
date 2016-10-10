package entity;

public class Professor {

	private int id;
	private String nome;
	private int cargaHorariaMinima;
	private int cargaHorariaMaxima;
	
	public Professor(){}
	
	public Professor(int id, String nome, int cargaHorariaMinima, int cargaHorariaMaxima) {
		super();
		this.id = id;
		this.nome = nome;
		this.cargaHorariaMinima = cargaHorariaMinima;
		this.cargaHorariaMaxima = cargaHorariaMaxima;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCargaHorariaMaxima() {
		return cargaHorariaMaxima;
	}

	public void setCargaHorariaMaxima(int cargaHorariaMaxima) {
		this.cargaHorariaMaxima = cargaHorariaMaxima;
	}

	public int getCargaHorariaMinima() {
		return cargaHorariaMinima;
	}

	public void setCargaHorariaMinima(int cargaHorariaMinima) {
		this.cargaHorariaMinima = cargaHorariaMinima;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		return Integer.hashCode(id);
	}
	
	@Override
	public boolean equals(Object obj) {
	
		if(obj != null && obj instanceof Professor){
			return ((Professor) obj).id  == id;
		}
		return false;
	}
}
