package view;

import com.formdev.flatlaf.FlatLightLaf;
import dao.PromocaoDAO;
import model.Promocao;

import javax.swing.*;
import java.awt.*;

public class TelaDetalhePromocao extends JFrame {

    private int id;

    public TelaDetalhePromocao(int id) {
        this.id = id;
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ignored) {}

        setTitle("Detalhe da Promoção");
        setSize(500, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        init();
        setVisible(true);
    }

    private void init() {
        PromocaoDAO dao = new PromocaoDAO();
        Promocao p = dao.buscarPorId(id);

        if (p == null) {
            JOptionPane.showMessageDialog(this, "Promoção não encontrada.");
            dispose();
            return;
        }

        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel nome = new JLabel("<html><h2>" + p.getProduto() + "</h2></html>");
        nome.setBounds(20, 20, 440, 40);
        add(nome);

        JLabel lojista = new JLabel("Lojista: " + p.getLojista());
        lojista.setBounds(20, 80, 300, 24);
        lojista.setFont(new Font("Inter", Font.PLAIN, 16));
        add(lojista);

        JLabel preco = new JLabel(String.format("Preço Promocional: R$ %.2f", p.getPrecoPromocional()));
        preco.setBounds(20, 120, 350, 24);
        preco.setFont(new Font("Inter", Font.BOLD, 16));
        preco.setForeground(new Color(255, 70, 70));
        add(preco);

        JLabel validade = new JLabel("Válido de " + p.getDataInicio() + " até " + p.getDataFim());
        validade.setBounds(20, 160, 400, 24);
        validade.setFont(new Font("Inter", Font.PLAIN, 14));
        validade.setForeground(new Color(100, 100, 100));
        add(validade);

        // imagem da promoção (se houver)
        if (p.getCaminhoImagem() != null && !p.getCaminhoImagem().isEmpty()) {
            ImageIcon imgIcon = new ImageIcon(p.getCaminhoImagem());
            Image img = imgIcon.getImage().getScaledInstance(440, 150, Image.SCALE_SMOOTH);
            JLabel imgLabel = new JLabel(new ImageIcon(img));
            imgLabel.setBounds(20, 200, 440, 150);
            add(imgLabel);
        }

        JButton btnVoltar = new JButton("⬅ Voltar");
        btnVoltar.setBounds(20, 360, 120, 35);
        btnVoltar.setBackground(new Color(252, 226, 207));
        btnVoltar.addActionListener(e -> {
            new TelaListagemPromocoes();
            dispose();
        });
        add(btnVoltar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaDetalhePromocao(1));
    }
}
