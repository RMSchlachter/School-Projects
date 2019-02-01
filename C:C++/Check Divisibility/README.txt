README
======

Ryan Schlachter, CS370 HW3

This package includs the following files:

Coordinator.c 		Accepts five command line arguments which will be used in Checker.c to determinewhether or not the arguments are divisible by the first argument. It creates 4 child processes using fork, all of which run simultaneously with shared memory, by using a pipe which provides the shared memory ID.

Checker.c 		Accepts three command line arguments, passed from Coordinator using execlp(), determines whether or not args 2-5 are divisible by arg1, stores a 1 in shared memory if they are divisible, or a 0 in shared memory if they are not divisible. Memory is detached after it is written, and a value 1 or 0 is returned if the write was successful or unsuccessful, respectively.

Makefile		Compiles Coordinator.c and Checker.c together.

README.txt		This file.
