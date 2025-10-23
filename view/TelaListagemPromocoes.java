package view;

import javax.swing.*;
import java.awt.*;
import dao.PromocaoDAO;
import model.Promocao;
import java.util.List;

public class TelaListagemPromocoes extends JFrame {
    public TelaListagemPromocoes() {
        setTitle("Promoções Atuais - UniLoja");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] colunas = {"Produto", "Preço", "Validade", "Lojista"};
        List<Promocao> lista = PromocaoDAO.listar();
        Object[][] dados = new Object[lista.size()][4];

        for (int i = 0; i < lista.size(); i++) {
            Promocao p = lista.get(i);
            dados[i][0] = p.getProduto();
            dados[i][1] = "R$ " + p.getPreco();
            dados[i][2] = p.getValidade();
            dados[i][3] = p.getLojista();
        }

        JTable tabela = new JTable(dados, colunas);
        JScrollPane scroll = new JScrollPane(tabela);
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            new TelaLogin();
            dispose();
        });

        add(scroll, BorderLayout.CENTER);
        add(btnVoltar, BorderLayout.SOUTH);
        setVisible(true);
    }
}
