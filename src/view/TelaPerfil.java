package view;

import com.formdev.flatlaf.FlatLightLaf;
import connection.ConnectionFactory;
import session.UserSession;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;

/**
 * Tela de Perfil do Usuário (estilizada).
 * - Carrega dados do Consumidor (Nome, Email, Telefone, Cidade)
 * - Tenta carregar avatar em uploads/profile_{id}.png (se existir)
 * - Botões: Voltar (para listagem) e Logout
 */
public class TelaPerfil extends JFrame {

    private JLabel avatarLabel;
    private JLabel nomeLabel;
    private JLabel emailLabel;
    private JLabel telefoneLabel;
    private JLabel cidadeLabel;

    private JButton btnVoltar;
    private JButton btnLogout;

    public TelaPerfil() {
        try { UIManager.setLookAndFeel(new FlatLightLaf()); } catch (Exception ignored) {}
        initUI();
        int id = UserSession.getUserId();
        if (id > 0) {
            loadUser(id);
        } else {
            // sem sessão: mostra mensagem genérica
            nomeLabel.setText("Nome: —");
            emailLabel.setText("E-mail: —");
            telefoneLabel.setText("Telefone: —");
            cidadeLabel.setText("Cidade: —");
        }
        setVisible(true);
    }

    // Construtor que carrega por id explícito
    public TelaPerfil(int userId) {
        try { UIManager.setLookAndFeel(new FlatLightLaf()); } catch (Exception ignored) {}
        initUI();
        if (userId > 0) loadUser(userId);
        setVisible(true);
    }

    private void initUI() {
        setTitle("UniLoja — Perfil");
        setSize(420, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        // Header / título
        JLabel titulo = new JLabel("Meu Perfil");
        titulo.setFont(new Font("Inter", Font.BOLD, 22));
        titulo.setBounds(20, 16, 300, 30);
        add(titulo);

        // Avatar (círculo)
        avatarLabel = new JLabel();
        avatarLabel.setBounds(20, 66, 96, 96);
        avatarLabel.setBorder(new EmptyBorder(4,4,4,4));
        add(avatarLabel);

        // Painel com informações
        JPanel info = new JPanel(null);
        info.setBounds(130, 66, 260, 220);
        info.setBackground(Color.WHITE);

        nomeLabel = new JLabel("Nome: ");
        nomeLabel.setFont(new Font("Inter", Font.BOLD, 16));
        nomeLabel.setBounds(0, 0, 260, 28);
        info.add(nomeLabel);

        emailLabel = new JLabel("E-mail: ");
        emailLabel.setFont(new Font("Inter", Font.PLAIN, 14));
        emailLabel.setBounds(0, 38, 260, 22);
        info.add(emailLabel);

        telefoneLabel = new JLabel("Telefone: ");
        telefoneLabel.setFont(new Font("Inter", Font.PLAIN, 14));
        telefoneLabel.setBounds(0, 72, 260, 22);
        info.add(telefoneLabel);

        cidadeLabel = new JLabel("Cidade: ");
        cidadeLabel.setFont(new Font("Inter", Font.PLAIN, 14));
        cidadeLabel.setBounds(0, 106, 260, 22);
        info.add(cidadeLabel);

        add(info);

        // Botões
        btnVoltar = new JButton("⬅ Voltar");
        btnVoltar.setBounds(20, 320, 140, 44);
        btnVoltar.setBackground(new Color(252, 226, 207));
        btnVoltar.setFocusPainted(false);
        btnVoltar.addActionListener(e -> {
            new TelaListagemPromocoes();
            dispose();
        });
        add(btnVoltar);

        btnLogout = new JButton("Logout");
        btnLogout.setBounds(180, 320, 140, 44);
        btnLogout.setBackground(new Color(255, 100, 100));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(e -> {
            UserSession.clear();
            new TelaLogin();
            dispose();
        });
        add(btnLogout);
    }

    /**
     * Carrega os dados do usuário do banco e atualiza a UI.
     * Também tenta carregar avatar em uploads/profile_{id}.png.
     */
    private void loadUser(int userId) {
        if (userId <= 0) return;

        SwingUtilities.invokeLater(() -> {
            // placeholder visual enquanto carrega
            drawInitialAvatar("?");
        });

        String sql = "SELECT Nome, Email, Telefone, Cidade FROM Consumidor WHERE Id_Consumidor = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("Nome");
                    String email = rs.getString("Email");
                    String telefone = rs.getString("Telefone");
                    String cidade = rs.getString("Cidade");

                    final String fNome = nome;
                    final String fEmail = email;
                    final String fTel = telefone;
                    final String fCid = cidade;

                    SwingUtilities.invokeLater(() -> {
                        nomeLabel.setText("Nome: " + (fNome != null ? fNome : "—"));
                        emailLabel.setText("E-mail: " + (fEmail != null ? fEmail : "—"));
                        telefoneLabel.setText("Telefone: " + (fTel != null ? fTel : "—"));
                        cidadeLabel.setText("Cidade: " + (fCid != null ? fCid : "—"));
                    });

                    // tentar carregar avatar local: uploads/profile_{id}.png
                    String avatarPath = "uploads/profile_" + userId + ".png";
                    File avatarFile = new File(avatarPath);
                    if (avatarFile.exists()) {
                        try {
                            BufferedImage img = ImageIO.read(avatarFile);
                            setCircularAvatar(img);
                        } catch (IOException ioe) {
                            // se falhar, desenha inicial
                            drawInitialAvatar(getFirstLetter(fNome));
                        }
                    } else {
                        // desenha inicial do nome
                        drawInitialAvatar(getFirstLetter(fNome));
                    }

                } else {
                    SwingUtilities.invokeLater(() -> {
                        nomeLabel.setText("Usuário não encontrado!");
                        emailLabel.setText("");
                        telefoneLabel.setText("");
                        cidadeLabel.setText("");
                        drawInitialAvatar("?");
                    });
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this, "Erro ao carregar usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                drawInitialAvatar("?");
            });
        }
    }

    // retorna primeira letra maiúscula do nome (ou "?")
    private String getFirstLetter(String nome) {
        if (nome == null || nome.trim().isEmpty()) return "?";
        return nome.trim().substring(0,1).toUpperCase();
    }

    // desenha uma imagem circular redimensionada no avatarLabel
    private void setCircularAvatar(BufferedImage src) {
        int size = 96;
        Image scaled = src.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        BufferedImage circleImg = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = circleImg.createGraphics();
        applyQualityRenderingHints(g2);

        // máscara circular
        g2.setClip(new Ellipse2D.Float(0, 0, size, size));
        g2.drawImage(scaled, 0, 0, null);
        g2.dispose();

        avatarLabel.setIcon(new ImageIcon(circleImg));
    }

    // desenha um avatar com cor de fundo + inicial
    private void drawInitialAvatar(String initial) {
        int size = 96;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        applyQualityRenderingHints(g2);

        // color palette simples (baseada no hash da inicial)
        Color bg = pickColorForString(initial);
        g2.setColor(bg);
        g2.fillOval(0, 0, size, size);

        // inicial
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Inter", Font.BOLD, 42));
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(initial);
        int textAscent = fm.getAscent();
        int x = (size - textWidth) / 2;
        int y = (size + textAscent) / 2 - 6;
        g2.drawString(initial, x, y);

        g2.dispose();
        avatarLabel.setIcon(new ImageIcon(img));
    }

    private Color pickColorForString(String s) {
        if (s == null) return new Color(120, 120, 200);
        int hash = Math.abs(s.hashCode());
        // choose between a set of pleasant colors
        Color[] palette = new Color[] {
                new Color(255, 99, 71),
                new Color(255, 159, 64),
                new Color(255, 205, 86),
                new Color(75, 192, 192),
                new Color(54, 162, 235),
                new Color(153, 102, 255)
        };
        return palette[hash % palette.length];
    }

    private void applyQualityRenderingHints(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    // main para testes rápidos
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPerfil()); // usa UserSession, ou teste com new TelaPerfil(1)
    }
}
