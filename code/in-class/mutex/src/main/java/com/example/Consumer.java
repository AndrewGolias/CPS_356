package com.example;

public class Consumer implements Runnable {

    char id;

    public Consumer(char id) {
        this.id = id;
    }

    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            try {
                SharedBuffer.full.waitSem();
                SharedBuffer.mutex.waitSem();

                // remove frame into buffer
                int item = SharedBuffer.buffer[SharedBuffer.out];

                // display console output
                System.out.printf("Consumer %c removed %d at location [%d]\n", this.id, item, SharedBuffer.out);

                // update the out location
                SharedBuffer.out = (SharedBuffer.out + 1) % SharedBuffer.SIZE;

                SharedBuffer.mutex.signalSem();
                SharedBuffer.empty.signalSem();
            } catch(InterruptedException e) {
                System.err.println("\tError: " + e.getMessage());
            }
        }

    }



}
