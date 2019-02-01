#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

struct Process{
    int Process_ID;
    int Arrival_Time;
    int Burst_Duration;
    int Priority;
    int TurnAroundTime;
    int WaitTime;
};
//**********************************************************
struct Gantt
{
    int Process_ID;
    int Arrival_Time;
    int End_Time;
};

void File_Content(struct  Process Processes[], int number);
void FCFS(struct Process Processes[], int number);
void SJFP(struct Process Processes[], int number);
void PRIORITY(struct Process Processes[], int number);
void RR(struct Process Processes[], int number, int quantum);
void Display_Output(struct  Process Processes[],int number,struct Gantt Rows[],int count, char* filename);

int main(int argc, char **argv)
{
    
    int number_of_pross = 0;
    
    if (argc != 2)
    {
        printf("Incorrect number of arguments.\n");
        return -1;
    }
    
    FILE* file1 = fopen(argv[1], "r");
    
    while(!feof(file1))
    {
        char  ch = fgetc(file1);
        if(ch == '\n')
        {
            number_of_pross++;
        }
    }
    struct Process Processes[number_of_pross];
    
    fseek(file1, 0, SEEK_SET);
    
    
    number_of_pross=0;
    
    
    while(fscanf(file1, "%d,%d,%d,%d",&Processes[number_of_pross].Process_ID,&Processes[number_of_pross].Arrival_Time,&Processes[number_of_pross].Burst_Duration,&Processes[number_of_pross].Priority) != EOF   )
    {
        number_of_pross++;
    }
    fclose(file1);
    //printf("Processes[number]: %d[%d]: %d\n", Processes[number_of_pross-1].Process_ID, number_of_pross-1, Processes[number_of_pross-1].Priority);

    
    //File_Content(Processes,number_of_pross);
    FCFS(Processes,number_of_pross);
    SJFP(Processes,number_of_pross);
    PRIORITY(Processes,number_of_pross);
    
    return 0;
}
//--------------------------------------------------------
void File_Content(struct Process Processes[], int number)
{
    int i;
    printf("PROCESS ID\tARRIVAL TIME\tBURST DURATION\tPRIORITY\n");
    for(i=0;i<number;i++)
    {
        
        printf("%d\t\t%d\t\t%d\t\t%d\n",Processes[i].Process_ID,Processes[i].Arrival_Time,Processes[i].Burst_Duration,Processes[i].Priority);
        
    }
}
//---------------------------------------------------------------
void Display_Output(struct Process Processes[],int number,struct Gantt Rows[],int count, char *filename)
{
    FILE* file;
    int i;
    file=fopen(filename,"w");
    fprintf(file,"PROCESS ID\tARRIVAL TIME\tEND TIME\n");
    
    for(i=0;i<count;i++)
    {
        fprintf(file,"%10d%10d%10d\n",Rows[i].Process_ID,Rows[i].Arrival_Time,Rows[i].End_Time);
    }
    
    float avgWait=0;
    float avgTurnAround=0;
    for(i=0;i<number;i++)
    {
        avgWait+=Processes[i].WaitTime;
        avgTurnAround+=Processes[i].TurnAroundTime;
    }
    fprintf(file,"Average Wait Time=%f\n",avgWait/number);
    fprintf(file,"TurnAround Time=%f\n",avgTurnAround/number);
    // Assumption: the value of count recieved is greater than exact number of rows in the Gantt chart by 1. Dont forget to adjust the value if yours is not so.
    fprintf(file,"Throughput=%f\n",((float)number/Rows[count-1].End_Time));
    fclose(file);
    
}
//------------------------------------------------------------

/*
 Note: Use function Display_Output to display the result. The Processes is the
 updated Processes with their respective waittime, turnAround time.
 Use the array of Gantt Struct to mimic the behaviour of Gantt Chart. Rows is the base name of the same array.
 number is the number of processes which if not changed during processing, is taken care. count is the number of rows in the Gantt chart.
 
 Number of Processes will not exceed 1000. Create and array of Gantt chart which can accomodate atleast 1000 process.
 */
// -------------FCFS----------------------------------------

/*void addToGantt(struct Process process[], struct Gantt gantt, int ganttSize, int index) {
    gantt[ganttSize-1].End_Time = process[index].Arrival_Time;
    gantt[ganttSize].Process_ID = process[index].Process_ID;
    printf("push = true: added %d to gantt\n", pushArray[index].Process_ID);
    gantt[ganttSize].Arrival_Time = process[index].Arrival_Time;
    ganttSize++;
}*/

bool checkexistgantt(struct Process checkGantt, struct Gantt gantt[], int number) {
    //printf("checkexist: checkProcess ")
    for(int i = 0; i < number; i++) {
        if(gantt[i].Process_ID == checkGantt.Process_ID) {
            // if already in gantt, return true
            //printf("checkexist: found %d at gantt[%d]\ncheckGantt: returning true.\n", checkGantt.Process_ID, i);
            return true;
        }
    }
    return false;
}

bool checkexist(struct Process checkProcess, struct Process pushArray[], int number) {
    //printf("checkexist: checkProcess ")
    for(int i = 0; i < number; i++) {
        if(pushArray[i].Process_ID == checkProcess.Process_ID) {
            // if already in pushArray, return true
            //printf("checkexist: found %d at pushArray[%d]\ncheckexist: returning true.\n", checkProcess.Process_ID, i);
            return true;
        }
    }
    return false;
}

void prioritysort(struct Process PRIOProcess[], int number) {
    struct Process swap;
    for(int i = 0; i <= number; i++) {
        for(int j = 0; j <= number; j++) {
            if(PRIOProcess[j].Arrival_Time == PRIOProcess[i].Arrival_Time) {
                if(PRIOProcess[j].Priority < PRIOProcess[i].Priority) {
                    swap = PRIOProcess[i];
                    PRIOProcess[i] = PRIOProcess[j];
                    PRIOProcess[j] = swap;
                }
            }
        }
    }
    
    printf("BURST SORT\n");
    printf("PROCESS ID\tARRIVAL TIME\tBURST DURATION\tPRIORITY\n");
    for(int i = 0; i <= 100; i++) {
        printf("%d\t\t%d\t\t%d\t\t%d\n", PRIOProcess[i].Process_ID, PRIOProcess[i].Arrival_Time, PRIOProcess[i].Burst_Duration, PRIOProcess[i].Priority);
    }
}


void burstsort(struct Process SJFProcess[], int sortSize) {
    struct Process swap;
    //printf("sortSize: %d", sortSize);
    for(int i = 0; i < sortSize; i++) {
        for(int j = 0; j < sortSize; j++) {
            //if(SJFProcess[j].Arrival_Time == SJFProcess[i].Arrival_Time) {
            if(SJFProcess[j].Burst_Duration > SJFProcess[i].Burst_Duration) {
                swap = SJFProcess[i];
                SJFProcess[i] = SJFProcess[j];
                SJFProcess[j] = swap;
            }
            //}
        }
    }
    
    /*printf("BURST SORT\n");
    printf("PROCESS ID\tARRIVAL TIME\tBURST DURATION\tPRIORITY\n");
    for(int i = 0; i < 100; i++) {
        printf("%d\t\t%d\t\t%d\t\t%d\n", SJFProcess[i].Process_ID, SJFProcess[i].Arrival_Time, SJFProcess[i].Burst_Duration, SJFProcess[i].Priority);
    }*/
}


void arrivalsort(struct Process FCFSProcess[], int number) {
    struct Process swap;
    for(int i = 0; i < number; i++) {
        //printf("FCFSProcess[%i].Arrival_Time: %d\n", i,  FCFSProcess[i].Arrival_Time);
        for(int j = 0; j < number; j++) {
            //printf("FCFSProcess[%i].Arrival_Time: %d\n", j, FCFSProcess[j].Arrival_Time);
            if(FCFSProcess[j].Arrival_Time == FCFSProcess[i].Arrival_Time) {
                if(FCFSProcess[j].Process_ID > FCFSProcess[i].Process_ID) {
                    swap = FCFSProcess[j];
                    FCFSProcess[j] = FCFSProcess[i];
                    FCFSProcess[i] = swap;
                }
            }
            if(FCFSProcess[j].Arrival_Time > FCFSProcess[i].Arrival_Time) {
                swap = FCFSProcess[j];
                FCFSProcess[j] = FCFSProcess[i];
                FCFSProcess[i] = swap;
            }
        }
    }
    
    printf("ARRIVAL SORT\n");
    printf("PROCESS ID\tARRIVAL TIME\tBURST DURATION\tPRIORITY\n");
    for(int i = 0; i < 100; i++) {
        printf("%d\t\t%d\t\t%d\t\t%d\n", FCFSProcess[i].Process_ID, FCFSProcess[i].Arrival_Time, FCFSProcess[i].Burst_Duration, FCFSProcess[i].Priority);
    }
}

void FCFS(struct Process Processes[], int number)
{
    //---------Start Processing
    struct Process FCFSProcess[number];
    struct Gantt FCFSGantt[number];
    
    for(int i = 0; i < number; i++) {
        FCFSProcess[i].Process_ID = Processes[i].Process_ID;
        FCFSProcess[i].Arrival_Time = Processes[i].Arrival_Time;
        FCFSProcess[i].Burst_Duration = Processes[i].Burst_Duration;
        FCFSProcess[i].Priority = Processes[i].Priority;
        FCFSProcess[i].TurnAroundTime = Processes[i].TurnAroundTime;
        FCFSProcess[i].WaitTime = Processes[i].WaitTime;
    }
    
    arrivalsort(FCFSProcess, number);
    
    int time = 0;
    for(int i = 0; i < number; i++) {
        FCFSGantt[i].Arrival_Time = time;
        FCFSProcess[i].WaitTime = time - FCFSProcess[i].Arrival_Time;
        FCFSProcess[i].TurnAroundTime = FCFSProcess[i].WaitTime + FCFSProcess[i].Burst_Duration;
        time += FCFSProcess[i].Burst_Duration;
        FCFSGantt[i].End_Time = time;
        FCFSGantt[i].Process_ID = FCFSProcess[i].Process_ID;
    }
    
    Display_Output(FCFSProcess, number, FCFSGantt, number, "HW4_FCFS.txt");
    
    //---------End of Processing
    //Hint: Call function with arguments shown Display_Output(Processes,number,Rows,count,"FCFS");
}

//---------------------SJFP---------------------------------------
void SJFP(struct Process Processes[], int number)
{
    //---------Start Processing
    struct Process SJFProcess[number];
    struct Gantt SJFPGantt[1000];
    int totalseconds = 0;
    int index = 0;
    bool exists = false;
    

    for(int i = 0; i < number; i++) {
        SJFProcess[i].Process_ID = Processes[i].Process_ID;
        SJFProcess[i].Arrival_Time = Processes[i].Arrival_Time;
        SJFProcess[i].Burst_Duration = Processes[i].Burst_Duration;
        SJFProcess[i].Priority = Processes[i].Priority;
        SJFProcess[i].TurnAroundTime = Processes[i].TurnAroundTime;
        SJFProcess[i].WaitTime = Processes[i].WaitTime;
        totalseconds += SJFProcess[i].Burst_Duration;
    }
    
    arrivalsort(SJFProcess, number);

    struct Process checkProcess;
    struct Process pushArray[number];
    int pushIndex = 0;
    int pushSize = 0, ganttSize = 0;
    int elapsed = 0;
    
    // each second out of total amount of seconds
    for(elapsed = 0; elapsed < totalseconds; elapsed++) {

        for(int i = 0; i < number; i++) {
            checkProcess = SJFProcess[i];
            exists = checkexist(checkProcess, pushArray, pushSize);
            // check if SJFProcess[i] is already in pushArray
            if((checkProcess.Arrival_Time <= elapsed) && !exists) {
                pushArray[pushIndex] = checkProcess;
                //printf("added [%d] burst [%d] at index [%d]\n", pushArray[index].Process_ID, pushArray[index].Burst_Duration, index);
                pushSize++;
                pushIndex++;
            }
            else if(exists) {
                continue;
            }
        } // END filling pushArray at current time

        // GET smallest burst
        struct Process smallestBurst;
        smallestBurst.Burst_Duration = 9999;
        for(int j = 0; j < pushSize; j++) {
            if((pushArray[j].Burst_Duration < smallestBurst.Burst_Duration)) {
                if(pushArray[j].Burst_Duration == 0) {
                    continue;
                }
                else {
                    smallestBurst = pushArray[j];
                    index = j;
                }
            }
        }
    
        // add smallest to gantt if it's not already on there
        struct Process checkGantt = pushArray[index];
        exists = checkexistgantt(checkGantt, SJFPGantt, ganttSize);
        
        if(!exists) {
            SJFPGantt[ganttSize-1].End_Time = elapsed;
            SJFPGantt[ganttSize].Process_ID = pushArray[index].Process_ID;
            //printf("exists = false: added %d to gantt\n", pushArray[index].Process_ID);
            SJFPGantt[ganttSize].Arrival_Time = elapsed;
            ganttSize++;
        } else if(exists && (pushArray[index].Process_ID != SJFPGantt[ganttSize-1].Process_ID)) {
            //printf("%d %d\n", pushArray[index].Process_ID, SJFPGantt[ganttSize-1].Process_ID);
            SJFPGantt[ganttSize-1].End_Time = elapsed;
            SJFPGantt[ganttSize].Process_ID = pushArray[index].Process_ID;
            //printf("exists = true: added %d to gantt\n", pushArray[index].Process_ID);
            SJFPGantt[ganttSize].Arrival_Time = elapsed;
            ganttSize++;
        }

        pushArray[index].Burst_Duration--;
        
        struct Process procCheck;
        struct Gantt ganttCheck;

        if(pushArray[index].Burst_Duration == 0) {
            for(int i = 0; i < number; i++) {
                if(pushArray[index].Process_ID == SJFProcess[i].Process_ID) {
                    procCheck = SJFProcess[i];
                }
            }
            //printf("procCheck:\nPID:\tArrival:\tBurst:\n");
            //printf("%d\t%d\t%d\t\n", procCheck.Process_ID, procCheck.Arrival_Time, procCheck.Burst_Duration);
            for(int i = ganttSize; i >= 0; i--) {
                if(pushArray[index].Process_ID == SJFPGantt[i].Process_ID) {
                    ganttCheck = SJFPGantt[i];
                }
            }
            //printf("ganttCheck:\nPID:\tArrival:\tEnd:\n");
            //printf("%d\t%d\t%d\t\n", ganttCheck.Process_ID, ganttCheck.Arrival_Time, ganttCheck.End_Time);

            pushArray[index].WaitTime = elapsed+1 - procCheck.Arrival_Time - procCheck.Burst_Duration;
            pushArray[index].TurnAroundTime = pushArray[index].WaitTime + procCheck.Burst_Duration;

            SJFPGantt[ganttSize].End_Time = elapsed+1;
        }
    } // TIME loop

    SJFPGantt[ganttSize-1].End_Time = elapsed;
    
    
    //printf("ganttSize: %d\n", ganttSize);
    
    //for(int i = 0; i < ganttSize; i++) {
    //    printf("%d\t\t%d\t\t%d\n", SJFPGantt[i].Process_ID, SJFPGantt[i].Arrival_Time, SJFPGantt[i].End_Time);
    //}
    //for(int i = 0; i < pushSize; i++) {
    //    printf("%d\t\t%d\t\t%d\n", pushArray[i].Process_ID, pushArray[i].Arrival_Time, pushArray[i].Burst_Duration);
    //}
    
    
    Display_Output(pushArray, number, SJFPGantt, ganttSize, "HW4_SJF.txt");
    //---------End of Processing
        
}

//------------------PRIORITY-------------------------------------
void PRIORITY(struct Process Processes[], int number)
{
    //---------Start Processing
    //---------Start Processing
    struct Process PRIOProcess[number];
    struct Gantt PRIOGantt[1000];
    int totalseconds = 0;
    int index = 0;
    bool exists = false;
    
    for(int i = 0; i < number; i++) {
        PRIOProcess[i].Process_ID = Processes[i].Process_ID;
        PRIOProcess[i].Arrival_Time = Processes[i].Arrival_Time;
        PRIOProcess[i].Burst_Duration = Processes[i].Burst_Duration;
        PRIOProcess[i].Priority = Processes[i].Priority;
        PRIOProcess[i].TurnAroundTime = Processes[i].TurnAroundTime;
        PRIOProcess[i].WaitTime = Processes[i].WaitTime;
        totalseconds += PRIOProcess[i].Burst_Duration;
    }

    arrivalsort(PRIOProcess, number);

    struct Process checkProcess;
    struct Process pushArray[number];
    int pushIndex = 0;
    int pushSize = 0, ganttSize = 0;
    int elapsed = 0;
    
    // each second out of total amount of seconds
    for(elapsed = 0; elapsed < totalseconds; elapsed++) {

        for(int i = 0; i < number; i++) {
            checkProcess = PRIOProcess[i];
            exists = checkexist(checkProcess, pushArray, pushSize);
            // check if PRIOProcess[i] is already in pushArray
            if((checkProcess.Arrival_Time <= elapsed) && !exists) {
                pushArray[pushIndex] = checkProcess;
                //printf("added [%d] burst [%d] at index [%d]\n", pushArray[index].Process_ID, pushArray[index].Burst_Duration, index);
                pushSize++;
                pushIndex++;
            }
            else if(exists) {
                continue;
            }
        } // END filling pushArray at current time

        // GET highest priority
        struct Process highestPriority;
        highestPriority.Priority = 51;

        for(int j = 0; j < pushSize; j++) {
            if((pushArray[j].Priority < highestPriority.Priority) && (pushArray[j].Priority > 0)) {
                if(pushArray[j].Burst_Duration == 0) {
                    continue;
                }
                else {
                    highestPriority = pushArray[j];
                    index = j;
                }
            }
        }
    
        // add smallest to gantt if it's not already on there
        struct Process checkGantt = pushArray[index];
        exists = checkexistgantt(checkGantt, PRIOGantt, ganttSize);
        
        if(!exists) {
            PRIOGantt[ganttSize-1].End_Time = elapsed;
            PRIOGantt[ganttSize].Process_ID = pushArray[index].Process_ID;
            //printf("exists = false: added %d to gantt\n", pushArray[index].Process_ID);
            PRIOGantt[ganttSize].Arrival_Time = elapsed;
            ganttSize++;
        } else if(exists && (pushArray[index].Process_ID != PRIOGantt[ganttSize-1].Process_ID)) {
            //printf("%d %d\n", pushArray[index].Process_ID, PRIOGantt[ganttSize-1].Process_ID);
            PRIOGantt[ganttSize-1].End_Time = elapsed;
            PRIOGantt[ganttSize].Process_ID = pushArray[index].Process_ID;
            //printf("exists = true: added %d to gantt\n", pushArray[index].Process_ID);
            PRIOGantt[ganttSize].Arrival_Time = elapsed;
            ganttSize++;
        }

        pushArray[index].Burst_Duration--;
        
        struct Process procCheck;
        struct Gantt ganttCheck;

        if(pushArray[index].Burst_Duration == 0) {
            for(int i = 0; i < number; i++) {
                if(pushArray[index].Process_ID == PRIOProcess[i].Process_ID) {
                    procCheck = PRIOProcess[i];
                }
            }
            //printf("procCheck:\nPID:\tArrival:\tBurst:\n");
            //printf("%d\t%d\t%d\t\n", procCheck.Process_ID, procCheck.Arrival_Time, procCheck.Burst_Duration);
            for(int i = ganttSize; i >= 0; i--) {
                if(pushArray[index].Process_ID == PRIOGantt[i].Process_ID) {
                    ganttCheck = PRIOGantt[i];
                }
            }
            //printf("ganttCheck:\nPID:\tArrival:\tEnd:\n");
            //printf("%d\t%d\t%d\t\n", ganttCheck.Process_ID, ganttCheck.Arrival_Time, ganttCheck.End_Time);
            pushArray[index].WaitTime = elapsed+1 - procCheck.Arrival_Time - procCheck.Burst_Duration;
            pushArray[index].TurnAroundTime = pushArray[index].WaitTime + procCheck.Burst_Duration;

            PRIOGantt[ganttSize].End_Time = elapsed+1;
        }
    } // TIME loop

    PRIOGantt[ganttSize-1].End_Time = elapsed;

    Display_Output(pushArray, number, PRIOGantt, ganttSize, "HW4_PRIO.txt");
    //---------End of Processing
}


