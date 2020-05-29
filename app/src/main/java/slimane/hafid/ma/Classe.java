package slimane.hafid.ma;

public class Classe {
    private String classe_name;
    private String classe_password;

    public Classe(String classe_name, String classe_password) {
        this.classe_name = classe_name;
        this.classe_password = classe_password;
    }

    public Classe() {
    }

    public String getClasse_name() {
        return classe_name;
    }

    public void setClasse_name(String classe_name) {
        this.classe_name = classe_name;
    }

    public String getClasse_password() {
        return classe_password;
    }

    public void setClasse_password(String classe_password) {
        this.classe_password = classe_password;
    }
}
