package dao;

import model.Promocao;
import java.util.ArrayList;
import java.util.List;

public class PromocaoDAO {
    private static List<Promocao> promocoes = new ArrayList<>();

    static {
        promocoes.add(new Promocao("Arroz 5kg", 25.90, "31/10/2025", "Mercadinho Boa Compra"));
        promocoes.add(new Promocao("Feijão 1kg", 6.50, "28/10/2025", "Mercadinho Boa Compra"));
    }

    public static void adicionar(Promocao p) {
        promocoes.add(p);
    }

    public static List<Promocao> listar() {
        return promocoes;
    }
}
