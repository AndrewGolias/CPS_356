public class Process {
    int id;             // unique process identifier
    int arrTime;        // time arrived
    int burstTime;      // time needed for CPU service
    int remainingTime;  // needed for preemptive mode
    private boolean jobCompleted;

    public Process(int id, int arrTime, int burstTime) {
        this.id = id;
        this.arrTime = arrTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.jobCompleted = false;
    }

    /*** helper getter method -> returns process arrival time */
    public int getArrivalTime() { return arrTime; }
    
    /*** helper getter method -> returns process burst time */
    public int getBurstTime() { return burstTime; }

    public boolean isFinished() { return jobCompleted; }

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
        jobCompleted = true;
    }

    @Override
    public String toString() {
        return "P" + this.id;
    }
}
