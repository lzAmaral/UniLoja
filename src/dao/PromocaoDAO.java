package dao;

import connection.ConnectionFactory;
import model.Promocao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PromocaoDAO {

    public void inserir(Promocao p) {
        String sql = "INSERT INTO Promocao (Id_Produto, Preco_Promocional, Data_Inicio, Data_Fim, Quantidade_Disponivel, Caminho_Imagem) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, p.getIdProduto());
            ps.setDouble(2, p.getPrecoPromocional());
            ps.setString(3, p.getDataInicio());
            ps.setString(4, p.getDataFim());
            ps.setInt(5, p.getQuantidadeDisponivel());
            ps.setString(6, p.getCaminhoImagem());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Promocao> listar() {
        List<Promocao> lista = new ArrayList<>();
        String sql = """
            SELECT p.Id_Promocao, pr.Nome AS Produto, p.Preco_Promocional, 
                   p.Data_Inicio, p.Data_Fim, p.Caminho_Imagem, 
                   m.Nome_Fantasia AS Lojista
            FROM Promocao p
            JOIN Produto pr ON p.Id_Produto = pr.Id_Produto
            JOIN Mercado m ON pr.Id_Mercado = m.Id_Mercado
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Promocao promo = new Promocao(
                        rs.getInt("Id_Promocao"),
                        rs.getString("Produto"),
                        rs.getDouble("Preco_Promocional"),
                        rs.getString("Data_Inicio"),
                        rs.getString("Data_Fim"),
                        rs.getString("Lojista")
                );
                promo.setCaminhoImagem(rs.getString("Caminho_Imagem"));
                lista.add(promo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Promocao buscarPorId(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
    }
}
