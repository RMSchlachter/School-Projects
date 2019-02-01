//
// Created by Ryan Schlachter on 2/1/18.
//
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <sys/wait.h>
#include <signal.h>
#include <string>
#include <iostream>
#include <vector>

#define BACKLOG 5
#define PORT "5590"
char buffer[144];
int csockfd, ssockfd, clientsockfd;
volatile sig_atomic_t keepRunning = true;


void sigHandler(int sig) {
    printf("\nExiting...\n");
    keepRunning = false;
    strcpy(buffer, "exit");
    send(csockfd, (char*)&buffer, sizeof(buffer), 0);
    close(csockfd);
    close(ssockfd);
    close(clientsockfd);
    exit(0);
}


void pack(int bufSize) {

    char temp[bufSize];
    uint16_t version = htons(457);
    unsigned char v1 = ((version >> 8) & 0xFF);
    unsigned char v2 = (version & 0xFF);

    uint16_t size = htons(bufSize);
    unsigned char s1 = ((size >> 8) & 0xFF);
    unsigned char s2 = (size & 0xFF);

    memcpy(&temp[0], &v1, 1);
    memcpy(&temp[1], &v2, 1);
    memcpy(&temp[2], &s1, 1);
    memcpy(&temp[3], &s2, 1);

   for(int i = 4; i <= bufSize; i++) {
       memcpy(&temp[i], &buffer[i-4], 1);
   }

    memset(buffer, 0, sizeof(buffer)); //clear the buffer
    memcpy(&buffer, temp, bufSize);
    //std::cout << "pack: buffer size: " << strlen(buffer) << '\n';
    //printf("pack: %s\n", buffer);
    //for(int i = 0; i < bufSize; i++) {
    //    std::cout << buffer[i] << '\n';
    //}
}


void unpack(int bufSize) {

    if(strcmp(buffer, "exit") == 0) {
        return;
    }

    char temp[bufSize];

    ntohs(((buffer[0] << 8) | buffer[1]));
    ntohs(((buffer[2] << 8) | buffer[3]));

    //std::cout << "unpack: buffer size: " << bufSize << '\n';
    //std::cout << size << '\n';

    for(int i = 0; i <= bufSize-4; i++) {
        memcpy(&temp[i], &buffer[i+4], 1);
        //printf("%s", temp[i]);
    }

    memset(buffer, 0, sizeof(buffer)); //clear the buffer
    memcpy(&buffer, temp, strlen(temp));
    //std::cout << "unpack: strlen(temp): " << strlen(temp) << '\n';
}


void server() {

    printf("Welcome to Chat!\n");
    int portno;
    socklen_t clilen;

    char ip[100];
    struct sockaddr_in servAddress, clientAddress;
    struct sockaddr_in *server;
    struct addrinfo hints, *servinfo, *p;
    int result;

    char hostname[1024];
    hostname[1023] = '\0';
    gethostname(hostname, 1023);

    memset(&hints, 0, sizeof hints);
    hints.ai_family = AF_UNSPEC;
    hints.ai_socktype = SOCK_STREAM;
    hints.ai_flags = AI_CANONNAME;

    ssockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (ssockfd == -1) {
        perror("[SERVER] socket error");
    }

    bzero((char *) &servAddress, sizeof(servAddress));
    portno = atoi(PORT);

    servAddress.sin_family = AF_INET;
    servAddress.sin_addr.s_addr = htonl(INADDR_ANY);
    servAddress.sin_port = htons(portno);

    if ((result = getaddrinfo(hostname, "http", &hints, &servinfo)) == -1) {
        fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(result));
        exit(1);
    }

    for(p = servinfo; p != NULL; p = p->ai_next) {
        server = (struct sockaddr_in *) p->ai_addr;
        strcpy(ip, inet_ntoa(server->sin_addr));
        break;
    }


    printf("[SERVER] waiting for a connection on:\n");
    printf("%s port %i\n", ip, portno);

    if(bind(ssockfd, (struct sockaddr *)&servAddress, sizeof(servAddress)) == -1) {
        perror("[SERVER] bind error");
        exit(1);
    }

    listen(ssockfd, BACKLOG);
    clilen = sizeof(clientAddress);

    clientsockfd = accept(ssockfd, (struct sockaddr *) &clientAddress, &clilen);
    if (clientsockfd == -1) {
        perror("[SERVER] accept error");
    }

    printf("Found a friend! You receive first.\n");


    bool turn = false;

    struct sigaction act;
    act.sa_handler = sigHandler;
    sigaction(SIGINT, &act, NULL);

    while(keepRunning) {
        if(turn == true) {  // supposed to send
            turn = false;
            std::string data;
            getline(std::cin, data);
            if(data.length() >= 140) {
                printf("Error: Input too long.\n");
                turn = true;
                continue;
            } else {
                //std::cout << "strlen(buffer) < 140: " << strlen(buffer) << '\n';
                memset(&buffer, 0, sizeof(buffer)); //clear the buffer
                strcpy(buffer, data.c_str());
                turn = false;
                printf("You: %s\n",buffer);
                pack(sizeof(buffer));
                send(clientsockfd, (char*)&buffer, sizeof(buffer), 0);
            }
        }


        if(recv(clientsockfd, (char*)&buffer, sizeof(buffer), 0)) {
            turn = true;
            unpack(sizeof(buffer));
            if(strcmp(buffer, "exit") == 0) {
                keepRunning = 0;
                std::cout << "Friend has left." << '\n';
                std::cout << "Disconnecting..." << '\n';
                break;
            }
            printf("Friend: %s\n",buffer);
            memset(buffer, 0, sizeof(buffer)); //clear the buffer
        }
    }

    close(ssockfd);
    close(clientsockfd);
    exit(0);
}


void client(char* port, char* ip) {

    int portno;
    struct sockaddr_in serv_addr;
    struct hostent *server;

    portno = atoi(port);
    csockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (csockfd < 0)
        perror("ERROR opening socket");
    server = gethostbyname(ip);
    if (server == NULL) {
        fprintf(stderr,"ERROR, no such host\n");
        exit(0);
    }
    bzero((char *) &serv_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    bcopy(server->h_addr, (char *)&serv_addr.sin_addr.s_addr, server->h_length);
    serv_addr.sin_port = htons(portno);

    if(connect(csockfd,(struct sockaddr *) &serv_addr,sizeof(serv_addr)) < 0) {
        perror("ERROR connecting");
    } else {
        printf("Connecting to server... Connected!\n");
        printf("Connected to a friend! You send first.\n");

        bool turn = true;

        struct sigaction act;
        act.sa_handler = sigHandler;
        sigaction(SIGINT, &act, NULL);

        while(keepRunning) {

            if(turn == true) {  // supposed to send
                turn = false;
                std::string data;
                getline(std::cin, data);
                if(data.length() >= 140) {
                    printf("Error: Input too long.\n");
                    turn = true;
                    continue;
                } else {
                    memset(buffer, 0, sizeof(buffer)); //clear the buffer
                    memcpy(buffer, data.c_str(), strlen(data.c_str()));
                    //std::cout << "strlen(buffer) < 140: " << strlen(buffer) << '\n';
                    turn = false;
                    printf("You: %s\n",buffer);
                    pack(sizeof(buffer));
                    send(csockfd, (char*)&buffer, sizeof(buffer), 0);
                }
            }

            if(recv(csockfd, (char*)&buffer, sizeof(buffer), 0)) {
                turn = true;
                unpack(sizeof(buffer));
                if(strcmp(buffer, "exit") == 0) {
                    keepRunning = 0;
                    std::cout << "Server has disconnected." << '\n';
                    break;
                }
                printf("Friend: %s\n",buffer);
                memset(buffer, 0, sizeof(buffer)); //clear the buffer
            }
        }
    }

    close(csockfd);
    exit(0);
}


int main(int argc, char *argv[]) {

    char* ip;

    if (argc == 5) {               // has 4 valid arguments + program name
        char* port;
        if (strcmp(argv[1], "-s") == 0) {
            port = argv[4];
            ip = argv[2];
        } else if (strcmp(argv[1], "-p") == 0) {
            if(atoi(argv[2]) > 999 && atoi(argv[2]) <= 9999) {
                //std::cout << "Valid port number." << '\n';
            } else {
                perror("Invalid port number");
                exit(1);
            }
            port = argv[2];
            ip = argv[4];
        } else if (strcmp(argv[1], "-h") == 0) {
            std::cout << "Help message." << '\n';
            exit(1);
        } else {
            port = 0;
            ip = 0;
        }

    client(port, ip);


    } else if (argc == 1) {
        server();
    } else if (argc < 1) {
        perror("Main: No arguments given.");
        exit(1);
    } else if(argc == 2) {
        if(strcmp(argv[1], "-h") == 0) {
            printf("HELP IM DYING!!!!!\n");
            exit(1);
        }
    }

    return 0;
}
