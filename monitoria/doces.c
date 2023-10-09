#include <stdio.h>

int main(){
    int n;
    scanf("%d",&n);
    for(int i=0; i<n; i++){
        int menor=0;
        int total;
        scanf("%d",&total);
        int caixas[total];
        for(int j=0;j<total;j++){
            scanf("%d",&caixas[j]);
            if(caixas[j]<caixas[menor]){
                menor=j;
            }
        }
        int soma=0;
        for(int j=0;j<total;j++){
            if(j!=menor){
                soma+=caixas[j]-caixas[menor];
            }
        }
        printf("%d\n",soma);
    }
}