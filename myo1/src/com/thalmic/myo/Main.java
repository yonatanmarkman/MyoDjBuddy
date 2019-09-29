package com.thalmic.myo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.thalmic.myo.myProg.DataCollector;

import com.thalmic.myo.Threads.*;
import kuusisto.tinysound.TinySound;

public class Main extends Application {

    private Controller appControler;
    private Controller controller;
    private ThreadHub threadhub1;

    @Override
    public void start(Stage primaryStage) throws Exception{

        // ** MYO SETUP ** - START
        DataCollector temp =  null;
        try {
            Hub hub = new Hub("com.example.hello-myo");

            System.out.println("Attempting to find a Myo...");
            Myo myo = hub.waitForMyo(10000);

            if (myo == null) {
                throw new RuntimeException("Unable to find a Myo!");
            }

            System.out.println("Connected to a Myo armband!");
            DeviceListener dataCollector = new DataCollector();
            temp = (DataCollector) dataCollector; // Use this to use functions you add to the 'dataCollector' object.
            hub.addListener(dataCollector);

            // Set the hub on a continous listening loop, in a seperate thread.
            threadhub1 = new ThreadHub(hub);
            new Thread(threadhub1).start();

        } catch (Exception e) {
            System.err.println("Error: ");
            e.printStackTrace();
            System.exit(1);
        }
        //** MYO SETUP ** -- END

        //JAVAFX SETUP - START
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gfx.fxml"));
        Parent root = loader.load();

        //THE FOLLOWING 2 LINES ARE USED TO SEND THE MYO DATA-COLLECTOR "TEMP" TO THE CONTROLLER.
        controller = loader.<Controller>getController(); // Set up a reference to the javafx scene "Controller".
        controller.setTemp(temp); // Give it the necassery objects. Specifically, a way to communicate with the MYO band.

        final Scene scene = new Scene(root, 429, 434);
        scene.setFill(null);
        System.out.println(" "+this.getClass()+" "+this.getClass().getResource("/cssStyles/style.css"));
        scene.getStylesheets().add(this.getClass().getResource("/cssStyles/style.css").toExternalForm());
        primaryStage.setTitle("Myo tracker");
        primaryStage.setScene(scene);
        primaryStage.show(); // Launches the GUI.  Activates functions "Initialize" from Controller.
    }


    public static void main(String[] args) {
        launch(args);
    }

    // This function is activated automatically when all of the program's windows are closed.
    // Shuts down all the various threads that were activated.
    @Override
    public void stop(){
        System.out.println("Stage is closing");
        TinySound.shutdown(); // End usage of Tinysound library
        threadhub1.set_shutdown(true); // Terminate Hub thread
        Controller.EndThreads(); // Terminate GUI threads
    }
}
