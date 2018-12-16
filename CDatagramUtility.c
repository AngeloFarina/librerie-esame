#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>

int main(int argc, char **argv){
	int nread, len;
	struct hostent *host, *clienthost;
	struct sockaddr_in clientaddr, servaddr;
	int sd, port;

	//-------------------------- CONTROLLO ARGOMENTI --------------------------
	{
		if (argc != 2){
			printf("Error: %s port\n", argv[0]);
			exit(1);
		}
		else{
			//controllo porta
			nread = 0;
			while (argv[1][nread] != '\0'){
				if ((argv[1][nread] < '0') || (argv[1][nread] > '9')){
					printf("Primo argomento non intero\n");
					printf("Error: %s port\n", argv[0]);
					exit(2);
				}
				nread++;
			}
			port = atoi(argv[1]);
			if (port < 1024 || port > 65535){
			      printf("Error: %s port\n", argv[0]);
			      printf("1024 <= port <= 65535\n");
			      exit(2);  	
	  		}
		}
	}

	//------------------ INIZIALIZZAZIONE INDIRIZZO CLIENT ---------------------
	memset((char *) &clientaddr, 0, sizeof(struct sockaddr_in));
	clientaddr.sin_family = AF_INET;
	clientaddr.sin_addr.s_addr == INADDR_ANY;
	clientaddr.sin_port = 0;

	//------------------ INIZIALIZZAZIONE INDIRIZZO SERVER ---------------------
	memset((char *) &servaddr, 0, sizeof(struct sockaddr_in));
	servaddr.sin_family = AF_INET;
	host = gethostbyname(argv[1]); // caso client con usage ./Client localhost serverport
	//controllo su host
	if (host == NULL){
		printf("%s not found in /etc/hosts\n", argv[1]);
		exit(2);
	}
	else{
		servaddr.sin_addr.s_addr = ((struct in_addr *)(host->h_addr))->s_addr;
		servaddr.sin_port = htons(port);
	}

	//----------------------- CREAZIONE SOCKET CLIENT -----------------------------
	sd = socket(AF_INET, SOCK_DGRAM, 0);
	if (sd < 0){
		perror("apertura socket");
		exit(1);
	}
	printf("Client: creata la socket sd=%d\n", sd);
	//parte server
	//------------------------- OPZIONI SERVER SOCKET ----------------------------
	if (setsockopt(sd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on)) < 0){
		perror("set opzioni socket ");
		exit(1);
	}

	//---------------------------- BIND SOCKET -------------------------------
	if (bind(sd, (struct sockaddr *) &clientaddr, sizeof(clientaddr)) < 0){
		perror("bind socket ");
		exit(1);
	}
	printf("Client: bind socket ok, alla porta %i\n", clientaddr.sin_port);

	//---------------------------- INVIO DATAGRAMMA -------------------------------
	len = sizeof(servaddr);
	/* richiesta operazione */
	if (sendto(sd, &nomeFile, strlen(nomeFile)+1, 0, (struct sockaddr *)&servaddr, len) < 0){
		perror("sendto");
		// se questo invio fallisce il client torna all'inzio del ciclo
		printf("Dammi il nome di file, EOF per terminare: ");
		continue;
	}

	//---------------------------- RICEZIONE DATAGRAMMA -------------------------------
	if (recvfrom(sd, &wordCount, sizeof(wordCount), 0, (struct sockaddr *) &servaddr, &len) < 0){
		perror("recvfrom");
		continue;
	}
	//parte server
	//------------------------- HOST SERVER SOCKET ----------------------------
	clienthost = gethostbyaddr((char *) &clientaddr.sin_addr,sizeof(clientaddr.sin_addr), AF_INET);
	if (clienthost == NULL)
		printf("client host information not found\n");
	else 
		printf("Operazione richiesta da: %s %i\n", clienthost->h_name, (unsigned) ntohs(cliaddr.sin_port));

	//---------------------------- CHIUSURA SOCKET -------------------------------
	close(sd);
}