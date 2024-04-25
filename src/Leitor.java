import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Leitor {
    private String arquivo;

    public Leitor(String caminhoArquivo) {
        this.arquivo = caminhoArquivo;
    }

    List<Compra> compras = new ArrayList<>();

    public List<Compra> lerCompras() {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                String[] entrada = linha.split(",");
                int idPedido = Integer.parseInt(entrada[0]);
                double valorPedido = Double.parseDouble(entrada[1]);
                int anoPedido = Integer.parseInt(entrada[2]);
                compras.add(new Compra(idPedido, valorPedido, anoPedido));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return compras;
    }

    public void printarCompras(){
        for(Compra compra: compras){
            compra.exibir();
        }
    }

}

