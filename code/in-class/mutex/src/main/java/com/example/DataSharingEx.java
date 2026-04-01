package com.example;
public class DataSharingEx {
    public static void main(String[] args) {
        Thread w1 = new Thread(new Writer('a'));
        Thread w2 = new Thread(new Writer('b'));
        Thread r1 = new Thread(new Reader('a'));
        Thread r2 = new Thread(new Reader('b'));


        w1.start();
        r1.start();

        // w2.start();
        // r2.start();
    }
}
