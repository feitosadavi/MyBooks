package model;

public class Usuario {
  private int id, idPerfil, status;
  private String nome, username, senha;

  public Usuario() {}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getIdPerfil() {
    return idPerfil;
  }

  public void setIdPerfil(int idPerfil) {
    this.idPerfil = idPerfil;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "Usuario{" +
      "id=" + id +
      ", idPerfil=" + idPerfil +
      ", nome='" + nome + '\'' +
      ", username='" + username + '\'' +
      ", senha='" + senha + '\'' +
      ", status='" + status + '\'' +
      '}';
  }
}
