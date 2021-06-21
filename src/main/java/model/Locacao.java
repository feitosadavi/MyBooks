package model;

import java.sql.Date;
import java.util.ArrayList;

public class Locacao {
  private int id;
  private String status;
  private Date dataLocacao, dataDevolucao, dataColeta;
  private String horarioColeta;

  public String getHorarioColeta() {
    return horarioColeta;
  }

  public void setHorarioColeta(String horarioColeta) {
    this.horarioColeta = horarioColeta;
  }

  public ArrayList<Livro> getLivro() {
    return livro;
  }

  public void setLivro(ArrayList<Livro> livro) {
    this.livro = livro;
  }

  private Usuario aluno;
  private Usuario bibliotecario;
  private ArrayList<Livro> livro;
	
	public ArrayList<Livro> getLivros() {
		return livro;
	}
	public void setLivros(ArrayList<Livro> livro) {
		this.livro = livro;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDataLocacao() {
		return dataLocacao;
	}
	public void setDataLocacao(Date dataLocacao) {
		this.dataLocacao = dataLocacao;
	}
	public Date getDataDevolucao() {
		return dataDevolucao;
	}
	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}
	
	public Date getDataColeta() {
		return dataColeta;
	}
	public void setDataColeta(Date dataColeta) {
		this.dataColeta = dataColeta;
	}
	public Usuario getAluno() {
		return aluno;
	}
	public void setAluno(Usuario aluno) {
		this.aluno = aluno;
	}
	public Usuario getBibliotecario() {
		return bibliotecario;
	}
	public void setBibliotecario(Usuario bibliotecario) {
		this.bibliotecario = bibliotecario;
	}
	
	
}