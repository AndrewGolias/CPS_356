package com.example;

public class Reader implements Runnable {
    char id;

    public Reader(char id) {
        this.id = id;
    }

    @Override
    public void run() {
        for(int i = 0; i < 5; i++) {
            try {
                SharedData.mutex.waitSem();
                SharedData.read_count++;

                if(SharedData.read_count == 1) {
                    SharedData.rw_mutex.waitSem();
                }

                SharedData.mutex.signalSem();

                // display console output
                System.out.printf("Reader [%c] is reading value = %d\n", this.id, SharedData.data);

                SharedData.mutex.waitSem();
                SharedData.read_count--;
                if(SharedData.read_count == 0) {
                    SharedData.rw_mutex.signalSem();
                }
                SharedData.mutex.signalSem();
                Thread.sleep(25);

            } catch(InterruptedException e) {
                System.err.println("\tError: " + e.getMessage());
            }
        }

    }

}
