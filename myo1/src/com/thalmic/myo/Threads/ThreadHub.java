package com.thalmic.myo.Threads;

import com.thalmic.myo.Hub;
import com.thalmic.myo.enums.LockingPolicy;

// This thread class is used to execute a Hub-listening loop, to run in the background of the program.
public class ThreadHub implements Runnable{
    Hub hub;
    boolean shutdown = false;

    public ThreadHub (Hub hubRef)
    {
        hub = hubRef;
    }

    public void run()
    {
        hub.setLockingPolicy(LockingPolicy.LOCKING_POLICY_NONE); // Set the gesture-detection to be unlocked, so you can recognize 'em all. Note it uses a file from the 'enum' section.
        while (true)
        {
            if (get_shutdown() == true)
            {
                System.out.println("Thread ended!");
                break;
            }
            hub.run(1000 / 20);
        }
    }

    public synchronized void set_shutdown(boolean x)
    {
        shutdown = x;
    }
    public synchronized boolean get_shutdown()
    {
        return shutdown;
    }
}
