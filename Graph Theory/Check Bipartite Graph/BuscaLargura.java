public class BuscaLargura {
    public int[] orig;
    public int[] dest;
    private int totalV;
    private int t;
    private Lista fila;
    private int[] L;
    private int[] nivel;
    private int[] pai;

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
    }

    public boolean buscaBipartido () {
        for(int v=0;v<totalV;v++){
            if(L[v]==0){
                t++;
                L[v]=t;
                fila.inserir(v);
                if(!busca()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean busca () {
        while(!fila.isVazia()) {
            int v = fila.remover();
            for(int i=orig[v]; i<orig[v+1]; i++){
                int w = dest[i];
                if (L[w]==0) {
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
                    System.out.println("\nAresta irmão encontrada!\nVértice "+(v+1)+" com vértice "+(1+w)+", pai é "+(pai[v]+1));
                    return false;
                }
                else if (nivel[w] == nivel[v] && pai[w] != pai[v] && L[w] > L[v]){
                    // Aresta primo
                    System.out.println("\nAresta primo encontrada!\nVértice "+(v+1)+" com vértice "+(1+w)+", o pai de v é "+(1+pai[v])+" e o pai de w é "+(1+pai[w]));
                    return false;
                }
            }
        }
        //System.out.println("Saí");
        return true;
    }


}
