package model;

public class Promocao {

    private int id;
    private int idProduto;                 // <-- campo necessário pelo DAO
    private String produto;
    private double precoPromocional;
    private String dataInicio;
    private String dataFim;
    private String lojista;
    private String caminhoImagem;
    private int quantidadeDisponivel;      // <-- campo usado pelo DAO

    public Promocao() {}

    // Construtor básico (sem idProduto)
    public Promocao(int id, String produto, double precoPromocional, String dataInicio, String dataFim, String lojista) {
        this.id = id;
        this.produto = produto;
        this.precoPromocional = precoPromocional;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.lojista = lojista;
    }

    // Construtor completo com idProduto e caminhoImagem
    public Promocao(int id, int idProduto, String produto, double precoPromocional,
                    String dataInicio, String dataFim, String lojista, String caminhoImagem, int quantidadeDisponivel) {
        this.id = id;
        this.idProduto = idProduto;
        this.produto = produto;
        this.precoPromocional = precoPromocional;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.lojista = lojista;
        this.caminhoImagem = caminhoImagem;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    // Construtor usado quando não se tem todos os campos
    public Promocao(int id, String produto, double precoPromocional, String dataInicio, String dataFim, String lojista, String caminhoImagem) {
        this(id, 0, produto, precoPromocional, dataInicio, dataFim, lojista, caminhoImagem, 0);
    }

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdProduto() { return idProduto; }
    public void setIdProduto(int idProduto) { this.idProduto = idProduto; }

    public String getProduto() { return produto; }
    public void setProduto(String produto) { this.produto = produto; }

    public double getPrecoPromocional() { return precoPromocional; }
    public void setPrecoPromocional(double precoPromocional) { this.precoPromocional = precoPromocional; }

    // compatibilidade com código antigo que pode chamar getPreco()
    public double getPreco() { return precoPromocional; }
    public void setPreco(double preco) { this.precoPromocional = preco; }

    public String getDataInicio() { return dataInicio; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }

    public String getDataFim() { return dataFim; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }

    public String getLojista() { return lojista; }
    public void setLojista(String lojista) { this.lojista = lojista; }

    public String getCaminhoImagem() { return caminhoImagem; }
    public void setCaminhoImagem(String caminhoImagem) { this.caminhoImagem = caminhoImagem; }

    public int getQuantidadeDisponivel() { return quantidadeDisponivel; }
    public void setQuantidadeDisponivel(int quantidadeDisponivel) { this.quantidadeDisponivel = quantidadeDisponivel; }

    @Override
    public String toString() {
        return "Promocao{" +
                "id=" + id +
                ", idProduto=" + idProduto +
                ", produto='" + produto + '\'' +
                ", precoPromocional=" + precoPromocional +
                ", dataInicio='" + dataInicio + '\'' +
                ", dataFim='" + dataFim + '\'' +
                ", lojista='" + lojista + '\'' +
                ", caminhoImagem='" + caminhoImagem + '\'' +
                ", quantidadeDisponivel=" + quantidadeDisponivel +
                '}';
    }
}
