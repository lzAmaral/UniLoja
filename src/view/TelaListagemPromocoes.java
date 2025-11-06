package view;

import com.formdev.flatlaf.FlatLightLaf;
import dao.PromocaoDAO;
import model.Promocao;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class TelaListagemPromocoes extends JFrame {

    private static final String[] EXAMPLE_IMAGES = {
            "Assets/macarrao.avif",
            "Assets/site-jmacedo-farinha-de-trigo-dona-benta-tipo-1-1kg-embalagem-plastica-2023.jpg",
            "Assets/mkp-feijao-carioca-1kg-removebg-preview.png"
    };

    private JPanel cardsContainer;
    private JTextField txtBuscar;

    public TelaListagemPromocoes() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ignored) {
        }

        setTitle("UniLoja — Promoções");
        setSize(980, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();
        loadPromocoes();

        setVisible(true);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel top = new JPanel(null);
        top.setPreferredSize(new Dimension(getWidth(), 120));
        top.setBackground(Color.WHITE);

        String nomeUsuario = session.UserSession.getNome();
        String saudacao = "Olá";

        java.time.LocalTime agora = java.time.LocalTime.now();
        if (agora.isAfter(java.time.LocalTime.of(5, 0)) && agora.isBefore(java.time.LocalTime.of(12, 0))) {
            saudacao = "Bom dia";
        } else if (agora.isBefore(java.time.LocalTime.of(18, 0))) {
            saudacao = "Boa tarde";
        } else {
            saudacao = "Boa noite";
        }

        JLabel lblSaudacao = new JLabel("<html><span style='font-size:24px'>" + saudacao +
                (nomeUsuario != null ? ", " + nomeUsuario.split(" ")[0] + "!" : "!") + "</span></html>");
        lblSaudacao.setFont(new Font("Inter", Font.BOLD, 24));
        lblSaudacao.setBounds(20, 16, 400, 40);
        top.add(lblSaudacao);

        JButton btnLocation = new JButton("Minha localização");
        btnLocation.setBounds(20, 64, 180, 34);
        btnLocation.setBackground(new Color(252, 226, 207, 200));
        btnLocation.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        top.add(btnLocation);

        JButton btnPedir = new JButton("Pedir agora");
        btnPedir.setBounds(220, 64, 140, 34);
        btnPedir.setBackground(new Color(252, 226, 207, 200));
        top.add(btnPedir);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout(8, 0));
        searchPanel.setBackground(new Color(0, 0, 0, 0));
        searchPanel.setBounds(380, 30, 350, 44);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));

        JLabel lsearchIcon = new JLabel("🔍");
        lsearchIcon.setPreferredSize(new Dimension(30, 30));
        searchPanel.add(lsearchIcon, BorderLayout.WEST);

        txtBuscar = new JTextField();
        txtBuscar.setBorder(null);
        txtBuscar.setForeground(new Color(80, 80, 80));
        txtBuscar.setText("");
        txtBuscar.setFont(new Font("Inter", Font.PLAIN, 14));
        searchPanel.add(txtBuscar, BorderLayout.CENTER);
        top.add(searchPanel);

        JButton btnPerfil = new JButton("Perfil");
        btnPerfil.setBounds(880, 30, 80, 34);
        btnPerfil.setBackground(new Color(252, 226, 207, 200));
        btnPerfil.addActionListener(e -> {
            new TelaPerfil();
            dispose();
        });
        top.add(btnPerfil);

        JButton btnAddPromo = new JButton("Cadastrar Promo");
        btnAddPromo.setBounds(760, 70, 200, 34);
        btnAddPromo.setBackground(new Color(252, 226, 207, 200));
        btnAddPromo.addActionListener(e -> {
            new TelaCadastroPromocao();
            dispose();
        });
        top.add(btnAddPromo);

        add(top, BorderLayout.NORTH);

        JPanel centerWrap = new JPanel(new BorderLayout());
        centerWrap.setBackground(Color.WHITE);

        // Categorias
        JPanel categories = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        categories.setBackground(Color.WHITE);
        String[] cats = { "Categorias", "Bebidas", "Frutas", "Limpeza", "Utensílios" };
        for (String c : cats) {
            JButton b = new JButton(c);
            b.setBackground(new Color(255, 240, 240));
            b.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            categories.add(b);
        }
        centerWrap.add(categories, BorderLayout.NORTH);

        cardsContainer = new JPanel();
        cardsContainer.setLayout(new GridLayout(0, 3, 16, 16));
        cardsContainer.setBackground(new Color(245, 245, 245));
        cardsContainer.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JScrollPane scroll = new JScrollPane(cardsContainer,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        centerWrap.add(scroll, BorderLayout.CENTER);

        add(centerWrap, BorderLayout.CENTER);
    }

    private void loadPromocoes() {
        PromocaoDAO dao = new PromocaoDAO();
        List<Promocao> promocoes = dao.listar();

        if (promocoes == null || promocoes.isEmpty()) {
            for (int i = 0; i < 6; i++) {
                Promocao p = new Promocao(0, 0, "Produto Exemplo " + (i + 1), 9.99 + i, "", "", "Lojista Exemplo");
                addCard(p, EXAMPLE_IMAGES[i % EXAMPLE_IMAGES.length]);
            }
            return;
        }

        int idx = 0;
        for (Promocao p : promocoes) {
            String imageUrl = EXAMPLE_IMAGES[idx % EXAMPLE_IMAGES.length];
            addCard(p, imageUrl);
            idx++;
        }
        revalidate();
        repaint();
    }

    private void addCard(Promocao p, String imageUrl) {
        JPanel card = new JPanel(null);
        card.setPreferredSize(new Dimension(280, 180));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        JLabel lblImg = new JLabel();
        lblImg.setBounds(8, 8, 264, 100);
        lblImg.setOpaque(true);
        lblImg.setBackground(new Color(240, 240, 240));

        try {
            ImageIcon icon = loadImage(imageUrl, 264, 100);
            if (icon != null)
                lblImg.setIcon(icon);
        } catch (Exception ignored) {
        }

        card.add(lblImg);

        JLabel nome = new JLabel("<html><b>" + p.getProduto() + "</b></html>");
        nome.setBounds(10, 116, 200, 20);
        nome.setFont(new Font("Inter", Font.BOLD, 14));
        card.add(nome);

        JLabel lojista = new JLabel(p.getLojista());
        lojista.setBounds(10, 136, 200, 16);
        lojista.setFont(new Font("Inter", Font.PLAIN, 12));
        lojista.setForeground(Color.GRAY);
        card.add(lojista);

        JLabel preco = new JLabel(String.format("R$ %.2f", p.getPreco()));
        preco.setBounds(10, 154, 120, 20);
        preco.setFont(new Font("Inter", Font.BOLD, 14));
        preco.setForeground(new Color(255, 70, 70));
        card.add(preco);

        JLabel dist = new JLabel("0,5 km");
        dist.setBounds(220, 116, 60, 16);
        dist.setForeground(Color.DARK_GRAY);
        dist.setFont(new Font("Inter", Font.PLAIN, 12));
        card.add(dist);

        JButton btnVer = new JButton("Ver");
        btnVer.setBounds(200, 148, 60, 24);
        btnVer.addActionListener(e -> new TelaDetalhePromocao(p.getId()));
        card.add(btnVer);

        cardsContainer.add(card);
    }

    private ImageIcon loadImage(String path, int w, int h) {
        try {
            Image img;
            if (path.startsWith("http")) {
                img = Toolkit.getDefaultToolkit().createImage(new URL(path));
            } else {
                img = Toolkit.getDefaultToolkit().createImage(path);
            }
            Image scaled = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception ex) {
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaListagemPromocoes::new);
    }
}
