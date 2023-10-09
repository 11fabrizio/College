/*
 * @author Fabrizio Peragallo de Mello
 */

/**
 * No código a seguir, implentei um código cujo objetivo é receber o nome de um arquivo fornecido pelo usuário, abri-lo
 * e realizar a leitura do grafo presente dentro dele, realizando algumas operações em cima do mesmo.
 * 
 * Inicialmente, segui a estratégia de usar a Matriz de Incidência, pois foi um método que me interessei durante as aulas
 * e, a partir da análise da matriz, era possível descobrir o vértice de maior grau de entrada e saída sem muito esforço (já que era um grafo direcionado).
 * Fiquei muito tempo trabalhando nela até conseguir monta-la e fazer todas as análises em cima dela, conseguindo, com êxito,
 * processar o grafo de 100 vértices. Porém, cometi o erro de não ter testado nenhuma vez com o de 50000 vértices, e quando 
 * terminei a matriz e fui para o grafo de 50000, ele retornou erro logo no começo, dizendo que houve um "estouramento" de 
 * memória. Depois de analisar o grafo e meu código, percebi que uma matriz de 50000 linhas por 1000000 de colunas estava sendo
 * criada, o que ocupava mais de 50000000000 posições na memória e se tornava inviável de ser executada. Cheguei à conclusão
 * de que a matriz de incidência, por mais que possua diversas informações do grafo de fácil acesso, só pode ser usada em casos
 * específicos onde o grafo não possui muitos vértices e não é denso.
 * 
 * Após isso, decidi implementar o Forward Star para conseguir as informações relacionadas aos graus de saída desse grafo maior.
 * A implementação do código foi tranquila pois eu havia entendido bem a teoria do método durante as aulas e, em poucos minutos,
 * já estava com ele funcional. Após isso, comecei a implementar o Reverse Star para buscar as informações relacioandas aos 
 * graus de entrada.
 * 
 * No começo, reaproveitei trechos do Forward Star com sucesso, porém chegou uma parte que prejudicou meu código: a ordenação
 * do vetor de destino. No começo, implementei um selection sort (O(n²)) para ordenar esse vetor pois era o método de seleção
 * mais fresco na minha cabeça para ser implementado. Após isso, criei o vetor de ponteiros e finalizei o algoritmo. Quando
 * fui rodar o código, ele ficou mais 5 minutos rodando e ainda me retornou um erro (não sei qual foi, mas provavelmente foi
 * algo relacionado ao grande tempo de execução ou algum index out of bounds), o que me deixou intrigado. Logo, adotei o método
 * do QuickSort (O(n*log n)) e o código rodou bem mais rápido, precisando de 2 segundos para sua execução completa.
 * 
 * Por fim, cheguei a conclusão de que os métodos ForwardStar e ReverseStar se encaixam bem para representar e armazenar grafos
 * como os passados no enunciado, onde é um grafo de tamanho estático. Além disso, percebi que a complexidade do código importa
 * muito e precisa de atenção redobrada sempre que se for trabalhar com um grafo denso (até o semestre passado, a complexidade
 * de código ficava mais na teoria, e nós nunca viamos na prática pois não mexíamos com grandes bases de dados ou operações,
 * mas agora ficou bem clara a importância de ter um código otimizado após a realização dessa atividade e da compreensão dos
 * grafos em si).
 * 
 * Como fiz o código (meios utilizados):
 * 
 * Para realizar o código, utilizei as anotações que fiz durante as aulas para montar os algoritmos para armazenar
 * os grafos e realizar as operações pedidas com ele no enunciado, além de ter usado da experiência de AEDS2 para
 * fazer boa parte das outras partes do código como input/output e criação de métodos. Precisei de recorrer ao StackOverflow
 * para relembrar como abrir e ler arquivos .txt e como filtrar uma String que possui "espaços" extras para transformá-la
 * em uma estrutura do tipo ("int int"), aplicando assim um .split(" ") para extrair os dois inteiros de cada linha do arquivo.
 * Além disso, utilizei o chat GPT para que ele me fizesse um método QuickSort que recebia como parâmetro um vetor. Após ele
 * me gerar um código Java, extraí a parte do QuickSort e adaptei ela ao meu código, de forma que ela passasse a receber dois
 * vetores como parâmetro (destino, arc_orig) e ordenasse o vetor de destino sempre mantendo sua paridade com o vetor de origem
 * (sempre que ordenava no vetor destino, fazia o mesmo "swap" no vetor arc_orig a partir dos índices que foram movimentados 
 * no vetor destino).
 * 
 * Referências bibliográficas:
 * 
 * StackOverflow (https://pt.stackoverflow.com/questions/203452/como-excluir-espa%C3%A7os-em-branco#:~:text=Para%20remover%20tamb%C3%A9m%20os%20espa%C3%A7os,texto%20com%20espa%C3%A7os%20em%20branco%22.) 
 * ChatGPT ("me faça um procedimento em java QuickSort que receba como parâmetro um vetor de inteiros")
 * Anotações durante sala de aula
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Grafo {
    public static int totalVertices=0,totalArestas=0;
    // Método do quicksort
    public static void quickSort(int[] arr, int[] arr2, int low, int high) {
        // Checa os pivôs
        if (low < high) {
            // Gera index do pivô
            int pivotIndex = partition(arr, arr2, low, high);
            // Chama recrusivamente o quicksort para ordenar a parte anterior do pivô
            quickSort(arr, arr2, low, pivotIndex - 1);
            // Chama recrusivamente o quicksort para ordenar a parte posterior do pivô
            quickSort(arr, arr2, pivotIndex + 1, high);
        }
    }

    public static int partition(int[] arr, int[] arr2, int low, int high) {
        // Realzia a partição do pivô
        int pivot = arr[high];
        int i = low - 1;
        // Realiza ordenação
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                // Ordena vetor destino
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                // Ordena vertor arc_orig
                temp = arr2[i];
                arr2[i]=arr2[j];
                arr2[j]=temp;
            }
        }
        // Termina de ordenar vetor destino
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        // Termina de ordenar vetor arc_orig
        temp = arr2[i+1];
        arr2[i+1]=arr2[high];
        arr2[high]=temp;

        return i + 1;
    }
    public static void ForwardStar(String nomeArq){
        try{
            FileReader arq = new FileReader(nomeArq);
            BufferedReader buff = new BufferedReader(arq);
            // Leitura da primeira linha
            String linha[] = buff.readLine().split("  ");
            totalVertices=Integer.parseInt(linha[0]);
            totalArestas=Integer.parseInt(linha[1]);
            int[] pointer = new int[totalVertices+1];
            int[] arcDest = new int[totalArestas];
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
            int grauSaida=0, posGrauSaida=0;
            // Achar maior grau de saída
            for(int i=0;i<totalVertices;i++){
                // Analisa grau do vértice i
                if(pointer[i+1]-pointer[i]>grauSaida){
                    // Atualiza vértice de maior grau de saída
                    grauSaida=pointer[i+1]-pointer[i];
                    posGrauSaida=i;
                }
            }
            /*System.out.println(pointer[49999]);
            System.out.println(pointer[totalVertices]);*/
            // Imprimir na tela resultados
            System.out.println("Vértice com maior grau de saída: "+(posGrauSaida+1));
            System.out.println("Grau de saída: "+grauSaida);
            System.out.print("Sucessores: ");
            // Imprimir Sucessores
            for(int i=pointer[posGrauSaida];i<pointer[posGrauSaida+1];i++){
                // Imprime todos os vértices que a origem aponta
                System.out.print((arcDest[i]+1)+" ");
            }
            System.out.println();
            buff.close();
        } catch(IOException e){
            System.out.println("Erro, não existe esse arquivo no diretório!");
        } catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Houve algum erro durante a execução do algoritmo!");
        }
    }
    public static void ReverseStar (String nomeArq){
        try{
            FileReader arq = new FileReader(nomeArq);
            BufferedReader buff = new BufferedReader(arq);
            // Leitura da primeira linha
            String linha[] = buff.readLine().split("  ");
            totalVertices=Integer.parseInt(linha[0]);
            totalArestas=Integer.parseInt(linha[1]);
            //System.out.println(totalVertices+"\n"+totalArestas);
            // Criar variáveis para armazenar o maior grau de entrada e seu vértice
            int grauEntrada=0, posGrauEntrada=0;
            // Criar vetor inicial de destino e origem
            int[] arcOrig = new int[totalArestas];
            int[] destInicial = new int[totalArestas];
            // Ler todas as linhas do arquivo restante
            for(int linhaArq=0;linhaArq<totalArestas;linhaArq++){
                // Mesmo processo que já foi comentado no Forward Star
                String tempLinha = buff.readLine();
                tempLinha = tempLinha.trim().replaceAll("\\s+"," ");
                linha = tempLinha.split(" ");
                int orig = Integer.parseInt(linha[0])-1;
                int dest = Integer.parseInt(linha[1])-1;
                // Deubug
                // System.out.println(orig+" "+dest);
                // Adicionar no vetor
                // Preenche vetor arcOrig
                arcOrig[linhaArq]=orig;
                // Preenche vetor destino (primeiro vetor)
                destInicial[linhaArq]=dest;
            }
            // Ordenar vetor de destino mantendo ponteiros no de origem
            quickSort(destInicial, arcOrig, 0, totalArestas-1);
            /*for(int i=0;i<totalArestas-1;i++){
                int menor = i;
                for(int j=i+1;j<totalArestas;j++){
                    if(destInicial[i]<destInicial[menor]){
                        menor=i;
                    }
                }
                int temp = destInicial[menor];
                destInicial[menor]=destInicial[i];
                destInicial[i]=temp;
                temp = arcOrig[menor];
                arcOrig[menor]=arcOrig[i];
                arcOrig[i]=temp;
            }*/
            // Criar novo vetor de ponteiros de destino
            int[] pointer = new int[totalVertices+1];
            // Setar primeira posição, que é sempre 0 (primeiro elemento de destino)
            pointer[0]=0;
            // Criar contador de vértices para auxiliar na criação do vetor de ponteiros
            int contVertices=0;
            // Preencher novo vetor a partir dos outros 2
            for(int i=0;i<totalArestas;i++){
                // Checar se mudou destino
                
                if(contVertices!=destInicial[i]){
                    // Inserir novo ponteiro no vetor de destinos
                    contVertices++;
                    pointer[contVertices]=i;
                    for(int j=contVertices;j!=destInicial[i];j++){
                        pointer[j+1]=pointer[j];
                        contVertices++;
                    }
                }
            }
            // Fechar o vetor
            pointer[totalVertices]=totalArestas;
            // Procurar maior grau de entrada
            for(int i=0;i<totalVertices;i++){
                // Checa grau de entrada do vértice i
                if(pointer[i+1]-pointer[i]>grauEntrada){
                    // Atualiza maior grau de entrada
                    grauEntrada=pointer[i+1]-pointer[i];
                    posGrauEntrada=i;
                }
            }
            // Imprimir resultados na tela
            System.out.println("Vértice com maior grau de entrada: "+(posGrauEntrada+1));
            System.out.println("Grau de entrada: "+grauEntrada);
            System.out.print("Predecessores: ");
            // Imprime todos vértices predecessores
            for(int i=pointer[posGrauEntrada];i<pointer[posGrauEntrada+1];i++){
                System.out.print((arcOrig[i]+1)+" ");
            }
            System.out.println();
            buff.close();
        } catch(IOException e){
            System.out.println("Erro, não existe esse arquivo no diretório!");
        } catch(Exception e){
            System.out.println("Houve algum erro durante a execução do algoritmo!");
        }
    }
    public static int[][] MatrizIncidencia(String nomeArq){
        try{ // Tenta abrir o arquivo pelo nome
            // Inicializa variáveis do total de vértices e arestas do grafo no arquivo
            // Tentativa de abrir o arquivo pelo java
            FileReader arq = new FileReader(nomeArq);
            BufferedReader buff = new BufferedReader(arq);
            // Caso tudo ocorra como esperado, leitura da primeira linha separando pelos espaços
            // OBS.: Cada linha sofrerá um split diferente devido ao formato do arquivo para que os vetores contenham
            // apenas 2 posições: vértice de origem e vértice do destino ou total de vértices e arestas (primeira linha)
            String linha[] = buff.readLine().split("  ");
            // Atribuir às variáveis de controle os valores totais de vértices e arestas
            totalVertices=Integer.parseInt(linha[0]);
            totalArestas=Integer.parseInt(linha[1]);
            // Fins de debug
            //System.out.println(totalVertices+"\n"+totalArestas);
            // Criação da matriz de incidência
            int M[][] = new int[totalVertices][totalArestas];
            // Origem será -1, destino +1. Será preenchida por colunas
            // Hora de continuar a leitura do arquivo, onde serão adquiridas as arestas
            int contAresta=0; // Será incrementado toda vez que passar de aresta
            for(int linhaArq=0;linhaArq<totalArestas;linhaArq++){
                String tempLinha = buff.readLine();
                tempLinha = tempLinha.trim().replaceAll("\\s+", " ");
                linha = tempLinha.split(" ");
                // Somando -1 em todos resultados pois o vértice começa do 1 e a matriz do 0
                // Lembrar de alterar na hora de imprimir resultado, somando +1 no vértice antes de imprimir
                int orig = Integer.parseInt(linha[0])-1;
                int dest = (Integer.parseInt(linha[1]))-1;
                // Configura origem e saída da aresta na matriz
                M[orig][contAresta]=-1;
                M[dest][contAresta]=1;
                // Vai para próxima coluna (no caso, a próxima aresta)
                contAresta++;
            }
            /* Debug para ver se estava funcional
            System.out.println(M[1][777]);
            System.out.println(M[99][777]);*/
            // Após matriz estar preenchida, é hora de retorná-la ao main para que sejam respondidas as questões
            buff.close();
            return M;
        } catch(IOException e){ // Caso não ache arquivo
            System.out.println("Erro, não existe esse arquivo no diretório!");
        } catch(Exception e){ // Caso haja algum erro durante execução do código (utilizar para debug caso ocorra algum erro)
            System.out.println("Houve algum erro durante a execução do algoritmo!");
        }
        // Caso aconteça algum erro no algoritmo, retorna nulo
        return null;
    }
    public static void main(String[] args){
        // Abrir scanner para leitura de input
        Scanner sc = new Scanner(System.in);
        // Pedir ao usuário que seja realizado o input do nome do arquivo
        System.out.print("Digite o nome do arquivo: ");
        // Leitura do nome
        String nomeArq = sc.nextLine();
        // Acionar método que fará abertura de arquivo e criação da matriz por incidência
        if(nomeArq.equals("grafo100.txt")){
            System.out.println("Matriz de Incidência\n");
            // Pegar tempo antes da execução do algoritmo
            long inicioM = System.currentTimeMillis();
            // Cria matriz no método, que é retornada ao main
            int M[][] = MatrizIncidencia(nomeArq);
            // Criar variável que vai receber vértice de maior grau
            // Achar vértices com maior grau de entrada e saída
            int grauEnt=0, posGrauEnt=0, grauSai=0, posGrauSai=0;
            for(int ln=0;ln<totalVertices;ln++){
                // Procurando maior grau de entrada e saída em cada linha da matriz
                int contEnt=0, contSai=0;
                // Pegar grau de entrada e saída do vértice ln
                for(int col=0;col<totalArestas;col++){
                    if(M[ln][col]==1){
                        contEnt++;
                    } else if(M[ln][col]==-1){
                        contSai++;
                    }
                }
                // Atualiza maior grau de entrada
                if(contEnt>grauEnt){
                    grauEnt=contEnt;
                    posGrauEnt=ln;
                }
                // Atualiza maior grau de saída
                if(contSai>grauSai){
                    grauSai=contSai;
                    posGrauSai=ln;
                }
            }
            // Mostrar vértice de maior grau de entrada e calcular o que precisa
            //System.out.println(posGrauEnt+1+"\n"+grauEnt);
            // Imprimir resultados
            System.out.println("Vértice com maior grau de saída: "+(posGrauSai+1));
            System.out.println("Grau de saída: "+grauSai);
            System.out.print("Sucessores: ");
            // Imprimir sucessores
            for(int i=0;i<totalArestas;i++){
                if(M[posGrauSai][i]==-1){
                    //System.out.println("Entrei");
                    for(int j=0;j<totalVertices;j++){
                        if(M[j][i]==1){
                            System.out.print((j+1)+" ");
                            j=totalVertices;
                        }
                    }
                }
            }
            System.out.println();
            System.out.println("Vértice com maior grau de entrada: "+(posGrauEnt+1));
            System.out.println("Grau de entrada: "+grauEnt);
            System.out.print("Predecessores: ");
            // Imprimir Predecessores
            for(int i=0;i<totalArestas;i++){
                if(M[posGrauEnt][i]==1){
                    //System.out.println("Entrei");
                    for(int j=0;j<totalVertices;j++){
                        if(M[j][i]==-1){
                            System.out.print((j+1)+" ");
                            j=totalVertices;
                        }
                    }
                }
            }
            System.out.println();
            // Calcular tempo de execução
            long totalM = (System.currentTimeMillis()-inicioM)/1000;
            // Imprimi-lo
            System.out.println("\nTempo de execução: "+totalM+" segundos");
        } else{
            // O arquivo aberto será o de 50000 vértices
            // Implementar forward e reverse star
            // Pegar tempo antes da execução do algoritmo
            long inicioV = System.currentTimeMillis();
            System.out.println("Forward Star\n");
            // Chamar forward star e fazer tudo dentro dele
            ForwardStar(nomeArq);
            // Implementar forward
            System.out.println("\nReverse Star\n");
            // Chamar reverse star e fazer tudo dentre dele
            // Implementar reverse
            ReverseStar(nomeArq);
            // Calcular tempo de execução
            long totalV = (System.currentTimeMillis()-inicioV)/1000;
            // Imprimi-lo
            System.out.println("\nTempo de execução: "+totalV+" segundos");
        }
        // Fechar Scanner
        sc.close();
        // Fim do programa
    }
}