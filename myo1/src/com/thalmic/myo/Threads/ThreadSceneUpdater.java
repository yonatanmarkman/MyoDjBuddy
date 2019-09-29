package com.thalmic.myo.Threads;

import com.thalmic.myo.Controller;
import com.thalmic.myo.enums.PoseType;
import com.thalmic.myo.myProg.DataCollector;
import static java.lang.Thread.sleep;
import javafx.application.Platform;

// This thread object is used to update the main scene with data from the "ThreadMyoData" object.
// It also translates the values into the arm "position" values, which are the main factor deciding which beat will play.
// This class needs the following 2 parameters to be instantiated: A reference to the main GUI ('controllerScene'), and a reference to the thread 'ThreadMyoData' that was started before.
public class ThreadSceneUpdater implements Runnable {

    private Controller controllerScene;
    private ThreadMyoData myoThread;
    private boolean shutdown;

    public synchronized void set_shutdown(boolean x)
    {
        shutdown = x;
    }
    public synchronized boolean get_shutdown()
    {
        return shutdown;
    }


    public ThreadSceneUpdater (Controller ctrlr, ThreadMyoData myo)
    {
        controllerScene = ctrlr;
        myoThread = myo;
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

            // I used the 'Platform.runLater' wrapper function to manipulate GUI fields from outside the GUI thread.
            Platform.runLater(
                    () ->
                    {   // Straight-up data display...
                        double pitch;
                        double yaw;
                        double roll;
                        pitch = myoThread.getPitch();
                        yaw = myoThread.getYaw();
                        roll = myoThread.getRoll();
                        controllerScene.getPitchView().setText(String.format("%.2f",pitch));
                        controllerScene.getRollView().setText(String.format("%.2f",roll));
                        controllerScene.getYawView().setText(String.format("%.2f",yaw));
                        controllerScene.getPoseView().setText(myoThread.getPose());
                        // Translating the data into "positioning"
                        if (( yaw >= 4 && yaw <= 8 ) && (pitch >=7 && pitch <=9) )
                            controllerScene.getPositView().setText("POINTS RIGHT, HORIZONTAL");
                        else if (( yaw >= 4 && yaw <= 8 ) && (pitch >=9 && pitch <=13) )
                            controllerScene.getPositView().setText("POINTS RIGHT, LEANING");
                        else if ( (pitch >=13 && pitch <=18) && ( roll >= 11 && roll <= 13))
                            controllerScene.getPositView().setText("POINTS RIGHT, VERTICAL");
                        else if (( yaw >= 8 && yaw <= 12 ) && (pitch >=7 && pitch <=9) )
                            controllerScene.getPositView().setText("POINTS FOWARD, HORIZONTAL");
                        else if (( yaw >= 8 && yaw <= 12 ) && (pitch >=9 && pitch <=13) )
                            controllerScene.getPositView().setText("POINTS FOWARD, LEANING");
                        else if ( (pitch >=13 && pitch <=18) && ( roll >= 9 && roll <= 11) )
                            controllerScene.getPositView().setText("POINTS FOWARD, VERTICAL");
                        else if (( yaw >= 12 && yaw <= 15 ) && (pitch >=7 && pitch <=9) )
                            controllerScene.getPositView().setText("POINTS LEFT, HORIZONTAL");
                        else if (( yaw >= 12 && yaw <= 15 ) && (pitch >=9 && pitch <=13) )
                            controllerScene.getPositView().setText("POINTS LEFT, LEANING");
                        else if (( yaw >= 12 && yaw <= 15 ) && (pitch >=13 && pitch <=18) )
                            controllerScene.getPositView().setText("POINTS LEFT, VERTICAL");
                        else if (pitch >=0 && pitch <= 3)
                            controllerScene.getPositView().setText("POINTS DOWN");
                    }
            );
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
