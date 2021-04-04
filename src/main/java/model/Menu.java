package model;

public class Menu {
  private int id, exibir;
  private String nome, link, icone;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getExibir() {
    return exibir;
  }

  public void setExibir(int exibir) {
    this.exibir = exibir;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getIcone() {
    return icone;
  }

  public void setIcone(String icone) {
    this.icone = icone;
  }

  @Override
  public String toString() {
    return "Menu{" +
      "id=" + id +
      ", exibir=" + exibir +
      ", nome='" + nome + '\'' +
      ", link='" + link + '\'' +
      ", icone='" + icone + '\'' +
      '}';
  }
}
