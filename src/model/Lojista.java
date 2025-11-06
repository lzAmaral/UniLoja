package model;

public class Lojista {
    private String nome;
    private String email;
    private String senha;  
    private String endereco;

    public Lojista(String nome, String email, String senha, String endereco) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public String getEndereco() { return endereco; } 


}
