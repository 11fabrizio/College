class sort {
    public static void Imprime(int V[]){
        for(int i=0;i<V.length;i++){
            System.out.print(V[i]+" ");
        }
        System.out.println();
    }

    public static boolean isOrdenado (int[] V){
        for(int i=0;i<V.length-1;i++){
            if(V[i]>V[i+1]){
                return false;
            }
        }
        return true;
    }

    public static void bubble (int[] V){
        while(!isOrdenado(V)){
            for(int i=0;i<V.length-1;i++){
                if(V[i]>V[i+1]){
                    int temp = V[i];
                    V[i]=V[i+1];
                    V[i+1]=temp;
                }
            }
        }
    }

    public static void insertion (int[] V){
        for(int i=0;i<V.length-1;i++){
            if(V[i]>V[i+1]){
                int j=i+1;
                while(j>=1 && V[j-1]>V[j]){
                    int temp = V[j];
                    V[j]=V[j-1];
                    V[j-1]=temp;
                    j--;
                }
            }
        }
    }

    public static void selection (int[] V){
        for(int i=0; i<V.length; i++){
            int menor=i;
            for(int j = i+1; j<V.length; j++){
                if(V[j]<V[menor]){
                    menor=j;
                }
            }
            int temp = V[menor];
            V[menor] = V[i];
            V[i] = temp;
        }
    }
    public static void main(String[] args){
        int arr[] = {5, 9, 1, 3, 8};
        //selection(arr);
        //insertion(arr);
        bubble(arr);
        Imprime(arr);


    }
}