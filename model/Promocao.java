package model;

public class Promocao {
    private String produto;
    private double preco;
    private String validade;
    private String lojista;

    public Promocao(String produto, double preco, String validade, String lojista) {
        this.produto = produto;
        this.preco = preco;
        this.validade = validade;
        this.lojista = lojista;
    }

    public String getProduto() { return produto; }
    public double getPreco() { return preco; }
    public String getValidade() { return validade; }
    public String getLojista() { return lojista; }
}
