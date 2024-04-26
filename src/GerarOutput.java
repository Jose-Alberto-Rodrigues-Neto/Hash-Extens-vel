import java.io.PrintWriter;
import java.util.List;

public class GerarOutput {
    public static void processarLinha(String linha, List<Compra> compras, HashExtensivel<Integer, Compra> hashTable, PrintWriter arquivoSaida) {
        int ano;
        String operacao = linha.substring(0, 3);

        switch (operacao) {
            case "INC":
                ano = Integer.parseInt(linha.substring(4));
                for (Compra compra : compras) {
                    if (compra.getAno() == ano) {
                        hashTable.inserir(ano, compra);

                        arquivoSaida.printf("INC:%d/%d,%d\n", ano, hashTable.getProfundidadeGlobal(), hashTable.getProfundidadeLocal(ano));

                        if (hashTable.getProfundidadeLocal(ano) > 1) {
                            arquivoSaida.printf("DUP_DIR:%d,%d\n", hashTable.getProfundidadeGlobal(), hashTable.getProfundidadeLocal(ano));
                        }

                        break;
                    }
                }
                break;
            case "REM":
                ano = Integer.parseInt(linha.substring(4));

                int removedCount = hashTable.remover(ano);

                arquivoSaida.printf("REM:%d/%d,%d,%d\n", ano, removedCount, hashTable.getProfundidadeGlobal(), hashTable.getProfundidadeLocal(ano));
                break;
            case "BUS":
                ano = Integer.parseInt(linha.substring(4));
                int quantidadeDeTuplas = hashTable.getProfundidadeLocal(ano);
                arquivoSaida.printf("BUS:%d/%d\n", ano, quantidadeDeTuplas);
                //arquivoSaida.write("BUS:%d/%d");
                break;
        }
    }
}
