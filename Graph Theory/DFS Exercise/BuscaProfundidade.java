import java.util.Stack;

public class BuscaProfundidade {
    private int t;
    private int[] TD;
    private int[] TT;
    private int[] pai;
    private int[] orig;
    private int[] dest;
    private int totalV;
    private int verticeBusca;
    //private int contVertices; // Para retornar ao main
    private int contArestas;
    private int[] arestasArvore;

    public BuscaProfundidade(int[] orig, int[] dest, int verticeBusca) {
        this.orig = orig;
        this.dest = dest;
        this.verticeBusca = verticeBusca;
        t = 0;
        totalV = orig.length - 1;
        TD = new int[totalV];
        TT = new int[totalV];
        pai = new int[totalV];
        for (int i = 0; i < totalV; i++) {
            TD[i] = 0;
            TT[i] = 0;
            pai[i] = 0;
        }
        //contVertices = 0;
        arestasArvore = new int[(totalV-1)*2];
        contArestas = 0;
    }

    public int[] buscaProfundidade() {
        for (int v = 0; v < totalV; v++) {
            if (TD[v] == 0) {
                buscaNaoRecursiva(v);
            }
        }
        return arestasArvore;
    }

    /*public void buscaProfundidade(int v) {
        Stack<Integer> stack = new Stack<>();
        stack.push(v);

        while (!stack.isEmpty()) {
            int current = stack.peek();
            if (TD[current] == 0) {
                TD[current] = ++t;
                contVertices++;

                for (int aux = orig[current]; aux < orig[current + 1]; aux++) {
                    int w = dest[aux];
                    if (TD[w] == 0) {
                        // Aresta de árvore
                        pai[w] = current;
                        stack.push(w);
                        // Se for da nossa busca de vértice inicial
                        if(current==verticeBusca){
                            System.out.println("Aresta {"+(current+1)+", "+(w+1)+"}: Aresta de árvore");
                        }
                    } else {
                        if (TT[w] == 0) {
                            // Aresta de retorno
                            if(current==verticeBusca){
                                System.out.println("Aresta {"+(current+1)+", "+(w+1)+"}: Aresta de retorno");
                            }
                        } else if (TD[current] < TD[w]) {
                            // Aresta de avanço
                            if(current==verticeBusca){
                                System.out.println("Aresta {"+(current+1)+", "+(w+1)+"}: Aresta de avanço");
                            }
                        } else {
                            // Aresta de cruzamento
                            if(current==verticeBusca){
                                System.out.println("Aresta {"+(current+1)+", "+(w+1)+"}: Aresta de cruzamento");
                            }
                        }
                    }
                }
            } else {
                stack.pop();
                if(TT[current] == 0){
                    TT[current] = ++t;
                }
            }
        }
    }*/
    /*public void teste(int v){
        TD[v]=++t;
        contVertices++;
        for(int aux = orig[v]; aux < orig[v + 1]; aux++){
            int w = dest[aux];
            if(TD[w]==0){
                // Aresta de árvore
                if(v==verticeBusca){
                            System.out.println("Aresta {"+(v+1)+", "+(w+1)+"}: Aresta de árvore");
                        }
                pai[w]=v;
                teste(w);
            } else{
                if(TT[w]==0){
                    // Aresta de retorno
                    if(v==verticeBusca){
                            System.out.println("Aresta {"+(v+1)+", "+(w+1)+"}: Aresta de retorno");
                        }
                } else if(TD[v]<TD[w]){
                    // Aresta de avanço
                    if(v==verticeBusca){
                            System.out.println("Aresta {"+(v+1)+", "+(w+1)+"}: Aresta de avanço");
                        }
                } else{
                    // Aresta de cruzamento
                    if(v==verticeBusca){
                            System.out.println("Aresta {"+(v+1)+", "+(w+1)+"}: Aresta de cruzamento");
                        }
                }
            }
        }
        TT[v]=++t;
    }*/

    public void buscaNaoRecursiva (int primeiro) {
        // Criação da pilha
        Stack <Integer> stack = new Stack<>();
        // Insere raiz
        stack.push(primeiro);
        // Executa até o fim da pilha
        while(!stack.empty()){
            // Analisa o primeiro elemento da pilha
            int v = stack.peek();
            // Descobre elementos novos
            if(TD[v]==0){
                TD[v]=++t;

                //contVertices++;
            }
            // Variável fundamental para o funcionamento da "simulação de recursão" do código
            // Responsável por apenas permitir a remoção de vértices da pilha caso eles sejam 100% explorados
            boolean check=true;
            for(int i=orig[v];i<orig[v+1];i++){ // Sucessores do vértice v
                // Pega sucessor i
                int w = dest[i];
                // Descobre vértices não descobertos
                if(TD[w]==0){
                    // Aresta de árvore
                    // Prepara aresta para ser imprimida no main
                    arestasArvore[contArestas]=v;
                    arestasArvore[contArestas+1]=w;
                    contArestas+=2;
                    // Condição para imprimir arestas do vértice de análise
                    if(v==verticeBusca){
                        System.out.println("Aresta ("+(v+1)+", "+(w+1)+"): Aresta de árvore");
                    }
                    pai[w]=v;
                    // Adiciona vértice na pilha
                    stack.push(w);
                    // Impede que vértice v seja removido da pilha, pois ainda não foi completamente explorado
                    check=false;
                    // Quebra o for, forçando com que esse novo elemento da pilha seja analisado
                    // Ponto fundamental para o conceito da busca em profundidade
                    break;
                } else{
                    if(TT[w]==0){
                        // Aresta de retorno
                        // Condição para imprimir arestas do vértice de análise
                        if(v==verticeBusca){
                            System.out.println("Aresta ("+(v+1)+", "+(w+1)+"): Aresta de retorno");
                        }
                    } else if(TD[v]<TD[w]) {
                        // Aresta de avanço
                        // Condição para imprimir arestas do vértice de análise
                        if(v==verticeBusca&&v!=pai[w]){
                            System.out.println("Aresta ("+(v+1)+", "+(w+1)+"): Aresta de avanço");
                        }
                    } else{
                        // Aresta de cruzamento
                        // Condição para imprimir arestas do vértice de análise
                        if(v==verticeBusca&&v!=pai[w]){
                            System.out.println("Aresta ("+(v+1)+", "+(w+1)+"): Aresta de cruzamento");
                        }
                    }
                }
            }
            // Checa se vértice foi 100% explorado para ser retirado da pilha
            if(check){
                TT[v]=++t;
                // Retira vértice da pilha
                stack.pop();
            }
        }
    }
}
