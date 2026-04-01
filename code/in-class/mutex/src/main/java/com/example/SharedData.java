package com.example;

public class SharedData {
    // shared data between Readers & Writers
    static int data = 0;
    static int read_count = 0;
    
    // Mutex locks
    static Semaphore mutex = new Semaphore(1);      // control read_count
    static Semaphore rw_mutex = new Semaphore(1);   // control writers

    public SharedData() {
    }

    


}
