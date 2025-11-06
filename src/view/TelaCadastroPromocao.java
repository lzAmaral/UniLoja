package view;

import com.formdev.flatlaf.FlatLightLaf;
import connection.ConnectionFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TelaCadastroPromocao extends JFrame {
    private JTextField txtTitulo, txtDescricao, txtPreco;
    private JLabel lblImagem;
    private String caminhoImagem = null;

    public TelaCadastroPromocao() {
        try { UIManager.setLookAndFeel(new FlatLightLaf()); } catch (Exception ignored) {}
        setTitle("Cadastrar Promoção");
        setSize(450, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        init();
        setVisible(true);
    }

    private void init() {
        setLayout(null);

        JLabel titulo = new JLabel("Cadastrar Promoção");
        titulo.setFont(new Font("Inter", Font.BOLD, 22));
        titulo.setBounds(20, 20, 300, 30);
        add(titulo);

        JLabel lbl1 = new JLabel("Título:");
        lbl1.setBounds(20, 80, 100, 25);
        add(lbl1);

        txtTitulo = new JTextField();
        txtTitulo.setBounds(120, 80, 280, 25);
        add(txtTitulo);

        JLabel lbl2 = new JLabel("Descrição:");
        lbl2.setBounds(20, 120, 100, 25);
        add(lbl2);

        txtDescricao = new JTextField();
        txtDescricao.setBounds(120, 120, 280, 25);
        add(txtDescricao);

        JLabel lbl3 = new JLabel("Preço:");
        lbl3.setBounds(20, 160, 100, 25);
        add(lbl3);

        txtPreco = new JTextField();
        txtPreco.setBounds(120, 160, 280, 25);
        add(txtPreco);

        lblImagem = new JLabel("Nenhuma imagem selecionada");
        lblImagem.setBounds(20, 210, 300, 25);
        add(lblImagem);

        JButton btnUpload = new JButton("Selecionar Imagem");
        btnUpload.setBounds(20, 240, 180, 30);
        btnUpload.addActionListener(e -> selecionarImagem());
        add(btnUpload);

        JButton btnSalvar = new JButton("Cadastrar");
        btnSalvar.setBounds(150, 400, 120, 40);
        btnSalvar.setBackground(new Color(52, 152, 219));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.addActionListener(e -> salvarPromocao());
        add(btnSalvar);
    }

    private void selecionarImagem() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Escolher Imagem");
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File arquivoSelecionado = fileChooser.getSelectedFile();
            try {
                File pastaUploads = new File("uploads");
                if (!pastaUploads.exists()) {
                    pastaUploads.mkdir();
                }

                File destino = new File(pastaUploads, arquivoSelecionado.getName());
                Files.copy(arquivoSelecionado.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);

                caminhoImagem = destino.getPath();
                lblImagem.setText("Imagem: " + arquivoSelecionado.getName());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar imagem: " + ex.getMessage());
            }
        }
    }

    private void salvarPromocao() {
        String sql = "INSERT INTO Promocao (Titulo, Descricao, Preco, CaminhoImagem) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, txtTitulo.getText());
            ps.setString(2, txtDescricao.getText());
            ps.setDouble(3, Double.parseDouble(txtPreco.getText()));
            ps.setString(4, caminhoImagem);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Promoção cadastrada com sucesso!");
            dispose();
            new TelaListagemPromocoes();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar promoção: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new TelaCadastroPromocao();
    }
}
