package view;

import connection.ConnectionFactory;
import com.formdev.flatlaf.FlatLightLaf;
import session.UserSession;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TelaLogin extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnLogin, btnSignup;

    public TelaLogin() {
        try { UIManager.setLookAndFeel(new FlatLightLaf()); } catch (Exception ignored) {}

        UIManager.put("Button.arc", 50);
        UIManager.put("Component.arc", 15);

        loadInterFont();
        initComponents();
    }

    private void initComponents() {
        setTitle("UniLoja — Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(420, 840);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Bem-vindo!");
        lblTitulo.setFont(new Font("Inter", Font.BOLD, 28));
        lblTitulo.setBounds(120, 180, 300, 60);
        add(lblTitulo);

        JPanel central = new JPanel();
        central.setLayout(null);
        central.setBackground(Color.WHITE);
        central.setBounds(20, 280, 380, 420);
        central.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230,230,230)),
                new EmptyBorder(16,16,16,16)
        ));
        add(central);

        JLabel lblEmail = new JLabel("E-mail");
        lblEmail.setBounds(10, 10, 200, 24);
        central.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(10, 40, 360, 48);
        central.add(txtEmail);

        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setBounds(10, 100, 200, 24);
        central.add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(10, 130, 360, 48);
        central.add(txtSenha);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(10, 220, 360, 64);
        btnLogin.setBackground(new Color(255, 70, 70));
        btnLogin.setForeground(Color.WHITE);
        central.add(btnLogin);

        btnSignup = new JButton("Sign up");
        btnSignup.setBounds(100, 300, 180, 44);
        btnSignup.setBackground(new Color(240,240,240));
        central.add(btnSignup);

        btnLogin.addActionListener(this::onLogin);
        btnSignup.addActionListener(e -> {
            new TelaCadastroConsumidor();
            dispose();
        });

        setVisible(true);
    }

    private void onLogin(ActionEvent ev) {
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword());

        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha E-mail e Senha.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ConsumidorData consumidor = autenticarConsumidor(email, senha);
        if (consumidor != null) {
            UserSession.setUser(consumidor.id, consumidor.nome); 
            JOptionPane.showMessageDialog(this, "Bem-vindo, " + consumidor.nome + "!");
            new TelaListagemPromocoes();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "E-mail ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class ConsumidorData {
        int id; String nome;
        ConsumidorData(int id, String nome) { this.id = id; this.nome = nome; }
    }

    private ConsumidorData autenticarConsumidor(String email, String senha) {
        String sql = "SELECT Id_Consumidor, Nome FROM Consumidor WHERE Email = ? AND Senha = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, senha);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new ConsumidorData(rs.getInt("Id_Consumidor"), rs.getString("Nome"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadInterFont() {
        try {
            File f = new File("lib/fonts/Inter-Regular.ttf");
            if (f.exists()) {
                Font inter = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(f));
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(inter);
            }
        } catch (Exception ignored) {}
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaLogin::new);
    }
}
