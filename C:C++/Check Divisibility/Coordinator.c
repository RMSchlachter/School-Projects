/* 
Ryan Schlachter
CS370/HW3/Coordinator.c

System Calls:
pipe():	creates pair of file descriptors, pointing to a pipe node, and places them
		into an array. pipe[0] is for read, pipe[1] is for write.
shmget(): identifier of shared memory segment.
shmat(): attach to memory identified by shmget().
sprintf(): sends formatted output to a string.
fork(): creates a new child process
exec(): replaces current process image with new process image. In this case, replaces
		coordinator process with checker process.
wait(): waits for state change in child process, once this happens, parent continues 
 		to execute. 
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/ipc.h>
#include <sys/types.h>
#include <sys/shm.h>

void coordinator(char *argv[]) {
	pid_t id[4];
	int *sharedMem[4];
	char string[50];
	int pipeFD[2];
	int shmid[4];
	int status = 0;

	for(int i = 0; i < 4; i++) {
		// pipe()
		if(pipe(pipeFD) < 0) {
			perror("Coordinator: pipe() has failed. Exiting.");
			exit(1);
		}
		// shmget()
		shmid[i] = shmget(IPC_PRIVATE, sizeof(int), IPC_CREAT | 0664);
		if(shmid[i] < 0) {
			perror("Coordinator: shmget() has failed. Exiting.");
			exit(1);
		}
		// shmat()
		sharedMem[i] = shmat(shmid[i], NULL, 0);
		if(*sharedMem[i] < 0) {
			perror("Coordinator: shmat() has failed. Exiting.");
			exit(1);
		}
		// sprintf()
		if(sprintf(string, "%d", pipeFD[0]) < 0) {
			perror("Coordinator: sprintf() has failed. Exiting.");
			exit(1);
		}
		// fork()
		id[i] = fork();
		if(id[i] < 0) { // fork fail case
			perror("Coordinator: fork() has failed. Exiting.");
			exit(1);
		} else if(id[i] == 0) { // is child, use pipe to read
			close(pipeFD[1]);
			// exec()
			status = execlp("./Checker", "Checker", argv[1], argv[i+2], string, NULL);
			if(status < 0) {
				perror("exec() has failed. Exiting.");
				exit(1);
			}
		} else { // is parent, use pipe to write
			// close()
			if(close(pipeFD[0]) < 0) {
				perror("Coordinator: close(pipeFD) has failed. Exiting.");
				exit(1);
			}
			printf("Coordinator: forked process with ID %d.\n", id[i]);
			// write()
			if(write(pipeFD[1], &shmid[i], sizeof(shmid[i])) < 0) {
				perror("Coordinator: write() has failed. Exiting.");
				exit(1);
			}
			printf("Coordinator: wrote shm ID %d to pipe. (4 bytes)\n", shmid[i]);
		}
	}
	// wait for all forks to return
	for(int i = 0; i < 4; i++) {
		printf("Coordinator: waiting for process ID %d...\n", id[i]);
		// wait()
		waitpid(id[i], &status, 0);
		if(*sharedMem[i] == 1) {
			printf("Coordinator: result %i read from shared memory: %i is divisible by %i.\n", *sharedMem[i], atoi(argv[i+2]), atoi(argv[1])); 
		} else if(*sharedMem[i] == 0) {
			printf("Coordinator: result %i read from shared memory: %i is not divisible by %i.\n", *sharedMem[i], atoi(argv[i+2]), atoi(argv[1])); 
		}
		// shmctl()
		if(shmctl(shmid[i], IPC_RMID, NULL) < 0) {
			perror("Coordinator: shmctl() has failed. Exiting.");
			exit(1);
		}	
	}
	printf("Coordinator: exiting.\n");
}	

int main(int argc, char *argv[]) {
	if(argc != 6) { // improper argument amount case
		perror("Coordinator: incorrect amount of arguments. Exiting.");
		exit(1);
	} else { // run coordinator
		coordinator(argv);
		return 0;
	}
}