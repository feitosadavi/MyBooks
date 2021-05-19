package model;

public class Livro {
  private int id, paginas, estoque, lancamento;
  private String nome, genero, capa;
  
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPaginas() {
		return paginas;
	}
	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}
	public int getEstoque() {
		return estoque;
	}
	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public int getLancamento() {
		return lancamento;
	}
	public void setLancamento(int lancamento) {
		this.lancamento = lancamento;
	}
	public String getCapa() {
		return capa;
	}
	public void setCapa(String capa) {
		this.capa = capa;
	}
	
	
}