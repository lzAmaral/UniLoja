package view;

import com.formdev.flatlaf.FlatLightLaf;
import connection.ConnectionFactory;
import session.UserSession;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TelaPerfil extends JFrame {

    private JLabel nomeLabel;
    private JLabel emailLabel;
    private JButton btnLogout, btnVoltar;

    public TelaPerfil() {
        try { UIManager.setLookAndFeel(new FlatLightLaf()); } catch (Exception ignored) {}
        setTitle("Perfil");
        setSize(420, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        init();
        loadUser(UserSession.getUserId());
        setVisible(true);
    }

    private void init() {
        setLayout(null);

        JLabel titulo = new JLabel("Meu Perfil");
        titulo.setFont(new Font("Inter", Font.BOLD, 22));
        titulo.setBounds(20, 20, 300, 30);
        add(titulo);

        nomeLabel = new JLabel("Nome: ");
        nomeLabel.setBounds(20, 80, 360, 24);
        nomeLabel.setFont(new Font("Inter", Font.PLAIN, 16));
        add(nomeLabel);

        emailLabel = new JLabel("E-mail: ");
        emailLabel.setBounds(20, 120, 360, 24);
        emailLabel.setFont(new Font("Inter", Font.PLAIN, 16));
        add(emailLabel);

        btnVoltar = new JButton("⬅ Voltar");
        btnVoltar.setBounds(20, 250, 120, 40);
        btnVoltar.setBackground(new Color(252, 226, 207));
        btnVoltar.addActionListener(e -> {
            new TelaListagemPromocoes();
            dispose();
        });
        add(btnVoltar);

        btnLogout = new JButton("Logout");
        btnLogout.setBounds(160, 250, 120, 40);
        btnLogout.setBackground(new Color(255, 100, 100));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.addActionListener(e -> {
            UserSession.clear(); 
            new TelaLogin();
            dispose();
        });
        add(btnLogout);
    }

    private void loadUser(int userId) {
        String sql = "SELECT Nome, Email FROM Consumidor WHERE Id_Consumidor = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("Nome");
                String email = rs.getString("Email");

                SwingUtilities.invokeLater(() -> {
                    nomeLabel.setText("Nome: " + nome);
                    emailLabel.setText("E-mail: " + email);
                });
            } else {
                nomeLabel.setText("Usuário não encontrado!");
                emailLabel.setText("");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar usuário: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaPerfil::new);
    }
}
