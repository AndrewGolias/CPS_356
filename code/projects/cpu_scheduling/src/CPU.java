
import java.util.ArrayList;
import java.util.List;

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

    public void sjf_scheduling(List<Process> processes, int mode) {
        // sort by burst time
        int completedProcesses = 0;
        int size = processes.size();

        switch(mode) {
            case 0:         // non-preemtive
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
                break;

            case 1:     // preemtive
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
                break;
        }
    }

    public static void main(String[] args) {
        System.out.println("CPU Scheduler");
        List<Process> processes = new ArrayList<>();
        
        // processes.add(new Process(1, 2, 4));
        // processes.add(new Process(2, 3, 2));
        // processes.add(new Process(3, 3, 1));
        // processes.add(new Process(4, 8, 3));
        // processes.add(new Process(5, 15, 5));
        // processes.add(new Process(6, 16, 2));
        // processes.add(new Process(7, 10, 1));


        // processes.add(new Process(5, 15, 5));
		// processes.add(new Process(6, 16, 2));
		// processes.add(new Process(4, 8, 3));
		// processes.add(new Process(7, 3, 1));
		// processes.add(new Process(2, 3, 2)); //P2
		// processes.add(new Process(1, 2, 4)); //P1
		// processes.add(new Process(3, 3, 1)); //P3
        
        
        processes.add(new Process(1,0,7));
        processes.add(new Process(2,2,4));
        processes.add(new Process(3,4,1));
        processes.add(new Process(4,5,4));


        // create CPU to execute process
        CPU cpu = new CPU();
        // cpu.fcfs_scheduling(processes);
        cpu.sjf_scheduling(processes, 1);   // non-preemptive
        //cpu.fcfs_scheduling(processes);
        System.out.println();
    }
}
