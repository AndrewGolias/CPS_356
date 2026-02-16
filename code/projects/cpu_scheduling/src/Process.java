public class Process {
    int id;             // unique process identifier
    int arrTime;        // time arrived
    int burstTime;      // time needed for CPU service
    int remainingTime;  // needed for preemptive mode

    public Process(int id, int arrTime, int burstTime) {
        this.id = id;
        this.arrTime = arrTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }

    /*** helper getter method -> returns process arrival time */
    public int getArrivalTime() { return arrTime; }
    
    /*** helper getter method -> returns process burst time */
    public int getBurstTime() { return burstTime; }

    /**
     * runs the process and gives the global current time
     *
     * @param currTime - the global time of the CPU when executing process
     */
    public void run(int currTime) {
        int timer=0;
        while(timer < burstTime) {
            System.out.printf("Time: %d   Process P%d is running...\n", currTime, this.id);
            timer++;
            currTime++;
        }
    }

    @Override
    public String toString() {
        return "P" + this.id;
    }
}
