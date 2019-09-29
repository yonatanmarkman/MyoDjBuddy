package com.thalmic.myo;

import com.thalmic.myo.Threads.*;
import com.thalmic.myo.myProg.DataCollector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import static java.lang.Thread.sleep;

import javafx.stage.Modality;
import javafx.stage.Stage;
import kuusisto.tinysound.TinySound;
import kuusisto.tinysound.Music;

import java.io.IOException;

public class Controller {
    private DataCollector myoAccess;
    private static ThreadMyoData thread1;
    private static ThreadSceneUpdater thread2;
    private static ThreadBeatListener thread3;
    public static void EndThreads()
    {
        if (thread1!= null)
            thread1.set_shutdown(true);
        if (thread2!= null)
            thread2.set_shutdown(true);
        if (thread3!= null)
            thread3.set_shutdown(true);
        return;
    }

    // Lightshow variables

    // Music files
    //private Music song1;

    private Music bass1;
    private Music bass2;
    private Music bass3;
    private Music bass4;
    private Music bass5;
    private Music bass6;
    private Music bass7;
    private Music bass8;
    private Music bass9;

    private Music drum1;
    private Music drum2;
    private Music drum3;
    private Music drum4;
    private Music drum5;
    private Music drum6;
    private Music drum7;
    private Music drum8;
    private Music drum9;

    private Music currBass;
    private Music currDrum;

    // Return the specified bass music file.
    public Music getBass(int i)
    {
        switch (i)
        {
            case 1:
                return bass1;
            case 2:
                return bass2;
            case 3:
                return bass3;
            case 4:
                return bass4;
            case 5:
                return bass5;
            case 6:
                return bass6;
            case 7:
                return bass7;
            case 8:
                return bass8;
            case 9:
                return bass9;
            default:
                System.out.println("Unrecognizable track number.");
                return null;
        }
    }
    public Music getDrum(int i)
    {
        switch (i)
        {
            case 1:
                return drum1;
            case 2:
                return drum2;
            case 3:
                return drum3;
            case 4:
                return drum4;
            case 5:
                return drum5;
            case 6:
                return drum6;
            case 7:
                return drum7;
            case 8:
                return drum8;
            case 9:
                return drum9;
            default:
                System.out.println("Unrecognizable track number.");
                return null;
        }
    }

    @FXML
    private Text Logo;

    @FXML
    private Label pitchView;

    @FXML
    private Label poseView;

    @FXML
    private Label rollView;

    @FXML
    private Label yawView;

    @FXML
    private Label positView;

    @FXML
    private Text LT_bl;

    @FXML
    private Text LT_bm;

    @FXML
    private Text LT_br;

    @FXML
    private Text LT_ml;

    @FXML
    private Text LT_mm;

    @FXML
    private Text LT_mr;

    @FXML
    private Text LT_tl;

    @FXML
    private Text LT_tm;

    @FXML
    private Text LT_tr;

    private Text[] lightArray = new Text[9];

    // Light up a single LT (Light), according to the current arm positioning.
    public void lightshow_single(String posit)
    {
        Text LightON = LT_bl;

        switch (posit)
        {
            case "POINTS RIGHT, HORIZONTAL":
                LightON = LT_br;
                break;
            case "POINTS RIGHT, LEANING":
                LightON = LT_mr;
                break;
            case "POINTS RIGHT, VERTICAL":
                LightON = LT_tr;
                break;
            case "POINTS FOWARD, HORIZONTAL":
                LightON = LT_bm;
                break;
            case "POINTS FOWARD, LEANING":
                LightON = LT_mm;
                break;
            case "POINTS FOWARD, VERTICAL":
                LightON = LT_tm;
                break;
            case "POINTS LEFT, HORIZONTAL":
                LightON = LT_bl;
                break;
            case "POINTS LEFT, LEANING":
                LightON = LT_ml;
                break;
            case "POINTS LEFT, VERTICAL":
                LightON = LT_tl;
                break;
            case "POINTS DOWN": // Turn off lights and return
                for (Text light: lightArray)
                {
                    light.setOpacity(0.2);
                }
                return;
        }

        for (Text light: lightArray)
        {
            if (light == LightON)
                light.setOpacity(1);
            else
                light.setOpacity(0.2);
        }


        return;
    }

    // Light up the entire LT array, according to the current arm pitch.
    public void lightshow_intensity(double pitch)
    {
        double intensity = 0.0;
        if (pitch>= 0 && pitch <= 6 )
            intensity = 0.0;
        else if (pitch>= 6 && pitch <= 7)
            intensity = 0.1;
        else if (pitch>= 7 && pitch <= 8)
            intensity = 0.2;
        else if (pitch>= 8 && pitch <= 9)
            intensity = 0.3;
        else if (pitch>=9 && pitch <= 10)
            intensity = 0.4;
        else if (pitch>= 10 && pitch <= 11)
            intensity = 0.5;
        else if (pitch>= 11 && pitch <= 12)
            intensity = 0.6;
        else if (pitch>= 12 && pitch <= 13)
            intensity = 0.7;
        else if (pitch>= 13 && pitch <= 14)
            intensity = 0.8;
        else if (pitch>= 14 && pitch <= 15)
            intensity = 0.9;
        else if (pitch>= 15 && pitch <= 16)
            intensity = 0.95;
        else if (pitch>= 16 && pitch <= 17)
            intensity = 0.97;
        else if (pitch>= 17 && pitch <= 18)
            intensity = 1;

        for (Text light: lightArray)
        {
            light.setOpacity(intensity);
        }

        return;
    }

    public Label getYawView(){
        return yawView;
    }

    public Label getRollView(){
        return rollView;
    }

    public Label getPitchView(){
        return pitchView;
    }

    public Label getPoseView() { return poseView; }

    public Label getPositView() { return positView; }

    // 'playBass' and 'playDrum' are functions called by the "Beat Listener" thread in order to add beats to the track.
    public void playBass(Music bass)
    {
        Music old = currBass;
        currBass = bass;

        addBass(old,currBass);
        try {
            sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void playDrum (Music drum)
    {
        Music old = currDrum;
        currDrum = drum;

        addDrum(old,currDrum);
        try {
            sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // This function adds a new Bass beat, while trying to play it in a synchronized fashion with the other beats.
    // Parameters: 'old' is the older Bass track, 'curBass' is the new one.
    private void addBass(Music old, Music currBass)
    {
        if ((currBass == old) && (currBass!=null))
            return;
        if(old!= null && old.playing()) // There's another bass beat playing.
        {
            if(currDrum!= null && currDrum.playing()) // There's a drum beat playing.
            {
                old.setLoop(false);
                threadAddition addBeat = new threadAddition(currDrum,currBass);
                new Thread(addBeat).start();
                while (old.playing())
                {
                    try {
                        sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                old.rewind();
            }
            else // There's no drum beat in the background. Simply replace.
            {
                threadReplacement replaceBeat = new threadReplacement(old,currBass);
                new Thread(replaceBeat).start();
            }
        }
        else if (currDrum!= null && currDrum.playing())  // There's no bass Beat, but there's a drum beat in the background.
        {
            threadAddition addBeat = new threadAddition(currDrum,currBass);
            new Thread(addBeat).start();
        }
        else
        {
            currBass.play(true);
        }
    }

    // Similar to the addBass function, except with Drum beats.
    private void addDrum(Music old, Music currDrum)
    {
        if ((currDrum == old) && (currDrum!=null) )
            return;
        if(old!= null)
        {
            if (currBass!= null && currBass.playing())
            {
                old.setLoop(false);
                threadAddition addBeat = new threadAddition(currBass,currDrum);
                new Thread(addBeat).start();
                while (old.playing())
                {
                    try {
                        sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                old.rewind();
            }
            else
            {
                threadReplacement replaceBeat = new threadReplacement(old,currDrum);
                new Thread(replaceBeat).start();
            }

        }
        else if (currBass!= null && currBass.playing())
        {
            threadAddition addBeat = new threadAddition(currBass,currDrum);
            new Thread(addBeat).start();
        }
        else
        {
            currDrum.play(true);
        }
    }

    // Launch help window, to explain usage of the program.
    @FXML
    void help_pushed(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("com/thalmic/myo/help.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Help");
            stage.setScene(new Scene(root,  600, 500));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


//    @FXML
//    public void music_press() {
//        if (song1.playing())
//            return;
//        song1.play(false);
//    }
//
//    @FXML
//    public void stopMusic_pressed(ActionEvent event) {
//        song1.stop();
//    }

    @FXML
    public void stopBass_pressed() {
        if (currBass == null)
            return;
        currBass.stop();
        currBass = null;

    }

    @FXML
    public void stopDrum_pressed() {
        if (currDrum == null)
            return;
        currDrum.stop();
        currDrum = null;
    }



    @FXML
    private Button startButton;

    @FXML
    private Button help_btn;

    //function launched by the "Activate Myo Detection" button being pressed.
    @FXML // Activate myo updates in this scene's window.
    void activateUpdates(MouseEvent event) {
        System.out.println("Button touched. Start displaying MYO stats...");
        thread1 = new ThreadMyoData(myoAccess); // Set up and start a thread that listens to Myo and saves it's data. Uses a reference to the Myo's Data collector.
        new Thread(thread1).start();
        thread2 = new ThreadSceneUpdater(this,thread1); // Set up and start a thread which looks at the Myo's data and updates the GUI scene accordingly. Uses a reference to this controller and the 'ThreadMyodata' thread.
        new Thread(thread2).start();
        thread3 = new ThreadBeatListener(this); // Set up and start a thread that plays beats and manipulates the lightshow according to the Myo pose + gestures that are on the GUI.  Uses a reference to this controller.
        new Thread(thread3).start();
        startButton.setDisable(true);
    }

    // Function launched when GUI starts.
    // To activate the main part of the program, press the "Activate Myo Detection" button. Launches the "activateUpdate" function.
    @FXML
    public void initialize() {
        System.out.println("Program started!");
        TinySound.init();
        //Setting up the various sound files...
        //song1 = TinySound.loadMusic("beatsNmusic/music1.wav");

        bass1 = TinySound.loadMusic("beatsNmusic/bass1.wav");
        bass2 = TinySound.loadMusic("beatsNmusic/bass2.wav");
        bass3 = TinySound.loadMusic("beatsNmusic/bass3.wav");
        bass4 = TinySound.loadMusic("beatsNmusic/bass4.wav");
        bass5 = TinySound.loadMusic("beatsNmusic/bass5.wav");
        bass6 = TinySound.loadMusic("beatsNmusic/bass6.wav");
        bass7 = TinySound.loadMusic("beatsNmusic/bass7.wav");
        bass8 = TinySound.loadMusic("beatsNmusic/bass8.wav");
        bass9 = TinySound.loadMusic("beatsNmusic/bass9.wav");

        drum1 = TinySound.loadMusic("beatsNmusic/drum1.wav");
        drum2 = TinySound.loadMusic("beatsNmusic/drum2.wav");
        drum3 = TinySound.loadMusic("beatsNmusic/drum3.wav");
        drum4 = TinySound.loadMusic("beatsNmusic/drum4.wav");
        drum5 = TinySound.loadMusic("beatsNmusic/drum5.wav");
        drum6 = TinySound.loadMusic("beatsNmusic/drum6.wav");
        drum7 = TinySound.loadMusic("beatsNmusic/drum7.wav");
        drum8 = TinySound.loadMusic("beatsNmusic/drum8.wav");
        drum9 = TinySound.loadMusic("beatsNmusic/drum9.wav");

        //lightArray = {LT_bl,LT_br,LT_bm,LT_ml,LT_mm,LT_mr,LT_tl,LT_tm,LT_tr};
        lightArray[0] = LT_bl;
        lightArray[1] = LT_bm;
        lightArray[2] = LT_br;
        lightArray[3] = LT_ml;
        lightArray[4] = LT_mm;
        lightArray[5] = LT_mr;
        lightArray[6] = LT_tl;
        lightArray[7] = LT_tm;
        lightArray[8] = LT_tr;

        // 'currBass\currDrum' are used to detect which beats are currently playing.
        currBass = null;
        currDrum = null;

    }

    // Used to give this scene controller access to the Myo. Used from 'Main.java'.
    public void setTemp(DataCollector temp){
        this.myoAccess = temp;
    }


}
