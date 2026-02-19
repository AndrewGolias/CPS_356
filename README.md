# CPS_356

## Java Threads Project: /code/projects/threads
### Performance result comparison between matrix multiplication run in concurrency vs. parallelism
Matrix class methods
+ multiplyBy(m): called with a Matrix to perform concurrent matrix multiplication
+ multiplyByThreads(m): called with a Matrix to perform concurrent matrix multiplication using a Java Threads[] for the columns of m

## CPU Scheduler Project: /code/projects/cpu_scheduler
### Runs processes based on specified algorithm to show CPU distribution of resources
Algorithms:
+ First-Come-First-Serve - fcfs_scheduling(List P): orders processes by arrival time; runs process list and displays CPU idle time
+ Shortest Job First - sjf_scehduling(List P, mode): runs process list and display CPU idle time
    + Non-preemptive mode=1: orders by shortest burst, reordering for processes in waiting queue after current process is finished running