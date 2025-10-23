package view;

import javax.swing.*;
import java.awt.*;
import dao.PromocaoDAO;
import model.Promocao;

public class TelaCadastroPromocao extends JFrame {
    public TelaCadastroPromocao() {
        setTitle("Cadastrar Promoção - UniLoja");
        setSize(350, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 5, 5));

        JLabel lblProduto = new JLabel("Produto:");
        JTextField txtProduto = new JTextField();
        JLabel lblPreco = new JLabel("Preço:");
        JTextField txtPreco = new JTextField();
        JLabel lblValidade = new JLabel("Validade:");
        JTextField txtValidade = new JTextField();
        JLabel lblLojista = new JLabel("Lojista:");
        JTextField txtLojista = new JTextField();

        JButton btnSalvar = new JButton("Salvar");
        JButton btnVer = new JButton("Ver Promoções");

        add(lblProduto); add(txtProduto);
        add(lblPreco); add(txtPreco);
        add(lblValidade); add(txtValidade);
        add(lblLojista); add(txtLojista);
        add(btnSalvar); add(btnVer);

        btnSalvar.addActionListener(e -> {
            try {
                Promocao p = new Promocao(
                    txtProduto.getText(),
                    Double.parseDouble(txtPreco.getText()),
                    txtValidade.getText(),
                    txtLojista.getText()
                );
                PromocaoDAO.adicionar(p);
                JOptionPane.showMessageDialog(this, "Promoção cadastrada!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar!");
            }
        });

        btnVer.addActionListener(e -> {
            new TelaListagemPromocoes();
            dispose();
        });

        setVisible(true);
    }
}
