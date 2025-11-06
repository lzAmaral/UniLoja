package session;

public class UserSession {
    private static int userId;
    private static String nome;

    public static void setUser(int id, String nomeUsuario) {
        userId = id;
        nome = nomeUsuario;
    }

    public static int getUserId() {
        return userId;
    }

    public static String getNome() {
        return nome;
    }

    public static void clear() {
        userId = 0;
        nome = null;
    }
}
