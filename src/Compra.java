public class Compra {
    private int pedido, ano;
    private double valor;

    public Compra(int p, double v, int a) {
        this.pedido = p;
        this.valor = v;
        this.ano = a;
    }

    public void exibir() {
        System.out.println("Pedido: " + pedido + ", Valor: R$ " + valor + ", Ano: " + ano);
    }

}
