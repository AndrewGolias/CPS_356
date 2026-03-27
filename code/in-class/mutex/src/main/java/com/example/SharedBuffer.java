package com.example;

public class SharedBuffer {

    static final int SIZE = 5;

    static int[] buffer = new int[SIZE];

    // two indeces for producer and consumer to use
    static int in = 0;
    static int out = 0;

    // 3 locks
    static Semaphore mutex = new Semaphore(1);
    static Semaphore empty = new Semaphore(SIZE);
    static Semaphore full = new Semaphore(0);



}
