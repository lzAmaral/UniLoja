package dao;

import connection.ConnectionFactory;
import model.Promocao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PromocaoDAO {

    // ✅ Lista todas as promoções do banco
    public List<Promocao> listar() {
        List<Promocao> lista = new ArrayList<>();

        String sql = """
            SELECT p.Id_Promocao, pr.Id_Produto, pr.Nome AS Produto, p.Preco_Promocional, 
                   p.Data_Inicio, p.Data_Fim, m.Nome_Fantasia AS Lojista
            FROM Promocao p
            JOIN Produto pr ON p.Id_Produto = pr.Id_Produto
            JOIN Mercado m ON pr.Id_Mercado = m.Id_Mercado
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Promocao promo = new Promocao(
                        rs.getInt("Id_Promocao"),
                        rs.getInt("Id_Produto"),
                        rs.getString("Produto"),
                        rs.getDouble("Preco_Promocional"),
                        rs.getString("Data_Inicio"),
                        rs.getString("Data_Fim"),
                        rs.getString("Lojista")
                );
                lista.add(promo);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar promoções: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    // ✅ Busca uma promoção específica
    public Promocao buscarPorId(int id) {
        String sql = """
            SELECT p.Id_Promocao, pr.Nome AS Produto, p.Preco_Promocional, 
                   p.Data_Inicio, p.Data_Fim, m.Nome_Fantasia AS Lojista
            FROM Promocao p
            JOIN Produto pr ON p.Id_Produto = pr.Id_Produto
            JOIN Mercado m ON pr.Id_Mercado = m.Id_Mercado
            WHERE p.Id_Promocao = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Promocao(
                        rs.getInt("Id_Promocao"),
                        id, rs.getString("Produto"),
                        rs.getDouble("Preco_Promocional"),
                        rs.getString("Data_Inicio"),
                        rs.getString("Data_Fim"),
                        rs.getString("Lojista")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar promoção: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    // ✅ Adiciona nova promoção (usado pela tela de cadastro)
    public void adicionar(Promocao p) {
        String sql = """
            INSERT INTO Promocao (Id_Produto, Preco_Promocional, Data_Inicio, Data_Fim)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // ⚠️ Aqui o Id_Produto deve existir na tabela Produto
            ps.setInt(1, p.getIdProduto());
            ps.setDouble(2, p.getPreco());
            ps.setString(3, p.getDataInicio());
            ps.setString(4, p.getDataFim());

            ps.executeUpdate();

            System.out.println("✅ Promoção adicionada com sucesso!");
    } catch (SQLException e) {
        System.err.println("Erro ao adicionar promoção: " + e.getMessage());
        e.printStackTrace();
    }
}


    // ✅ Atualiza uma promoção existente
    public void atualizar(Promocao p) {
        String sql = """
            UPDATE Promocao
            SET Preco_Promocional = ?, Data_Inicio = ?, Data_Fim = ?
            WHERE Id_Promocao = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, p.getPreco());
            ps.setString(2, p.getDataInicio());
            ps.setString(3, p.getDataFim());
            ps.setInt(4, p.getIdProduto());

            ps.executeUpdate();

            System.out.println("✅ Promoção atualizada com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar promoção: " + e.getMessage());
            e.printStackTrace();
        }
    }
    // ✅ Remove uma promoção pelo ID
    public void remover(int id) {
        String sql = "DELETE FROM Promocao WHERE Id_Promocao = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("✅ Promoção removida com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao remover promoção: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

