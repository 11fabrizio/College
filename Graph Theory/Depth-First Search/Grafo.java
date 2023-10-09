import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

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
        // Implementar forward e reverse star
        // Pegar tempo antes da execução do algoritmo
        long inicioV = System.currentTimeMillis();
        // Chamar forward star e fazer tudo dentro dele
        ForwardStar(nomeArq);
        System.out.println("Forward Star finalizado com êxito!\n");
        // Fazer busca em profundidade
        
        BuscaProfundidade busca = new BuscaProfundidade(pointer, arcDest);
        System.out.println("Busca em profundidade realizada com êxito!\nForam visitados "+busca.buscaProfundidade()+" vértices");


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