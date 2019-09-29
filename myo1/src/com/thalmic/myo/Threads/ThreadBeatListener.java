package com.thalmic.myo.Threads;

import com.thalmic.myo.Controller;

import static java.lang.Thread.sleep;

//This thread is responsible for looking at the values currently displayed in the program window, and plays beats accordingly.
// You can use them via the WAVE_IN, WAVE_OUT, and DOUBLE_TAP gestures.
// The thread is also in charge of the Lightshow aspect of the program. You can activate it via the FINGER_SPREAD and FIST gestures.
// This class needs the following parameter to be instantiated: A reference to the main GUI ('controllerScene').
public class ThreadBeatListener implements Runnable {
    private Controller controllerScene;
    private boolean shutdown;

    public synchronized void set_shutdown(boolean x)
    {
        shutdown = x;
    }
    public synchronized boolean get_shutdown()
    {
        return shutdown;
    }

    public ThreadBeatListener(Controller ctrlr)
    {
        controllerScene = ctrlr;
    }


    public void run()
    {
        Double yaw;
        Double pitch;
        Double roll;
        String pose;
        String posit;
        while(true)
        {
            if (get_shutdown() == true)
            {
                System.out.println("Thread ended!");
                break;
            }
            yaw = Double.parseDouble(controllerScene.getYawView().getText());
            pitch = Double.parseDouble(controllerScene.getPitchView().getText());
            roll = Double.parseDouble(controllerScene.getRollView().getText());
            pose =  controllerScene.getPoseView().getText();
            posit = controllerScene.getPositView().getText();

            if( pose.equals("Pose [type=WAVE_OUT]") ) // Waving out with the Myo signals a Bass beat to start playing.
            {
                switch (posit)
                {
                    case "POINTS RIGHT, HORIZONTAL":
                        controllerScene.playBass(controllerScene.getBass(1));
                        break;
                    case "POINTS RIGHT, LEANING":
                        controllerScene.playBass(controllerScene.getBass(2));
                        break;
                    case "POINTS RIGHT, VERTICAL":
                        controllerScene.playBass(controllerScene.getBass(3));
                        break;
                    case "POINTS FOWARD, HORIZONTAL":
                        controllerScene.playBass(controllerScene.getBass(4));
                        break;
                    case "POINTS FOWARD, LEANING":
                        controllerScene.playBass(controllerScene.getBass(5));
                        break;
                    case "POINTS FOWARD, VERTICAL":
                        controllerScene.playBass(controllerScene.getBass(6));
                        break;
                    case "POINTS LEFT, HORIZONTAL":
                        controllerScene.playBass(controllerScene.getBass(7));
                        break;
                    case "POINTS LEFT, LEANING":
                        controllerScene.playBass(controllerScene.getBass(8));
                        break;
                    case "POINTS LEFT, VERTICAL":
                        controllerScene.playBass(controllerScene.getBass(9));
                        break;
                    case "POINTS DOWN": // Turn off lights and return
                        controllerScene.stopBass_pressed();
                }
            }
            else if ( pose.equals("Pose [type=WAVE_IN]") ) // Waving in with the Myo signals a Drum beat to start playing.
            {
                switch (posit)
                {
                    case "POINTS RIGHT, HORIZONTAL":
                        controllerScene.playDrum(controllerScene.getDrum(1));
                        break;
                    case "POINTS RIGHT, LEANING":
                        controllerScene.playDrum(controllerScene.getDrum(2));
                        break;
                    case "POINTS RIGHT, VERTICAL":
                        controllerScene.playDrum(controllerScene.getDrum(3));
                        break;
                    case "POINTS FOWARD, HORIZONTAL":
                        controllerScene.playDrum(controllerScene.getDrum(4));
                        break;
                    case "POINTS FOWARD, LEANING":
                        controllerScene.playDrum(controllerScene.getDrum(5));
                        break;
                    case "POINTS FOWARD, VERTICAL":
                        controllerScene.playDrum(controllerScene.getDrum(6));
                        break;
                    case "POINTS LEFT, HORIZONTAL":
                        controllerScene.playDrum(controllerScene.getDrum(7));
                        break;
                    case "POINTS LEFT, LEANING":
                        controllerScene.playDrum(controllerScene.getDrum(8));
                        break;
                    case "POINTS LEFT, VERTICAL":
                        controllerScene.playDrum(controllerScene.getDrum(9));
                        break;
                    case "POINTS DOWN": // Turn off lights and return
                        controllerScene.stopDrum_pressed();
                }
            }
            else if ( pose.equals("Pose [type=DOUBLE_TAP]") ) // Doing the double-tap gesture adds a continuous Bass note if arm is horizontal\leaning, or stops all sounds if arm points down.
            {
                if (posit.equals("POINTS DOWN"))
                {
                    controllerScene.stopDrum_pressed();
                    controllerScene.stopBass_pressed();
                }
            }
            else  if (pose.equals("Pose [type=FINGERS_SPREAD]")) // Doing the fist gesture allows you to control the "lightshow" portion of the program.
            {
                controllerScene.lightshow_single(posit);
            }
            else if (pose.equals("Pose [type=FIST]"))
            {
                controllerScene.lightshow_intensity(pitch);
            }

        }
    }
}
