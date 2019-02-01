# List of files
C_SRCS = Checker.c Coordinator.c
C_OBJS = Checker Coordinator
#C_HEADERS = symbol.h
OBJS = ${C_OBJS}
EXE = Checker
EXE2 = Coordinator

# Compiler and loader commands and flags
GCC = gcc
GCC_FLAGS = -g -std=c11 -Wall -c -I.
LD_FLAGS = -g -std=c11 -Wall -I.
# Compile .c files to .o files
.c.o: 
	$(GCC) -D_XOPEN_SOURCE $(GCC_FLAGS) $<
# Target is the executable
default: all
all: Checker Coordinator
Checker: Checker.o
	$(GCC) $(LD_FLAGS) Checker.o -o $(EXE)
Coordinator: Coordinator.o
	$(GCC) $(LD_FLAGS) Coordinator.o -o $(EXE2)
# Recompile C objects if headers change
${C_OBJS}: ${C_HEADERS}
# Clean up the directory
clean:
	rm -f *.o *~ $(EXE) $(EXE2)

package:
	tar -cvf Ryan_Schlachter_HW3.tar Checker.c Coordinator.c Makefile README.txt
