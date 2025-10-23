package dao;

import model.Lojista;
import java.util.ArrayList;
import java.util.List;

public class LojistaDAO {
    private static List<Lojista> lojistas = new ArrayList<>();

    static {
        lojistas.add(new Lojista("Mercadinho Boa Compra", "mercado@email.com", "123", "Rua A, 100"));
    }

    public static boolean autenticar(String email, String senha) {
        for (Lojista l : lojistas) {
            if (l.getEmail().equals(email) && l.getSenha().equals(senha)) {
                return true;
            }
        }
        return false;
    }

    public static void adicionar(Lojista l) {
        lojistas.add(l);
    }
}
