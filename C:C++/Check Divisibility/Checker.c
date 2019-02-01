/* 
Ryan Schlachter
CS370/HW3/Checker.c

System Calls:
read(): attempts to read up to 'count' bytes from file descriptor
		into the buffer.
shmat(): attach to memory identified by shmget().
shmdt(): detaches the shared memory segment located at the address
		 specified from the address space of the calling process.
*/
#include <stdio.h>
#include <sys/types.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/ipc.h>
#include <sys/shm.h>

int main(int argc, char *argv[]) {
	int denom = atoi(argv[1]);
	int numer = atoi(argv[2]);
	int pipeFD = atoi(argv[3]);
	int memID = 0;
	int *sharedMem;
	printf("Checker process [%d]: starting.\n", getpid());

	// read()
	if(read(pipeFD, &memID, sizeof(int)) < 0) {
		perror("Checker: read() has failed. Exiting.");
		exit(1);
	}	
	// shmat()
	sharedMem = shmat(memID, NULL, 0);
	if(*sharedMem < 0) {
		perror("Checker: shmat has failed. Exiting.");
		exit(1);
	}
	printf("Checker process [%d]: read 4 bytes containing shm ID %d\n", getpid(), memID);
	if(numer%denom == 0) { 
		printf("Checker process [%d]: %i *IS* divisible by %i.\n", getpid(), numer, denom); 
		printf("Checker process [%d]: wrote result (1) to shared memory.\n", getpid());
		*sharedMem = 1;
		// shmdt()
		if(shmdt(sharedMem) < 0) {
			perror("Checker: shmdt() has failed. Exiting.");
			exit(1);
		}
	} else {
		printf("Checker process [%d]: %i *IS NOT* divisible by %i.\n", getpid(), numer, denom); 
		printf("Checker process [%d]: wrote result (0) to shared memory.\n", getpid());
		*sharedMem = 0;
		// shmdt()
		if(shmdt(sharedMem) < 0) {
			perror("Checker: shmdt() has failed. Exiting.");
			exit(1);
		}
	}
	if(sharedMem == NULL) {
		return 0;
	} else {
		return 1;
	}
}

