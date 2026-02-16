
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CPU {
    int time=0;

    public void fcfs_scheduling(List<Process> processes) {
        // sort by arrival time
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));
        System.out.println("\tProcesses sorted by arrival time (FCFS): " + processes);

        for(int i = 0; i < processes.size(); i++) {
            while(time < processes.get(i).getArrivalTime()) {
                System.out.printf("Time: %d   ... CPU is idling\n", time);
                time++;
            }
            processes.get(i).run(time);
            time += processes.get(i).getBurstTime();
        }
    }

    public static void main(String[] args) {
        System.out.print("CPU Scheduler");
        List<Process> processes = new ArrayList<>();
        
        processes.add(new Process(1, 2, 4));
        processes.add(new Process(2, 3, 2));
        processes.add(new Process(3, 3, 1));
        processes.add(new Process(4, 8, 3));
        processes.add(new Process(5, 15, 5));
        processes.add(new Process(6, 16, 2));
        processes.add(new Process(7, 10, 1));

        // create CPU to execute process
        CPU cpu = new CPU();
        cpu.fcfs_scheduling(processes);
        System.out.println();
    }
}
