/**
 * @author Fabrizio Peragallo de Mello
 * 
 * Na minha classe "Main", li o nome do arquivo e o vértice escolhido para análise que o usuário informa. Após isso,
 * abri o arquivo no método forwardstar e criei o grafo no modelo forwardstar nesse próprio método (reaproveitei do
 * envio do meu trabalho anterior, modificando apenas algumas coisas para adaptar à nova solução). Após isso, crio
 * um objeto do tipo BuscaProfundidade, que tem como construtor a preparação de um grafo junto a todos atributos necessários
 * para realizar a busca em profundidade. Nesse método, tive um problema relacionado à recursividade, onde ocorreu o
 * estouro da pilha de chamadas recursivas na leitura do grafo grande. Inicialmente recorri ao Chat GPT, mas ele me deu
 * uma solução errônea que só fui descobrir muito tempo depois. Acabei criando do 0 uma busca em profundidade iterativa
 * utilizando uma pilha para conseguir controlar a "recursão" da busca. No próprio método é imprimido as arestas do vértice
 * escolhido e o retorno da função é um vetor com todas as arestas de árvore do grafo, que são imprimidas no "Main" após
 * a utilização do método.Ao final, o tempo de resposta é impresso.
 * 
 * Para realização desse trabalho, recorri a perguntas em sala de aula e ao Chat GPT, que mais me atrapalhou do que ajudou.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;

public class Grafo {
    public static int totalVertices=0,totalArestas=0;
    public static int[] pointer;
    public static int[] arcDest;
    public static void ForwardStar(String nomeArq){
        try{
            FileReader arq = new FileReader(nomeArq);
            BufferedReader buff = new BufferedReader(arq);
            // Leitura da primeira linha
            String linha[] = buff.readLine().trim().replaceAll("\\s+"," ").split(" ");
            totalVertices=Integer.parseInt(linha[0]);
            totalArestas=Integer.parseInt(linha[1]);
            pointer = new int[totalVertices+1];
            arcDest = new int[totalArestas];
            //System.out.println(totalVertices+"\n"+totalArestas);
            int contVertices=0; // Controlar acesso ao vetor pointer
            // Criar variáveis para armazenar maior grau de saída e seu vértice
            // Ler todas as linhas do arquivo restante (já comentado na Matriz de Incidência)
            for(int linhaArq=0;linhaArq<totalArestas;linhaArq++){
                String tempLinha = buff.readLine();
                tempLinha = tempLinha.trim().replaceAll("\\s+"," ");
                linha = tempLinha.split(" ");
                // Ler os 2 inteiros do arquivo
                int orig = Integer.parseInt(linha[0])-1;
                int dest = Integer.parseInt(linha[1])-1;
                // Preenche vetor de destino
                arcDest[linhaArq]=dest;
                // Analisa se mudou origem
                if(contVertices!=orig){
                    // Preencha próximo ponteiro para vetor de destino
                    contVertices++;
                    pointer[contVertices]=linhaArq;
                    for(int i=contVertices;i!=orig;i++){
                        pointer[i+1]=pointer[i];
                        contVertices++;
                    }
                }
                // Debug
                // System.out.println(orig+" "+dest);
            }
            // Fechar vetor
            pointer[totalVertices]=totalArestas;
            for(int i=0; i<totalVertices; i++){
                int V[] = new int[pointer[i+1]-pointer[i]];
                for(int j=pointer[i];j<pointer[i+1];j++){
                    V[j-pointer[i]] = arcDest[j];
                }
                Arrays.sort(V);
                for(int j=pointer[i];j<pointer[i+1];j++){
                    arcDest[j]=V[j-pointer[i]];
                }
            }
            buff.close();
        } catch(IOException e){
            System.out.println("Erro, não existe esse arquivo no diretório!");
        } catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Houve algum erro durante a execução do algoritmo!");
        }
    }
    public static void main(String[] args){
        // Abrir scanner para leitura de input
        Scanner sc = new Scanner(System.in);
        // Pedir ao usuário que seja realizado o input do nome do arquivo
        System.out.print("Digite o nome do arquivo: ");
        // Leitura do nome
        String nomeArq = sc.nextLine();
        // Pedir ao usuário pelo vértice
        System.out.print("Digite o vértice para análise: ");
        int vertice = sc.nextInt();
        // Limpar buffer de leitura (tem um "enter" sobrando nele)
        sc.nextLine();
        // Pegar tempo antes da execução do algoritmo
        long inicioV = System.currentTimeMillis();
        // Chamar forward star e gerar o grafo a partir da leitura do arquivo
        ForwardStar(nomeArq);
        System.out.println("Forward Star finalizado com êxito!\n");
        // Prints inicias para indicar arestas oriundas do vértice de análise escolhido
        System.out.println("Arestas do vértice de análise\n");
        // Fazer busca em profundidade
        BuscaProfundidade busca = new BuscaProfundidade(pointer, arcDest, vertice-1);
        int[] arestasArvore = busca.buscaProfundidade();
        System.out.print("\nArestas de árvore: ");
        for(int i=0;i<arestasArvore.length;i+=2){
            System.out.print("("+(arestasArvore[i]+1)+" "+(arestasArvore[i+1]+1)+") ");
        }
        System.out.println("\n\nBusca em profundidade realizada com êxito!");


        // Calcular tempo de execução
        long totalV = (System.currentTimeMillis()-inicioV)/1000;
        // Imprimi-lo
        System.out.println("\nTempo de execução: "+totalV+" segundos");
        // System.out.println(arcDest[pointer[12]]);
        // Fechar Scanner
        sc.close();
        // Fim do programa
    }
}