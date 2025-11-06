package view;

import com.formdev.flatlaf.FlatLightLaf;
import dao.PromocaoDAO;
import model.Promocao;

import javax.swing.*;
import java.awt.*;

/**
 * Tela de detalhe da promoção (abre por id)
 */
public class TelaDetalhePromocao extends JFrame {
    private int id;

    public TelaDetalhePromocao(int id) {
        this.id = id;
        try { UIManager.setLookAndFeel(new FlatLightLaf()); } catch (Exception ignored) {}
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
        JLabel nome = new JLabel("<html><h2>" + p.getProduto() + "</h2></html>");
        nome.setBounds(20,20,440,60);
        add(nome);

        JLabel loj = new JLabel("Lojista: " + p.getLojista());
        loj.setBounds(20,90,440,20);
        add(loj);

        JLabel preco = new JLabel(String.format("Preço: R$ %.2f", p.getPreco()));
        preco.setForeground(new Color(255,70,70));
        preco.setFont(new Font("Inter", Font.BOLD, 18));
        preco.setBounds(20,120,300,30);
        add(preco);

        JLabel periodo = new JLabel("Período: " + p.getDataInicio() + " → " + p.getDataFim());
        periodo.setBounds(20,160,440,20);
        add(periodo);

        JTextArea descricao = new JTextArea("Descrição não disponível (use tabela Produto.descricao se quiser).");
        descricao.setEditable(false);
        descricao.setBounds(20,200,440,120);
        descricao.setLineWrap(true);
        descricao.setWrapStyleWord(true);
        add(descricao);
    }
}
