public class FuncHash {

    public static int calcularHash(int valor, int profundidade) {
        return valor & ((1 << profundidade) - 1);
    }

}
