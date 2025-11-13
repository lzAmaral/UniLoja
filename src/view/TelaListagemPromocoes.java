package view;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TelaListagemPromocoes extends JFrame {

    private JTextField txtBuscar;

    public TelaListagemPromocoes() {
        try { UIManager.setLookAndFeel(new FlatLightLaf()); } catch (Exception ignored) {}

        setTitle("UniLoja — Promoções");
        setSize(1000, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initComponents();
        setVisible(true);
    }

    private void initComponents() {

        JPanel header = new JPanel(null);
        header.setPreferredSize(new Dimension(getWidth(), 80));
        header.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Boas compras ");
        lblTitulo.setFont(new Font("Inter", Font.BOLD, 26));
        lblTitulo.setBounds(20, 20, 300, 40);
        header.add(lblTitulo);

        txtBuscar = new JTextField("");
        txtBuscar.setFont(new Font("Inter", Font.PLAIN, 14));
        txtBuscar.setBounds(300, 25, 260, 34);
        header.add(txtBuscar);

        JButton btnLocalizacao = new JButton("📍 Localização");
        btnLocalizacao.setBounds(580, 25, 150, 34);
        btnLocalizacao.setBackground(new Color(252, 226, 207));
        btnLocalizacao.addActionListener(e -> {
            try {
            java.net.URI uri = new java.net.URI("https://www.google.com/maps/search/?api=1&query=Sorocaba+SP");
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(uri);
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível abrir o navegador.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir mapa: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        header.add(btnLocalizacao);

        JButton btnPerfil = new JButton("👤 Perfil");
        btnPerfil.setBounds(750, 25, 120, 34);
        btnPerfil.addActionListener(e -> {
            new TelaPerfil();
            dispose();
        });
        header.add(btnPerfil);

        add(header, BorderLayout.NORTH);


        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);


        content.add(criarSecao("Itens de Limpeza", getItensLimpeza()));

        content.add(criarSecao("Alimentos", getItensAlimentos()));

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);
    }



    private JPanel criarSecao(String titulo, List<PromocaoCard> lista) {

        JPanel sec = new JPanel();
        sec.setLayout(new BoxLayout(sec, BoxLayout.Y_AXIS));
        sec.setBackground(Color.WHITE);

        JLabel lbl = new JLabel(titulo);
        lbl.setFont(new Font("Inter", Font.BOLD, 22));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        sec.add(lbl);

        JPanel grid = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        grid.setBackground(Color.WHITE);

        for (PromocaoCard p : lista) {
            grid.add(criarCard(p));
        }

        sec.add(grid);
        return sec;
    }



    private JPanel criarCard(PromocaoCard p) {

        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(260, 260));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        JLabel img = new JLabel();
        img.setHorizontalAlignment(SwingConstants.CENTER);
        img.setIcon(new ImageIcon(new ImageIcon(p.imagem)
                .getImage().getScaledInstance(200, 120, Image.SCALE_SMOOTH)));
        img.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        card.add(img, BorderLayout.NORTH);

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(Color.WHITE);

        JLabel nome = new JLabel("<html><center><b>" + p.nome + "</b></center></html>");
        nome.setFont(new Font("Inter", Font.BOLD, 14));
        nome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel preco = new JLabel("R$ " + String.format("%.2f", p.preco));
        preco.setForeground(new Color(220, 20, 60));
        preco.setFont(new Font("Inter", Font.BOLD, 14));
        preco.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTempo = new JLabel("Expira em " + p.minutosRestantes + " min");
        lblTempo.setFont(new Font("Inter", Font.PLAIN, 12));
        lblTempo.setForeground(new Color(120, 120, 120));
        lblTempo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton ver = new JButton("Ver");
        ver.setAlignmentX(Component.CENTER_ALIGNMENT);

        info.add(nome);
        info.add(preco);
        info.add(lblTempo);
        info.add(Box.createVerticalStrut(6));
        info.add(ver);

        card.add(info, BorderLayout.CENTER);


        new Timer(60_000, e -> {
            p.minutosRestantes--;
            if (p.minutosRestantes <= 0) {
                lblTempo.setText("Expirado");
                lblTempo.setForeground(Color.RED);
            } else {
                lblTempo.setText("Expira em " + p.minutosRestantes + " min");
            }
        }).start();

        return card;
    }



    private List<PromocaoCard> getItensLimpeza() {
        List<PromocaoCard> lista = new ArrayList<>();
        lista.add(new PromocaoCard("Sabão em pó OMO 1kg", 9.90, "Assets/sabao.png", 12));
        lista.add(new PromocaoCard("Detergente Ypê 500ml", 3.50, "Assets/detergente.png", 7));
        lista.add(new PromocaoCard("Amaciante Comfort 1L", 11.90, "Assets/amaciante.png", 20));
        return lista;
    }

    private List<PromocaoCard> getItensAlimentos() {
        List<PromocaoCard> lista = new ArrayList<>();
        lista.add(new PromocaoCard("Arroz Camil 5kg", 27.90, "Assets/arroz.png", 15));
        lista.add(new PromocaoCard("Feijão Carioca 1kg", 8.50, "Assets/feijao.png", 9));
        lista.add(new PromocaoCard("Farinha Dona Benta 1kg", 6.90, "Assets/farinha.png", 18));
        return lista;
    }


    private static class PromocaoCard {
        String nome;
        double preco;
        String imagem;
        int minutosRestantes;

        PromocaoCard(String nome, double preco, String imagem, int minutosRestantes) {
            this.nome = nome;
            this.preco = preco;
            this.imagem = imagem;
            this.minutosRestantes = minutosRestantes;
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaListagemPromocoes::new);
    }
}
