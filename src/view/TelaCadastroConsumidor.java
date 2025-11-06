package view;

import connection.ConnectionFactory;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TelaCadastroConsumidor extends JFrame {

    private JTextField txtNome;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JPasswordField txtConfirma;
    private JTextField txtTelefone;
    private JTextField txtCidade;
    private JButton btnCadastrar;
    private JButton btnVoltar;

    public TelaCadastroConsumidor() {
        try { UIManager.setLookAndFeel(new FlatLightLaf()); } catch (Exception ignored) {}
        UIManager.put("Button.arc", 50);
        UIManager.put("Component.arc", 15);
        UIManager.put("TextField.arc", 10);
        UIManager.put("PasswordField.arc", 10);

        loadInterFont();
        initUI();
    }

    private void initUI() {
        setTitle("UniLoja — Cadastro");
        setSize(420, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JPanel header = new JPanel();
        header.setBackground(new Color(255, 245, 246));
        header.setBounds(0, 0, getWidth(), 120);
        add(header);

        JLabel titulo = new JLabel("<html><b>Crie sua conta</b></html>");
        titulo.setFont(new Font("Inter", Font.BOLD, 22));
        titulo.setForeground(new Color(41, 45, 50));
        titulo.setBounds(20, 130, 360, 30);
        add(titulo);

        // Painel principal com todos os campos
        JPanel card = new JPanel(null);
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(360, 650));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230,230,230)),
                new EmptyBorder(14,14,14,14)
        ));

        int y = 10;
        txtNome = new JTextField(); addField(card, "Nome completo", txtNome, y); y += 70;
        txtEmail = new JTextField(); addField(card, "E-mail", txtEmail, y); y += 70;
        txtSenha = new JPasswordField(); addField(card, "Senha", txtSenha, y); y += 70;
        txtConfirma = new JPasswordField(); addField(card, "Confirmar senha", txtConfirma, y); y += 70;
        txtTelefone = new JTextField(); addField(card, "Telefone (opcional)", txtTelefone, y); y += 70;
        txtCidade = new JTextField(); addField(card, "Cidade (opcional)", txtCidade, y); y += 70;

        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(10, y, 340, 50);
        btnCadastrar.setBackground(new Color(255, 70, 70));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFocusPainted(false);
        card.add(btnCadastrar);
        y += 60;

        btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(10, y, 340, 44);
        btnVoltar.setBackground(new Color(240,240,240));
        card.add(btnVoltar);

        // 🔽 Painel rolável para evitar corte
        JScrollPane scroll = new JScrollPane(card);
        scroll.setBounds(20, 170, 380, 500);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll);

        btnCadastrar.addActionListener(e -> cadastrar());
        btnVoltar.addActionListener(e -> {
            new TelaLogin();
            dispose();
        });

        setVisible(true);
    }

    private void addField(JPanel parent, String labelText, JComponent field, int y) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Inter", Font.PLAIN, 13));
        label.setBounds(10, y, 340, 18);
        field.setBounds(10, y + 22, 340, 40);
        parent.add(label);
        parent.add(field);
    }

    private void cadastrar() {
        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword());
        String confirma = new String(txtConfirma.getPassword());
        String telefone = txtTelefone.getText().trim();
        String cidade = txtCidade.getText().trim();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha Nome, E-mail e as senhas.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!senha.equals(confirma)) {
            JOptionPane.showMessageDialog(this, "As senhas não conferem.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "INSERT INTO Consumidor (Nome, Email, Senha, Telefone, Cidade) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nome);
            ps.setString(2, email);
            ps.setString(3, senha);
            ps.setString(4, telefone.isEmpty() ? null : telefone);
            ps.setString(5, cidade.isEmpty() ? null : cidade);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
            new TelaLogin();
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadInterFont() {
        try {
            File f = new File("lib/fonts/Inter-Regular.ttf");
            if (f.exists()) {
                Font inter = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(f));
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(inter);
                UIManager.put("Label.font", new Font("Inter", Font.PLAIN, 14));
                UIManager.put("Button.font", new Font("Inter", Font.PLAIN, 14));
                UIManager.put("TextField.font", new Font("Inter", Font.PLAIN, 14));
                UIManager.put("PasswordField.font", new Font("Inter", Font.PLAIN, 14));
            }
        } catch (Exception ignored) {}
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaCadastroConsumidor::new);
    }
}
