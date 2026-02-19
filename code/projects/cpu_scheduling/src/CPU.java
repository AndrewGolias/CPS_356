
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
            while(time < p.getArrivalTime()) {
                System.out.printf("Time: %d   ... CPU is idling\n", time);
                time++;
            }
            p.run(time);
            time += p.getBurstTime();
        }
    }

    public void sjf_scheduling(List<Process> processes, int mode) {
        // sort by burst time
        List<Process> executionOrder = new ArrayList<>();
        int completedProcesses = 0;

        switch(mode) {
            case 0:         // non-preemtive
                while(completedProcesses < processes.size()) {
                    Process currShortestProcess = null;
                    
                    // find shortest job currently in waiting queue
                    for(Process p : processes) {
                        if(p.getArrivalTime() <= this.time && !p.isFinished() &&
                                (currShortestProcess == null || p.getBurstTime() < currShortestProcess.getBurstTime())) {
                            currShortestProcess = p;
                        }
                    }

                    if(currShortestProcess != null) {
                        currShortestProcess.run(time);
                        time += currShortestProcess.getBurstTime();
                        executionOrder.add(currShortestProcess);
                        completedProcesses++;
                    } else {
                        System.out.printf("Time: %d   ... CPU is idling\n", time);
                        time++;
                    }
                }
                processes = executionOrder;
                break;
            case 1:     // preemtive

                break;
        }
        System.out.println("\tProcesses sorted by burst time (SJF): " + processes);
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

        processes.add(new Process(1,0,7));
        processes.add(new Process(2,2,4));
        processes.add(new Process(3,4,1));
        processes.add(new Process(4,5,4));


        // create CPU to execute process
        CPU cpu = new CPU();
        // cpu.fcfs_scheduling(processes);
        cpu.sjf_scheduling(processes, 0);   // non-preemptive
        //cpu.fcfs_scheduling(processes);
        System.out.println();
    }
}
