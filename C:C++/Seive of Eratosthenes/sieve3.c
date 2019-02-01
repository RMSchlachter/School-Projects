
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "timer.h"

#define MIN(a,b) (((a)<(b))?(a):(b))
#define MAX(a,b) (((a)>(b))?(a):(b))


long FMIB(long prime, long blockStart, long blockSize) {
    long i;

    for (i=blockStart; i < (blockSize * blockStart); i++) {
        if (i%prime == 0) {
          //printf("returning %ld\n\n", i);
          return i;
        }
    }
    return 0;
}


int main(int argc, char **argv) {

   long N  = 100;
   long blockSize = 10;

   char *mark;
   char primes[3] = {3,5,7};
   long   size;
   long   curr;
   long   i, j, n, k, ii;
   long   count;
   long   blockStart;
   long   index = 0;
   long   numprimes;
   long   start;

   /* Time */

   double time;

   if ( argc > 1 ){
    N  = atoi(argv[1]);
    if(argc > 2 ) {
      blockSize = atoi(argv[2]);
    }
   }


   n = sqrt(N);
   size = (N+1)*sizeof(char);
   mark = (char *)malloc(size);
   for (i=2; i<=N; i=i+1){
     mark[i]=0;
   }

   numprimes = count;
   blockStart = 0;
   blockSize = n;

    /* Start Timer */
   initialize_timer();
   start_timer();

   long blockNum = 1;
   while(blockStart < N) {
    for (ii=blockStart; ii<MIN(blockStart+blockSize, N); ii+=blockSize) {
      for (j=0; j<=numprimes; j++) {
        if(primes[j] == 0) {
          continue;
        }
        for (i=FMIB(primes[j], blockStart, blockSize); i<=MIN(blockStart+blockSize, N); i+= primes[j]) {
            if(i == 3 || i == 5 || i == 7) {
                continue;
            } else {
                mark[i]=1;
                //printf("mark[%i] = 1\n", i);
            }
        }
      }
     }

     blockStart += blockSize;
   }


   /* stop timer */
   stop_timer();
   time=elapsed_time ();


   /*number of primes*/
   count = 1;
   for(i = 3; i <=N; i+=2){
        if(mark[i] == 0) {
          /* printf("\t prime %ld  \n",i ); */
          ++count;
        }
   }

   printf("There are %ld primes less than or equal to %ld\n", count, N);

   /* print results */
   printf("First three primes:");
   j = 1;
   printf("%d ", 2);
   for ( i=3 ; i <= N  && j < 3; i+=2 ) {
      if (mark[i]==0){
            printf("%ld ", i);
            ++j;
      }
   }
   printf("\n");

   printf("Last three primes:");
   j = 0;
   n=(N%2?N:N-1);
   for (i = n; i > 1 && j < 3; i-=2){
     if (mark[i]==0){
        printf("%ld ", i);
        j++;
     }
   }
   printf("\n");


   printf("elapsed time = %lf (sec)\n", time);

   free(mark);
   return 0;
}




