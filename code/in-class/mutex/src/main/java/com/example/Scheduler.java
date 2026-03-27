package com.example;

public class Scheduler {
    public static void main(String[] args) {
        // Producer threads
        Thread Pa = new Thread(new Producer('a'));
        Thread Pb = new Thread(new Producer('b'));

        // Consumer threads
        Thread Ca = new Thread(new Consumer('a'));
        Thread Cb = new Thread(new Consumer('b'));

        // Run Prod/Cons threads
        Pa.start();
        Pb.start();
        Ca.start();
        Cb.start();

        
    }
}
