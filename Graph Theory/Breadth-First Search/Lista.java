public class Lista {
    public Celula primeiro;
    public Celula ultimo;
    public int tam;

    public Lista () {
        primeiro = new Celula();
        ultimo = primeiro;
        tam=0;
    }

    public void inserir (int v) {
        tam++;
        Celula celula = new Celula(v);
        ultimo.prox = celula;
        ultimo = celula;
    }

    public int remover () {
        tam--;
        Celula temp = primeiro.prox;
        primeiro.prox=temp.prox;
        if(tam==0){
            ultimo=primeiro;
        }
        return temp.vertice;
    }

    public boolean isVazia () {
        return tam==0;
    }

    public void imprimir () {
        for(Celula temp = primeiro.prox; temp!=null; temp=temp.prox){
            System.out.println(temp.vertice);
        }
    }
}