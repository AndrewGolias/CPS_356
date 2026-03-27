package com.example;

public class Producer implements Runnable {

    char id;

    public Producer(char id) {
        this.id = id;
    }

    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            // item being produced
            int item = i;
            try {
                SharedBuffer.empty.waitSem();
                SharedBuffer.mutex.waitSem();

                // add frame into buffer
                SharedBuffer.buffer[SharedBuffer.in] = item;

                // display console output
                System.out.printf("Producer %c added %d at location %d\n", this.id, item, SharedBuffer.in);

                // update the in location
                SharedBuffer.in = (SharedBuffer.in + 1) % SharedBuffer.SIZE;

                SharedBuffer.mutex.signalSem();
                SharedBuffer.full.signalSem();
            } catch(InterruptedException e) {
                System.err.println("\tError: " + e.getMessage());
            }

        }
    }

}
