package com.example;

public class Semaphore {

    int value; // total number of processes allowed to enter CS

    public Semaphore(int value) {
        this.value = value;
    }

    // acquire lock
    public synchronized void waitSem() throws InterruptedException {
        while(value == 0) {
            wait();
        }
        value--;
    }

    // release lock
    public synchronized void signalSem() {
        value++;
        notify();
    }

}
