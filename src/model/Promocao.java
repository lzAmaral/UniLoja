package model;

public class Promocao {
    private int id;
     private int idProduto;
    private String produto;
    private double preco;
    private String dataInicio;
    private String dataFim;
    private String lojista;

    public Promocao(int id, int idProduto, String produto, double preco, String dataInicio, String dataFim, String lojista) {
        this.id = id;
        this.idProduto = idProduto;
        this.produto = produto;
        this.preco = preco;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.lojista = lojista;
    }

    public int getId() { return id; }
    public String getProduto() { return produto; }
    public double getPreco() { return preco; }
    public String getDataInicio() { return dataInicio; }
    public String getDataFim() { return dataFim; }
    public String getLojista() { return lojista; }
    public int getIdProduto() {
    return idProduto;
}

public void setIdProduto(int idProduto) {
    this.idProduto = idProduto;
}
}
