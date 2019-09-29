package com.thalmic.myo.Threads;

import static java.lang.Thread.sleep;

// This thread test class creates a thread which continously prints out "Thread loop!".
public class ThreadTest implements Runnable {

    public void run()
    {
        while (true)
        {
            System.out.println( "Thread loop!" );
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
