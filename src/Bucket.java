import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.io.FileWriter;

public class Bucket {
    
    public List<Compra> registros;
    public String fileName;
    public int profundidadeLocal;
    public int maximoRegistros;

    Bucket(String fileName, int profundidadeLocal, int maximoRegistros) {
        this.fileName = fileName;
        this.profundidadeLocal = profundidadeLocal;
        this.maximoRegistros = maximoRegistros;

        // criar arquivo com nome fileName
        try {

            File arquivo = new File(fileName);

            if ( arquivo.createNewFile() ) {
                System.out.println("Arquivo criado com sucesso: " + fileName);
            } else {
                System.out.println("Arquivo " + fileName + " já existe.");
            }

        } catch (IOException e) {
            System.out.println("Erro ao criar o arquivo: " + e.getMessage());
        }
    }

    Bucket(String fileName, List<Compra> registros, int profundidadeLocal, int maximoRegistros) {
        this.fileName = fileName;
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
            System.out.println("Número máximo de registros atingido: " + this.maximoRegistros);
            return true;
        }
        return false;
    }

    void carregar() {
        Leitor leitor = new Leitor(this.fileName);
        this.registros = leitor.lerCompras();
    }

    void salvar() throws IOException {
        // criar arquivo com nome fileName
        File arquivo = new File(this.fileName);
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
