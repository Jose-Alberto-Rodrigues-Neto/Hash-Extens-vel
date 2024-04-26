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

            Diretorio diretorio = new Diretorio(profundidadeInicial);

            while ((linha = arquivoInstrucoes.readLine()) != null) {
                processarLinha(linha, compras, diretorio, arquivoSaida);
            }

            arquivoSaida.printf("P:/%d%n", diretorio.getProfundidadeGlobal());

        } catch (IOException e) {
            System.err.println("Erro ao abrir um dos arquivos!");
            e.printStackTrace();
        }
    }

    private static void processarLinha(String linha, List<Compra> compras, Diretorio diretorio, PrintWriter arquivoSaida) {
        int ano;
        String operacao = linha.substring(0, 3);

        if (operacao.equals("INC")) {
            ano = Integer.parseInt(linha.substring(4));
            for (Compra compra : compras) {
                if (compra.getAno() == ano) {
                    Par<Integer, Integer> resultadoInsercao = diretorio.inserirRegistro(compra);

                    arquivoSaida.printf("INC:%d/%d,%d%n", ano, diretorio.getProfundidadeGlobal(), resultadoInsercao.first);

                    if (resultadoInsercao.second == 1) {
                        arquivoSaida.printf("DUP_DIR:/%d,%d%n", diretorio.getProfundidadeGlobal(), resultadoInsercao.first);
                    }

                    break;
                }
            }
        } else if (operacao.equals("REM")) {
            ano = Integer.parseInt(linha.substring(4));

            Par<Integer, Integer> resultadoRemocao = diretorio.removerRegistros(ano);

            arquivoSaida.printf("REM:%d/%d,%d,%d%n", ano, resultadoRemocao.first, diretorio.getProfundidadeGlobal(), resultadoRemocao.second);
        } else if (operacao.equals("BUS")) {
            ano = Integer.parseInt(linha.substring(5));
            int quantidadeDeTuplas = diretorio.buscarPorAno(ano);
            arquivoSaida.printf("BUS:%d/%d%n", ano, quantidadeDeTuplas);
        }
    }
}