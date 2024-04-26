import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.List;
import java.io.IOException;

public class Bucket {
    
    public List<Compra> registros;
    public String nomeArquivo;
    public int profundidadeLocal;
    public int maximoRegistros;

    Bucket(String nomeArquivo, int profundidadeLocal, int maximoRegistros) {
        this.nomeArquivo = nomeArquivo;
        this.profundidadeLocal = profundidadeLocal;
        this.maximoRegistros = maximoRegistros;

        // criar arquivo com nome nomeArquivo
        try {

            File arquivo = new File(nomeArquivo);

            if ( arquivo.createNewFile() ) {
                System.out.println("bucket " + nomeArquivo + " criado com sucesso!!!");
            } else {
                System.out.println("bucket " + nomeArquivo + " já existe!!!");
            }

        } catch (IOException e) {
            System.out.println("Erro ao criar o arquivo: " + e.getMessage());
        }
    }

    Bucket(String nomeArquivo, List<Compra> registros, int profundidadeLocal, int maximoRegistros) {
        this.nomeArquivo = nomeArquivo;
        this.profundidadeLocal = profundidadeLocal;
        this.maximoRegistros = maximoRegistros;

        if ( registros.size() > this.maximoRegistros ) {
            System.out.println("O número máximo de registros é " + this.maximoRegistros);
            System.out.println("Nenhum registro foi adicionado");
        } else {
            this.registros = registros;
        }
    }

    // SETTERS
    void setProfundidadeLocal(int profundidadeLocal) {
        this.profundidadeLocal = profundidadeLocal;
    }

    void setRegistros(List<Compra> registros) {
        this.registros = registros;
    }

    // GETTERS
    int getProfundidadeLocal() {
        return this.profundidadeLocal;
    }

    List<Compra> getRegistros() {
        return this.registros;
    }

    Boolean cheio() {
        if ( this.registros.size() >= this.maximoRegistros ) {
            // System.out.println("Número máximo de registros atingido: " + this.maximoRegistros);
            return true;
        }
        return false;
    }

    void carregar() {
        Leitor leitor = new Leitor(this.nomeArquivo);
        this.registros = leitor.lerCompras();
    }

    void salvar() throws IOException {
        // criar arquivo com nome nomeArquivo
        File arquivo = new File(this.nomeArquivo);
        BufferedWriter arquivoEscrita = new BufferedWriter(new FileWriter(arquivo));
        for ( Compra compra : this.registros ) {
            // escrever no arquivo <pedido, valor, ano>
            arquivoEscrita.write(compra.getPedido() + "," + compra.getValor() + "," + compra.getAno() + "\n");
        }
        arquivoEscrita.close();
    }

    Boolean adicionarRegistro(Compra compra) throws IOException {
        if ( !cheio() ) {
            registros.add(compra);
            salvar();
            return true;
        }
        return false;
    }

    void imprimir() {
        for ( Compra compra : this.registros ) {
            compra.exibir();
        }
    }

}
