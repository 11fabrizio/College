// Client side C program to demonstrate Socket
// programming
#include <arpa/inet.h>
#include <stdio.h>
#include <string.h>
#include <sys/socket.h>
#include <unistd.h>
#define PORT 5000
 
int main(int argc, char const* argv[])
{
    struct sockaddr_in serv_addr;
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_port = htons(PORT);
    char ip_server[32] = {0};
    printf("Digite o ip do servidor AWS: ");
    scanf("%s", ip_server);

    // Convert IPv4 and IPv6 addresses from text to binary
    // form
    if (inet_pton(AF_INET, ip_server, &serv_addr.sin_addr) <= 0) {
        printf(
            "\nInvalid address/ Address not supported \n");
        return -1;
    }
    while(1){
        int status, valread, client_fd;
        char buffer[1024] = { 0 };
        if ((client_fd = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
            printf("\n Socket creation error \n");
            return -1;
        }
    
        if ((status = connect(client_fd, (struct sockaddr*)&serv_addr, sizeof(serv_addr))) < 0) {
            printf("\nConnection Failed \n");
            return -1;
        }
        char mensage[1024];
        printf("Chat: ");
        scanf("%s", mensage);
        send(client_fd, mensage, strlen(mensage), 0);
        //printf("\nMessage sent\n");
        valread = read(client_fd, buffer, 1024 - 1); // subtract 1 for the null
        // terminator at the end
        printf("%s\n", buffer);
    
        // closing the connected socket
        close(client_fd);
    }
    return 0;
}