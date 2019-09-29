package com.thalmic.myo.Threads;
import com.thalmic.myo.Controller;
import com.thalmic.myo.enums.PoseType;
import com.thalmic.myo.myProg.DataCollector;

import static java.lang.Thread.sleep;


// This thread class is used for the main scene in the program - Continuously updates the roll, pitch, and yaw fields that are contained in this thread.
// This class needs the following parameter: the "myoAccess" DataCollector.
public class ThreadMyoData implements Runnable {
    private DataCollector myoAccess;
    private double pitch;
    private double yaw;
    private double roll;
    private String pose;
    private boolean shutdown;

    public synchronized void set_shutdown(boolean x)
    {
        shutdown = x;
    }
    public synchronized boolean get_shutdown()
    {
        return shutdown;
    }

    public ThreadMyoData( DataCollector myoRef)
    {
        myoAccess = myoRef;
    }

    public void run()
    {
        while (true)
        {
            if (get_shutdown() == true)
            {
                System.out.println("Thread ended!");
                break;
            }
            setPitch(myoAccess.getPitch());
            setYaw(myoAccess.getYaw());
            setRoll(myoAccess.getRoll());
            setPose(myoAccess.getPose().toString());
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    // Synchronized "set" and "get" methods, used for access to the Myo data held in this object.
    private synchronized void setPitch(double data)
    {
        pitch = data;
        return;
    }

    private synchronized void setYaw(double data)
    {
        yaw = data;
        return;
    }
    private synchronized void setRoll(double data)
    {
        roll = data;
        return;
    }
    private synchronized void setPose(String data)
    {
        pose = data;
        return;
    }

    public synchronized double getPitch()
    {
        return pitch;
    }
    public synchronized double getYaw()
    {
        return yaw;
    }
    public synchronized double getRoll()
    {
        return roll;
    }
    public synchronized String getPose()
    {
        return pose;
    }

}
