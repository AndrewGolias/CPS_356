public class Process {
    int id;             // unique process identifier
    int arrTime;        // time arrived
    int burstTime;      // time needed for CPU service
    int remainingTime;  // needed for preemptive mode
    int waitingTime;
    private boolean finished;

    public Process(int id, int arrTime, int burstTime) {
        this.id = id;
        this.arrTime = arrTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.finished = false;
    }

    /*** helper getter method -> returns process arrival time */
    public int getArrivalTime() { return arrTime; }
    
    /*** helper getter method -> returns process burst time */
    public int getBurstTime() { return burstTime; }

    /*** helper getter method -> returns process remaining time */
    public int getRemainingTime() { return remainingTime; }

    public boolean isFinished() { return finished; }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    /**
     * runs the process and gives the global current time
     *
     * @param currTime - the global time of the CPU when executing process
     */
    public void run(int currTime) {
        System.out.printf("Time: %d   Process P%d is running...\n", currTime, this.id);
        remainingTime--;
        if(remainingTime == 0)
            finished = true;
    }

    @Override
    public String toString() {
        return "P" + this.id;
    }
}
