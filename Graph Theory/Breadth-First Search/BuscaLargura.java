public class BuscaLargura {
    public int[] orig;
    public int[] dest;
    private int totalV;
    private int t;
    private Lista fila;
    private int[] L;
    private int[] nivel;
    private int[] pai;
    private int contVertices; // O que será retornado no main, total de vértices

    public BuscaLargura (int[] orig, int[] dest) {
        this.orig = orig;
        this.dest = dest;
        totalV = orig.length-1;
        t=0;
        fila = new Lista();
        L = new int[totalV];
        nivel = new int[totalV];
        pai = new int[totalV];
        for(int i=0;i<totalV;i++){
            L[i]=0;
            nivel[i]=0;
            pai[i]=0;
        }
        contVertices=0;
    }

    public int buscaBipartido () {
        for(int v=0;v<totalV;v++){
            if(L[v]==0){
                contVertices++;
                t++;
                L[v]=t;
                fila.inserir(v);
                busca();
            }
        }
        return contVertices;
    }

    public void busca () {
        while(!fila.isVazia()) {
            int v = fila.remover();
            for(int i=orig[v]; i<orig[v+1]; i++){
                //System.out.println(i);
                int w = dest[i];
                if (L[w]==0) {
                    contVertices++;
                    // Aresta árvore
                    pai[w]=v;
                    nivel[w]=nivel[v]+1;
                    t++;
                    L[w]=t;
                    fila.inserir(w);
                }
                else if (nivel[w] == nivel[v] + 1) {
                    // Aresta tio
                }
                else if (nivel[w] == nivel[v] && pai[w] == pai[v] && L[w] > L[v]){
                    // Aresta irmão
                }
                else if (nivel[w] == nivel[v] && pai[w] != pai[v] && L[w] > L[v]){
                    // Aresta primo
                }
            }
        }
        //System.out.println("Saí");
    }
}
