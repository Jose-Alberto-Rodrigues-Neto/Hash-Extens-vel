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
                GerarOutput.processarLinha(linha, compras, hashTable, arquivoSaida);
            }

            arquivoSaida.printf("PG/%d%n", hashTable.getProfundidadeGlobal());

        } catch (IOException e) {
            System.err.println("Erro ao abrir um dos arquivos!");
            e.printStackTrace();
        }
    }

    
}


