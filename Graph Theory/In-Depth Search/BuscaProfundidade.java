import java.util.Stack;

public class BuscaProfundidade {
    private int t;
    private int[] TD;
    private int[] TT;
    private int[] pai;
    private int[] orig;
    private int[] dest;
    private int totalV;
    private int contVertices; // Para retornar ao main

    public BuscaProfundidade(int[] orig, int[] dest) {
        this.orig = orig;
        this.dest = dest;
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
        contVertices = 0;
    }

    public int buscaProfundidade() {
        for (int v = 0; v < totalV; v++) {
            if (TD[v] == 0) {
                buscaProfundidade(v);
            }
        }
        return contVertices;
    }

    public void buscaProfundidade(int v) {
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
                    } else if (TT[w] == 0) {
                        // Aresta de retorno
                    } else if (TD[current] < TD[w]) {
                        // Aresta de avanço
                    } else {
                        // Aresta de cruzamento
                    }
                }
            } else {
                stack.pop();
            }
        }
    }
}
