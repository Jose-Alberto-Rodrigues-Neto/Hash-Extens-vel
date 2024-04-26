import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Diretorio {
    private List<Bucket> buckets;
    private int profundidadeGlobal;

    public Diretorio(int profundidade) {
        this.profundidadeGlobal = profundidade;
        int tamanhoInicial = (int) Math.pow(2, profundidadeGlobal);
        this.buckets = new ArrayList<>(tamanhoInicial);

        for (int i = 0; i < tamanhoInicial; ++i) {
            String nomeArquivo = "bucket_" + i + ".csv";
            this.buckets.add(i, new Bucket(nomeArquivo, profundidade, MAXIMO_REGISTROS));
        }
    }

    public Par<Integer, Integer> inserirRegistro(Compra compra) {
        int indice = FuncHash.calcularHash(compra.getAno(), this.profundidadeGlobal);
        Bucket bucket = this.buckets.get(indice);
        boolean duplicouDiretorio = false;
    
        while (true) {
            bucket.carregar();
            if (bucket.adicionarRegistro(compra)) {
                break; // Sai do loop se a inserção for bem-sucedida
            }
            if (bucket.getProfundidadeLocal() == profundidadeGlobal) {
                duplicarDiretorio();
                duplicouDiretorio = true;
            }
            dividirBucket(indice);
            // Atualiza o índice e o bucket após dividir o bucket
            indice = FuncHash.calcularHash(compra.getAno(), this.profundidadeGlobal);
            bucket = this.buckets.get(indice);
        }
        bucket.salvar();
        return new Par<>(bucket.getProfundidadeLocal(), duplicouDiretorio);
    }
    

    private void duplicarDiretorio() {
        int tamanhoAtual = buckets.size();
        profundidadeGlobal++;
        buckets.addAll(new ArrayList<>(buckets));
    }

    private void dividirBucket(int indice) {
        Bucket bucket = this.buckets.get(indice);
        List<Compra> registrosAntigos = new ArrayList<>(bucket.getRegistros());
        bucket.getRegistros().clear();

        int profundidadeLocal = bucket.getProfundidadeLocal();
        int novaProfundidadeLocal = profundidadeLocal + 1;
        bucket.setProfundidadeLocal(novaProfundidadeLocal);

        String novaFilename = "bucket_" + this.buckets.size() + ".csv";
        Bucket novoBucket = new Bucket(novaFilename, novaProfundidadeLocal);
        this.buckets.add(novoBucket);

        for (Compra compra : registrosAntigos) {
            int novoIndice = FuncHash.calcularHash(compra.getAno(), novaProfundidadeLocal);
            if (novoIndice == indice) {
                bucket.adicionarRegistro(compra);
            } else {
                novoBucket.adicionarRegistro(compra);
            }
        }
        bucket.salvar();
        novoBucket.salvar();
    }

    public Par<Integer, Integer> removerRegistros(int ano) {
        int indice = FuncHash.calcularHash(ano, profundidadeGlobal);
        Bucket bucket = buckets.get(indice);

        bucket.carregar();
        int contagemInicial = bucket.getRegistros().size();

        bucket.setRegistros(bucket.getRegistros()
            .stream()
            .filter(compra -> compra.getAno() != ano)
            .collect(Collectors.toList()));

        int contagemFinal = bucket.getRegistros().size();
        int tuplasRemovidas = contagemInicial - contagemFinal;

        bucket.salvar();

        return new Par<>(tuplasRemovidas, bucket.getProfundidadeLocal());
    }

    public int buscarPorAno(int ano) {
        int indice = FuncHash.calcularHash(ano, profundidadeGlobal);
        Bucket bucket = buckets.get(indice);

        bucket.carregar(); 
        
        int quantidadeEncontrada = (int) bucket.getRegistros()
            .stream()
            .filter(compra -> compra.getAno() == ano)
            .count();

        return quantidadeEncontrada;
    }

    public void imprimir() {
        System.out.println("Profundidade Global: " + profundidadeGlobal);
        for (Bucket bucket : this.buckets) {
            bucket.imprimir();
        }
    }

    public int getProfundidadeGlobal(){
        return this.profundidadeGlobal;
    }
}
