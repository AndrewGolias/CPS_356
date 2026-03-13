package com.example;

class Process implements Runnable {
    
    int id;             // process id number
    volatile int turn;           // turn indicator
    volatile boolean[] flag;     // turn flag

    public Process(int id) {
        this.id = id;
        this.flag = new boolean[10];
        this.turn = 1 - this.id;
    }

    public void enterCS() {
        this.flag[id] = true;
        this.turn = 1 - id;
        while(this.flag[1 - this.id] && this.turn == 1 - this.id) {}
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
