
public class App {
    public static void main(String[] args) throws Exception {
        Leitor leitor = new Leitor("public//compras.csv");
        leitor.lerCompras();
        leitor.printarCompras();
    }
}