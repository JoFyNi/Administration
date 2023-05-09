package Hardware;

public class GraphicsCard {
    String name;
    String hersteller;
    String serie;
    String formfaktor;
    String architektur;
    public GraphicsCard(String hersteller, String serie, String architektur, String formfaktor, String name) {
        this.hersteller = hersteller;
        this.serie = serie;
        this.architektur = architektur;
        this.formfaktor = formfaktor;
        this.name = name;
    }
}
