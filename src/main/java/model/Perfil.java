package model;

import java.util.ArrayList;

public class Perfil {
    private int id;
    private String nome;
    private ArrayList<Menu> menus; 
    
    public Perfil() {}
    public Perfil(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Menu> getMenus() {
      return menus;
    }
  
    public void setMenus(ArrayList<Menu> menus) {
      this.menus = menus;
    }

  @Override
    public String toString() {
        return "Perfil{" + "id=" + id + ", nome=" + nome + '}';
    }
   
}
