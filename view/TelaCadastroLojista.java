package view;

import javax.swing.*;
import java.awt.*;
import dao.LojistaDAO;
import model.Lojista;

public class TelaCadastroLojista extends JFrame {
    public TelaCadastroLojista() {
        setTitle("Cadastro de Lojista - UniLoja");
        setSize(350, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 5, 5));

        JLabel lblNome = new JLabel("Nome:");
        JTextField txtNome = new JTextField();
        JLabel lblEmail = new JLabel("E-mail:");
        JTextField txtEmail = new JTextField();
        JLabel lblSenha = new JLabel("Senha:");
        JPasswordField txtSenha = new JPasswordField();
        JLabel lblEndereco = new JLabel("Endereço:");
        JTextField txtEndereco = new JTextField();
        JButton btnSalvar = new JButton("Cadastrar");

        add(lblNome); add(txtNome);
        add(lblEmail); add(txtEmail);
        add(lblSenha); add(txtSenha);
        add(lblEndereco); add(txtEndereco);
        add(new JLabel("")); add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            Lojista l = new Lojista(txtNome.getText(), txtEmail.getText(),
                    new String(txtSenha.getPassword()), txtEndereco.getText());
            LojistaDAO.adicionar(l);
            JOptionPane.showMessageDialog(this, "Lojista cadastrado com sucesso!");
            new TelaLogin();
            dispose();
        });

        setVisible(true);
    }
}
