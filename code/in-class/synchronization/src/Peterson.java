
class Process implements Runnable {
    
    int id;             // process id number
    int turn;           // turn indicator
    boolean[] flag;     // turn flag

    public Process(int id) {
        this.id = id;
        this.flag = new boolean[10];
        this.turn = id+1;
    }

    public void enterCS() {
        this.flag[id] = true;
        this.turn = id+1;
        while(this.flag[id+1] && this.turn == this.id+1) {}
    }

    public void exitCS() {
        this.flag[id] = false;
    }

    @Override
    public void run() {
        this.enterCS();
        // loop is critical section
        for (int i = 0; i < 100; i++) {
            System.out.printf("Process %d: %d\n", this.id, i);
        }
        this.exitCS();
    }
}

public class Peterson {
    public static void main(String[] args) throws InterruptedException {
        Thread p0 = new Thread(new Process(0));
        Thread p1 = new Thread(new Process(1));
        p0.start();
        p1.start();
        p0.join();
        p1.join();
        System.out.println("End");
    }
}
