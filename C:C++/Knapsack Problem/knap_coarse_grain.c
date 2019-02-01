// Knapsack problem coarse-grain implementation

#include <stdio.h>
#include <stdlib.h>
#include "timer.h"


#define    MAX(x,y)   ((x)>(y) ? (x) : (y))

long  *get_last_row(int start, int end, long *weights, long *profits, long C) {
  long *returnRow;
  returnRow = (long *) calloc(C+1, sizeof(long));
    
  //for(int k = 0; k <= C; k++) {
  //  printf("%d ", returnRow[k]);
  //}
  //printf("\n");
  
  for(int i = start-1; i < end; i++) {
    for(int j = C; j >= weights[i]; j--) {
      if(j >= weights[i]) {
        returnRow[j] = MAX((returnRow[j - weights[i]] + profits[i]), returnRow[j]);
      }
    }
    //printf("\n");
  }

  return (long*)returnRow;
}


void solve_kp(long start, long end, long *weights, long *profits, long C, int *solution) {
  if(start == end ) {
    if(weights[start-1] <= C) {  
      solution[start-1] = 1;
    }
  } else {
    long mid = (start + end) / 2;
    long midEnd = mid;
    long midStart = mid;

    if((start+end) % 2 == 0) {
      midEnd = mid - 1;
    } else {
      midStart = mid + 1;
    }
    #pragma omp parallel
    long *A1 = get_last_row(start, midEnd, weights, profits, C);
    #pragma omp parallel
    long *A2 = get_last_row(midStart, end, weights, profits, C);

    long cutA1 = C;
    long cutA2 = 0;
    int value = -1;
    long high = 0;
    for(int i = 0; i <= C; i++) {
      #pragma omp task
      {
        value = A1[C-i] + A2[i];
        if (value >= high) {
          high = value;
          cutA1 = C-i;
          cutA2 = i;
        }
      }
    }

    free(A1);
    free(A2);
    #pragma omp task 
    {
      solve_kp(start, midEnd, weights, profits, cutA1, solution);
      solve_kp(midStart, end, weights, profits, cutA2, solution);
    }

  }
}


void print_last_row(long *lastRow, int C) {
  //printf("Last row is: \n");
  for(int i = 0; i < C; i++) {
    printf("%d ", (long*)lastRow[i]);
  }
  printf("\n");
}


int main(int argc, char **argv) {

   FILE   *fp;
   long    N, C;                   // # of objects, capacity 
   long    *weights, *profits;     // weights and profits
   int    verbose,count;
   int  *solution;

   // Temp variables
   long    i, j, size;

   // Time
   double time;

   // Read input file (# of objects, capacity, (per line) weight and profit )
   if ( argc > 1 ) {
      fp = fopen(argv[1], "r"); 
      if ( fp == NULL) {
         printf("[ERROR] : Failed to read file named '%s'.\n", argv[1]);
         exit(1);
      }
   } else {
      printf("USAGE : %s [filename].\n", argv[0]);
      exit(1);
   }

   if (argc > 2) verbose = atoi(argv[2]); else verbose = 0;

   fscanf(fp, "%ld %ld", &N, &C);
   printf("The number of objects is %d, and the capacity is %d.\n", N, C);

   size = N * sizeof(long);
   weights = (long *)malloc(size);
   profits = (long *)malloc(size);

   long solutionSize = N * sizeof(int);
   solution = (int*)malloc(solutionSize);

   for ( i=0 ; i < N ; i++ ) {
      count = fscanf(fp, "%ld %ld", &(weights[i]), &(profits[i]));

      if ( count != 2 ) {
         printf("[ERROR] : Input file is not well formatted.\n");
         exit(1);
      }
   }

   fclose(fp);


   //print_last_row(get_last_row(1, 5, weights, profits, C), C);

   // print profits
  //for(int k = 0; k < N; k++) {
  //  printf("%d ", profits[k]);
  //}
  //printf("\n");

   
   initialize_timer ();
   start_timer();

   solve_kp(1, N, weights, profits, C, solution);

   // solve for optimal profits
   long optimalProfit = 0;
   for(int i = 0; i < N; i++) {
    if(solution[i] == 1) {
      optimalProfit += profits[i];
    }
   }

   stop_timer();
   time = elapsed_time ();
 

   printf("The optimal profit is %d \nTime taken : %lf.\n", optimalProfit, time);
      if (verbose==1) {
        printf("Solution vector is: ");
        for (i=0 ; i<N ; i++ ) {
           printf("%d ", solution[i]);
        }
        printf("\n");
      }

   return 0;
}
