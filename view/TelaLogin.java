package view;

import javax.swing.*;
import java.awt.*;
import dao.LojistaDAO;

public class TelaLogin extends JFrame {
    public TelaLogin() {
        setTitle("UniLoja - Login");
        setSize(350, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 5, 5));

        JLabel lblEmail = new JLabel("E-mail:");
        JTextField txtEmail = new JTextField();
        JLabel lblSenha = new JLabel("Senha:");
        JPasswordField txtSenha = new JPasswordField();

        JButton btnLojista = new JButton("Entrar como Lojista");
        JButton btnConsumidor = new JButton("Entrar como Consumidor");
        JButton btnCadastrar = new JButton("Cadastrar Lojista");

        add(lblEmail); add(txtEmail);
        add(lblSenha); add(txtSenha);
        add(btnLojista); add(btnConsumidor);
        add(new JLabel("")); add(btnCadastrar);

        btnLojista.addActionListener(e -> {
            String email = txtEmail.getText();
            String senha = new String(txtSenha.getPassword());
            if (LojistaDAO.autenticar(email, senha)) {
                JOptionPane.showMessageDialog(this, "Bem-vindo, lojista!");
                new TelaCadastroPromocao();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Login inválido!");
            }
        });

        btnConsumidor.addActionListener(e -> {
            new TelaListagemPromocoes();
            dispose();
        });

        btnCadastrar.addActionListener(e -> {
            new TelaCadastroLojista();
            dispose();
        });

        setVisible(true);
    }
}
