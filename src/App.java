import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class App {
    private static final String COMPRAS_CSV = "public/compras.csv";
    private static final String INPUT_FILE = "public/in.txt";
    private static final String OUTPUT_FILE = "public/out.txt";

    public static void main(String[] args) throws Exception {
        Leitor leitorCompras = new Leitor(COMPRAS_CSV);
        List<Compra> compras = leitorCompras.lerCompras();

        try (BufferedReader arquivoInstrucoes = new BufferedReader(new FileReader(INPUT_FILE));
             PrintWriter arquivoSaida = new PrintWriter(new FileWriter(OUTPUT_FILE))) {

            String linha = arquivoInstrucoes.readLine();
            int profundidadeInicial = Integer.parseInt(linha.substring(3));
            arquivoSaida.println(linha);

            HashExtensivel<Integer, Compra> hashTable = new HashExtensivel<>(profundidadeInicial);

            while ((linha = arquivoInstrucoes.readLine()) != null) {
                processarLinha(linha, compras, hashTable, arquivoSaida);
            }

            arquivoSaida.printf("P:/%d%n", hashTable.getProfundidadeGlobal());

        } catch (IOException e) {
            System.err.println("Erro ao abrir um dos arquivos!");
            e.printStackTrace();
        }
    }

    private static void processarLinha(String linha, List<Compra> compras, HashExtensivel<Integer, Compra> hashTable, PrintWriter arquivoSaida) {
        int ano;
        String operacao = linha.substring(0, 3);

        if (operacao.equals("INC")) {
            ano = Integer.parseInt(linha.substring(4));
            for (Compra compra : compras) {
                if (compra.getAno() == ano) {
                    hashTable.inserir(ano, compra);

                    arquivoSaida.printf("INC:%d/%d,%d%n", ano, hashTable.getProfundidadeGlobal(), hashTable.getProfundidadeLocal(ano));

                    if (hashTable.getProfundidadeLocal(ano) > 1) {
                        arquivoSaida.printf("DUP_DIR:/%d,%d%n", hashTable.getProfundidadeGlobal(), hashTable.getProfundidadeLocal(ano));
                    }

                    break;
                }
            }
        } else if (operacao.equals("REM")) {
            ano = Integer.parseInt(linha.substring(4));

            int removedCount = hashTable.remover(ano);

            arquivoSaida.printf("REM:%d/%d,%d,%d%n", ano, removedCount, hashTable.getProfundidadeGlobal(), hashTable.getProfundidadeLocal(ano));
        } else if (operacao.equals("BUS")) {
            ano = Integer.parseInt(linha.substring(5));
            int quantidadeDeTuplas = hashTable.getProfundidadeLocal(ano);
            arquivoSaida.printf("BUS:%d/%d%n", ano, quantidadeDeTuplas);
        }
    }
}
