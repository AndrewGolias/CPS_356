package com.example;

public class Writer implements Runnable {

    char id;

    public Writer(char id) {
        this.id = id;
    }

    @Override
    public void run() {
        for(int i = 0; i < 5; i++) {
            try {
                SharedData.rw_mutex.waitSem();

                SharedData.data++;
                System.out.printf("Writer [%c] is writing value = %d\n", this.id, SharedData.data);

                SharedData.rw_mutex.signalSem();
                Thread.sleep(25);  // gives next thread a chance to enter
            } catch(InterruptedException e) {
                System.err.println("\tError " + e.getMessage());
            }
        }
    }
}
