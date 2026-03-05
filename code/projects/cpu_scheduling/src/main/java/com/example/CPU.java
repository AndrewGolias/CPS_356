package com.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CPU {
    int time=0;

    public void fcfs_scheduling(List<Process> processes) {
        // sort by arrival time
        // processes.sort(Comparator.comparingInt(Process::getArrivalTime));
        processes.sort((p1, p2) -> p1.getArrivalTime() - p2.getArrivalTime());
        
        System.out.println("\tProcesses sorted by arrival time (FCFS): " + processes);
        
        for(Process p : processes) {
            while(this.time < p.getArrivalTime()) {
                System.out.printf("Time: %d   ... CPU is idling\n", this.time);
                this.time++;
            }
            p.run(this.time);
            this.time += p.getBurstTime();
        }
        System.out.printf("Time: %d   ... CPU is idling\n", this.time);
    }

    /**
     * SJF:
     * Non-preemptive mode=0: orders by shortest burst, reordering for processes in
     *  waiting queue after current process is finished running
     * Preemptive mode=1: orders by shortest burst, allows for newly arrived processes
     *  to jump in front of current process as context switching
     * @param processes - list of CPU processes
     * @param mode - preemption mode
     */
    public void sjf_scheduling(List<Process> processes, int mode) {
        // sort by burst time
        int completedProcesses = 0;
        int size = processes.size();

        switch(mode) {
            case 0 -> {
                // non-preemtive
                List<Process> executionOrder = new ArrayList<>();
                while(completedProcesses < size) {
                    Process currShortestProcess = null;
                    
                    // find shortest job currently in waiting queue
                    for(Process p : processes) {
                        if(p.getArrivalTime() <= this.time && !p.isFinished() &&
                                (currShortestProcess == null || p.getBurstTime() < currShortestProcess.getBurstTime())) {
                            currShortestProcess = p;
                        }
                    }

                    // run process OR CPU idles if no process is available
                    if(currShortestProcess != null) {
                        currShortestProcess.run(this.time);
                        this.time += currShortestProcess.getBurstTime();
                        executionOrder.add(currShortestProcess);
                        processes.remove(currShortestProcess);
                        completedProcesses++;
                    } else {
                        System.out.printf("Time: %d   ... CPU is idling\n", this.time);
                        this.time++;
                    }
                }
                processes = executionOrder;
                System.out.println("\tProcesses sorted by burst time (SJF): " + processes);
            }

            case 1 -> {
                // preemtive
                while(completedProcesses < size) {
                    Process currentProcess = null;
                    int minRemainingTime = Integer.MAX_VALUE;

                    // find process with shortest remaining time that has already arrived
                    for(Process p : processes) {
                        if(p.getArrivalTime() <= this.time && !p.isFinished()
                                && p.getRemainingTime() < minRemainingTime) {
                            minRemainingTime = p.getRemainingTime();
                            currentProcess = p;
                        }
                    }

                    // run a single time unit
                    if(currentProcess != null) {
                        currentProcess.run(this.time);
                        if(currentProcess.isFinished()) {
                            completedProcesses++;
                            int turnaroundTime = this.time - currentProcess.getArrivalTime();
                            currentProcess.setWaitingTime(turnaroundTime - currentProcess.getBurstTime());
                        }
                    } else {
                        System.out.printf("Time: %d   ... CPU is idling\n", this.time);
                    }
                    this.time++;
                }
            }
        }
    }

    /**
     * Uses MLFQ scheduling to track processes through 3 queue levels. The top queue receives
     *  8 quantum as its time unit to execute a process. Each process running is demoted to
     *  the next level which gives it 16 quantum. The final queue is ordered FCFS
     *
     *      q0 - RR
     *      q1 - RR
     *      q2 - FCFS
     *
     * @param processes - list of CPU processes
     */
    public void multiFeedbackScheduling(List<Process> processes) {
        final int quantumQ0 = 8;
        final int quantumQ1 = 16;
        final int quantumQ2 = Integer.MAX_VALUE/100;
        int size = processes.size();
        int completedJobs = 0;
        Process currProcess = null;
        int currQueue = -1;
        int currQuantum = 0;
        Queue<Process> q0 = new LinkedList<>();
        Queue<Process> q1 = new LinkedList<>();
        Queue<Process> q2 = new LinkedList<>();

        while(completedJobs < size) {
            for(Process p : processes) {
                if(p.hasArrived(this.time) && !p.isFinished() && !q0.contains(p) &&
                        !q1.contains(p) && !q2.contains(p) && p != currProcess)
                {
                    q0.add(p);
                    // Preempt if currently running lower priority
                    if(currProcess != null && currQueue > 0) {
                        if(currQueue == 1)
                            q1.add(currProcess);
                        else if (currQueue == 2)
                            q2.add(currProcess);

                        currProcess = null;
                    }
                }
            }
            
            // find next available process to execute in highest queue
            if(currProcess == null) {
                if(!q0.isEmpty()) {
                    currProcess = q0.poll();
                    currQueue = 0;
                    currQuantum = 0;
                }
                else if(!q1.isEmpty()) {
                    currProcess = q1.poll();
                    currQueue = 1;
                    currQuantum = 0;
                }
                else if(!q2.isEmpty()) {
                    currProcess = q2.poll();
                    currQueue = 2;
                    currQuantum = 0;
                }
            }

            // no available process; idle CPU
            if(currProcess == null) {
                System.out.printf("Time: %d   ... CPU is idling\n", this.time);
                this.time++;
                continue;
            }

            // run for a single time unit
            currProcess.run(this.time);
            this.time++;
            currQuantum++;

            // curr process finishes
            if(currProcess.isFinished()) {
                completedJobs++;
                currProcess = null;
                continue;
            }

            // check if quantum has been consumed & demote
            if(currQueue == 0 && currQuantum == quantumQ0) {
                q1.add(currProcess);   // demote
                currProcess = null;
            }
            else if(currQueue == 1 && currQuantum == quantumQ1) {
                q2.add(currProcess);   // demote
                currProcess = null;
            } else if(currQueue == 2 && currQuantum == quantumQ2) {
                System.err.print("Timeout...");
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("CPU Scheduler");
        List<Process> processes = new ArrayList<>();
        
        // processes.add(new Process(1,0,7));
        // processes.add(new Process(2,2,4));
        // processes.add(new Process(3,4,1));
        // processes.add(new Process(4,5,4));

        processes.add(new Process(0, 0, 7));
        processes.add(new Process(1,2,15));
		processes.add(new Process(2,4,25));
		processes.add(new Process(3, 6, 10));
		processes.add(new Process(4, 8, 5));
		processes.add(new Process(5, 15, 10));
		processes.add(new Process(6,20, 2));
		processes.add(new Process(7, 30, 3));
		processes.add(new Process(8, 79 , 10));
		processes.add(new Process(9,90, 2));


        // create CPU to execute process
        CPU cpu = new CPU();
        // cpu.fcfs_scheduling(processes);
        // cpu.sjf_scheduling(processes, 1);
        cpu.multiFeedbackScheduling(processes);
        System.out.println();
    }
}
